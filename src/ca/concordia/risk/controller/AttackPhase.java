package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Dice;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

public class AttackPhase 
{
	Map map = new Map();
	
	public boolean canAttack(Country from, Country to) {
		boolean canAttack;
		canAttack = map.getNeighbourCountries(from).contains(to) && from.getCountryOwner()!=to.getCountryOwner() 
				&& from.getCountryArmy()>=2 && to.getCountryArmy()>0 ? true:false;
		return canAttack;
	}
	
	/**
	 * check condition for dice decision of attacker
	 * @param num number of dice decided by attacker
	 * @param c the country from where attack is carried out
	 * @return check returns true if dice limit fulfills condition
	 */
	public boolean checkDiceRA(int num, Country c)
	{
		boolean check = true;
		if(num>3)
			check=false;
		else if(num < 1 || num > c.getCountryArmy()-1)
			check=false;
		return check;
	}
	
	/**
	 * check condition for dice decision of defender
	 * @param num number of dice decided by defender
	 * @param c the country attacked
	 * @return returns true if dice limit fulfills condition
	 */
	public boolean checkDiceRD(int num,Country c)
	{
		boolean check = true;
		if(num>2)
			check=false;
		else if(num<1 || num > c.getCountryArmy()-1)
			check=false;
		return check;
	}
	
	/**
	 * 
	 * @param p player who rolls the dice
	 * @param d object of Dice class
	 * @param n number of legal dice rolls
	 * @return list of the result of the rollDice method
	 */
	  public void roll(Player p,int n) 
	  {
		  Dice d = new Dice(n);
		  List<Integer> res = d.rollDice(n, p);
		 
	  }
	  
	  public void attack(Country from, Country to, Player attacker, Player defender) 
	  {
		  //list for result of comparison taki move armies me check ho paye kitne armies move krne hai
		  
		  
		  List<Integer> attackWins = new ArrayList<Integer>();
		  int size = attacker.getDiceResult().size() > defender.getDiceResult().size() ? 
				  attacker.getDiceResult().size() : defender.getDiceResult().size();
		  for(int i=0;i<size;i++)
		  {
			  int a = attacker.getDiceResult().get(i);
			  int d = defender.getDiceResult().get(i);
			  
			  if(a > d)
			  {
				  //defender army minus honi chahiye ///country ke army parameter me kya hona chiye?????////////$$$$$$#%#
				  //defender.remArmies(1);
				  //attackWins.add(1);
				  //moveArmies(attacker,from,to,attackWins);
				  System.out.println("Attacker wins");
			  }
			  else
			  {
				  //attacker army minus honi chhaiye
				  //attacker.remArmies(1);
				  System.out.println("Defender defends");
			  }
		  }
		  
		  //Call method to move armies :parameter a ya d
	  }
	 
	  public void moveArmies(Player p, Country from, Country to, List<Integer> wins)
	  {
		  //check kitne armies min move krne hai
		  //check ek army from me bachi honi hi chahiye
		  //army from se minus
		  //army to ko plus
		  
		  //check puri country oocupy hui ya nai
		  // if yes then player country list se hatao aur dusre me add wala (++++ totalownedcountries me plus minus)
		  //mapping else country.setOwnerArmy()
	  }
	  
	//Continue till attacker says NOATTACK
	//Successful attack pr check kro about continent conquered or not and CARD  
	
}
