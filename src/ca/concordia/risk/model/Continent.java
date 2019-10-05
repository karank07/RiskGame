/**
 * 
 */
package ca.concordia.risk.model;

/**
 * @author Rohan
 *
 */
public class Continent {
	String continentName;
	String continentColor;
	int controlValue;

	public Continent() {

	}

	public Continent(String continentName, String continentColor, int controlValue) {
		this.continentName = continentName;
		this.continentColor = continentColor;
		this.controlValue = controlValue;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	public String getContinentName() {
		return continentName;
	}

	public void setContinentColor(String continentColor) {
		this.continentColor = continentColor;
	}

	public String getContinentColor() {
		return continentColor;
	}

	public void setContinentControlValue(int controlValue) {
		this.controlValue = controlValue;
	}

	public int getContinentControlValue() {
		return controlValue;
	}

	public String toString() {
		return "" + continentName + " " + controlValue + " " + continentColor;
	}

}
