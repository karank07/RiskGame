package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;

public class BenevolentStrategy {
	 public void BenevolentStrategyReinforcement(Player p) 
	 {
		 Country weakest = p.getWeakestCountry();
			int reinforceArmy = p.getPlayerReinforceArmy();
			p.reinforceArmy(weakest.getCountryName(), reinforceArmy);
			BenevolentStrategyAttack(p);
	 }

	private void BenevolentStrategyAttack(Player p) 
	{
		BenevolentStrategyFortify(p);
		
	}

	private void BenevolentStrategyFortify(Player p) {
		
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
		boolean flag=false;
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
		
	}
	 

}
