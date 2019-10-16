package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Country;

/**
 * @author Karan
 *
 */
public class FortificationPhase {
	/**
	 * @param countryList- list of all the objects of countries
	 */

	MainClass m;
	boolean adjFlag = false;
	List<Country> visited = new ArrayList<Country>();

	/**
	 * to instantiate object
	 */
	public FortificationPhase() {

	}

	/**
	 * fortification-move armies to another country that belongs to the player
	 * 
	 * @param from
	 * @param to
	 * @param owner
	 * @param army
	 */
	public void fortify(Country from, Country to, int owner, int army) {

		adjFlag = checkNeighbours(from, to, owner);
		System.out.println("player "+owner);
		if (adjFlag) {
			if (from.getCountryArmy() - army >= 1) {
				from.setCountryArmy(from.getCountryArmy() - army);
				to.setCountryArmy(to.getCountryArmy() + army);
				System.out.println("\nFortification successful");
				m.phase="reinforce";

			} else
				System.out.println("There must be atleast one army in a country!");
		} else
			System.out.println("Move not possible");
		System.out.println("Country and Army Count for Player "+owner);
		for (Country c : m.CountryList)
		{
			if(c.getCountryOwner()==owner)
				System.out.println(c.getCountryName() + " " + c.getCountryArmy());
			
		}
				
	}

	/**
	 * to check if two countries are connected and belongs to same owner
	 * 
	 * @param from
	 * @param to
	 * @param owner
	 * @return adjFlag
	 */
	public boolean checkNeighbours(Country from, Country to, int owner) {
		adjFlag = false;
		int[] listOfNeighbours = from.getNeighbours();
		visited.clear();
		visited.add(from);
		//System.out.println(visited.toString());
		if (from.getCountryOwner() == to.getCountryOwner()) {
			dfs(visited, from, to, owner);
		}
		return adjFlag;
	}

	private void dfs(List<Country> visited, Country from, Country to, int owner) {
		int[] listOfNeighbours = from.getNeighbours();

		if (!arrayContains(listOfNeighbours, to.getCountryNumber())) {
			visited.add(from);
			for (int i = 0; i < listOfNeighbours.length; i++) {
				if (m.CountryList.get(i).getCountryOwner() == owner) {

					Country mayBecomeFrom = m.CountryList.get(i);
					if (!visited.contains(mayBecomeFrom)) {
						dfs(visited, mayBecomeFrom, to, owner);
					}
				}
			}
		} else {
			adjFlag = true;
		}
	}

	/**
	 * To check if array contains the key
	 * 
	 * @param arr
	 * @param key
	 * @return
	 */
	public boolean arrayContains(int[] arr, int key) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == key)
				return true;
		}
		return false;
	}

}
