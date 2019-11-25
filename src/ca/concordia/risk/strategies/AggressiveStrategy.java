package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;

/**
 * This class implements the aggressive strategy wherein it will always focus on
 * attacking until it can no longer attack
 * 
 * @author Pranal
 *
 */
public class AggressiveStrategy {

	public static void AggresiveStrategyReinforcement(Player p) {
		if (!p.hasMoreThanFiveCards()) {
			Country strongest = p.getStrongestCountry();
			int reinforceArmy = p.getPlayerReinforceArmy();
			p.reinforceArmy(strongest.getCountryName(), reinforceArmy);
			AggressiveStrategyAttack(p);
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
			AggresiveStrategyReinforcement(p);
		}
		
	}

	public static void AggressiveStrategyAttack(Player p) {
		Country strongest = p.getStrongestCountry();

		if (strongest != null) {
			if (!p.attackableCountries(strongest).isEmpty()) {

				Country defendingCountry = p.attackableCountries(strongest).get(0);
				MainClass.getM_instance().alloutAttack(strongest, defendingCountry, p,
						MainClass.getM_instance().playerList.get(defendingCountry.getCountryOwner() - 1));
				p.setAttackResult(MainClass.getM_instance().attackResult(strongest, defendingCountry, p));
			}
		}
		AggressiveStrategyFortify(p);
	}

	public static void AggressiveStrategyFortify(Player p) {
		List<Country> countryConquered = p.getPlayerCountries();
		List<Country> countryFromList = new ArrayList<>();
		Country to = p.getStrongestCountry();

		for (Country c : countryConquered) {
			if (c.getCountryArmy() > 1 && c != to) {
				countryFromList.add(c);
			}
		}

		int maxArmy = 0;
		Country from = null;
		boolean flag = false;
		for (Country country : countryFromList) {
			flag = MainClass.main_instance.checkNeighbours(country, to, p.getPlayerId());
			if (flag) {

				int playerArmy = country.getCountryArmy();

				if (playerArmy > maxArmy) {
					maxArmy = playerArmy;
					from = country;
				}
			}
		}

		p.fortify(from, to, from.getCountryArmy() - 1);
		MainClass.getM_instance().nextTurn(p);

	}

}
