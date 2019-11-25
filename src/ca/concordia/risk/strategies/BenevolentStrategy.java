package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;

public class BenevolentStrategy {
	public static void BenevolentStrategyReinforcement(Player p) {
		if (!p.hasMoreThanFiveCards()) {
			Country weakest = p.getWeakestCountry();
			int reinforceArmy = p.getPlayerReinforceArmy();
			p.reinforceArmy(weakest.getCountryName(), reinforceArmy);
			BenevolentStrategyAttack(p);
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
			BenevolentStrategyReinforcement(p);
		}
	}

	private static void BenevolentStrategyAttack(Player p) {
		BenevolentStrategyFortify(p);

	}

	private static void BenevolentStrategyFortify(Player p) {

		List<Country> countryConquered = p.getPlayerCountries();
		List<Country> countryFromList = new ArrayList<>();
		Country to = p.getWeakestCountry();

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
