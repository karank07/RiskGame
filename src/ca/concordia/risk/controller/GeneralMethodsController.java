package ca.concordia.risk.controller;

import java.util.HashMap;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;


public class GeneralMethodsController {
	
	
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
	
	
	public int getCountryNumByName(HashMap<Integer, Country> countries, String countryName)
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
}