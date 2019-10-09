package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * This class parses the map file and create countries, territories, continents and neighboring countries.
 * @author dhruv
 */

public class Map
{
	public static Map m_instance;
	
	private static List<Country> countries;
	private static List<Continent> continents;
	private HashMap<Country, ArrayList<String>> territories;
	
	/**
	 * Default Constructor
	 */
	private Map()
	{
		Map.countries = new ArrayList<Country>();
		Map.continents = new ArrayList<Continent>();
		this.territories = new HashMap<Country, ArrayList<String>>();
	}
	
	
	/**
	 * Only One intance of Map so Singleton
	 * @return the instance of Map class
	 */
	public static Map getM_instance() {
		if(m_instance==null)
		{
			m_instance= new Map();	
		}
		return m_instance;
	}

	
	/**
	 * get the list of countries
	 * @return the list of countries
	 */
	public static List<Country> getCountries() {
		return countries;
	}

	
	/**
	 * sets the countries which are passed in the countries list
	 * @param countries the list of countries to set
	 */
	public void setCountries(List<Country> countries) {
		Map.countries = countries;
	}

	
	/**
	 * get the list of continents
	 * @return the list of continents
	 */
	public static List<Continent> getContinents() {
		return continents;
	}

	
	/**
	 * sets the continents which are passed in the continents list
	 * @param continents the list of continents to set
	 */
	public void setContinents(List<Continent> continents) {
		Map.continents = continents;
	}

	
	/**
	 * get the list of territories
	 * @return territories the list of territories
	 */
	public HashMap<Country, ArrayList<String>> getTerritories() {
		return territories;
	}
	
	
	/**
	 * sets the territories
	 * @param territories
	 */
	public void setTerritories(HashMap<Country, ArrayList<String>> territories) {
		this.territories = territories;
	}
	
	
	/**
	 * gets the country by country name
	 * @param cName the name of country to be searched
	 * @return the country object matched with country name
	 */
	public Country getCountryByName(String cName)
	{
		for(Country c: Map.countries)
		{
			if((c.getCountryName()).equals(cName))
			{
				return c;
			}
		}
		return null;
	}
	
	
	/**
	 * gets the continent by continent name
	 * @param contName the name of the continent to be searched
	 * @return the continent object matched with the continent name
	 */
	public Continent getContinentByName(String contName)
	{
		for(Continent cont: Map.continents)
		{
			if((cont.getContinentName()).equals(contName))
			{
				return cont;
			}
		}
		return null;
	}
	
	
	/**
	 * gets the countries located in a particular continent
	 * @param continentName the name of continent whose countries are to be listed
	 * @return list of countries in given continent name
	 */
	public static List<Country> getCountriesByContinent(String continentName)
	{
		List<Country> continentCountry=new ArrayList<>();
		int continentIndex=0;
		
		//get index of continent from continent name
		for(int i=0; i<continents.size() ; i++)
		{
			if((continents.get(i).getContinentName()).equals(continentName))
			{
				continentIndex=i+1;
				break;
			}
		}
		
		//match continent index with continent id and add the matched countries to the array list
		for(Country c: getCountries())
		{
			if(continentIndex==c.getContinentID())
			{
				continentCountry.add(c);
			}
		}
		return continentCountry;
	}
	
	/**
	 * get the neighboring countries
	 * @param country the country object
	 * @return List of Neighboring countries
	 */
	public List<Country> getNeighbourCountries(Country country)
	{
		List<Country> neighbourCountry= new ArrayList<>();
		
		for(String neighbourC: territories.get(country))
		{
			neighbourCountry.add(getCountryByName(neighbourC));
		}
		return neighbourCountry;
	}
	
	
}