package ca.concordia.risk.controller;

import java.io.BufferedReader;
import java.io.File;
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
	private FortificationPhase fp;
	private ReinforcementPhase rp;
	private StartUpPhase sp;
	public static List<Player> playerList;
	public static HashMap<Integer, Continent> continents;
	public static HashMap<Integer, Country> countries;
	public static HashMap<Integer, ArrayList<Integer>> borders;
	private Map mapInstance;

	private MapOperations mapOperations;
	private MapWriter mapWriter;

	static Console c;
	int currentPlayer = 0;
	static String phase = "loadmap";
	static String mapPhase = "editmap";
	private boolean gamePlayerSet = false;
	private boolean placeArmyFlag = false;
	public static String errorFlag = "false";

	public static void main(String[] a) throws Exception {
		new MainClass();
		c.createConsole();
	}

	public MainClass() {
		c = new Console();
		fp = new FortificationPhase();
		rp = new ReinforcementPhase();


		playerList = new ArrayList<Player>();
		continents = new HashMap<Integer, Continent>();
		countries = new HashMap<Integer, Country>();
		borders = new HashMap<Integer, ArrayList<Integer>>();
		
		mapInstance = Map.getM_instance();
		mapOperations = new MapOperations();
		mapWriter = new MapWriter();

	}

	/**
	 * reads the map file to be loaded
	 * 
	 * @param fileName Map file to be read
	 * @throws IOException to handle exceptions
	 */
	public void readMapFile(String fileName) throws IOException {
		if (!phase.contentEquals("loadmap")) {
			errorFlag = "invalid command!";
			return;
		}
		MapValidate mv = new MapValidate();
		fileName = Paths.get("").toAbsolutePath().toString() + "\\maps\\" + fileName;
		
		String fileData="";
		FileReader file;
		
		try {
			file = new FileReader(fileName);
			File fileValidate = new File(fileName);
			if (!mv.validateFile(fileValidate)) {
				errorFlag = "Map invalid!";
				return;
			}

			BufferedReader br = new BufferedReader(file);
			List<String> continentString = new ArrayList<String>();
			List<String> countryString = new ArrayList<String>();
			List<String> borderString = new ArrayList<String>();
			while (fileData != null) {
				fileData = br.readLine();

				if (fileData.equals("[continents]")) {
					fileData = br.readLine();

					while (!fileData.isEmpty()) {
						continentString.add(fileData);
						fileData = br.readLine();
					}
					//stringToContinent(continentString, continentList);
					String[] temp = new String[3];
					int index=0;
					for (String obj : continentString) {
						index++;
						temp = obj.split(" ");

						Continent objContinent = new Continent(temp[0], Integer.parseInt(temp[1]), temp[2]); // name, c_value, color

						continents.put(index, objContinent);

					}

					for (Continent o : continents.values()) {
						System.out.println(o.toString());
					}

				} else if (fileData.equals("[countries]")) {
					fileData = br.readLine();

					while (!fileData.isEmpty()) {
						countryString.add(fileData);
						fileData = br.readLine();
					}
					//stringToCountry(countryString, countryList);
					String[] temp = new String[3];
					
					for (String obj : countryString) {
						temp = obj.split(" ");
						Country objCountry = new Country(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]),
								Integer.parseInt(temp[3]), Integer.parseInt(temp[4]));
						countries.put(objCountry.getCountryNumber(), objCountry);
					}

				} else if (fileData.equals("[borders]")) {
					fileData = br.readLine();

					while (fileData != null) {
						borderString.add(fileData);
						fileData = br.readLine();
					}

					//setNeigbourCountry(countries, BorderString);
					String[] temp2;
					int[] temp3 = null;

					int k = 0;
					for (Country obj : countries.values()) {
			 
						temp2 = borderString.get(k).split(" ");
						k = k + 1;
						temp3 = new int[temp2.length];
						for (int i = 0; i < temp2.length; i++) {
							temp3[i] = Integer.parseInt(temp2[i]);
						}

						obj.setNeighbours(temp3);

					}

					for (Country o : countries.values()) {
						System.out.println(o.toString());

					}

					break;
				}
				errorFlag = "false";
			}

		} catch (FileNotFoundException e) {
			errorFlag = "Given Map file doesnot exist!";
		}
		finally {
			
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
		for (Country obj : countries.values()) {

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
		System.out.println("Continents: ");
		System.out.println(continents.values().toString());

		System.out.println("Countries: ");
		System.out.println(countries.values().toString());

		System.out.println("Neighbours: ");
		System.out.println(borders.toString());
	}

	private void showmapForGamePhase() {
		for (Country c : countries.values()) {
			System.out.println("\nCountry: " + c.getCountryName() + " Continent: "
					+ continents.get(c.getContinentID() - 1).getContinentName() + " Country army: "
					+ c.getCountryArmy() + " Owner Name:" + playerList.get(c.getCountryOwner() - 1).getPlayerName()
					+ " Neighbours :");
			System.out.print(getNeighboursName(c.getNeighbours()));
		}
	}


	private List<String> getNeighboursName(int[] neighbours) {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i < neighbours.length; i++) {
			list.add(countries.get(neighbours[i] - 1).getCountryName());
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
			if (!mapPhase.contentEquals("edit")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			s1 = s1 + " stop";
			temp = s1.split(" ");

			for (int i = 1; i < temp.length; i++) {
				if (temp[i].contentEquals("-add")) {
					if (!(temp[i + 1].contentEquals("stop")) && !(temp[i + 2].contentEquals("stop"))) {
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
			if (!mapPhase.contentEquals("edit")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			s1 = s1 + " stop";
			temp = s1.split(" ");
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].contentEquals("-add")) {
					if (!(temp[i + 1].contentEquals("stop")) && !(temp[i + 2].contentEquals("stop"))) {
						errorFlag = "false";
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
			if (!mapPhase.contentEquals("edit")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}

			for (int i = 0; i < temp.length; i++) {
				if (temp[i].contentEquals("-add")) {
					if (temp[i + 1] != null || temp[i + 2] != null) { // country name and neighbour country name should
																		// not // be null
						errorFlag = "false";
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
			if (mapPhase.contentEquals("end")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}

			try {
				try {
					mapWriter.writeMapFile(continents, countries, borders, temp[1]);
					errorFlag = "false";
					mapPhase = "end";
				} catch (ValidMapException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "showmap":
			if (!mapPhase.contentEquals("end")) {
				if (borders.isEmpty()) {
					errorFlag = "Invalid command!";
				} else
					showmapForMapPhase();
			} else if (playerList.isEmpty()) {
				errorFlag = "Invalid command!";
			} else
				showmapForGamePhase();

			break;
		case "validatemap":
			if (borders.isEmpty()) {
				errorFlag = "Invalid!";
			} else if (mapOperations.isConnected(borders)) {
				System.out.println("Map valid!");
			} else
				errorFlag = "Invalid map!";
			break;
		case "editmap":
			if (!mapPhase.contentEquals("editmap")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}

			try {
				if (mapWriter.loadMap(continents, countries, borders, temp[1])) {
					System.out.println("Loaded");
					errorFlag = "false";
					mapPhase = "edit";
				} else {
					System.out.println("Not Loaded!");
					errorFlag = "false";
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
			mapPhase = "end";
			String fileName = temp[1];
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
			temp = s1.split(" ");

			sp = new StartUpPhase();
			for (int i = 1; i < temp.length; i++) {
				if (temp[i].contentEquals("-add")) {
					if (!temp[i + 1].contentEquals("stop")) {
						addPlayer(temp[i + 1]);
						errorFlag = "false";
					} else {
						errorFlag = "add a valid name";
					}
				} else if (temp[i].contentEquals("-remove")) {
					if (!temp[i + 1].contentEquals("stop")) {
						removePlayer(temp[i + 1]);
						errorFlag = "false";
					} else {
						errorFlag = "add a valid name";
					}

				}
			}
			System.out.println("\nPlayers:");

			for (Player p : playerList) {
				System.out.println(p.getPlayerId() + " " + p.getPlayerName());
			}
			if (!playerList.isEmpty()) {
				gamePlayerSet = true;

			}
			break;
		case "populatecountries":
			if (gamePlayerSet == true && !playerList.isEmpty()) {
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
			for(Country c:countries.values())
			{
				for(Player p:playerList)
				{
					if(c.getCountryOwner()==p.getPlayerId())
					{
						p.setPlayerTotalArmies(p.getPlayerTotalArmies()-1);
					}
				}
			}
			break;
		
		case "placearmy":
			
		
			if (!phase.contentEquals("placearmy")) {
				errorFlag = "Invalid command!";
				return errorFlag;
			}
			//currentPlayer=1;
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
			if (temp[1] == "" ) {
				errorFlag = "Invalid command!";
			
			}
			else if (temp[1].contentEquals("none")) {
				System.out.println("Fortification skipped!");
				phase = "reinforce";
			
			}
			 else {
					// temp[1]- countryFrom, temp[2]- countryTo, temp[3]- armyCount
					setFortify(temp[1], temp[2], Integer.parseInt(temp[3]));
				
			}

			break;
			

			
		default:
			// set flag for alert("Wrong Input!");
			errorFlag = "Check commands again!";
		}
		return errorFlag;
	}
}