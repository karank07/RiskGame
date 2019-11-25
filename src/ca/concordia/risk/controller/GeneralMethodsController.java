package ca.concordia.risk.controller;

import java.util.HashMap;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;


/**
 * This Class contains common general methods used for map read/write operations
 * @author Dhruv
 */
public class GeneralMethodsController {
	
	/**
	 * This method gets continent id from continent name
	 * @param continents hashmap of continents
	 * @param continentName the name of continent whose id needs to be returned
	 * @return the id of the continent
	 */
	public int getContinentID(HashMap<Integer, Continent> continents, String continentName)
	{
		for (int i : continents.keySet())
		{
			if(continents.get(i).getContinentName().equalsIgnoreCase(continentName))
			{
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * This method is used to get the country ID from country name
	 * @param countries the hashmap of countries
	 * @param countryName the name of the country whose id needs to be found
	 * @return the id of country
	 */
	public int getCountryIdByName(HashMap<Integer, Country> countries, String countryName)
	{
		for (int i : countries.keySet())
		{
			Country coun = countries.get(i);
			if (coun.getCountryName().equalsIgnoreCase(countryName))
			{
				return i;
			}
		}
		return 0;
	}
	
	
	/**
	 * This method is used to get continent object from country name
	 * @param continents the hashmap of continent
	 * @param countries the hashmap of countries
	 * @param countryName the name of the country
	 * @return the continent object
	 */
	public Continent getContinentFromCountryName(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries, String countryName)
	{
		Country c1 = getCountryFromName(countries, countryName);
		int n = c1.getContinentID();
		return continents.get(n);
	}
	
	
	/**
	 * This method is used to get country object form the country name
	 * @param countries the hashmap of country
	 * @param countryName the name of country
	 * @return the country object
	 */
	public Country getCountryFromName(HashMap<Integer, Country> countries, String countryName)
	{
		for(int i : countries.keySet())
		{
			Country coun = countries.get(i);
			if(coun.getCountryName().equalsIgnoreCase(countryName))
			{
				return coun;
			}
		}
		return null;
	}
	
	
	/**
	 * This method is used to get country name from the country ID
	 * @param countries the hashmap of countries
	 * @param countryNum the ID of country
	 * @return the name of the country as string
	 */
	public String getCountryNameById(HashMap<Integer, Country> countries, int countryNum)
	{
		Country coun = countries.get(countryNum);
		
		return coun.getCountryName();
	}
}