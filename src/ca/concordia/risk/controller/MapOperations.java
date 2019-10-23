package ca.concordia.risk.controller;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	static int continent_num=1;
	static int hashMapID=0;
	static List<ArrayList<String>> neighboursList=new ArrayList< ArrayList<String>>();
	HashMap<Integer, ArrayList<Integer>> hMap;
	private boolean[] visited;
	private int flag;
	/**
	 * This method adds continent to map
	 * @param map current map object
	 * @param continents the hashmap of continents
	 * @param continent_name the name of the continent
	 * @param control_val the control value of continent
	 * @param color the color of continent
	 * @return boolean value true if country is added else returns false
	 * @throws ValidMapException throws an exception if map is invalid
	 */
	public boolean addContinent(Map map, HashMap<Integer, Continent> continents, String continent_name, int control_val, String color) throws ValidMapException 
	{
		for (int n : continents.keySet()) 
		{
			String cont_name = continents.get(n).getContinentName();
			if (cont_name.equalsIgnoreCase(continent_name))
			{
				throw new ValidMapException("The Continent with name "+ continent_name + "already exists" );
			} 
		}
		color=null;
		continent_num=continents.size();
		Continent cont = new Continent(continent_name, control_val, color);
		continents.put(continent_num, cont);
		return true;
	}
	
	
	
	/**
	 * This method adds countries to map 
	 * @param map the map object instance
	 * @param continents the hashmap of existing continents
	 * @param countries the hashmap of existing countries
	 * @param country_name the country name to be added
	 * @param continent_name the continent name in which country needs to be added
	 * @param borders for the borders
	 * @return true if country added, else false
	 * @throws ValidMapException to handle exception
	 */
	public boolean addCountry(Map map,HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries,HashMap<Integer, ArrayList<Integer>> borders,String country_name, String continent_name ) throws ValidMapException
	{
		Random r=new Random();
		int x_co=r.nextInt(600);
		int y_co=r.nextInt(600);
		country_num= countries.size();
		
		boolean continentFlag=false,countryFlag=false;
		int continent_id=0;
		
		for (int i : continents.keySet()) {
			String con_name = continents.get(i).getContinentName();
			if (con_name.equalsIgnoreCase(continent_name)) 
			{
				continent_id=i;
				continentFlag=true;
				break;				
			}
		}
		
		if(continentFlag==true)
		{
			if(countries.size()>0)
			{
				for (int n : countries.keySet()) {
					String co = countries.get(n).getCountryName();
					if (co.equalsIgnoreCase(country_name)) {					
						throw new ValidMapException("Country with name:"+ country_name +" already exists!");
					}
					else
					{
						countryFlag=true;
					}
				}
			}
			else
			{
				Country co = new Country(country_num, country_name, continent_id, x_co, y_co);
				countries.put(country_num+1, co);
				borders.put(country_num, new ArrayList<Integer>());
				return true;
			}

		}
		
		else
		{
			throw new ValidMapException("The Continent:"+continent_name +"does not exist!");
		}
		
		if(countryFlag == true)
		{
			Country co = new Country(country_num, country_name, continent_id, x_co, y_co);
			countries.put(country_num +1, co);
			borders.put(country_num, new ArrayList<Integer>());
			//country_num+=1;
			borders.put(country_num, new ArrayList<Integer>());
			return true;
		}
		return false;
	}
	
	/**
	 * This method adds borders to map
	 * @param map the map object
	 * @param borders the borders hashmap
	 * @param country_name the name of the country
	 * @param neighbour_country_name the name of the neighbour country
	 * @param countries for country
	 * @return true if border added, else false
	 * @throws ValidMapException handles exceptions
	 */
	public boolean addNeighbours(Map map,HashMap<Integer, Country> countries, HashMap<Integer, ArrayList<Integer>> borders, String country_name, String neighbour_country_name) throws ValidMapException
	{
		
		boolean country_flag = false;
		boolean neighbour_flag = false;
		int country_id=0,neighbour_country_id=0;
		
		//get the country id from country name
		for (int n : countries.keySet()) {
			String coun = countries.get(n).getCountryName();
			if (country_name.equalsIgnoreCase(coun)) {
				country_id=n;
				country_flag=true;
				break;
			}
		}
		
		
		if(country_flag==true)
		{	//get the neighbour country id from neighbour country name
			for (int p : countries.keySet()) {
				String neigh_country = countries.get(p).getCountryName();
				if (neighbour_country_name.equalsIgnoreCase(neigh_country)) {
					neighbour_country_id=p;
					neighbour_flag=true;
					break;
				}
			}
			
			if(neighbour_flag==true)
			{
				if(borders.size()>0)
				{
					if(borders.containsKey(country_id))
					{
						if(borders.get(country_id).contains(neighbour_country_id))
						{
							throw new ValidMapException("The neighbour country already exists in Borders List!");
						}
						else
						{
							borders.get(country_id).add(neighbour_country_id);
						}
					}
					else
					{
						//When no elements are present
						ArrayList<Integer> list = new ArrayList<Integer>();
						list.add(neighbour_country_id);
						borders.put(country_id, list);
					}
				}
				else
				{
					ArrayList<Integer> list1 = new ArrayList<Integer>();
					list1.add(neighbour_country_id);
					borders.put(country_id, list1);
				}				
				return true;
			}
			else
			{
				throw new ValidMapException("THe neighbour country named "+ neighbour_country_name +" does not exist. Please add the country and then try to add the neighbour");
			}
		}
		else
		{
			throw new ValidMapException("The Country "+ country_name +" does not exists. Please add the country and then neighbour");
		}
		
	}
	
	
	/**
	 * This method removes continent
	 * @param continents the hashmap of continents
	 * @param countries the hashmap of countries
	 * @param borders the hashmap of borders
	 * @param continentName the name of continent to be removed
	 * @return true if continent and its countries along with neighbours is deleted
	 */
	public boolean deleteContinent(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries, HashMap<Integer, ArrayList<Integer>> borders, String continentName)
	{
		int state=0;
		
		Iterator<Entry<Integer, Continent>> iteratorContinent=continents.entrySet().iterator();
		Iterator<Entry<Integer, Country>> iteratorCountry=countries.entrySet().iterator();
		Iterator<Entry<Integer, ArrayList<Integer>>> iteratorBoundries=borders.entrySet().iterator();
		
		while (iteratorContinent.hasNext())
		{
			Entry<Integer, Continent> entryContinents=iteratorContinent.next();
			
			String cont = entryContinents.getValue().getContinentName();
			
			if (cont.equalsIgnoreCase(continentName))
			{
				iteratorContinent.remove();
				state=1;
				
				while(iteratorCountry.hasNext()) 
				{
					Entry<Integer, Country> entryCountries=iteratorCountry.next();
				
					Country c = entryCountries.getValue();
					if (c.getContinentID() == entryContinents.getKey()) 
					{
						iteratorCountry.remove();
						state=2;
						while (iteratorBoundries.hasNext()) 
						{
							Entry<Integer, ArrayList<Integer>> entryBoundries=iteratorBoundries.next();
							
							ArrayList<Integer> list = entryBoundries.getValue();
							
							if (entryBoundries.getKey() == entryCountries.getKey()) 
							{
								iteratorBoundries.remove();
								state=3;
							}
							else
							{								
								if(list.contains(entryCountries.getKey()))
								{
									list.remove(Integer.valueOf(entryCountries.getKey()));
								}
							}							
						}
					}
				}				
			}
		}
		if(state==3)
		{
			return true;
		}
		else
		{
			return false;
		}
}

		
	
	/**
	 * This method checks if the map is connected or not
	 * @param borders the list of borders, to check connectivity
	 * @return true if map is connected, else false
	 */
	public boolean isConnected(HashMap<Integer, ArrayList<Integer>> borders)
	{
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
	 * @param node the current vertex of map which is being traversed
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