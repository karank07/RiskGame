package ca.concordia.risk.utilities;

import java.util.Comparator;

import ca.concordia.risk.model.Country;


/**
 * Class CountryComparator compares two countries 
 * @author dhruv
 */
public class CountryComparator implements Comparator<Country>{

	/**
	 * This method compares two country objects
	 * @param c1 country object 1
	 * @param c2 country object 2
	 * @return integer is returned according to country comparison
	 */

	@Override
	public int compare(Country c1, Country c2) {
		
		int c=-1;
		
		c = c1.getContinentName().compareTo(c2.getContinentName());
		if (c == 0)
		{
	       c = c1.getCountryName().compareTo(c2.getCountryName());
		}
		return c;
	}
}