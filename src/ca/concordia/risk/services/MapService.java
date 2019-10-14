package ca.concordia.risk.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	 * Creates a new Map File
	 * @param saveFilePath the path of the file to be saved.
	 */
	public void createMap(String saveFilePath)
	{
		Map map= Map.getM_instance();
		
		List<String> lines=new ArrayList<String>();
		
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
			StringBuffer strbuf=new StringBuffer();
			strbuf.append(cn.getCountryNumber() + " " + cn.getCountryName() + " " + cn.getContinentID() + " " + cn.getXCo() + " " + cn.getYCo());
			lines.add(strbuf.toString());
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
	
	
	/**
	 * This method loads map file
	 * @param filepath the path of the file top be loaded
	 * @return Returns the Map object 
	 * @throws ValidMapException
	 * @throws URISyntaxException
	 */
	public Map loadMap(String filepath) throws ValidMapException,URISyntaxException
	{
		Map map = Map.getM_instance();
		List<String> list = new ArrayList<>();
		
		try 
		{
			BufferedReader buffr = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(filepath).toURI())) ;
			list = buffr.lines().collect(Collectors.toList());

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		extractFileInfo(map, list);
		
		return map;
	}
	
	/**
	 * This method loads map form file object
	 * @param mapFile The map file
	 * @return map object
	 * @throws ValidMapException
	 */
	public Map loadMap(File mapFile) throws ValidMapException{
		Map map = Map.getM_instance();
		List<String> list = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(mapFile));
			list = br.lines().collect(Collectors.toList());
			br.close();
		} 
		catch (IOException e) 
		{
				e.printStackTrace();
		}
		
	
		extractFileInfo(map, list);
		
		return map;
	}
	
	/**
	 * This method  extracts file information and check validation of map file
	 * @param map The map object
	 * @param list The collection list 
	 * @throws ValidMapException
	 */
	public void extractFileInfo(Map map, List<String> list) throws ValidMapException 
	{
		if(list.indexOf(GameConstants.CONTINENT_HEADER)<0){
			throw new ValidMapException("The Map does not have continents");
		}
		
		if(list.indexOf(GameConstants.COUNTRIES_HEADER)<0){
			throw new ValidMapException("The Map does not have countries");
		}
		
		if(list.indexOf(GameConstants.BORDERS_HEADER)<0){
			throw new ValidMapException("The Map does not have borders");
		}
		
		
		List<String> metaContinents = list.subList(list.indexOf(GameConstants.CONTINENT_HEADER) + 1,
				list.indexOf(GameConstants.COUNTRIES_HEADER) - 1);

		
		List<String> metaCountries = list.subList(list.indexOf(GameConstants.COUNTRIES_HEADER) + 1, 
				list.indexOf(GameConstants.BORDERS_HEADER) - 1);

		if(metaContinents.size()==0) {
			throw new ValidMapException("The Map does not have any continents! Invalid Map");
		}
		
		validateAndParseContinents(metaContinents,map);
		
		if(metaCountries.size()==0) {
			throw new ValidMapException("The Map does not have any countries! Invalid Map");
		}
		
		validateAndParseCountries(metaCountries, map);

		try {
			isTraversable();
		}
		catch(ValidMapException vme)
		{
			vme.getMessage();
		}
	}
	
	
	/**
	 * Checks if the map is traversable.
	 * @throws ValidMapException
	 */
	public void isTraversable() throws ValidMapException
	{
		GameServices gs= GameServices.getInstance();
		for(Country c1 : gs.getMap().getCountries())
		{
			for(Country c2 : gs.getMap().getCountries())
			{
				if(!c1.equals(c2))
				{
					if(!gs.isConnected(c1, c2))
					{
						throw new ValidMapException("The Countries " + c1.getCountryName()+" and "+c2.getCountryName()+" are disconnected. The Map Is Invalid!");
					}
				}
			}
		}
	}
	
	
	
	/**
	 * This method validates and parses continents.
	 * @param metaContinents the meta continents
	 * @param map the instance of map
	 * @throws ValidMapException if map invalid
	 */
	private void validateAndParseContinents(List<String> metaContinents, Map map) throws ValidMapException{

		List<Continent> continents = new ArrayList<>();

		for (String c : metaContinents) {
			
			if(c.isEmpty()){
				continue;
			}
			
			String[] data = c.split(" ");
			
			if(data.length != 3) {
				throw new ValidMapException("The map does not contain valid continent");
			}
			
			String continent_name = data[0].trim();
			
			int control_val = Integer.parseInt(data[1].trim());
			
			String color= data[2].trim();
			
			if(control_val<0)
			{	
				throw new ValidMapException("The Continent "+ continent_name+" does not have valid control value!");
			}
			continents.add(new Continent(continent_name, control_val, color));
		}

		map.setContinents(continents);
	}
	
	
	/**
	 * This method validates and parses the countries.
	 * @param metaCountries the list of countries
	 * @param map instance of map
	 * @throws ValidMapException
	 */
	public void validateAndParseCountries(List<String> metaCountries, Map map) throws ValidMapException{
		
		List<Country> countries = new ArrayList<>();

		for (String s : metaCountries) 
		{
			
			if(s.isEmpty()){
				continue;
			}
			
			String[] data = s.split(" ");
			
			if(data.length != 5) {
				throw new ValidMapException("Map does not contain valid country");
			}
			
			int continent_id = Integer.parseInt(data[2].trim()); 
			
			int x_coord= Integer.parseInt(data[3].trim());
			
			int y_coord= Integer.parseInt(data[4].trim());
			
			if(x_coord<0 || y_coord<0) {
				throw new ValidMapException("The map does not contain valid (x,y) coordinates");
			}
			
			if(data[0].trim().isEmpty() || data[1].trim().isEmpty() )
			{
				throw new ValidMapException("The map does not contain valid country values");
			}
			
			String continent_name= map.getContinents().get(continent_id-1).getContinentName();
			
			if(continent_name==null)
			{
				throw new ValidMapException(continent_name+" is not a valid continent");
			}
			
			Country coun = new Country(Integer.parseInt(data[0].trim()) , data[1].trim(), continent_id ,x_coord, y_coord);
			countries.add(coun);	
		}
		
		map.setCountries(countries);
	}
}