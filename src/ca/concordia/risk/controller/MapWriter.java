package ca.concordia.risk.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.utilities.GameConstants;
import ca.concordia.risk.utilities.ValidMapException;

/**
 * This class is used for reading and writing map files
 * 
 * @author dhruv
 */
public class MapWriter {

	MapValidate mv = new MapValidate();
	MapOperations mo = new MapOperations();
	String continent_start, country, country_start, border, border_start;
	String[] cont_info, country_info, border_info;
	boolean flag;
	ArrayList<Integer> borderList;

	/**
	 * This method the map file
	 * 
	 * @param continents the hashmap of continents
	 * @param countries the hashmap of countries
	 * @param borders the hashmap of borders
	 * @param mapFile stores the file of map
	 * @throws IOException handles exception
	 * @throws ValidMapException handles exception
	 */
	public void writeMapFile(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries,
			HashMap<Integer, ArrayList<Integer>> borders, String mapFile) throws IOException, ValidMapException {

		if (mo.isConnected(borders)) {
			String filePath = Paths.get("").toAbsolutePath().toString() + File.separator + "maps" + File.separator
					+ mapFile;
			File mapfile = new File(filePath);
			FileWriter fileWriter = new FileWriter(mapfile, false);
			BufferedWriter buffW = new BufferedWriter(fileWriter);
			mapfile.createNewFile();

			buffW.write(GameConstants.CONTINENT_HEADER);
			buffW.newLine();
			for (Integer n : continents.keySet()) {
				Continent con = continents.get(n);
				buffW.write(
						con.getContinentName() + " " + con.getContinentControlValue() + " " + con.getContinentColor());
				buffW.newLine();
			}

			buffW.write("\n");

			buffW.write(GameConstants.COUNTRIES_HEADER);

			buffW.newLine();

			for (Integer i : countries.keySet()) {
				Country coun = countries.get(i);

				int continent_id = coun.getContinentID();

				buffW.write(i + " " + coun.getCountryName() + " " + (continent_id) + " " + coun.getXCo() + " "
						+ coun.getYCo());
				buffW.newLine();
			}

			buffW.write("\n");
			buffW.write(GameConstants.BORDERS_HEADER);
			buffW.newLine();

			for (Integer m : borders.keySet()) {
				ArrayList<Integer> list = new ArrayList<Integer>();
				String border_string = "";
				list = borders.get(m);

				for (Integer n : list) {
					border_string = border_string + n + " ";
				}

				buffW.write(m + " " + border_string.trim());
				buffW.newLine();
			}
			buffW.close();
		} else {
			throw new ValidMapException("The map is not connected");
		}
	}

	/**
	 * This method loads map file and validates it.
	 * 
	 * @param continents the hashmap of continents
	 * @param countries the hashmap of countries
	 * @param borders the hashmap of borders
	 * @param fileName the name of the map file
	 * @return true if file loaded else retun false
	 * @throws FileNotFoundException handles exceptions
	 */
	public boolean loadMap(HashMap<Integer, Continent> continents, HashMap<Integer, Country> countries,
			HashMap<Integer, ArrayList<Integer>> borders, String fileName) throws FileNotFoundException {
		String filePath = Paths.get("").toAbsolutePath().toString() + File.separator + "maps" + File.separator
				+ fileName;
		File filePtr = new File(filePath);
		Scanner sc = new Scanner(filePtr);

		try {
			flag = mv.validateFile(filePtr);

			if (flag == true) {
				while (sc.hasNext()) {
					String continent = sc.nextLine();
					if (continent.equals(GameConstants.CONTINENT_HEADER)) {
						break;
					}
				}

				int continents_count = 0;
				
				while (sc.hasNext()) {
					continents_count++;
					continent_start = sc.nextLine();

					if (continent_start.length() > 0) {
						cont_info = continent_start.split(" ");

						Continent con1 = new Continent(cont_info[0], Integer.parseInt(cont_info[1]), cont_info[2]);

						continents.put(continents_count, con1);
					} else {
						break;
					}
				}

				while (sc.hasNext()) {
					country = sc.nextLine();
					if (country.equals(GameConstants.COUNTRIES_HEADER) == false) {
						continue;
					} else {
						break;
					}
				}

				while (sc.hasNext()) {

					country_start = sc.nextLine();

					if (country_start.length() > 0) {

						country_info = country_start.split(" ");

						Country coun = new Country(country_info[1], Integer.parseInt(country_info[2]),
								Integer.parseInt(country_info[3]), Integer.parseInt(country_info[4]));

						countries.put(Integer.parseInt(country_info[0]), coun);

					} else {
						break;
					}
				}

				while (sc.hasNext()) {

					border = sc.nextLine();
					if (border.equals(GameConstants.BORDERS_HEADER) == false) {
						continue;
					} else {
						break;
					}
				}

				while (sc.hasNext()) {
					border_start = sc.nextLine();

					if (border_start.length() > 0) {
						border_info = border_start.split(" ");

						borderList = new ArrayList<Integer>();

						for (int i = 1; i < border_info.length; i++) {
							borderList.add(Integer.parseInt(border_info[i]));
						}

						borders.put(Integer.parseInt(border_info[0]), borderList);
					} else {
						break;
					}
				}

				sc.close();

				return true;
			} else {
				sc.close();
				return false;

			}

		} catch (Exception e) {
			return false;
		}
	}
}