package ca.concordia.risk.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
 * @author dhruv
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
				Continent c = new Continent(continentDetails[0], Integer.parseInt(continentDetails[1]) , "null");
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
				listNumber.add(gmc.getCountryIdByName(countries, str));
			}
			
			borders.put(i, listNumber);
		}
		sc.close();
	}
	
	
	/**
	 * This method writes the conquest map file
	 * @param continents the hashmap of continents
	 * @param countries the hashmap of countries
	 * @param borders the hashmap of borders
	 * @param mapFile the mapfile to be loaded
	 * @throws IOException
	 */
	public void conquestMapWriter(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries, HashMap<Integer, ArrayList<Integer>> borders, String mapFile) throws IOException
	{
		String path = Paths.get("").toAbsolutePath().toString() + "\\maps\\" + mapFile;
		File file = new File(path);
		FileWriter fw = new FileWriter(file, false);
		BufferedWriter bw = new BufferedWriter(fw);
		file.createNewFile();
		
		bw.write("[Map]");
		bw.write("\n");
		bw.write("[Continents]");
		bw.newLine();
		for (Integer i : continents.keySet())
		{
			Continent c = continents.get(i);
			bw.write(c.getContinentName() + "=" + c.getContinentControlValue());
			bw.newLine();
		}
		
		bw.write("\n");
		bw.write("[Territories]");
		bw.newLine();
		
		for (Integer i : countries.keySet())
		{
			Country c1 = countries.get(i);
			String str1 = c1.getCountryName() + ","+ c1.getXCo() + "," + c1.getYCo()+ "," + gmc.getContinentFromCountryName(continents, countries, c1.getCountryName())+",";
			String str2="";
			
			ArrayList<Integer> bList = borders.get(i);
			
			for(int b : bList)
			{
				str2 = str2 + gmc.getCountryNameById(countries, b) + ",";
			}
			
			str2 = str2.substring(0, bList.size()-1);
			bw.write(str1+str2);
			bw.newLine();
		}

		bw.write("\n");

		bw.close();
	}
}