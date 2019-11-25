package ca.concordia.risk.strategies;

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
		
		
	}
	 

}
