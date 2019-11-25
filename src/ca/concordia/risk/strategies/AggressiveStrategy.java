package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;

/**
 * This class implements the aggressive strategy wherein it will always focus on attacking 
 * until it can no longer attack
 * @author Pranal
 *
 */
public class AggressiveStrategy {
	
	public static void AggresiveStrategyReinforcement(Player p) 
	{
		Country strongest = p.getStrongestCountry();
		int reinforceArmy = p.getPlayerReinforceArmy();
		p.reinforceArmy(strongest.getCountryName(), reinforceArmy);
	}
	
	public void AggresiveStrategyAttack(Player p)
	{
		Country strongest = p.getStrongestCountry();
		
		if(strongest!=null) {
			if(!p.attackableCountries(strongest).isEmpty()) {
				
				Country defendingCountry = p.attackableCountries(strongest).get(0);
				MainClass.getM_instance().alloutAttack(strongest, defendingCountry, p,
						MainClass.getM_instance().playerList.get(defendingCountry.getCountryOwner()-1));
				p.setAttackResult(MainClass.getM_instance().attackResult(strongest, defendingCountry, p));
			}
		}
	}
	
	public void AggressiveStrategyFortify(Player p) {
		List<Country> countryConquered = p.getPlayerCountries();
		List<Country> countryFromList = new ArrayList<>();
		Country to = p.getStrongestCountry();
		
		for (Country c : countryConquered) {
			if (c.getCountryArmy() > 1 && c!=to) {
				countryFromList.add(c);
			}
		}
		
		int maxArmy = 0;
		Country from = null;
		for (Country country : countryFromList) {
			
			int playerArmy = country.getCountryArmy();
			
			if (playerArmy > maxArmy) {
				maxArmy = playerArmy;
				 from = country;
			}
		}
		
		p.fortify(from, to, from.getCountryArmy()-1);
		
		
		
	}

}
