package ca.concordia.risk.controller;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.utilities.ValidMapException;

/**
 * This class contains all map operations
 * @author dhruv
 *
 */
public class MapOperations{
	
	static int country_num=1;
	/**
	 * This method adds continent to map
	 * @param map current map object
	 * @param continents the list of continents
	 * @param continent_name the name of the continent
	 * @param control_val the control value of continent
	 * @param color the color of continent
	 * @return boolean value true if country is added else returns false
	 * @throws ValidMapException throws an exception if map is invalid
	 */
	public boolean addContinent(Map map, List<Continent> continents, String continent_name, int control_val, String color) throws ValidMapException 
	{
		Continent con= new Continent(continent_name,control_val,color);
		
		if(map.getContinents().contains(con))
		{
			throw new ValidMapException("The Continent with name "+ continent_name + "already exists" );
		}
		continents.add(con);
		return true;
	}
	
	
	/**
	 * This method add countries to map.
	 * @param map the map object
	 * @param countries the list of countries
	 * @param country_name the name of the country to be added
	 * @param continent_name the name of continent in which the country needs to be added
	 * @return boolean value true if country is added else returns false
	 * @throws ValidMapException
	 */
	public boolean addCountry(Map map,List<Country> countries, String country_name, String continent_name ) throws ValidMapException
	{
		Random r=new Random();
		int x_co=r.nextInt(600);
		int y_co=r.nextInt(600);
		
		if(map.getContinents().contains(map.getContinentByName(continent_name)))
		{
			int continent_id= map.getContinents().indexOf(map.getContinentByName(continent_name)) + 1;
		
			Country co=new Country(country_num,country_name, continent_id, x_co, y_co);
			country_num++;
			for(Continent cont: map.getContinents())
			{
				if(map.getCountriesByContinent(cont.getContinentName()).contains(co))
				{
					throw new ValidMapException("The Country with same name "+ country_name +" already exist in continent " + cont.getContinentName() +".");
				}
			}
			countries.add(co);
			return true;
		}
		else
		{
			throw new ValidMapException("There is no continent with name:"+continent_name);
		}
	}
	
	
	/**
	 * This method adds neighbours to map
	 * @param map the map object
	 * @param borders the borders list
	 * @param country_name the name of country to which border need to be added
	 * @param neighbour_country_name the name of the country which is the border to given country
	 * @return  true if border is added else false
	 * @throws ValidMapException
	 */
	
	public boolean addNeighbours(Map map,List<String> borders, String country_name, String neighbour_country_name) throws ValidMapException
	{
		int country_id= map.getCountryByName(country_name).getCountryNumber();
		int neighbour_country_id= map.getCountryByName(neighbour_country_name).getCountryNumber();
		
		List<ArrayList<String>> neighboursList=new ArrayList< ArrayList<String>>();
		
		if(map.getCountries().contains(map.getCountryByName(country_name))) 
		{	
			if(map.getCountries().contains(map.getCountryByName(neighbour_country_name)))
			{
				//neighboursList.put(country_id,  );
				
				neighboursList.get(country_id).add(Integer.toString(neighbour_country_id));
				neighboursList.get(neighbour_country_id).add(Integer.toString(country_id));
				map.setBorders(neighboursList);
				return true;
			}
			else
			{
				throw new ValidMapException("The Neighbour Country: "+ neighbour_country_name +" does not exist!");
			}
		}
		else
		{
			throw new ValidMapException("The Country: "+ country_name +" does not exist!" );
		}
		
		
	}
	
}