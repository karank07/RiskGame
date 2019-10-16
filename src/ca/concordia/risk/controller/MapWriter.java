package ca.concordia.risk.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.utilities.CountryComparator;
import ca.concordia.risk.utilities.GameConstants;

public class MapWriter{
	
	/**
	 * This method the map file
	 * @param continents the hashmap of continents
	 * @param countries the hashmap of countries
	 * @param boundries  the hashmap of borders
	 * @param mapFile
	 * @throws IOException
	 */
	public void writeMapFile(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries, HashMap<Integer, ArrayList<Integer>> borders, String mapFile) throws IOException {

		String filePath = Paths.get("").toAbsolutePath().toString() + "\\maps\\" + mapFile;
		File mapfile = new File(filePath);
		FileWriter fileWriter = new FileWriter(mapfile, false);
		BufferedWriter buffW = new BufferedWriter(fileWriter);
		mapfile.createNewFile();
		
		
		buffW.write(GameConstants.CONTINENT_HEADER);
		buffW.newLine();
		for (Integer n : continents.keySet()) {
			Continent con = continents.get(n);
			buffW.write(con.getContinentName() + " " + con.getContinentControlValue() + " " + con.getContinentColor());
			buffW.newLine();
		}

		buffW.write("\n");
		
		buffW.write(GameConstants.COUNTRIES_HEADER);
		
		buffW.newLine();
		
		for (Integer i : countries.keySet()) 
		{
			Country coun = countries.get(i);
			
			int continent_id=coun.getContinentID()+1;
			
			buffW.write(i + " " + coun.getCountryName() + " " + continent_id + " " + coun.getXCo() + " " + coun.getYCo());
			buffW.newLine();
		}

		buffW.write("\n");
		buffW.write(GameConstants.BORDERS_HEADER);
		buffW.newLine();
		
		for (Integer m : borders.keySet()) 
		{
			ArrayList<Integer> list = new ArrayList<Integer>();
			String border_string = "";
			list = borders.get(m);
			
			for (Integer n : list) {
				border_string =border_string + n + " ";
			}
			
			buffW.write(m + " " + border_string.trim());
			buffW.newLine();
		}
		buffW.close();
	}
}