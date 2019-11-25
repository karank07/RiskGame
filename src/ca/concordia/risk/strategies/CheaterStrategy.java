package ca.concordia.risk.strategies;

import java.util.List;
import java.util.Random;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

public class CheaterStrategy {
	static MainClass mainClassInstance = MainClass.getM_instance();

	public static void cheaterStrategyReinforcement(Player p) {
		if (!p.hasMoreThanFiveCards()) {
			// doubles the army in all coutnries of player p
			for (Country c : p.getPlayerCountries()) {
				c.setCountryArmy(c.getCountryArmy() * 2);
			}
			cheaterStrategyAttack(p);

		} else {
			// do exchange the cards randomly without UI
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
			cheaterStrategyReinforcement(p);
		}

	}

	public static void cheaterStrategyAttack(Player p) {
		for (int i = 0; i < p.getPlayerCountries().size(); i++) {
			Country currentCountry = p.getPlayerCountries().get(i);
			List<Country> neighboutList = Map.getM_instance().getNeighbourCountries(currentCountry);

			for (Country c : neighboutList) {
				c.setCountryOwner(p.getPlayerId());
			}
		}

		cheaterStrategyFortify(p);
	}

	private static void cheaterStrategyFortify(Player p) {
		// doubles the army in all coutnries of player p

		for (int i = 0; i < p.getPlayerCountries().size(); i++) {
			Country currentCountry = p.getPlayerCountries().get(i);
			List<Country> neighboutList = Map.getM_instance().getNeighbourCountries(currentCountry);
			for (Country c : neighboutList) {
				if (c.getCountryOwner() != p.getPlayerId()) {
					c.setCountryArmy(c.getCountryArmy() * 2);
				}
			}
		}
		mainClassInstance.nextTurn(p);
	}

}
