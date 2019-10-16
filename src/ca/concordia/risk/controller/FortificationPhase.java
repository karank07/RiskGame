package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Country;

/**
 * This class contains data members such as list of country - visited, fortify(), searchNeighbors(), etc to implement a 
 * valid fortification move according to risk rules
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
	 * @param from country-name from which armies are sent
	 * @param to country-name to which armies are sent
	 * @param owner the player fortifying
	 * @param army number of armies sent
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
	 * @param from country name whose neighbor has to be checked
	 * @param to country name which is checked
	 * @param owner the player owning the given countries
	 * @return adjFlag determines the given countries are neighbors or not
	 */
	public boolean checkNeighbours(Country from, Country to, int owner) {
		adjFlag = false;
		int[] listOfNeighbours = from.getNeighbours();
		visited.clear();
		visited.add(from);
		//System.out.println(visited.toString());
		if (from.getCountryOwner() == to.getCountryOwner()) {
			searchNeighbors(visited, from, to, owner);
		}
		return adjFlag;
	}
	
/**
 * 
 * @param visited the list of countries traversed
 * @param from country 1 to be checked for neighbor
 * @param to country 2 to be checked for neighbor
 * @param owner the player owning countries to be checked
 */

	private void searchNeighbors(List<Country> visited, Country from, Country to, int owner) {
		int[] listOfNeighbours = from.getNeighbours();

		if (!arrayContains(listOfNeighbours, to.getCountryNumber())) {
			visited.add(from);
			for (int i = 0; i < listOfNeighbours.length; i++) {
				if (m.CountryList.get(i).getCountryOwner() == owner) {

					Country mayBecomeFrom = m.CountryList.get(i);
					if (!visited.contains(mayBecomeFrom)) {
						searchNeighbors(visited, mayBecomeFrom, to, owner);
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
	 * @param arr the array to be checked for the key
	 * @param key the key to be found
	 * @return true if the arr contains the key
	 */
	public boolean arrayContains(int[] arr, int key) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == key)
				return true;
		}
		return false;
	}

}
