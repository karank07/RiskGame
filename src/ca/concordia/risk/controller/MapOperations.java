package ca.concordia.risk.controller;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
	static List<ArrayList<String>> neighboursList=new ArrayList< ArrayList<String>>(250);
	HashMap<Integer, ArrayList<Integer>> hMap;
	private boolean[] visited;
	private int flag;
	/**
	 * This method adds continent to map
	 * @param map current map object
	 * @param continents the list of continents
	 * @param continent_name the name of the continent
	 * @param control_val the control value of continent
	 * @param color the color of continent
	 * @return boolean value true if country is added else returns false
	 * @throws ValidMapException throws an exception if map is invalid
	 */
	public boolean addContinent(Map map, List<Continent> continents, String continent_name, int control_val, String color) throws ValidMapException 
	{
		Continent con= new Continent(continent_name,control_val,color);
		
		if(map.getContinents().contains(con))
		{
			throw new ValidMapException("The Continent with name "+ continent_name + "already exists" );
		}
		continents.add(con);
		return true;
	}
	
	
	/**
	 * This method add countries to map.
	 * @param map the map object
	 * @param countries the list of countries
	 * @param country_name the name of the country to be added
	 * @param continent_name the name of continent in which the country needs to be added
	 * @return boolean value true if country is added else returns false
	 * @throws ValidMapException
	 */
	public boolean addCountry(Map map,List<Country> countries, String country_name, String continent_name ) throws ValidMapException
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
			countries.add(co);
			return true;
		}
		else
		{
			throw new ValidMapException("There is no continent with name:"+continent_name);
		}
	}
	
	
	/**
	 * This method adds neighbours to map
	 * @param map the map object
	 * @param borders the borders list
	 * @param country_name the name of country to which border need to be added
	 * @param neighbour_country_name the name of the country which is the border to given country
	 * @return  true if border is added else false
	 * @throws ValidMapException
	 */
	
	public boolean addNeighbours(Map map,List<ArrayList<String>> borders, String country_name, String neighbour_country_name) throws ValidMapException
	{
		int country_id= map.getCountryByName(country_name).getCountryNumber();
		int neighbour_country_id= map.getCountryByName(neighbour_country_name).getCountryNumber();
		
		if(map.getCountries().contains(map.getCountryByName(country_name))) 
		{	
			if(map.getCountries().contains(map.getCountryByName(neighbour_country_name)))
			{
				//neighboursList.put(country_id,  );
				neighboursList.get(country_id).add(Integer.toString(neighbour_country_id));
				neighboursList.get(neighbour_country_id).add(Integer.toString(country_id));
				//map.setBorders(neighboursList);
				borders=neighboursList;
				return true;
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
	
	/**
	 * This method converts List of ArrayList of String to HashMap for borders
	 * @param array the list to be converted into hashmap
	 * @return the converted hashmap
	 */
	public HashMap<Integer, ArrayList<Integer>> convertToHashMap(List<ArrayList<String>> array)
	{
		HashMap<Integer, ArrayList<Integer>> newHash= new HashMap<Integer, ArrayList<Integer>>();
		
		for(int i=0; i<array.size();i++)
		{
			ArrayList<Integer> line = new ArrayList<Integer>();
			for(int j=0; j<array.get(i).size();j++)
			{
				line.add(Integer.parseInt(array.get(i).get(j)));
			}
			newHash.put(i+1, line);
		}
		return newHash;
	}
	
	
	/**
	 * This method checks if the map is connected or not
	 * @param borders the list of borders, to check connectivity
	 * @return true if map is connected, else false
	 */
	public boolean isConnected(List<ArrayList<String>> bordersList)
	{
		HashMap<Integer,ArrayList<Integer>> borders;
		borders=convertToHashMap(bordersList);
		hMap=borders;
		visited= new boolean[borders.size()];
		flag=0;
		
		Entry<Integer, ArrayList<Integer>> firstNode = borders.entrySet().iterator().next();
		traverseMap(firstNode.getKey());
		
		for(int i=0; i<visited.length ;i++)
		{
			if(visited[i]==false)
			{
				flag=1;
			}
		}
		
		if(flag==1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	
	/**
	 * This method traverses the full map and checks the connectivity
	 * @param node the vertex of map which is being traversed
	 */
	public void traverseMap(int node)
	{
		visited[node-1]=true;
		
		for(int n: neighbourNodes(node))
		{
			if(visited[n-1]==false)
			{
				traverseMap(n);
			}
		}
	}
	
	
	/**
	 * This method returns array list of neighbours of given node
	 * @param n the node of which neighbours are to be found
	 * @return the array of neighbours
	 */
	public ArrayList<Integer> neighbourNodes(int n)
	{
		if(n>hMap.size())
		{
			return null;
		}
		else
		{
			ArrayList<Integer> list = new ArrayList<Integer>(hMap.get(n)); 
			return list;
		}
	}
}