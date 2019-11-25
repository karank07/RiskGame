package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import ca.concordia.risk.controller.MainClass;

/**
 * This class parses the map file and create countries, territories, continents
 * and neighboring countries.
 * 
 * @author dhruv
 */
public class Map
{
	public static Map m_instance=null;
	
	
	private HashMap<Integer, Country> countries;
	private HashMap<Integer, Continent> continents;
	private HashMap<Integer, ArrayList<Integer>> borders;
	int continentIndex=0;
	/**
	 * Default Constructor
	 */
	private Map()
	{
		countries = new HashMap<Integer, Country>();
		continents = new HashMap<Integer, Continent>();
		borders= new HashMap<Integer, ArrayList<Integer>>();
	}
	
	/*
	 * public void initializeList() { countries.clear(); continents.clear();
	 * for(Country c:MainClass.countries.values()) {
	 * countries.put(c.getCountryNumber(), c); } for(Continent
	 * c:MainClass.continents.values()) { continentIndex++;
	 * continents.put(continentIndex, c); } }
	 */
	/**
	 * Only One instance of Map so Singleton
	 * 
	 * @return the instance of Map class
	 */
	public static Map getM_instance() {
		if (m_instance == null) {
			m_instance = new Map();
		}
		return m_instance;
	}

	public HashMap<Integer, Country> getCountries() {
		return countries;
	}

	public void setCountries(HashMap<Integer, Country> countries) {
		this.countries = countries;
	}

	public HashMap<Integer, Continent> getContinents() {
		return continents;
	}

	public void setContinents(HashMap<Integer, Continent> continents) {
		this.continents = continents;
	}

	public HashMap<Integer, ArrayList<Integer>> getBorders() {
		return borders;
	}
	public void setBorders(HashMap<Integer, ArrayList<Integer>> borders) {
		this.borders = borders;
	}

	/**
	 * gets the country by country name
	 * 
	 * @param cName the name of country to be searched
	 * @return the country object matched with country name
	 */
	public Country getCountryByName(String cName)
	{
		for(Country c: m_instance.getCountries().values())
		{
			if(cName.equalsIgnoreCase(c.getCountryName()))
			{
				return c;
			}
				
		}
		return null;
	}
	
	
	
	/**
	 * gets the continent by continent name
	 * 
	 * @param contName the name of the continent to be searched
	 * @return the continent object matched with the continent name
	 */
	public Continent getContinentByName(String contName)
	{
		for(Continent c: m_instance.getContinents().values())
		{
			if(contName.equalsIgnoreCase(c.getContinentName()))
			{
				return c;
			}
				
		}
		return null;
	}

	/**
	 * gets the countries located in a particular continent
	 * 
	 * @param continentName the name of continent whose countries are to be listed
	 * @return list of countries in given continent name
	 */
	public List<Country> getCountriesByContinent(String continentName)
	{
		int id=0;
		List<Country> countryList = new ArrayList<Country>();
		for(Entry<Integer, Continent> entry : continents.entrySet())
		{
			if(continentName.equalsIgnoreCase(entry.getValue().getContinentName()))
			{
				id=entry.getKey();
				break;
			}
		}
		
		for(Entry<Integer, Country> entry : countries.entrySet())
		{
			if(id==entry.getValue().getContinentID())
			{
				countryList.add(entry.getValue());
			}
		}
		
		return countryList;
	}
			
	

	/**
	 * get the neighboring countries
	 * 
	 * @param country the country object
	 * @return List of Neighboring countries
	 */
	public List<Country> getNeighbourCountries(Country country)
	{
		List<Country> neighbourCountry= new ArrayList<>();
		ArrayList<Integer>temp=borders.get(country.getCountryID());
		System.out.println(borders);
		ArrayList<Country>tempstr=new ArrayList<Country>();
		
		for(int i=0;i<temp.size();i++) {
			tempstr.add(getCountries().get(temp.get(i)));
		}
		return tempstr;
	}
	
	/**
	 * Method to make the sets of countries in each continents - will be use later
	 * to check the 2nd rule of the reinforcement phase.
	 */
	public ArrayList<ArrayList<Country>> getCountriesOfContinent () {
		ArrayList<ArrayList<Country>> set_of_countries;
		ArrayList<Continent> name_of_continents = new ArrayList<Continent>();

		for (Continent c : getContinents().values()) {
			name_of_continents.add(c);
		}
		set_of_countries = new ArrayList<ArrayList<Country>>(getContinents().size());

		for (int i = 0; i < getContinents().size(); i++) {
			ArrayList<Country> temp_country_list = new ArrayList<Country>();
			temp_country_list = (ArrayList<Country>) getCountriesByContinent(name_of_continents.get(i).getContinentName().toString());
			set_of_countries.add(temp_country_list);
		}
		return set_of_countries;

		// printing set of countries
		/*
		 * for(ArrayList<Country> c : set_of_contries) {
		 * System.out.println(c.toString()); }
		 */

	}

	public void resetMap()
	{
		continents.clear();
		countries.clear();
		borders.clear();
	}

}
