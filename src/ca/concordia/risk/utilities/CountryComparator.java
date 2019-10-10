package ca.concordia.risk.utilities;

import java.util.Comparator;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;

/**
 * Class CountryComparator for comparing two countries
 * @author dhruv
 */
public class CountryComparator implements Comparator<Country>{

	/**
	 * method for comparing countries
	 * @param c1 ,c2
	 */

	@Override
	/**
	 * This method compares two countries
	 * return c integer 
	 */
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