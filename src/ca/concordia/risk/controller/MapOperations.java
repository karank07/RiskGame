package ca.concordia.risk.controller;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
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
	 * @param continent_name the name of the continent
	 * @param control_val the control value of continent
	 * @param color the color of continent
	 * @return the continent object that is created
	 * @throws ValidMapException throws an exception if map is invalid
	 */
	public Continent addContinent(Map map, String continent_name, int control_val, String color) throws ValidMapException 
	{
		Continent con= new Continent(continent_name,control_val,color);
		
		if(map.getContinents().contains(con))
		{
			throw new ValidMapException("The Continent with name "+ continent_name + "already exists" );
		}
		
		return con;
	}
	
	
	/**
	 * This method add countries to map.
	 * @param map the map object
	 * @param country_name the name of the country to be added
	 * @param continent_name the name of continent in which the country needs to be added
	 * @return the country object that is created
	 * @throws ValidMapException
	 */
	public Country addCountry(Map map, String country_name, String continent_name ) throws ValidMapException
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
			
			return co;
		}
		else
		{
			throw new ValidMapException("There is no continent with name:"+continent_name);
		}
	}
	
	
	
	public HashMap<Integer, ArrayList<Integer>> addNeighbours(Map map, String country_name, String neighbour_country_name) throws ValidMapException
	{
		int country_id= map.getCountryByName(country_name).getCountryNumber();
		int neighbour_country_id= map.getCountryByName(neighbour_country_name).getCountryNumber();
		
		HashMap<Integer, ArrayList<Integer>> neighboursList=new HashMap<Integer, ArrayList<Integer>>();
		
		if(map.getCountries().contains(map.getCountryByName(country_name))) 
		{	
			if(map.getCountries().contains(map.getCountryByName(neighbour_country_name)))
			{
				//neighboursList.put(country_id,  );
				
				/*neighboursList.get(country_id).add(Integer.toString(neighbour_country_id));
				neighboursList.get(neighbour_country_id).add(Integer.toString(country_id));
				map.setBorders(neighbourList);*/
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