package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Dice;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

public class AttackPhase {

	Map map = new Map();

	public boolean canAttack(Country from, Country to) {
		boolean canAttack;
		canAttack = map.getNeighbourCountries(from).contains(to) && from.getCountryOwner() != to.getCountryOwner()
				&& from.getCountryArmy() >= 2 && to.getCountryArmy() > 0 ? true : false;
		return canAttack;
	}

	/**
	 * check condition for dice decision of attacker
	 * 
	 * @param num number of dice decided by attacker
	 * @param c   the country from where attack is carried out
	 * @return check returns true if dice limit fulfills condition
	 */
	public boolean checkDiceRA(int num, Country c) {
		boolean check = true;
		if (num > 3)
			check = false;
		else if (num < 1 || num > c.getCountryArmy() - 1)
			check = false;
		return check;
	}

	/**
	 * check condition for dice decision of defender
	 * 
	 * @param num number of dice decided by defender
	 * @param c   the country attacked
	 * @return returns true if dice limit fulfills condition
	 */
	public boolean checkDiceRD(int num, Country c) {
		boolean check = true;
		if (num > 2)
			check = false;
		else if (num < 1 || num > c.getCountryArmy() - 1)
			check = false;
		return check;
	}

	/**
	 * 
	 * @param p player who rolls the dice
	 * @param d object of Dice class
	 * @param n number of legal dice rolls
	 * @return list of the result of the rollDice method
	 */
	public void roll(Player p, int n) {
		Dice d = new Dice(n);
		List<Integer> res = d.rollDice(n, p);
	}

	public String attack(Country from, Country to, Player attacker, Player defender) {

		String resultString = "";
		List<Integer> attackerWins = new ArrayList<Integer>();
		List<Integer> defenderWins = new ArrayList<Integer>();
		int size = attacker.getDiceResult().size() > defender.getDiceResult().size() ? attacker.getDiceResult().size()
				: defender.getDiceResult().size();
		for (int i = 0; i < size; i++) {
			int a = attacker.getDiceResult().get(i);
			int d = defender.getDiceResult().get(i);

			if (a > d) {

				defender.remArmies(1);
				to.setCountryArmy(to.getCountryArmy() - 1);
				attackerWins.add(1);
				attacker.setDiceWins(attackerWins);
				System.out.println("Attacker wins");
			} else {

				attacker.remArmies(1);
				defenderWins.add(1);
				defender.setDiceWins(defenderWins);
				from.setCountryArmy(from.getCountryArmy() - 1);
				System.out.println("Defender defends");
			}
		}

		if (attackerWins.size() == defenderWins.size()) {
			resultString = "DRAW";
		} else {
			resultString = attackerWins.size() > defenderWins.size() ? "ATTACKER WINS" : "DEFENDER WINS";
		}
		System.out.println("attacker :" + attackerWins);
		System.out.println("defender: " + defenderWins);
		System.out.println(resultString);

		return resultString;
	}

	public void moveArmies(Player p, Country from, Country to, int numOfArmies) {
		System.out.println("from army before: "+from.getCountryArmy());
		if (numOfArmies >= p.getDiceWins().size() && (from.getCountryArmy() - numOfArmies) > 1) {
			from.setCountryArmy(from.getCountryArmy() - numOfArmies);
			to.setCountryArmy(to.getCountryArmy() + numOfArmies);
			p.setPlayerTotalCountries(p.getPlayerTotalCountries() + 1);
			p.getPlayerCountries().add(to);
			
//			  if(map.getCountriesByContinent(MainClass.continents.get(to.getContinentID()-1).getContinentName()).equals(p.getPlayerCountries())) {
//				  
//			  }

		}
		System.out.println("from army: "+from.getCountryArmy());
		System.out.println(to.getCountryArmy());

		// CHANGE THE LIST OF COUNTRY IN MAP PLAYER/////

	}

	// Continue till attacker says NOATTACK
	// Successful attack pr check kro about continent conquered or not and CARD

}
