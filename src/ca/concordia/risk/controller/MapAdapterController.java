package ca.concordia.risk.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.utilities.ValidMapException;

/**
 * This Class is the controller for adapter to read conquest map file
 * @author dhruv
 *
 */
public class MapAdapterController extends MapWriter {

	private ConquestMapController cmc;
	
	public void conquestToDomination(ConquestMapController cmc)
	{
		this.cmc = cmc;
	}
	
	
	public boolean loadMap(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries, HashMap<Integer, ArrayList<Integer>> borders, String fileName) throws FileNotFoundException
	{
		cmc.conquestMapReader(continents, countries, borders, fileName);
		return true;
	}
	
	public void writeMapFile(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries, HashMap<Integer, ArrayList<Integer>> borders, String mapFile) throws IOException, ValidMapException
	{
		cmc.conquestMapWriter(continents, countries, borders, mapFile);
	}
	
}
