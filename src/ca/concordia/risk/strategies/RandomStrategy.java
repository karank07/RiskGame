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
		if (p.getPlayerCards().size() >= 5) {
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

		Country country = p.getPlayerCountries().get(r.nextInt(mainClassInstance.player_country_map.get(p).size()));

		String flag = p.reinforceArmy(country.getCountryName(), p.getPlayerReinforceArmy());

		RandomStrategyAttack(p);

	}

	private static void RandomStrategyAttack(Player p) {

		System.out.println(
				"PLAYER COUNTRY MAP SIZE BEFORE ATTACK : " + mainClassInstance.player_country_map.get(p).size());

		List<Country> cList = new ArrayList<Country>();
		for (Country c : mainClassInstance.player_country_map.get(p)) {
			if (c.getCountryArmy() > 1) {

				cList.add(c);

			}
		}

		Country attackerCountry = cList.get(r.nextInt(cList.size()));
		Country defenderCountry = null;

//		while (p.attackableCountries(attackerCountry).size() == 0) {
//			attackerCountry = cList.get(r.nextInt(cList.size()));
//			if (p.attackableCountries(attackerCountry).size() != 0) {
//				break;
//			}
//		}
		System.out.println("In RANDOM : ATTACKABLE COUNTRY SIZE: " + p.attackableCountries(attackerCountry).size());

		if (p.attackableCountries(attackerCountry).size() != 0) {

			defenderCountry = p.attackableCountries(attackerCountry)
					.get(r.nextInt(p.attackableCountries(attackerCountry).size()));
		} 
			
		int randomTimesAttack = r.nextInt(5) + 1; // Bw. 1 and 10

		for (int i = 0; i < randomTimesAttack; i++) {
			
			while(attackerCountry.getCountryArmy() <= 1) {
				attackerCountry = cList.get(r.nextInt(cList.size()));
			} 
				int dice = attackerCountry.getCountryArmy() == 2 ? 1 : attackerCountry.getCountryArmy() == 3 ? 2 : 3;
				mainClassInstance.doAttack(attackerCountry, defenderCountry, dice, p);
				Player defender = MainClass.playerList.get(defenderCountry.getCountryOwner() - 1);

				mainClassInstance.doDefend(defenderCountry.getCountryArmy() == 1 ? 1 : 2, p, defender, attackerCountry,
						defenderCountry);
				p.setAttackResult(mainClassInstance.attackResult(attackerCountry, defenderCountry, p));
				if (p.getAttackResult().equalsIgnoreCase("Attacker won! Country conquered")) {
					mainClassInstance.moveArmies(p, attackerCountry, defenderCountry, p.getDiceWins().size());
				}
//				dice = defenderCountry.getCountryArmy() == 1 ? 1 : 2;
//				mainClassInstance.doDefend(dice, p, defender, attackerCountry, defenderCountry);

			
		}
		RandomStrategyFortify(p);
	}

	private static void RandomStrategyFortify(Player p) {
		Country fromCountry = p.getPlayerCountries().get(r.nextInt(mainClassInstance.player_country_map.get(p).size()));
//		System.out.println("Trying to fortify...");
//		System.out.println("Player country map size: "+ mainClassInstance.player_country_map.get(p).size());
//		int temp = r.nextInt(mainClassInstance.player_country_map.get(p).size());

		// System.out.println("From COuntry: " + fromCountry.getCountryName());
		Country toCountry = null;
		int army = 0;
		while (true) {
			// System.out.println("in while");
			toCountry = Map.getM_instance().getNeighbourCountries(fromCountry)
					.get(r.nextInt(Map.getM_instance().getNeighbourCountries(fromCountry).size()));

			if (toCountry.getCountryOwner() == p.getPlayerId()) {
				break;
			} else {
				fromCountry = mainClassInstance.player_country_map.get(p)
						.get(r.nextInt(mainClassInstance.player_country_map.get(p).size()));
			}
		}
		System.out.println("To country: " + toCountry.getCountryName());
		if (fromCountry.getCountryArmy() > 0) {
			army = r.nextInt(fromCountry.getCountryArmy()) + 1;

		} else {
			army = 1;
		}

		
		//if (mainClassInstance.checkNeighbours(fromCountry, toCountry, p.getPlayerId())) {
			if (fromCountry.getCountryArmy() - 1 >= 1) {
				// System.out.println("in randomStrategy File: "+ "From country: " +
				// fromCountry.getCountryName() + " to country:"+toCountry.getCountryName() +"
				// player name "+ p.getPlayerName() );
				// move 1 army
				p.fortify(fromCountry, toCountry, 1);
			}
//		} else {
//			System.out.println("Fortification not done: not neighbours");
//		}
		mainClassInstance.setNextPlayerTurn();
		p = MainClass.playerList.get(mainClassInstance.getPlayerTurn() - 1);

		mainClassInstance.nextTurn(p);
		// }
	}

}
