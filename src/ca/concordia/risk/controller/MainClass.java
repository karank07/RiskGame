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
 * @author Karan
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
}