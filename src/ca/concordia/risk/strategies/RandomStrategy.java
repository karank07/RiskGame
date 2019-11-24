package ca.concordia.risk.strategies;

import java.util.Random;

import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;

/**
 * @author Rohan
 *
 */
public class RandomStrategy {

	/**
	 * @param p This function does the select country and number army to be placed
	 *          in that country randomly.
	 * 
	 *          After that it will call function for doing reinforcement
	 * 
	 */
	public void RandomStrategyReinforcement(Player p) {
		if (!p.hasMoreThanFiveCards()) {
			// select any random country from playr's country list
			Random r = new Random();
			Country country;

			// armies will be assigned at populatecountries function for FIRST time
			// second time armies will be assigned at the end of the fortification phase

			int armyToPlace = 0;

			// choose random country and random number of reinforcement army from total
			// reinforcement army
			// and do reinforcement till Reinforcement army will become 0
			while (p.getPlayerReinforceArmy() > 0) {
				country = p.getPlayerCountries().get(r.nextInt(p.getPlayerCountries().size()));
				armyToPlace = r.nextInt(p.getPlayerReinforceArmy());
				String flag = p.reinforceArmy(country.getCountryName(), armyToPlace);
			}

		} else {
			// do exchange the cards randomly without UI
		}

	}

}
