/**
 * 
 */
package ca.concordia.risk.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.view.Console;

/**
 * @author Karan and Rohan
 *
 */
public class MainClass {

	String fileName;
	String fileData;
	FileReader file;
	BufferedReader br;
	List<String> continentString;
	/**
	 * 
	 */
	List<Continent> continentList;
	List<String> countryString;
	List<Country> CountryList;
	List<String> BorderString;
	FortificationPhase fp;
	List<Player> playerList;
	
	static Console c;
	static Player currentPlayer;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new MainClass();
		c.createConsole();

	}

	public MainClass() {
		fileData = "";
		file = null;
		br = null;
		continentString = new ArrayList<String>();
		continentList = new ArrayList<Continent>();
		countryString = new ArrayList<String>();
		CountryList = new ArrayList<Country>();
		BorderString = new ArrayList<String>();
		c = new Console();
		fp = new FortificationPhase();
	}

	public void readMapFile(String fileName) throws IOException {
		fileName = "D:\\Project\\RiskGame\\maps\\" + fileName + ".map";

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
					stringToContinent(continentString, continentList);
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
	private static void stringToContinent(List<String> continentString, List<Continent> continentList) {
		// TODO Auto-generated method stub
		String[] temp = new String[3];

		for (String obj : continentString) {

			temp = obj.split(" ");

			Continent objContinent = new Continent(temp[0], Integer.parseInt(temp[1]), temp[2]); // name, c_value, color

			continentList.add(objContinent);

		}

		for (Continent o : continentList) {
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

	public void setFortify(String from, String to, int army) {
		Country countryTo = null, countryFrom = null;
		for (Country obj : CountryList) {

			if (obj.getCountryName().equals(from)) {
				countryFrom = obj;
			}
			if (obj.getCountryName().equals(to)) {
				countryTo = obj;
			}

		}
		fp.fortify(countryFrom, countryTo, currentPlayer.getPlayerId(), army);
	}

}