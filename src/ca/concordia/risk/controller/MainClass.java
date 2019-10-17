package ca.concordia.risk.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.utilities.ValidMapException;
import ca.concordia.risk.view.Console;

/**
 * This class manages the overall execution of all the phases in the game.
 * Controls various model class object according to the phase. Includes
 * filename, filereader, list of countries and continents, model class objects,
 * etc as data members
 * 
 * @author Karan
 * @author Rohan
 * @author Pranal
 */
public class MainClass {
	private String fileName;
	private String fileData;
	private FileReader file;
	BufferedReader br;
	private List<String> continentString;
	public static List<Continent> continentList;
	private List<String> countryString;
	public static List<Country> countryList;
	private List<String> BorderString;
	private FortificationPhase fp;
	private ReinforcementPhase rp;
	private StartUpPhase sp;
	public static List<Player> playerList;
	public HashMap<Integer, Continent> continents;
	public HashMap<Integer, Country> countries;
	public HashMap<Integer, ArrayList<Integer>> borders;
	private Map mapInstance;

	private MapOperations mapOperations;
	private MapWriter mapWriter;

	static Console c;
	int currentPlayer = 0;
	static String phase = "loadmap";

	private boolean gamePlayerSet = false;
	private boolean placeArmyFlag = false;
	public static String errorFlag = "false";

	public static void main(String[] a) throws Exception {
		new MainClass();
		c.createConsole();

	}

	public MainClass() {
		fileData = "";
		file = null;
		br = null;
		playerList = new ArrayList<Player>();
		continentString = new ArrayList<String>();
		continentList = new ArrayList<Continent>();
		countryString = new ArrayList<String>();
		countryList = new ArrayList<Country>();
		BorderString = new ArrayList<String>();
		c = new Console();
		fp = new FortificationPhase();
		rp = new ReinforcementPhase();

		continents = new HashMap<Integer, Continent>();
		countries = new HashMap<Integer, Country>();
		borders = new HashMap<Integer, ArrayList<Integer>>();
		mapInstance = Map.getM_instance();
		mapOperations = new MapOperations();
		mapWriter = new MapWriter();

//		Scanner in=new Scanner(System.in);
//		while(true)
//		{
//			String s1=in.nextLine();
//			phaseDecider(s1);

//
//		}
	}

	/**
	 * This method converts string continent name to continent object
	 * 
	 * @param continentString the names of continents
	 * @param ContinentList   the list containing the continent entities
	 */
	private static void stringToContinent(List<String> continentString, List<Continent> continentList) {
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
	 * This method converts string country name to country object
	 * 
	 * @param countryString the names of countries
	 * @param CountryList   the list of the country objects
	 */
	private static void stringToCountry(List<String> countryString, List<Country> CountryList) {
		String[] temp = new String[3];

		for (String obj : countryString) {
			temp = obj.split(" ");
			Country objCountry = new Country(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]),
					Integer.parseInt(temp[3]), Integer.parseInt(temp[4]));
			CountryList.add(objCountry);
		}

	}

	/**
	 * sets the neighbors of the country
	 * 
	 * @param countryList  the list of country entities
	 * @param borderString contains the names of the borders
	 */
	private static void setNeigbourCountry(List<Country> countryList, List<String> borderString) {
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

	/**
	 * reads the map file to be loaded
	 * 
	 * @param fileName Map file to be read
	 * @throws IOException
	 */
	public void readMapFile(String fileName) throws IOException {
		if (!phase.contentEquals("loadmap")) {
			errorFlag = "invalid command!";
			return;
		}

		fileName = Paths.get("").toAbsolutePath().toString() + "\\maps\\" + fileName;

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
					stringToCountry(countryString, countryList);
				} else if (fileData.equals("[borders]")) {
					fileData = br.readLine();

					while (fileData != null) {
						BorderString.add(fileData);
						fileData = br.readLine();
					}

					setNeigbourCountry(countryList, BorderString);
					break;
				}
				errorFlag = "false";
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			errorFlag = "Given Map file doesnot exist!";
		}

		phase = "gameplayer";

	}

	/**
	 * 
	 * @param playerName the player to be added
	 */
	private void addPlayer(String playerName) {
		if (!phase.contentEquals("gameplayer"))
			return;
		sp.addPlayer(playerName);

	}

	/**
	 * 
	 * @param playerName player to be removed from the game
	 */
	private void removePlayer(String playerName) {
		if (!phase.contentEquals("gameplayer"))
			return;
		sp.removePlayer(playerName);
	}

	/**
	 * to assign countries to the players initially
	 */
	private void populateCountries() {
		if (!phase.contentEquals("populatecountries"))
			return;
		sp.populateCountries();
		phase = "placearmy";
	}

	/**
	 * to place all the armies initially without the player choosing
	 */
	private void placeAll() {
		if (!phase.contentEquals("placearmy"))
			return;
		sp.placeArmiesInitialRandom();
	}

	/**
	 * assigning army to particular mentioned country
	 * 
	 * @param cName country to be assigned army
	 */
	private void placeArmyByCountry(String cName) {
		if (!phase.contentEquals("placearmy"))
			return;
		sp.placeArmyByCountryName(cName);
	}

	/**
	 * Reinforcement phase base method
	 * 
	 * @param countryName country to reinforced
	 * @param armyNumber  army to be reinforced
	 */
	private void setReinforce(String countryName, int armyNumber) {
		if (!phase.contentEquals("reinforce"))
			return;
		rp.reinforceArmy(playerList.get(currentPlayer - 1), countryName, armyNumber);

		if (playerList.get(currentPlayer - 1).getPlayerReinforceArmy() == 0) {
			phase = "fortify";
		}
	}

	/**
	 * Fortification base method
	 * 
	 * @param from country from where armies would be sent
	 * @param to   country to where armies would be sent
	 * @param army number of armies used to fortify
	 */
	private void setFortify(String from, String to, int army) {
		if (!phase.contentEquals("fortify"))
			return;
		Country countryTo = null, countryFrom = null;
		for (Country obj : countryList) {

			if (obj.getCountryName().equalsIgnoreCase(from)) {
				countryFrom = obj;
			}
			if (obj.getCountryName().equalsIgnoreCase(to)) {
				countryTo = obj;
			}

		}
		fp.fortify(countryFrom, countryTo, playerList.get(currentPlayer - 1).getPlayerId(), army);
	}

	private void divideInitialArmies() {
		for (Player p : playerList)
			p.setPlayerTotalArmies(sp.getInitialArmies());
	}

	private void showmapForMapPhase() {
		for (Country c : countryList) {
			System.out.println("Country: " + c.getCountryName() + " Continent: "
					+ continentList.get(c.getContinentID() - 1).getContinentName() + " Country army: "
					+ c.getCountryArmy());
			System.out.println(getNeighboursName(c.getNeighbours()));
		}
	}

	private void showmapForGamePhase() {
		for (Country c : countryList) {
			System.out.println("Country: " + c.getCountryName() + " Continent: "
					+ continentList.get(c.getContinentID() - 1).getContinentName() + " Country army: "
					+ c.getCountryArmy() + " Owner Name:" + playerList.get(c.getCountryOwner() - 1).getPlayerName()
					+ " Neighbours :");
			System.out.print(getNeighboursName(c.getNeighbours()));
		}
	}

	private List<String> getNeighboursName(int[] neighbours) {
		List<String> list = new ArrayList();
		for (int i = 1; i < neighbours.length; i++) {
			list.add(countryList.get(neighbours[i] - 1).getCountryName());
		}
		return list;
	}

	/**
	 * 
	 * @param s1 phase command taken as input from console
	 * @return errorFlag to indicate successful execution or not
	 */
	public String phaseDecider(String s1) {
		String[] temp = new String[10];
		temp = s1.split(" ");
		int j = 0;
		System.out.println("\n" + s1);
		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp[i].toLowerCase();
		}

		switch (temp[0]) {
		case "editcontinent":
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].contentEquals("-add")) {
					if (temp[i + 1] != null || temp[i + 2] != null) { // continent name and continent value should not
																		// be null
						errorFlag = "false";
						try {
							mapOperations.addContinent(mapInstance, continents, temp[i + 1],
									Integer.parseInt(temp[i + 2]), null);
							// mapWriter.writeMapFile(continents, countries, borders, "risk1.txt");
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						errorFlag = "Enter a valid command";
					}
				} else if (temp[i].contentEquals("-remove")) {

				}
			}
			break;

		case "editcountry":
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].contentEquals("-add")) {
					if (temp[i + 1] != null || temp[i + 2] != null) { // country name and continent name should not
																		// be null
						errorFlag = "flase";
						try {
							mapOperations.addCountry(mapInstance, continents, countries, borders, temp[i + 1],
									temp[i + 2]);

						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						errorFlag = "Enter a valid command";
					}
				} else if (temp[i].contentEquals("-remove")) {

				}
			}
			break;

		case "editneighbor":
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].contentEquals("-add")) {
					if (temp[i + 1] != null || temp[i + 2] != null) { // country name and neighbour country name should
																		// not // be null
						errorFlag = "flase";
						try {
							mapOperations.addNeighbours(mapInstance, countries, borders, temp[i + 1], temp[i + 2]);
							mapOperations.addNeighbours(mapInstance, countries, borders, temp[i + 2], temp[i + 1]);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						errorFlag = "Enter a valid command";
					}
				} else if (temp[i].contentEquals("-remove")) {

				}
			}
			break;

		case "savemap":
			try {
				try {
					mapWriter.writeMapFile(continents, countries, borders, temp[1]);
					errorFlag = "flase";
				} catch (ValidMapException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "showMap":
			if (countryList.isEmpty() || continentList.isEmpty() || borders.isEmpty()) {
				errorFlag = "Invalid command!";
			} else
				showmapForMapPhase();
			break;
		case "editmap":
			try {
				if (mapWriter.loadMap(continents, countries, borders, temp[1])) {
					System.out.println("Loaded");
					errorFlag = "flase";
				} else {
					System.out.println("Not Loaded!");
					errorFlag = "flase";
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case "loadmap":
			if (!phase.contentEquals("loadmap")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			phase = "loadmap";
			fileName = temp[1];
			try {
				readMapFile(fileName);
			} catch (IOException e1) {
				e1.printStackTrace();
				// set flag for alert("File Not Found!");
				errorFlag = "Check map file name again!";
			}
			break;

		case "gameplayer":
			if (!phase.contentEquals("gameplayer")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			errorFlag = "false";
			s1 = s1 + " stop";
			String[] t = new String[3];
			temp = s1.split(" ");

			System.out.println("\nPlayers:");
			sp = new StartUpPhase();
			for (int i = 1; i < temp.length; i++) {
				if (temp[i].contentEquals("-add")) {
					if (!temp[i + 1].contentEquals("stop")) {
						addPlayer(temp[i + 1]);
					} else {
						errorFlag = "add a valid name";
					}
				} else if (temp[i].contentEquals("-remove")) {
					if (!temp[i + 1].contentEquals("stop")) {
						removePlayer(temp[i + 1]);
					} else {
						errorFlag = "add a valid name";
					}

				}
			}
			for (Player p : playerList) {
				System.out.println(p.getPlayerId() + " " + p.getPlayerName());
			}
			if (!playerList.isEmpty()) {
				gamePlayerSet = true;

			}

			break;

		case "populatecountries":
			if (gamePlayerSet) {
				phase = "populatecountries";
			}
			if (!phase.contentEquals("populatecountries")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			errorFlag = "false";
			sp = new StartUpPhase();

			populateCountries();

		case "dividearmies":
			divideInitialArmies();
			break;

		case "placearmy":
			if (!phase.contentEquals("placearmy")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			errorFlag = "false";
			if (temp[1] != "") {
				placeArmyByCountry(temp[1]);
			} else {
				errorFlag = "Check the country name entered!";
			}
			if (playerList.get(currentPlayer).getPlayerTotalArmies() == 0)
				placeArmyFlag = true;
			break;

		case "placeall":
			if (!phase.contentEquals("placearmy")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			errorFlag = "false";
			placeAll();
			if (playerList.get(currentPlayer).getPlayerTotalArmies() == 0)
				phase = "reinforce";

		case "caclulate armies for reinforcement":
			for (Player p : playerList) {
				rp.beginReinforcement(p);

			}
			currentPlayer = 1;// for build 1 implemented for single player
			System.out.println("\nTurn for Player " + (currentPlayer));
			break;
		case "reinforce":
			if (!phase.contentEquals("reinforce")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			errorFlag = "false";
			if (temp[1] == "" || temp[2] == "") {
				errorFlag = "Invalid command!";
				break;
			} else {
				setReinforce(temp[1], Integer.parseInt(temp[2]));// temp[1]-countryName, temp[2]- armyCount

				// for printing list of all player's countries and armies
				for (int i = 0; i < playerList.get(currentPlayer - 1).getPlayerCountries().size(); i++) {
					System.out.println(playerList.get(currentPlayer - 1).getPlayerCountries().get(i).getCountryName()
							+ " " + playerList.get(currentPlayer - 1).getPlayerCountries().get(i).getCountryArmy());

				}

			}
			break;
		case "fortify":
			if (!phase.contentEquals("fortify")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			errorFlag = "false";
			currentPlayer = 1;// for build 1 static player
			if (temp[1] == "" || temp[2] == "" || temp[3] == "") {
				errorFlag = "Invalid command!";
				break;
			} else {
				if (temp[1].contentEquals("none")) {
					System.out.println("Fortification skipped!");
					phase = "reinforce";
				} else {
					// temp[1]- countryFrom, temp[2]- countryTo, temp[3]- armyCount
					setFortify(temp[1], temp[2], Integer.parseInt(temp[3]));
				}
			}

			break;

		case "showmap":
			if (countryList.isEmpty() || continentList.isEmpty() || playerList.isEmpty()) {
				errorFlag = "Invalid command!";
			} else
				showmapForGamePhase();
			break;
		default:
			// set flag for alert("Wrong Input!");
			errorFlag = "Check commands again!";
		}
		return errorFlag;
	}
}