package ca.concordia.risk.controller;

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
	
	/*
	 * public List<Integer> roll(Player p,Dice d,int n) {
	 * 
	 * }
	 */
	
}
