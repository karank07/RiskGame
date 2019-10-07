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

	int totalArmy;
	String countryName;
	int countryNumber;
	int xCo;
	int yCo;
	int continentID;
	int owner;
	int[] neighbours;

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

	public Country(int countryNumber, String countryName, int continentID, int xCo, int yCo) {
		this.countryName = countryName;
		this.countryNumber = countryNumber;
		this.xCo = xCo;
		this.yCo = yCo;
		this.continentID = continentID;
	}

	String getCountryName() {
		return countryName;
	}

	void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	int getCountryNumber() {
		return countryNumber;
	}

	void setCountryNumber(int countryNumber) {
		this.countryNumber = countryNumber;
	}

	int getXCo() {
		return xCo;
	}

	void setXCo(int xCo) {
		this.xCo = xCo;
	}

	int getYCo() {
		return yCo;
	}

	void setYCo(int yCo) {
		this.yCo = yCo;
	}

	int getContinentID() {
		return continentID;
	}

	void setContinentID(int continentID) {
		this.continentID = continentID;
	}

	int getCountryOwner() {
		return owner;
	}

	void setCountryOwner(int owner) {
		this.owner = owner;
	}

	public String toString() {
		return "" + countryNumber + " " + countryName + " " + continentID + " " + xCo + " " + yCo + " "
				+ Arrays.toString(this.getNeighbours());
	}
}
