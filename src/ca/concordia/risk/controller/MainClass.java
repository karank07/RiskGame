/**
 * 
 */
package ca.concordia.risk.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;

/**
 * @author Karan and Rohan
 *
 */
public class MainClass {

	/**
	 * @param args
	 * @return @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String fileName = "D:\\Project\\RiskGame\\maps\\risk.map";
		String fileData = "";
		FileReader file = null;
		BufferedReader br = null;
		List<String> continentString = new ArrayList<String>();
		List<Continent> ContinentList = new ArrayList<Continent>();
		List<String> countryString = new ArrayList<String>();
		List<Country> CountryList = new ArrayList<Country>();
		List<String> BorderString = new ArrayList<String>();

		try {
			file = new FileReader(fileName);
			br = new BufferedReader(file);

			while (fileData != null) {
				fileData = br.readLine();

				if (fileData.equals("[continents]")) {
					fileData = br.readLine();
					
					while (!fileData.isEmpty()) {
						continentString.add(fileData);
						fileData = br.readLine();
					}
					stringToContinent(continentString, ContinentList);
				} else if (fileData.equals("[countries]")) {
					fileData = br.readLine();
					
					while (!fileData.isEmpty()) {
						countryString.add(fileData);
						fileData = br.readLine();
					}
					stringToCountry(countryString, CountryList);
				} else if (fileData.equals("[borders]")) {
					fileData = br.readLine();
					
					while (fileData != null) {
						BorderString.add(fileData);
						fileData = br.readLine();
					}
					
					setNeigbourCountry(CountryList, BorderString);
					break;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			br.close();
			file.close();
		}

	}
	
	/**
	* @param continentString
	* @param ContinentList
	*/
	private static void stringToContinent(List<String> continentString, List<Continent> ContinentList) {
	// TODO Auto-generated method stub
	String[] temp = new String[3];

	for (String obj : continentString) {

	temp = obj.split(" ");
	Continent objContinent = new Continent();
	objContinent.setContinentName(temp[0]);

	objContinent.setContinentControlValue(Integer.parseInt(temp[1]));
	objContinent.setContinentColor(temp[2]);
	ContinentList.add(objContinent);

	}

	for (Continent o : ContinentList) {
	System.out.println(o.toString());
	}

	}

	/**
	* @param countryString
	* @param CountryList
	*/
	private static void stringToCountry(List<String> countryString, List<Country> CountryList) {
	// TODO Auto-generated method stub
	String[] temp = new String[3];

	for (String obj : countryString) {
	temp = obj.split(" ");
	Country objCountry = new Country(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]),
	Integer.parseInt(temp[3]), Integer.parseInt(temp[4]));
	CountryList.add(objCountry);
	}

	}

	/**
	* @param countryList
	* @param borderString
	*/
	private static void setNeigbourCountry(List<Country> countryList, List<String> borderString) {
	// TODO Auto-generated method stub
	String[] temp2;
	int[] temp3 = null;

	int k = 0;
	for (Country obj : countryList) {

	temp2 = borderString.get(k).split(" ");
	k = k + 1;
	temp3 = new int[temp2.length];
	for (int i = 0; i < temp2.length; i++) {
	temp3[i] = Integer.parseInt(temp2[i]);
	}

	obj.setNeighbours(temp3);

	}

	for (Country o : countryList) {
	System.out.println(o.toString());

	}

	}
}