package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;

public class BenevolentStrategy {
	static MainClass mainClassInstance = MainClass.getM_instance();

	public static void BenevolentStrategyReinforcement(Player p) {
		if (!p.hasMoreThanFiveCards()) {
			Country weakest = p.getWeakestCountry();
			int reinforceArmy = p.getPlayerReinforceArmy();
			p.reinforceArmy(weakest.getCountryName(), reinforceArmy);
			BenevolentStrategyAttack(p);
		} else {
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
			BenevolentStrategyReinforcement(p);
		}
	}

	private static void BenevolentStrategyAttack(Player p) {
		BenevolentStrategyFortify(p);

	}

	private static void BenevolentStrategyFortify(Player p) {

		Country from = p.getStrongestCountry();
		Country to = p.getWeakestCountry();

	
		System.out.println("Fortification move : "+from.getCountryName() +" "+ to.getCountryName());
		p.fortify(from, to, from.getCountryArmy() - 1);
		mainClassInstance.nextTurn(p);

	}

}
