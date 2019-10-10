package ca.concordia.risk.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ca.concordia.app.model.GameMap;
import ca.concordia.app.util.MapValidationException;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.utilities.CountryComparator;
import ca.concordia.risk.utilities.GameConstants;
import ca.concordia.risk.utilities.ValidMapException;

/**
 * The Class MapService.
 * @author dhruv
 */
public class MapService {

	/** 
	 * The instance of MapService
	 */
	public static MapService instance;
	
	/**
	 * This method gets the single instance of MapService.
	 *
	 * @return single instance of MapService
	 */
	public static MapService getInstance() {
		if(instance==null)
			instance= new MapService();
		return instance;
	}
	
	
	/**
	 * Creates New Map File
	 * @param saveFilePath the path of the file to be saved.
	 */
	public void createMap(String saveFilePath)
	{
		Map map= Map.getM_instance();
		
		List<String> lines=new ArrayList<String>();
		//lines.add("author="+GameConstants.MAP_AUTHOR);
		lines.add(GameConstants.CONTINENT_HEADER);
		
		for(Continent c: map.getContinents())
		{
			lines.add(c.getContinentName() + " " + c.getContinentControlValue() + " " + c.getContinentColor());
		}
		
		lines.add(GameConstants.NEW_LINE);
		
		lines.add(GameConstants.COUNTRIES_HEADER);
		
		List<Country> countries= Map.getCountries();
		Collections.sort(countries, new CountryComparator());
		
		
		for(Country cn: countries)
		{
			StringBuffer sb=new StringBuffer();
			sb.append(cn.getCountryNumber() + " " + cn.getCountryName() + " " + cn.getContinentID() + " " + cn.getXCo() + " " + cn.getYCo());
			lines.add(sb.toString());
		}
		
		lines.add(GameConstants.BORDERS_HEADER);
		
		for(Country coun: countries)
		{
			lines.add(coun.getNeighbours().toString());
		}
		
		
		Path path=Paths.get(saveFilePath);
		
		try 
		{
			Files.write(path, lines,StandardOpenOption.CREATE);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public Map loadMap(String path) throws ValidMapException,URISyntaxException
	{
		Map map = Map.getM_instance();
		List<String> list = new ArrayList<>();
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(path).toURI()))) 
		{
			list = br.lines().collect(Collectors.toList());

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		extractFileInformation(map, list);
		
		return map;
	}
	
	
	private void extractFileInformation(Map map, List<String> list) throws ValidMapException 
	{
		
		if(list.indexOf(GameConstants.CONTINENT_HEADER)<0){
			throw new ValidMapException("Map does not declare continents");
		}
		
		if(list.indexOf(GameConstants.COUNTRIES_HEADER)<0){
			throw new ValidMapException("Map does not declare countries");
		}
		
		if(list.indexOf(GameConstants.BORDERS_HEADER)<0){
			throw new ValidMapException("Map does not declare borders");
		}
		
		
		List<String> metaContinents = list.subList(list.indexOf(GameConstants.CONTINENT_HEADER) + 1,
				list.indexOf(GameConstants.COUNTRIES_HEADER) - 1);

		
		List<String> metaTerritories = list.subList(list.indexOf(GameConstants.COUNTRIES_HEADER) + 1, list.size());

		if(metaContinents.size()==0) {
			throw new ValidMapException("Map does not have any continents");
		}
		
		parseContinents(metaContinents,map);
		
		if(metaTerritories.size()==0) {
			throw new ValidMapException("Map does not have any countries");
		}
		
		parseCountries(metaTerritories,map);

		isTraversable();
	}
	
	
}