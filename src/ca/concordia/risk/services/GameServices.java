package ca.concordia.risk.services;


import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;


/**
 * This class includes various services needed for playing the game.
 * @author Dhruv
 */
public class GameServices{
	
	private static GameServices instance=null;
	private Map map;
	
	
	/**
	 * Constructor
	 */
	public GameServices() {
		map=Map.getM_instance();
	}
	
	/**
	 * Gets the instance single of GameServices class.
	 * @return instance of GameServices
	 */
	public static GameServices getInstance() {
		if (instance == null)
			instance = new GameServices();
		return instance;
	}
	
	
	/**
	 * This method gets the map object
	 * @return the map object
	 */
	public Map getMap() {
		return map;
	}

	
	/**
	 * This method  sets the map object
	 * @param map the map object to be set
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	
	/**
	 * This method checks if 2 countries are connected
	 * @param c1 the country object1
	 * @param c2 the country object2
	 * @return true, if countries are connected
	 */
	public boolean isConnected(Country c1, Country c2)
	{
		return isConnected(c1, c2, new ArrayList<Country>());
	}
	
	
	/**
	 * This method checks if 2 countries are connected
	 * @param c1 the country object1
	 * @param c2 the country object2
	 * @param countries array list of countries
	 * @return true, if countries are connected
	 */
	private boolean isConnected(Country c1, Country c2, List<Country> countries) {
		if (isNeighbour(c1, c2))
			return true;

		if (countries == null)
			countries = new ArrayList<>();
		else if (countries.contains(c1))
			return false;
		countries.add(c1);

		for (Country co : map.getNeighbourCountries(c1)) 
		{
			if(!countries.contains(co) && isConnected(co, c2, countries))
				return true;
		}
		return false;
	}
	
	
	/**
	 * Checks if countries are in neighbour
	 * @param c1 the country object1
	 * @param c2 the country object2
	 * @return true, if is neighbour
	 */
	public boolean isNeighbour(Country c1, Country c2) 
	{
		if(map.getNeighbourCountries(c1).contains(c2))
		{
			return true;
		}
		return false;
	}
	

	
	
}