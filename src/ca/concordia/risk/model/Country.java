/**
 * 
 */
package ca.concordia.risk.model;

import java.util.Arrays;

/**
 * @author Karan
 *
 */
public class Country {

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
	
	int countryArmy=0;
	
	public int getCountryArmy() {
		return countryArmy;
	}

	public void setCountryArmy(int countryArmy) {
		this.countryArmy = countryArmy;
	}

	/**
	 * Constructor to instantiate Country object
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
	 * @param neighbours the neighbours to set
	 */
	public void setNeighbours(int[] neighbours) {

		this.neighbours = new int[neighbours.length];
		this.neighbours = neighbours;
	}

	/**
	 * 
	 */
	public String toString() {
		return "" + countryNumber + " " + countryName + " " + continentID + " " + xCo + " " + yCo + " "
				+ Arrays.toString(this.getNeighbours());
	}
	
	/**
	 * 
	 */
	public void setOwnerArmy(int owner, int armies ) {
		this.owner = owner;
		this.countryArmy = armies;
		
	}
}
