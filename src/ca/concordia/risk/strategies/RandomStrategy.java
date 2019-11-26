package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

/**
 * Random strategy that reinforces random a random country, attacks a random
 * number of times a random country, and fortifies a random country, all
 * following the standard rules for each phase.
 * 
 * @author Rohan
 *
 */

public class RandomStrategy {
	static Random r = new Random();
	static MainClass mainClassInstance = MainClass.getM_instance();

	/**
	 * @param p This function does the select country and number army to be placed
	 *          in that country randomly.
	 * 
	 *          After that it will call function for doing reinforcement
	 * 
	 */
	public static void RandomStrategyReinforcement(Player p) {
		if (!p.hasMoreThanFiveCards()) {
			Country country = p.getPlayerCountries().get(r.nextInt(p.getPlayerCountries().size()));

			String flag = p.reinforceArmy(country.getCountryName(), p.getPlayerReinforceArmy());

			RandomStrategyAttack(p);

		} else {
			while (p.getPlayerCards().size() >= 5) {
				if (p.getPlayerCards().get(Card.ARTILLERY) == 3) {
					mainClassInstance.exchangeCardsForArmy(p, 3, 0, 0);
				} else if (p.getPlayerCards().get(Card.CAVALRY) == 3) {
					mainClassInstance.exchangeCardsForArmy(p, 0, 3, 0);
				} else if (p.getPlayerCards().get(Card.INFANTRY) == 3) {
					mainClassInstance.exchangeCardsForArmy(p, 0, 0, 3);
				} else if ((p.getPlayerCards().get(Card.ARTILLERY) >= 1) && (p.getPlayerCards().get(Card.CAVALRY) >= 1)
						&& (p.getPlayerCards().get(Card.INFANTRY) >= 1)) {
					mainClassInstance.exchangeCardsForArmy(p, 1, 1, 1);
				}
			}
			RandomStrategyReinforcement(p);
		}

	}

	private static void RandomStrategyAttack(Player p) {
		List<Country> cList=new ArrayList<Country>();
		for(Country c:mainClassInstance.player_country_map.get(p)) {
			if(c.getCountryArmy()>1) {
				cList.add(c);
				
			}
		}
		
		Country attackerCountry = cList.get(r.nextInt(cList.size()));
		Country defenderCountry = p.attackableCountries(attackerCountry)
				.get(r.nextInt(p.attackableCountries(attackerCountry).size()));
		
		int randomTimesAttack = r.nextInt(11) + 1; // Bw. 1 and 10

		for (int i = 0; i < randomTimesAttack; i++) {

			if (defenderCountry.getCountryArmy() <= 0 || attackerCountry.getCountryArmy() <= 1) {
				break;
			} else {
				int dice = attackerCountry.getCountryArmy() == 2 ? 1 : attackerCountry.getCountryArmy() == 3 ? 2 : 3;
				mainClassInstance.doAttack(attackerCountry, defenderCountry,
						attackerCountry.getCountryArmy() == 2 ? 1 : attackerCountry.getCountryArmy() == 3 ? 2 : 3, p);
				Player defender = mainClassInstance.playerList.get(defenderCountry.getCountryOwner() - 1);

				mainClassInstance.doDefend(defenderCountry.getCountryArmy() == 1 ? 1 : 2, p, defender, attackerCountry,
						defenderCountry);
				p.setAttackResult(mainClassInstance.attackResult(attackerCountry, defenderCountry, p));
				if(p.getAttackResult().equalsIgnoreCase("Attacker won! Country conquered")) {
					mainClassInstance.moveArmies(p, attackerCountry,defenderCountry, p.getAttackResult().length());
				}
			}
		}
		RandomStrategyFortify(p);
	}

	private static void RandomStrategyFortify(Player p) {
		Country fromCountry = p.getPlayerCountries().get(r.nextInt(p.getPlayerTotalCountries()));
		int army = 0;
		//while (true) {
			Country toCountry = Map.getM_instance().getNeighbourCountries(fromCountry)
					.get(r.nextInt(Map.getM_instance().getNeighbourCountries(fromCountry).size()));
			if(fromCountry.getCountryArmy() == 0) {
				army = 1;
			}
			else {
				army = r.nextInt(fromCountry.getCountryArmy()) + 1;
			}
			System.out.println();
			
			System.out.println("Randomly selected From Country: "+ fromCountry.getCountryName() + " to country: "+ toCountry.getCountryName());
			
			if (mainClassInstance.checkNeighbours(fromCountry, toCountry, p.getPlayerId())) { 
				if (fromCountry.getCountryArmy() - army >= 1) {
					//System.out.println("in randomStrategy File: "+ "From country: " + fromCountry.getCountryName() + " to country:"+toCountry.getCountryName() +" player name "+ p.getPlayerName() );
					p.fortify(fromCountry, toCountry, army);
				
				}
			}
			else {
				System.out.println("Fortification not done: not neighbours");
			}
			
			mainClassInstance.nextTurn(p);

		//}
	}

}
