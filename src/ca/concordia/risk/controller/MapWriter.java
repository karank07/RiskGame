package ca.concordia.risk.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.utilities.CountryComparator;
import ca.concordia.risk.utilities.GameConstants;

public class MapWriter{
	
	/**
	 * This method writes the map details to the map file.
	 * @param map object of the map which is being processed
	 * @param fileName file name
	 */
	public void writeMapFile(Map map, String fileName) {
		
		FileWriter fileWriter;
		try {
			if (map == null) {
				System.out.println("Map Object is NULL!");
			}
			String createPath = Paths.get("").toAbsolutePath().toString() + "\\maps" + fileName;
			File mapfile = new File(createPath);
			String content = parseMapAndReturn(map);
			fileWriter = new FileWriter(mapfile, false);
			fileWriter.write(content);
			fileWriter.close();
			
		}catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	/**
	 * This method processes the map and makes a string to be written in the map file.
	 * @param map object of the map which is being processed
	 * @return List of String to be written in the map file
	 */
	private String parseMapAndReturn(Map map)
	{
		List<String> lines=new ArrayList<String>();
		
		lines.add(GameConstants.CONTINENT_HEADER);
		
		for(Continent c: map.getContinents().values())
		{
			lines.add(c.getContinentName() + " " + c.getContinentControlValue() + " " + c.getContinentColor());
		}
		
		lines.add(GameConstants.NEW_LINE);
		
		lines.add(GameConstants.COUNTRIES_HEADER);
		
		List<Country> countries= (List<Country>) map.getCountries().values();
		Collections.sort(countries, new CountryComparator());
		
		
		for(Country cn: countries)
		{
			lines.add(cn.getCountryNumber() + " " + cn.getCountryName() + " " + cn.getContinentID() + " " + cn.getXCo() + " " + cn.getYCo());
		}
		
		lines.add(GameConstants.BORDERS_HEADER);
		
		for (Integer n : map.getBorders().keySet()) 
		{
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			String adj = "";
			tempList = map.getBorders().get(n);
			for (Integer m : tempList)
			{
				adj = adj + m + " ";
			}
			lines.add(n + " " + adj.trim());
		}
		
		return lines.toString();
	}
	
}