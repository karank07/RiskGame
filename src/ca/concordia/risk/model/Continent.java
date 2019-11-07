/**
 * Continent class is the model class to store the data of Continent entity (continent object)
 */
package ca.concordia.risk.model;

/**
 * @author Rohan
 *
 */
public class Continent {
	private String continentName;
	private String continentColor;
	private int controlValue;
	private int ruler;
	

	/**
	 * @return the ruler
	 */
	public int getRuler() {
		return ruler;
	}

	/**
	 * @param ruler the ruler to set
	 */
	public void setRuler(int ruler) {
		this.ruler = ruler;
	}

	/**
	 * Constructor to instantiate Continent object
	 * @param continentName String 
	 * @param continentColor String 
	 * @param controlValue int 
	 */
	public Continent(String continentName, int controlValue,  String continentColor) {
		this.continentName = continentName;
		this.continentColor = continentColor;
		this.controlValue = controlValue;
	}

	public Continent() {
		
	}

	/**
	 * @param continentName String
	 */
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	/**
	 * @return continentName Stirng
	 */
	public String getContinentName() {
		return continentName;
	}

	/**
	 * @param continentColor
	 */
	public void setContinentColor(String continentColor) {
		this.continentColor = continentColor;
	}

	/**
	 * @return continentColor String
	 */
	public String getContinentColor() {
		return continentColor;
	}

	/**
	 * @param controlValue int to set the control value for each continent
	 */
	public void setContinentControlValue(int controlValue) {
		this.controlValue = controlValue;
	}

	/**
	 * @return controlValue int
	 */
	public int getContinentControlValue() {
		return controlValue;
	}

	/**
	 *Override toString to print continentName, controlValue and continentColor
	 */
	@Override
	public String toString() {
		return "" + continentName + " " + controlValue + " " + continentColor;
	}

}
