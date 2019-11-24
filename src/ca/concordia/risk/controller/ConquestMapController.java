package ca.concordia.risk.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.utilities.GameConstants;


/**
 * This class is used for reading and writing files in conquest game map format
 * @author Dhruv
 *
 */
public class ConquestMapController {

	String continentStart, countryStart, borderStart, border, country;
	String[] continentDetails, countryDetails, borderDetails;
	GeneralMethodsController gmc = new GeneralMethodsController();
	HashMap<Integer, ArrayList<String>> borderNames = new HashMap<Integer, ArrayList<String>>();
	
	
	/**
	 * This method reads the conquest map file
	 * @param continents the hashmap of continents
	 * @param countries the hashmap of countries
	 * @param borders the hashmap of borders
	 * @param fileName the map file to be loaded
	 * @throws FileNotFoundException
	 */
	public void conquestMapReader(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries, HashMap<Integer, ArrayList<Integer>> borders, String fileName) throws FileNotFoundException
	{
		String path = Paths.get("").toAbsolutePath().toString() + "\\maps\\" + fileName;
		File file = new File(path);
		Scanner sc = new Scanner(file);
		
		HashMap<Integer, ArrayList<String>> borderNames = new HashMap<Integer, ArrayList<String>>();
		
		while(sc.hasNext())
		{
			String continent = sc.nextLine();
			
			if(continent.equals(GameConstants.CONTINENT_HEADER))
			{
				break;
			}
		}
		
		int continentCount = 0;
		
		while(sc.hasNext())
		{
			continentCount++;
			continentStart = sc.nextLine();
			
			if(continentStart.length() > 0)
			{
				continentDetails = continentStart.split("=");
				Continent c = new Continent(continentDetails[0], Integer.parseInt(continentDetails[1]) , null);
				continents.put(continentCount, c);
			}
			else
			{
				break;
			}
		}
		
		while(sc.hasNext())
		{
			country = sc.nextLine();
				
			if(country.equals("[Territories]"))
			{
				break;
			}
		}
		
		int countryCount = 0;
		
		while(sc.hasNext())
		{
			countryCount++;
			countryStart = sc.nextLine();
			
			if(countryStart.length() > 0)
			{
				countryDetails = countryStart.split(",");
				Country coun = new Country(countryDetails[0], gmc.getContinentID(continents, countryDetails[3]) , Integer.parseInt(countryDetails[1]),Integer.parseInt(countryDetails[2]));
				countries.put(countryCount, coun);
				
				ArrayList<String> tempList = new ArrayList<String>();
				for(int i=4 ; i<= countryDetails.length ; i++)
				{
					tempList.add(countryDetails[i]);
				}
				
				borderNames.put(countryCount, tempList);
			}
			else
			{
				break;
			}
		}
		
		for(int i : borderNames.keySet())
		{
			ArrayList<String> list = borderNames.get(i);
			ArrayList<Integer> listNumber = new ArrayList<Integer>();
			
			for(String str: list)
			{
				listNumber.add(gmc.getCountryNumByName(countries, str));
			}
			
			borders.put(i, listNumber);
		}
		sc.close();
	}
	
	
	public void conquestMapWriter(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries, HashMap<Integer, ArrayList<Integer>> borders, String mapFile) throws IOException
	{
		
	}
}
