package ca.concordia.risk.strategies;

import java.security.DrbgParameters.NextBytes;
import java.util.Random;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.utilities.GamePhase;

/**
 * @author Rohan
 *
 */
public class RandomStrategy {
	static Random r = new Random();

	/**
	 * @param p This function does the select country and number army to be placed
	 *          in that country randomly.
	 * 
	 *          After that it will call function for doing reinforcement
	 * 
	 */
	public static void RandomStrategyReinforcement(Player p) {
		if (!p.hasMoreThanFiveCards()) {
			// select any random country from playr's country list

			Country country;

			// armies will be assigned at populatecountries function for FIRST time
			// second time armies will be assigned at the end of the fortification phase

			country = p.getPlayerCountries().get(r.nextInt(p.getPlayerCountries().size()));

			String flag = p.reinforceArmy(country.getCountryName(), p.getPlayerReinforceArmy());

			RandomStrategyAttack(p);


		} else {
			// do exchange the cards randomly without UI
			while (p.getPlayerCards().size() >= 5) {
				if (p.getPlayerCards().get(Card.ARTILLERY) == 3) {
					MainClass.getM_instance().exchangeCardsForArmy(p, 3, 0, 0);
				} else if (p.getPlayerCards().get(Card.CAVALRY) == 3) {
					MainClass.getM_instance().exchangeCardsForArmy(p, 0, 3, 0);
				} else if (p.getPlayerCards().get(Card.INFANTRY) == 3) {
					MainClass.getM_instance().exchangeCardsForArmy(p, 0, 0, 3);
				} else if ((p.getPlayerCards().get(Card.ARTILLERY) >= 1) && (p.getPlayerCards().get(Card.CAVALRY) >= 1)
						&& (p.getPlayerCards().get(Card.INFANTRY) >= 1)) {
					MainClass.getM_instance().exchangeCardsForArmy(p, 1, 1, 1);
				}
			}
			RandomStrategyReinforcement(p);
		}

	}

	private static void RandomStrategyAttack(Player p) {
		Country attackerCountry = p.getPlayerCountries().get(r.nextInt(p.getPlayerCountries().size()));
		Country defenderCountry = p.attackableCountries(attackerCountry)
				.get(r.nextInt(p.attackableCountries(attackerCountry).size()));

		int randomTimesAttack = r.nextInt(11) + 1; // Bw. 1 and 10

		for (int i = 0; i < randomTimesAttack; i++) {

			if (defenderCountry.getCountryArmy() <= 0 || attackerCountry.getCountryArmy() <= 1) {
				break;
			}
			MainClass.getM_instance().doAttack(attackerCountry, defenderCountry,
					attackerCountry.getCountryArmy() == 2 ? 1 : attackerCountry.getCountryArmy() == 3 ? 2 : 3, p);

			Player defender = MainClass.getM_instance().playerList.get(defenderCountry.getCountryOwner() - 1);

			MainClass.getM_instance().doDefend(attackerCountry.getCountryArmy() == 1 ? 1 : 2, p, defender,
					attackerCountry, defenderCountry);
		}
		RandomStrategyFortify(p);
	}

	private static void RandomStrategyFortify(Player p) {
		Country fromCountry = p.getPlayerCountries().get(r.nextInt(p.getPlayerTotalCountries()));
		int army = 0;
		while(true) {
			Country toCountry = Map.getM_instance().getNeighbourCountries(fromCountry).get(r.nextInt(Map.getM_instance().getNeighbourCountries(fromCountry).size()));
			army = r.nextInt(fromCountry.getCountryArmy())+1;
			if(MainClass.getM_instance().checkNeighbours(fromCountry, toCountry, p.getPlayerId())) {
				if( fromCountry.getCountryArmy() - army >= 1) {
					break;
				}
			}			
			p.fortify(fromCountry, toCountry, army);
			MainClass.getM_instance().nextTurn(p);
			
		}
	}

}
