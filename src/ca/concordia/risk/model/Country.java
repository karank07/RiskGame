/**
 * Country class is the model class to store the data of Country entity (continent object)
 */
package ca.concordia.risk.model;

import java.util.Arrays;
import java.util.List;

/**
 * This is a model class for Country object containing data members for storing, assigning and retrieving  name, number,
 * co-ordinates, armies and continents.
 * @author Karan
 *
 */
public class Country {
	private ca.concordia.risk.model.Map mapInstance;
	/**
	 * @param countryName - for assigning name to Country
	 */
	private String countryName;
	/**
	 * @param countryNumber - for unique Country Number
	 */
	private int countryNumber;

	/**
	 * @param xCo - x coordinate to plot on map
	 */
	private int xCo;

	/**
	 * @param yCo - y coordinate to plot on map
	 */
	private int yCo;

	/**
	 * @param continentID - identify continent it belongs to
	 */
	private int continentID;

	/**
	 * @param owner - PlayerID of country owner
	 */
	private int owner;

	/**
	 * @param neigbours - list of neighbouring countries
	 */
	private int[] neighbours;

	int countryArmy = 0;

	public int getCountryArmy() {
		return countryArmy;
	}

	public void setCountryArmy(int countryArmy) {
		this.countryArmy = countryArmy;
	}

	/**
	 * Constructor to instantiate Country object
	 * 
	 * @param countryNumber
	 * @param countryName
	 * @param continentID
	 * @param xCo
	 * @param yCo
	 */
	public Country(int countryNumber, String countryName, int continentID, int xCo, int yCo) {
		this.countryName = countryName;
		this.countryNumber = countryNumber;
		this.xCo = xCo;
		this.yCo = yCo;
		this.continentID = continentID;
		
	}
	
	/**
	 * Constructor with 4 parameters
	 * @param countryName the name of country
	 * @param continentID the ID number of continent
	 * @param xCo the xCoordinate of country
	 * @param yCo the yCoordinate of country
	 */
	public Country(String countryName, int continentID, int xCo, int yCo) {
		this.countryName = countryName;
		this.xCo = xCo;
		this.yCo = yCo;
		this.continentID = continentID;
		
	}
	

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the countryNumber
	 */
	public int getCountryNumber() {
		return countryNumber;
	}

	/**
	 * @param countryNumber the countryNumber to set
	 */
	public void setCountryNumber(int countryNumber) {
		this.countryNumber = countryNumber;
	}

	/**
	 * @return the xCo- x coordinate
	 */
	public int getXCo() {
		return xCo;
	}

	/**
	 * @param xCo the xCoordinate to set
	 */
	public void setXCo(int xCo) {
		this.xCo = xCo;
	}

	/**
	 * @return the yCo- y coordinate
	 */
	public int getYCo() {
		return yCo;
	}

	/**
	 * @param yCo the yCoordinate to set
	 */
	public void setYCo(int yCo) {
		this.yCo = yCo;
	}

	/**
	 * @return the continentID
	 */
	public int getContinentID() {
		return continentID;
	}

	/**
	 * @param continentID the continentID to set
	 */
	public void setContinentID(int continentID) {
		this.continentID = continentID;
	}

	/**
	 * @return the countryOwner
	 */
	public int getCountryOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setCountryOwner(int owner) {
		this.owner = owner;
	}

	/**
	 * @return the neighbours
	 */
	public int[] getNeighbours() {
		return neighbours;
	}

	/**
	 * @param neighbours the neighbors to set
	 */
	public void setNeighbours(int[] neighbours) {

		this.neighbours = new int[neighbours.length];
		this.neighbours = neighbours;
	}

	/**
	 * Override toString to print data members of country
	 */
	public String toString() {
		return "" + countryNumber + " " + countryName + " " + continentID + " " + xCo + " " + yCo + " "
				+ Arrays.toString(this.getNeighbours());
	}
	
	/**
	 *@param owner player to be assigned armies
	 *@param armies number of armies to be assigned
	 */
	public void setOwnerArmy(int owner, int armies ) {
		this.owner = owner;
		this.countryArmy = armies;
		
	}
	
	/**
	 * @param addN number of armies to be added
	 *
	 */
	public void addCountryArmies(int addN)
	{
		this.countryArmy+= addN;
	}
	
	/**
	 * @param n armies deducted from total
	 *
	 */
	public void remCountryArmies(int n)
	{
		this.countryArmy-= n;
	}
	
	
	/*
	 * Get the continent name of country
	 * @return returns the continent name of the country.
	 	
	public String getContinentName()							//added by dhruv
	{
		mapInstance = ca.concordia.risk.model.Map.getM_instance();
		List<Continent> continents = mapInstance.getContinents();
		return continents.get(continentID).getContinentName();
	}
	*/
}
