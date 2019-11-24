package ca.concordia.risk.strategies;

import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;

/**
 * This class implements the aggressive strategy wherein it will always focus on attacking 
 * until it can no longer attack
 * @author Pranal
 *
 */
public class AggressiveStrategy {
	
	public void AggresiveStrategyReinforcement(Player p) 
	{
		Country strongest = p.getStrongestCountry();
		int reinforceArmy = p.getPlayerReinforceArmy();
		p.reinforceArmy(strongest.getCountryName(), reinforceArmy);
	}
	
	public void AggresiveStrategyAttack(Player p)
	{
		Country strongest = p.getStrongestCountry();
		
	}

}
