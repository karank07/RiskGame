package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Country;

/**
 * @author Karan
 *
 */
public class FortificationPhase {
	/**
	 * @param countryList- list of all the objects of countries
	 */
	List<Country> countryList;
	
	/**
	 * to instantiate object
	 */
	public FortificationPhase()
	{
		countryList=new ArrayList<Country>();
	}
	
	
	/**
	 * fortification-move armies to another country that belongs to the player
	 * @param from
	 * @param to
	 * @param owner
	 * @param army
	 */
	public void fortify(Country from,Country to,int owner,int army) {
		boolean adjFlag=false;
		adjFlag=checkNeighbours(from,to,owner);	
		if(adjFlag)
		{
			if(from.getCountryArmy()-army>=1)
			{
				from.setCountryArmy(from.getCountryArmy()-army);
				to.setCountryArmy(to.getCountryArmy()+army);
			}
			else 
				System.out.println("There must be atleast one army in a country!");
		}
		else 
			System.out.println("Move not possible");
		
	}
	
	/**
	 * to check if two countries are connected and belongs to same owner
	 * @param from
	 * @param to
	 * @param owner
	 * @return adjFlag
	 */
	public boolean checkNeighbours(Country from,Country to,int owner)
	{
		boolean adjFlag=false;
		int[] listOfNeighbours=from.getNeighbours();
		if((!arrayContains(listOfNeighbours,to.getCountryNumber())) && (checkNeighbours(countryList.get(listOfNeighbours[0]),to,owner)) && (from.getCountryOwner()==to.getCountryOwner()))
		{
			adjFlag=true;
		}
		return adjFlag;
	}
	/**
	 * To check if array contains the key
	 * @param arr 
	 * @param key
	 * @return
	 */
	public boolean arrayContains(int[] arr,int key)
	{
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]==key)
				return true;
		}
		return false;
	}

}
