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
import java.util.Random;
import java.util.regex.Pattern;

import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Dice;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.model.TournamentMode;
import ca.concordia.risk.model.TournamentResult;
import ca.concordia.risk.strategies.AggressiveStrategy;
import ca.concordia.risk.strategies.BenevolentStrategy;
import ca.concordia.risk.strategies.CheaterStrategy;
import ca.concordia.risk.strategies.RandomStrategy;
import ca.concordia.risk.utilities.GamePhase;
import ca.concordia.risk.utilities.ValidMapException;
//import ca.concordia.risk.view.GameView;

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
	/**
	 * @param player_country_map a hash-map for a player and its owned countries
	 */
	public static HashMap<Player, List<Country>> player_country_map = new HashMap<Player, List<Country>>();
	public static HashMap<String, Integer> globalCardDeck;
	public static List<Player> playerList;
	private Map mapInstance;
	private MapOperations mapOperations;
	private MapWriter mapWriter;
	public static MainClass main_instance;
	private static TournamentMode tournamentObject;
	TournamentController tournamentController;
	TournamentResult tournamentResult;
	static int turn = 1;
	private boolean gamePlayerSet = false;
	public static String errorFlag = "false";
	private String mode = "single";
	boolean adjFlag = false;

	List<Country> visited = new ArrayList<Country>();

	/**
	 * constructor to initialize player list,card deck, map instance, map operations
	 * and map writer
	 */
	public MainClass() {
		playerList = new ArrayList<Player>();
		globalCardDeck = new HashMap<String, Integer>();
		mapInstance = Map.getM_instance();
		mapOperations = new MapOperations();
		mapWriter = new MapWriter();
		tournamentObject = TournamentMode.getInstance();

		tournamentResult = TournamentResult.getInstance();

	}

	/**
	 * creates and returns the instance of main class
	 * 
	 * @return main_instance instance of the main class
	 */
	public static MainClass getM_instance() {
		if (main_instance == null) {
			main_instance = new MainClass();
		}
		return main_instance;
	}

	/**
	 * reads the map file to be loaded
	 * 
	 * @param fileName Map file to be read
	 * @throws IOException to handle exceptions
	 */
	public void readMapFile(String fileName) throws IOException {
		/*
		 * if (!phase.contentEquals("loadmap")) { errorFlag = "invalid command!";
		 * return; }
		 */
		MapValidate mv = new MapValidate();
		fileName = Paths.get("").toAbsolutePath().toString() + File.separator + "maps" + File.separator + fileName;

		String fileData = "";
		FileReader file;

		try {
			file = new FileReader(fileName);
			File fileValidate = new File(fileName);
			if (!mv.validateFile(fileValidate)) {
				errorFlag = "Map invalid!";
				return;
			}

			BufferedReader br = new BufferedReader(file);
			HashMap<Integer, Country> countries = new HashMap<Integer, Country>();
			HashMap<Integer, Continent> continents = new HashMap<Integer, Continent>();
			HashMap<Integer, ArrayList<Integer>> borders = new HashMap<Integer, ArrayList<Integer>>();

			List<String> continentString = new ArrayList<String>();
			List<String> countryString = new ArrayList<String>();
			while (fileData != null) {
				fileData = br.readLine();

				if (fileData.equals("[continents]")) {
					fileData = br.readLine();

					while (!fileData.isEmpty()) {
						continentString.add(fileData);
						fileData = br.readLine();
					}
					String[] temp = new String[3];
					int index = 0;
					for (String obj : continentString) {
						index++;
						temp = obj.split(" ");

						Continent objContinent = new Continent(temp[0], Integer.parseInt(temp[1]), temp[2]); // name,control
																												// value,color
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

					String[] temp = new String[3];

					for (String obj : countryString) {
						temp = obj.split(" ");
						Country objCountry = new Country(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]),
								Integer.parseInt(temp[3]), Integer.parseInt(temp[4]));
						countries.put(objCountry.getCountryID(), objCountry);
					}

				} else if (fileData.equals("[borders]")) {
					fileData = br.readLine();

					while (fileData != null) {
						String[] border_info = fileData.split(" ");
						ArrayList<Integer> borderList = new ArrayList<Integer>();
						for (int i = 1; i < border_info.length; i++) {
							borderList.add(Integer.parseInt(border_info[i]));
						}
						borders.put(Integer.parseInt(border_info[0]), borderList);

						fileData = br.readLine();
					}

					mapInstance.setCountries(countries);
					mapInstance.setContinents(continents);
					mapInstance.setBorders(borders);

					for (Country o : countries.values()) {
						System.out.println(o.getCountryID() + " " + o.getCountryName());

					}

					break;
				}
				errorFlag = "false";
			}

		} catch (FileNotFoundException e) {
			errorFlag = "Given Map file doesnot exist!";
		} finally {

		}
	}

	/**
	 * 
	 * @return the current turn of the player
	 * 
	 */
	int getPlayerTurn() {
		return turn;
	}

	/**
	 * sets the turn for the next player in the list
	 */
	public void setNextPlayerTurn() {

		turn++;
		turn = turn > playerList.size() ? 1 : turn;
	}

	/**
	 * resets player turn to first player
	 */
	void resetPlayerTurn() {
		turn = 0;
	}

	/**
	 * this method adds or removes a player for the game
	 * 
	 * @param s1 string to check whether to add or remove player
	 */
	public void gamePlayer(String s1) {
		errorFlag = "false";
		s1 = s1 + " stop";
		String[] temp = s1.split(" ");

		for (int i = 1; i < temp.length; i++) {
			if (temp[i].contentEquals("-add")) {
				if (!temp[i + 1].contentEquals("stop")) {
					errorFlag = "false";
					addPlayer(temp[i + 1], temp[i + 2]);

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
	}

	/**
	 * 
	 * @param playerName the player to be added in the game
	 */
	public void addPlayer(String playerName, String stratergy) {
		for (Player p : playerList) {
			if (p.getPlayerName().equalsIgnoreCase(playerName)) {
				errorFlag = "Name Already exists!";
				return;
			}
		}
		int playerID = playerList.size() + 1;
		if(stratergy.equals("human") || stratergy.equals("random") || stratergy.equals("cheater") || stratergy.equals("aggressive") || stratergy.equals("benevolent")){
			Player p = new Player(playerID, playerName, stratergy);
			playerList.add(p);
			errorFlag = "false";
		}
		else {
			errorFlag  = "Invalid strategy type";
		}
		
	}

	/**
	 * 
	 * @param playerName the player to be removed from the game
	 */
	public void removePlayer(String playerName) {
		if (playerList.isEmpty()) {
			errorFlag = "There are no players currently playing";
		} else {
			for (Player obj : MainClass.playerList) {
				if (playerName.contentEquals(obj.getPlayerName())) {
					playerList.remove(obj.getPlayerId() - 1);
					System.out.println("\nplayer deleted");
					errorFlag = "false";
					for (int i = 0; i < playerList.size(); i++) {
						playerList.get(i).setPlayerId(i + 1);
					}
					break;
				}

			}
		}
	}

	/**
	 * all countries are randomly assigned to players for initial startup
	 */
	public void startupPhase() {

		for (Player p : playerList) {
			if (gamePlayerSet) {
				p.setCurrentPhase(GamePhase.STARTUP);
			}

		}
		divideInitialArmies();
		generateDeck();
		resetPlayerTurn();
		populatecountries();
	}

	/**
	 * to divide armies to player at game start
	 */
	public void divideInitialArmies() {
		for (Player p : playerList) {
			p.setPlayerTotalArmies(getInitialArmies());
			p.setIntialArmies(getInitialArmies());
		}
	}

	/**
	 * to map countries to player initially
	 */
	public void populatecountries() {
		int playerCount = playerList.size();
		int j = 0;
		for (Country country : mapInstance.getCountries().values()) {
			Player player = playerList.get(j % playerCount);
			setNewCountryRuler(player, country, 1);
			player.setPlayerCountries(player_country_map.get(player));
			j++;
		}

		for (Player p : playerList) {
			System.out.println(
					"\nCountries Allocated to Player " + p.getPlayerId() + " " + p.getPlayerName() + " are : ");
			for (Country c : player_country_map.get(p)) {
				System.out.println(c.getCountryName());
			}
			p.setPlayerReinforceArmy(p.assign_army());
		}
		resetPlayerTurn();

	}

	/**
	 * 
	 * Getting the initial armies according the number of players playing
	 * 
	 * @param numberOfPlayers the players playing
	 * 
	 * @return the initial armies
	 */

	public int getInitialArmies() {
		int numberOfPlayers = playerList.size();

		int setArmy = 0;
		switch (numberOfPlayers) {
		case 2:
			setArmy = 40;
			break;
		case 3:
			setArmy = 35;
			break;
		case 4:
			setArmy = 30;
			break;
		case 5:
			setArmy = 25;
			break;
		case 6:
			setArmy = 20;
			break;
		default:
			setArmy = 40;
		}
		return setArmy;

	}

	/**
	 * 
	 * @param pl     player to be assigned army for
	 * @param co     country
	 * @param armies the number of armies assigned
	 * @return
	 */
	private boolean setNewCountryRuler(Player pl, Country co, int armies) {
		if (co.getCountryArmy() != 0)
			return false;
		co.setCountryArmy(armies);
		pl.setIntialArmies(pl.getIntialArmies() - 1);
		mapPlayerToCountry(pl, co);
		return true;

	}

	/**
	 * 
	 * @param p player to be assigned a country
	 * @param c country being assigned to the player
	 */
	public void mapPlayerToCountry(Player p, Country c) {
		List<Country> cList = player_country_map.get(p);
		c.setCountryOwner(p.getPlayerId());
		if (cList == null) {
			cList = new ArrayList<>();
		}
		cList.add(c);
		player_country_map.put(p, cList);
	}

	/**
	 * 
	 * @param p player whose conquered countries are to be known
	 * @return the list of countries owned by the player p
	 */
	public List<Country> getCountriesConqueredBy(Player p) {
		return player_country_map.get(p);
	}

	/**
	 * This method allows the player to choose and place the armies in a particular
	 * country
	 * 
	 * @param countryName the country to be assigned armies
	 */
	public String placeArmyByCountryName(String countryName) {
		Player p = playerList.get(getPlayerTurn() - 1);
		if (p.getIntialArmies() > 0) {
			Country c1 = mapInstance.getCountryByName(countryName);
			List<Country> playerCountryList = getCountriesConqueredBy(p);
			for (Country obj : playerCountryList) {
				if (obj.equals(c1)) {
					c1.addCountryArmies(1);
					p.setIntialArmies(p.getIntialArmies() - 1);
					System.out.println("Remaining Armies: " + p.getIntialArmies());
					errorFlag = "false";
					break;

				} else
					errorFlag = "Not your country!";
				if (p.getIntialArmies() == 0) {
					p.setCurrentPhase(GamePhase.REINFORCEMENT);
				}
			}

		} else {
			errorFlag = "No armies left!";
		}
		if (errorFlag.equals("false")) {
			setNextPlayerTurn();
			if (playerList.get(getPlayerTurn() - 1).getIntialArmies() == 0) {
				resetPlayerTurn();
			}
		}
		return errorFlag;

	}

	/**
	 * Randomly assigns the armies to each player in round robin fashion
	 */
	public void placeAll() {
		setNextPlayerTurn();
		int playersLeftForAssign = playerList.size();

		while (playersLeftForAssign > 0) {

			Player p = playerList.get(getPlayerTurn() - 1);
			if (p.getIntialArmies() > 0) {

				List<Country> playerCountryList = getCountriesConqueredBy(p);
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				p.setIntialArmies(p.getIntialArmies() - 1);
				randomCountry.addCountryArmies(1);
//				HashMap<String, Object> eventPayload = new HashMap<>();
//				eventPayload.put("countryName", randomCountry.getCountryName());
			} else {
				playersLeftForAssign--;
			}
			setNextPlayerTurn();
		}
		for (Player p : playerList) {
			p.setCurrentPhase(GamePhase.REINFORCEMENT);
			System.out.println("\nFor player : " + p.getPlayerId() + " " + p.getPlayerName());
			for (Country c : player_country_map.get(p)) {
				System.out.println("Owns Country : " + c.getCountryName() + " and has Armies : " + c.getCountryArmy());
			}
		}
		System.out.println();

		resetPlayerTurn();
		nextTurn(playerList.get(getPlayerTurn()));

	}

	Country countryAttacking = null;
	Country countryDefending = null;
	Player attacker = null;
	Player defender = null;

	/**
	 * to set calls for game phase commands reinforce,attack, fortify
	 * 
	 * @param inputCommand
	 * @return
	 */
	public String startGamePhase(String inputCommand) {

		String[] commands = inputCommand.split(" ");
		Player p = playerList.get(getPlayerTurn() - 1);

		switch (commands[0]) {

		case "reinforce":

			if (commands.length == 3 && p.getCurrentPhase() == GamePhase.REINFORCEMENT
					&& Pattern.matches("[0-9]", commands[2]) && Integer.parseInt(commands[2]) > 0) {
				// If player has more then 5 cards then he/she must have to exchange the cards
				if (!p.hasMoreThanFiveCards()) {
					errorFlag = p.reinforceArmy(commands[1], Integer.parseInt(commands[2]));
					if (p.getPlayerReinforceArmy() == 0) {
						p.setCurrentPhase(GamePhase.ATTACK);
						System.out.println("phase set now: " + p.getCurrentPhase());
					}
				} else {
					errorFlag = "Please exchnage the cards first";
					p.checkForExchangeCards();
				}

			} else
				errorFlag = "Invalid command!";

			break;

		case "exchangecards":
			try {
				if ((commands.length == 4 || (commands.length == 2 && commands[1].equalsIgnoreCase("-none"))
						&& p.getCurrentPhase() == GamePhase.REINFORCEMENT) && Pattern.matches("[0-9]", commands[1])
						&& Pattern.matches("[0-9]", commands[2]) && Pattern.matches("[0-9]", commands[3])
						&& Integer.parseInt(commands[1]) > 0 && Integer.parseInt(commands[2]) > 0
						&& Integer.parseInt(commands[3]) > 0) {
					if (commands.length == 4) {
						errorFlag = MainClass.getM_instance().exchangeCardsForArmy(p, Integer.parseInt(commands[1]),
								Integer.parseInt(commands[2]), Integer.parseInt(commands[3]))
										? "Successfully exchanged the cards"
										: "Not have enough cards!";
					}
				} else {
					errorFlag = "Invalid Command";

				}
			} catch (NumberFormatException e) {
				errorFlag = "Not A Valid Input!";
			}

			break;

		case "attack":
			errorFlag = "" + "false";
			if (p.getCurrentPhase() != GamePhase.ATTACK) {

				errorFlag = "Invalid command!";

			}

			else if (commands.length == 2 && commands[1].equals("-noattack")) {
				if (countryDefending != null && countryDefending.getCountryArmy() == 0) {
					errorFlag = "invalid command!";
					break;
				}

				errorFlag = "false";
				System.out.println("Attack Over!");
				p.setCurrentPhase(GamePhase.FORTIFICATION);

			} else if (commands.length == 4) {

				if (!p.getPlayerCountries().contains(Map.getM_instance().getCountryByName(commands[1]))
						|| p.getPlayerCountries().contains(Map.getM_instance().getCountryByName(commands[2]))) {

					errorFlag = "Check country ownership again";
				} else {
					countryAttacking = mapInstance.getCountryByName(commands[1]);
					countryDefending = mapInstance.getCountryByName(commands[2]);

					attacker = playerList.get(countryAttacking.getCountryOwner() - 1);
					defender = playerList.get(countryDefending.getCountryOwner() - 1);
					if (commands[3].equals("-allout")) {
						errorFlag = "false";
						alloutAttack(countryAttacking, countryDefending, attacker, defender);
						String s = attackResult(countryAttacking, countryDefending, attacker);
						attacker.setAttackResult(s);
						System.out.println(s);

					} else if (Pattern.matches("[0-9]", commands[3]) && Integer.parseInt(commands[3]) > 0) {
						errorFlag = "false";
						doAttack(countryAttacking, countryDefending, Integer.parseInt(commands[3]), attacker);
						System.out.println("Defender's Turn :" + defender.getPlayerName());
					}
				}

			} else
				errorFlag = "Invalid command!";

			break;
		case "defend":
			if (commands.length == 2 && p.getCurrentPhase() == GamePhase.ATTACK && Pattern.matches("[0-9]", commands[1])
					&& Integer.parseInt(commands[1]) > 0) {
				if (!attacker.getDiceResult().isEmpty()) {
					errorFlag = "false";
					doDefend(Integer.parseInt(commands[1]), attacker, defender, countryAttacking, countryDefending);
					System.out.println(attackResult(countryAttacking, countryDefending, attacker));
				} else
					errorFlag = "wait for attacker's turn";
			} else
				errorFlag = "Invalid command!";

			break;
		case "attackmove":
			if (commands.length == 2 && p.getCurrentPhase() == GamePhase.ATTACK
					&& countryDefending.getCountryArmy() == 0 && Pattern.matches("[0-9]", commands[1])
					&& Integer.parseInt(commands[1]) > 0) {

				moveArmies(attacker, countryAttacking, countryDefending, Integer.parseInt(commands[1]));
			} else {
				errorFlag = "Invalid command!";
			}
			break;
		case "fortify":
			if (p.getCurrentPhase() == GamePhase.FORTIFICATION) {
				if (commands.length == 2 && commands[1].equals("none")) {
					errorFlag = "false";
					p.setFortificationDone(true);

					System.out.println("Fortification over!");
					nextTurn(p);
					System.out.println(
							"Next Player Turn " + MainClass.playerList.get(getPlayerTurn() - 1).getPlayerName());
				} else if (commands.length == 4 && Pattern.matches("[0-9]", commands[3])
						&& Integer.parseInt(commands[3]) > 0) {
					if (p.getPlayerCountries().contains(mapInstance.getCountryByName(commands[1]))
							&& p.getPlayerCountries().contains(mapInstance.getCountryByName(commands[2]))) {
						errorFlag = "false";
						Country from = mapInstance.getCountryByName(commands[1]);
						Country to = mapInstance.getCountryByName(commands[2]);
						p.fortify(from, to, Integer.parseInt(commands[3]));
						nextTurn(p);
						System.out.println(
								"Next Player Turn " + MainClass.playerList.get(getPlayerTurn() - 1).getPlayerName());
					} else
						errorFlag = "the country doesnot exist or isnot owned by you ";

				} else
					errorFlag = "Invalid command!";

			} else
				errorFlag = "Invalid command!";
			break;
		}
		return errorFlag;

	}

	/**
	 * @param attackingCountry country attacking
	 * @param defendingCountry country being attacked
	 * @param attacker         player attacking
	 * @return string declaring the winner
	 */
	public String attackResult(Country attackingCountry, Country defendingCountry, Player attacker) {
		if (attackingCountry.getCountryArmy() == 1) {
			attacker.setAttackResult("Defender won!");

			return "Defender won!";
		} else if (defendingCountry.getCountryArmy() == 0) {
			mapPlayerToCountry(attacker, defendingCountry);
			unmapPlayerToCountry(playerList.get(defendingCountry.getCountryOwner()), defendingCountry);
			assignCardToPlayer(attacker, pickUpCardFromDeck());
			if (gameOver(attacker) && mode.equalsIgnoreCase("tournament")) {
				List<String> temp;
				if (tournamentResult.results.get(tournamentController.currentMap).isEmpty()) {
					temp = new ArrayList<String>();
				} else
					temp = tournamentResult.results.get(tournamentController.currentMap);
				temp.add(attacker.getPlayerName());
				tournamentResult.results.put(tournamentController.currentMap, temp);
				if (tournamentResult.results.size() == tournamentObject.getGameMaps().size() && tournamentResult.results
						.get(tournamentObject.getGameMaps().size() - 1).size() == tournamentObject.getNumGames()) {
					tournamentResult.end = true;

				}
			} else if (gameOver(attacker)) {
				System.out.println("Game Over! " + attacker.getPlayerName() + " wins!");
			}
			errorFlag = "You have to move armies";
			attacker.setAttackResult("Attacker won! Country conquered");
			return "Attacker won! Country conquered";

		} else
			return "Enter attack -noattack to end else continue";
	}

	/**
	 * @param player  the player to disown the country
	 * @param country the country to be disowned
	 */
	private void unmapPlayerToCountry(Player player, Country country) {
		player_country_map.get(player).remove(country);
	}

	/**
	 * This method rolls the dice for the defender
	 * 
	 * @param numDice          number of dice rolls the defender decides
	 * @param attacker
	 * @param defender
	 * @param countryAttacking
	 * @param countryDefending
	 */
	public void doDefend(int numDice, Player attacker, Player defender, Country countryAttacking,
			Country countryDefending) {
		if (!checkDiceRD(numDice, countryDefending)) {
			errorFlag = "invalid defender dice";
			return;
		}
		roll(defender, numDice);
		attacker.attack(countryAttacking, countryDefending, defender);
	}

	/**
	 * to check game end state
	 * 
	 * @param p
	 */
	public boolean gameOver(Player p) {
		if (player_country_map.get(p).size() == mapInstance.getCountries().size()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Method to execute the 3rd rule: to exchange cards with army, and remove those
	 * cards from player's cardList
	 * 
	 * @param player
	 * @param countOfArtillery
	 * @param countOfCavalry
	 * @param countOfInfantry
	 */
	public boolean exchangeCardsForArmy(Player player, int countOfArtillery, int countOfCavalry, int countOfInfantry) {

		boolean operationStatus = false;

		if (countOfArtillery == 3 && countOfCavalry == 0 && countOfInfantry == 0) {
			player.setPlayerReinforceArmy(player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);

			player.setCardExchangeCount(player.getCardExchangeCount() + 1);

			player.getPlayerCards().replace(Card.ARTILLERY, (player.getPlayerCards().get(Card.ARTILLERY) - 3));

			operationStatus = true;

			MainClass.globalCardDeck.replace(Card.ARTILLERY, MainClass.globalCardDeck.get(Card.ARTILLERY) + 3);

		} else if (countOfArtillery == 0 && countOfCavalry == 3 && countOfInfantry == 0) {
			player.setPlayerReinforceArmy(player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);

			player.setCardExchangeCount(player.getCardExchangeCount() + 1);

			player.getPlayerCards().replace(Card.CAVALRY, (player.getPlayerCards().get(Card.CAVALRY) - 3));

			operationStatus = true;

			MainClass.globalCardDeck.replace(Card.CAVALRY, MainClass.globalCardDeck.get(Card.CAVALRY) + 3);

		} else if (countOfArtillery == 0 && countOfCavalry == 0 && countOfInfantry == 3) {
			player.setPlayerReinforceArmy(player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);

			player.setCardExchangeCount(player.getCardExchangeCount() + 1);

			player.getPlayerCards().replace(Card.INFANTRY, (player.getPlayerCards().get(Card.INFANTRY) - 3));

			operationStatus = true;

			MainClass.globalCardDeck.replace(Card.INFANTRY, MainClass.globalCardDeck.get(Card.INFANTRY) + 3);
		}

		else if (countOfArtillery == 1 && countOfCavalry == 1 && countOfInfantry == 1) {
			player.setPlayerReinforceArmy(player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);

			player.setCardExchangeCount(player.getCardExchangeCount() + 1);

			player.getPlayerCards().replace(Card.ARTILLERY, (player.getPlayerCards().get(Card.ARTILLERY) - 1));
			player.getPlayerCards().replace(Card.CAVALRY, (player.getPlayerCards().get(Card.CAVALRY) - 1));
			player.getPlayerCards().replace(Card.INFANTRY, (player.getPlayerCards().get(Card.INFANTRY) - 1));

			operationStatus = true;

			MainClass.globalCardDeck.replace(Card.ARTILLERY, MainClass.globalCardDeck.get(Card.ARTILLERY) + 1);
			MainClass.globalCardDeck.replace(Card.CAVALRY, MainClass.globalCardDeck.get(Card.CAVALRY) + 1);
			MainClass.globalCardDeck.replace(Card.INFANTRY, MainClass.globalCardDeck.get(Card.INFANTRY) + 1);

		} else {
			System.out.println("cards selection is invalid! Please choose it again");
		}

		return operationStatus;
	}

	/**
	 * 
	 * @param player      the player object to be checked whether he owns the given
	 *                    country
	 * @param countryName the country to be checked
	 * @return true if the player owns the country
	 */
	public boolean countryBelongsToPlayer(Player player, String countryName) {
		if (player_country_map.get(player).contains(mapInstance.getCountryByName(countryName)))
			return true;
		else
			return false;

	}

	/**
	 * 
	 * @param countryName the country to be assigned an army
	 * @param armyNumber  the number of armies to be assigned
	 */
	public void PlaceArmy(String countryName, int armyNumber) {

		Country country = mapInstance.getCountryByName(countryName);
		country.setCountryArmy(armyNumber + country.getCountryArmy());
	}

	/**
	 * This function is generating the global deck of cards of all the type
	 * gloabalCardArray 0th position value describes: Infantry type of card
	 * gloabalCardArray 1st position value describes: Cavalry type of card
	 * gloabalCardArray 2nd position value describes: Artillery type of card
	 * 
	 */
	public void generateDeck() {

		int numberOfTotalCards = 40;

		if (numberOfTotalCards > 0) {

			int divison = (int) Math.ceil(numberOfTotalCards / 3);
			globalCardDeck.put(Card.INFANTRY, Integer.valueOf(divison));

			numberOfTotalCards -= divison;
			int divison2 = (int) Math.ceil(numberOfTotalCards / 2);
			globalCardDeck.put(Card.CAVALRY, Integer.valueOf(divison2));

			numberOfTotalCards -= divison2;
			int divison3 = (int) Math.ceil(numberOfTotalCards);
			globalCardDeck.put(Card.ARTILLERY, Integer.valueOf(divison3));
			System.out.println(globalCardDeck);

		}

	}

	public HashMap<String, Integer> getDeck() {
		return globalCardDeck;
	}

	/**
	 * to remove a card from deck of specified type
	 * 
	 * @param cardType
	 * @return true if cards get successfully removed from deck
	 */
	public boolean removeCardFromDeck(String cardType) {

		boolean removalSuccess = false;
		int new_val = 0;
		switch (cardType) {

		case Card.ARTILLERY:
			new_val = globalCardDeck.get(Card.ARTILLERY) - 1;
			globalCardDeck.replace(Card.ARTILLERY, new_val);
			removalSuccess = true;
			break;

		case Card.CAVALRY:
			new_val = globalCardDeck.get(Card.CAVALRY) - 1;
			globalCardDeck.replace(Card.CAVALRY, new_val);
			removalSuccess = true;
			break;

		case Card.INFANTRY:
			new_val = globalCardDeck.get(Card.INFANTRY) - 1;
			globalCardDeck.replace(Card.INFANTRY, new_val);
			removalSuccess = true;
			break;

		default:
			removalSuccess = false;
		}

		return removalSuccess;

	}

	/**
	 * to add a card to deck of specified type
	 * 
	 * @param cardType
	 * @return true if card get added to deck successfully
	 */
	public boolean addCardToDeck(String cardType) {

		boolean additionSuccess = false;
		int new_val = 0;
		switch (cardType) {

		case Card.ARTILLERY:
			new_val = globalCardDeck.get(Card.ARTILLERY) + 1;
			globalCardDeck.replace(Card.ARTILLERY, new_val);
			additionSuccess = true;
			break;

		case Card.CAVALRY:
			new_val = globalCardDeck.get(Card.CAVALRY) + 1;
			globalCardDeck.replace(Card.CAVALRY, new_val);
			additionSuccess = true;
			break;

		case Card.INFANTRY:
			new_val = globalCardDeck.get(Card.INFANTRY) + 1;
			globalCardDeck.replace(Card.INFANTRY, new_val);
			additionSuccess = true;
			break;

		default:
			additionSuccess = false;
		}

		return additionSuccess;

	}

	/**
	 * This method is used to pick one card from global card deck
	 * 
	 * @return Card object
	 */
	public Card pickUpCardFromDeck() {
		String[] cardTypeArray = { "Artillery", "Cavalry", "Infantry" };

		int pickUpIndex = (new Random().nextInt(cardTypeArray.length));
		// System.out.println("pickupIndex:" + pickUpIndex);
		Card card = new Card(cardTypeArray[pickUpIndex]);

		removeCardFromDeck(card.getCardType());

		return card;

	}

	/**
	 * This method is used to assign card to player
	 * 
	 * @param player
	 * @param card
	 */
	public void assignCardToPlayer(Player player, Card card) {
		if (player.getPlayerCards().containsKey(card.getCardType())) {

			player.getPlayerCards().replace(card.getCardType(), player.getPlayerCards().get(card.getCardType()) + 1);
		} else {

			player.getPlayerCards().put(card.getCardType(), 1);
		}

		removeCardFromDeck(card.getCardType());
	}

	/**
	 * perform attacks till attacker conquers country or has no more armies to
	 * attack for a country
	 * 
	 * @param countryAttacking
	 * @param countryDefending
	 * @param attacker
	 * @param defender
	 */
	public void alloutAttack(Country countryAttacking, Country countryDefending, Player attacker, Player defender) {
		int numDice = 3;
		while (countryAttacking.getCountryArmy() > 1 && countryDefending.getCountryArmy() > 0) {
			numDice = (numDice <= countryAttacking.getCountryArmy() - 1) ? 3 : countryAttacking.getCountryArmy() - 1;
			if (!canAttack(countryAttacking, countryDefending)) {
				return;
			}
			if (!checkDiceRA(numDice, countryAttacking)) {
				return;
			}
			System.out.println("Attacking country has " + countryAttacking.getCountryArmy() + " armies");
			System.out.println("numDice for attacker: " + numDice);
			roll(attacker, numDice);
			numDice = 2;

			numDice = (numDice <= countryDefending.getCountryArmy()) ? numDice : 1;
			if (!checkDiceRD(numDice, countryDefending)) {
				return;
			}

			System.out.println("Defending country has " + countryDefending.getCountryArmy() + " armies");
			System.out.println("numDice for defender: " + numDice);
			roll(defender, numDice);
			attacker.attack(countryAttacking, countryDefending, defender);

		}
	}

	/**
	 * This method rolls the dice for the attacker
	 * 
	 * @param countryAttacking
	 * @param countryDefending
	 * @param numDice          the number of dice rolls decide by attacker
	 * @param attacker
	 */
	public void doAttack(Country countryAttacking, Country countryDefending, int numDice, Player attacker) {

		if (!canAttack(countryAttacking, countryDefending)) {
			System.out.println("country defender wrong");
			errorFlag = "invalid attack";
			return;
		}

		if (!checkDiceRA(numDice, countryAttacking)) {
			System.out.println("dice wrong");
			errorFlag = "invalid attacker dice";
			return;
		}
		System.out.println("num of dice rolls" + numDice);
		roll(attacker, numDice);
	}

	/**
	 * Fortification base method
	 * 
	 * @param from country from where armies would be sent
	 * @param to   country to where armies would be sent
	 * @param army number of armies used to fortify
	 */
	public void setFortify(String from, String to, int army) {

		Country countryTo = null, countryFrom = null;
		for (Country obj : mapInstance.getCountries().values()) {

			if (obj.getCountryName().equalsIgnoreCase(from)) {
				countryFrom = obj;
			}
			if (obj.getCountryName().equalsIgnoreCase(to)) {
				countryTo = obj;
			}

		}
		playerList.get(getPlayerTurn() - 1).fortify(countryFrom, countryTo, army);
	}

	/**
	 * to check if two countries are connected and belongs to same owner
	 * 
	 * @param from  country name whose neighbor has to be checked
	 * @param to    country name which is checked
	 * @param owner the player owning the given countries
	 * @return adjFlag determines the given countries are neighbors or not
	 */
	public boolean checkNeighbours(Country from, Country to, int owner) {
		adjFlag = false;
		visited.clear();
		visited.add(from);

		if (from.getCountryOwner() == to.getCountryOwner()) {
			//System.out.println("in check neighbours: " + "visited: "+ visited + " from: " + from + " to: " + to + " owner: "+ owner);
			searchNeighbors(visited, from, to, owner);
		}
		else {
			//System.out.println("Fortification can not be dobne because you don't own country "+ to.getCountryName());
		}
		return adjFlag;
	}

	/**
	 * 
	 * @param visited the list of countries traversed
	 * @param from    country 1 to be checked for neighbor
	 * @param to      country 2 to be checked for neighbor
	 * @param owner   the player owning countries to be checked
	 */

	private void searchNeighbors(List<Country> visited, Country from, Country to, int owner) {
		//System.out.println("country id in search"+from.getCountryID());
		ArrayList<Integer> listOfNeighbours = mapInstance.getBorders().get(from.getCountryID());
		//System.out.println(listOfNeighbours);
		if (!listOfNeighbours.contains(to.getCountryID()) && visited.size()<=10) {
		
			visited.add(from);
			
			for (int i = 0; i < listOfNeighbours.size(); i++) {
				//System.out.println("In search Neighbours: get country owner: " + mapInstance.getCountries().get(i).getCountryOwner());
				if (mapInstance.getCountries().get(listOfNeighbours.get(i)).getCountryOwner() == owner) {
					//System.out.println("first element of neighbours: " + mapInstance.getCountries().get(listOfNeighbours.get(i)) );
					Country mayBecomeFrom = mapInstance.getCountries().get(listOfNeighbours.get(i));
					//System.out.println("Array of Visited: " + visited);
					if (!visited.contains(mayBecomeFrom))  {
						searchNeighbors(visited, mayBecomeFrom, to, owner);
					}
					else {
						return;
					}
				}
				else {
					return;
				}
			
			}
		} else {
			adjFlag = true;
			return;
		}
	}

	/**
	 * To show map
	 */
	public void showmap() {
		if (player_country_map.isEmpty()) {
			if (mapInstance.getBorders().isEmpty()) {
				errorFlag = "Cannot show map!";
			} else
				showmapForMapPhase();
		} else
			showmapForGamePhase();
	}

	/**
	 * to showmap for map phase, list of countries,continent it belongs to and
	 * adjacent countries
	 */
	public void showmapForMapPhase() {
		for (Country c : mapInstance.getCountries().values()) {
			System.out.println("\nCountry: " + c.getCountryName() + " || Continent: "
					+ mapInstance.getContinents().get(c.getContinentID()).getContinentName() + " ||\nNeighbours :");
			showNeighbors(c);
		}
	}

	/**
	 * to showmap for game phase, list countries, continent it belongs to, owner,
	 * country army and neighbors
	 */
	public void showmapForGamePhase() {

		for (Country c : mapInstance.getCountries().values()) {
			System.out.println("\nCountry: " + c.getCountryName() + " || Continent: "
					+ mapInstance.getContinents().get(c.getContinentID()).getContinentName() + " || Country army: "
					+ c.getCountryArmy() + " || Owner Name:" + playerList.get(c.getCountryOwner() - 1).getPlayerName()
					+ " ||\nNeighbours :");
			showNeighborsForGame(c);

		}
	}

	/**
	 * displays list of neighbor countries of object c for game phase
	 * 
	 * @param c
	 */
	public void showNeighborsForGame(Country c) {
		for (int b : mapInstance.getBorders().get(c.getCountryID())) {
			System.out.println(mapInstance.getCountries().get(b).getCountryName());
		}

	}

	/**
	 * displays list of neighbor countries of object c for map phase
	 * 
	 * @param c
	 */
	public void showNeighbors(Country c) {
		if (mapInstance.getBorders().isEmpty()) {
			errorFlag = "Invalid map";
			return;
		}
		for (int b : mapInstance.getBorders().get(c.getCountryID())) {
			System.out.println(mapInstance.getCountries().get(b).getCountryName());
		}

	}

	/**
	 * to check validity of map
	 */
	public void validatemap() {
		if (mapInstance.getBorders().isEmpty()) {
			errorFlag = "Invalid!";
		} else if (mapOperations.isConnected(mapInstance.getBorders())) {
			System.out.println("Map valid!");
		} else
			errorFlag = "Invalid map!";

	}

	/**
	 * loads map to edit
	 * 
	 * @param s1
	 * @return errorFlag
	 */
	public String editmap(String s1) {
		String[] temp = s1.split(" ");
		try {
			if (mapWriter.loadMap(mapInstance.getContinents(), mapInstance.getCountries(), mapInstance.getBorders(),
					temp[1])) {
				System.out.println("Loaded");
				errorFlag = "false";
				// mapPhase = "edit";
			} else {
				System.out.println("Not Loaded!");
				errorFlag = "false";
			}
		} catch (FileNotFoundException e) {
			errorFlag = e.getLocalizedMessage().toString();
		}
		return errorFlag;
	}

	/**
	 * to editcontinent -add or remove continent
	 * 
	 * @param s1
	 * @return errorFlag
	 */
	public String editcontinent(String s1) {
		s1 = s1 + " stop";
		String[] temp = s1.split(" ");

		for (int i = 1; i < temp.length; i++) {
			if (temp[i].contentEquals("-add")) {
				if (!(temp[i + 1].contentEquals("stop")) && !(temp[i + 2].contentEquals("stop"))) {
					errorFlag = "false";
					try {
						mapOperations.addContinent(mapInstance, mapInstance.getContinents(), temp[i + 1],
								Integer.parseInt(temp[i + 2]), null);

					} catch (Exception e) {
						errorFlag = e.getMessage();
					}

				} else {
					errorFlag = "Enter a valid command";
				}
			} else if (temp[i].contentEquals("-remove")) {
				if (!(temp[i + 1].contentEquals("stop"))) {
					errorFlag = "false";
					try {
						errorFlag = mapOperations.deleteContinent(mapInstance.getContinents(),
								mapInstance.getCountries(), mapInstance.getBorders(), temp[i + 1]);
					} catch (Exception e) {
						errorFlag = e.getMessage().toString();
					}

				} else {
					errorFlag = "Enter a valid command";
				}

			}
		}
		return errorFlag;
	}

	/**
	 * to editcountry -add or remove country
	 * 
	 * @param s1
	 * @return errorFlag
	 */
	public String editcountry(String s1) {
		s1 = s1 + " stop";
		String[] temp = s1.split(" ");
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].contentEquals("-add")) {
				if (!(temp[i + 1].contentEquals("stop")) && !(temp[i + 2].contentEquals("stop"))) {
					errorFlag = "false";
					try {
						mapOperations.addCountry(mapInstance, mapInstance.getContinents(), mapInstance.getCountries(),
								mapInstance.getBorders(), temp[i + 1], temp[i + 2]);

					} catch (Exception e) {
						errorFlag = e.getLocalizedMessage().toString();
					}
				} else {
					errorFlag = "Enter a valid command";
				}
			} else if (temp[i].contentEquals("-remove")) {
				if (!(temp[i + 1].contentEquals("stop"))) {
					errorFlag = "false";
					try {
						errorFlag = mapOperations.deleteCountry(mapInstance.getCountries(), mapInstance.getBorders(),
								temp[i + 1]);

					} catch (Exception e) {
						errorFlag = e.getMessage().toString();
					}

				} else {
					errorFlag = "Enter a valid command";
				}
			}
		}
		return errorFlag;

	}

	/**
	 * to editneighbor -add or remove neighbor
	 * 
	 * @param s1
	 * @return errorFlag
	 */
	public String editneigbor(String s1) {
		String[] temp = s1.split(" ");
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].contentEquals("-add")) {
				if (temp[i + 1] != null || temp[i + 2] != null) { // country name and neighbour country name should
																	// not // be null
					errorFlag = "false";
					try {
						mapOperations.addNeighbours(mapInstance, mapInstance.getCountries(), mapInstance.getBorders(),
								temp[i + 1], temp[i + 2]);
						mapOperations.addNeighbours(mapInstance, mapInstance.getCountries(), mapInstance.getBorders(),
								temp[i + 2], temp[i + 1]);
					} catch (Exception e) {
						errorFlag = e.getLocalizedMessage().toString();
					}
				} else {
					errorFlag = "Enter a valid command";
				}
			} else if (temp[i].contentEquals("-remove")) {
				if (!(temp[i + 1].contentEquals("stop")) && !(temp[i + 2].contentEquals("stop"))) {
					errorFlag = "false";
					try {
						mapOperations.deleteNeighbour(mapInstance.getCountries(), mapInstance.getBorders(), temp[i + 1],
								temp[i + 2]);

					} catch (Exception e) {
						errorFlag = e.getMessage();
					}

				} else {
					errorFlag = "Enter a valid command";
				}
			}
		}
		return errorFlag;
	}

	/**
	 * saves map file
	 * 
	 * @param s1 contains file
	 * @return errorFlag
	 */
	public String savemap(String s1) {
		String[] temp = s1.split(" ");
		try {
			try {
				mapWriter.writeMapFile(mapInstance.getContinents(), mapInstance.getCountries(),
						mapInstance.getBorders(), temp[1]);
				errorFlag = "false";
			} catch (ValidMapException e) {
				errorFlag = e.getLocalizedMessage().toString();
			}
		} catch (IOException e) {
			errorFlag = e.getLocalizedMessage().toString();
		}
		return errorFlag;
	}

	/**
	 * check if from country obj can attack to country obj
	 * 
	 * @param from country object
	 * @param to   country object
	 * @return boolean canAttack
	 */
	public boolean canAttack(Country from, Country to) {

		boolean canAttack = false;
		boolean neighbourFlag = false;

		if (mapInstance.getBorders().get(from.getCountryID()).contains(to.getCountryID()))
			neighbourFlag = true;
		canAttack = neighbourFlag && (from.getCountryOwner() != to.getCountryOwner()) && (from.getCountryArmy() >= 2)
				&& (to.getCountryArmy() > 0) ? true : false;


		return canAttack;
	}

	/**
	 * check condition for dice decision of attacker
	 * 
	 * @param num number of dice decided by attacker
	 * @param c   the country from where attack is carried out
	 * @return check returns true if dice limit fulfills condition
	 */
	public boolean checkDiceRA(int num, Country c) {
		boolean check = true;
		if (num > 3)
			check = false;
		else if (num < 1 || num > c.getCountryArmy() - 1)
			check = false;
		System.out.println(check);
		return check;
	}

	/**
	 * check condition for dice decision of defender
	 * 
	 * @param num number of dice decided by defender
	 * @param c   the country attacked
	 * @return returns true if dice limit fulfills condition
	 */
	public boolean checkDiceRD(int num, Country c) {
		boolean check = true;
		if (num > 2 && num > c.getCountryArmy())
			check = false;
		else {
			check = true;
		}

		return check;
	}

	/**
	 * Rolls the dice for the attack phase
	 * 
	 * @param p player who rolls the dice
	 * @param d object of Dice class
	 * @param n number of legal dice rolls
	 * @return list of the result of the rollDice method
	 */
	public void roll(Player p, int n) {
		Dice d = new Dice(n);
		List<Integer> res = d.rollDice(n, p);
	}

	/**
	 * for moving number of armies to conquered country
	 * 
	 * @param p           player who attacked
	 * @param from        the country from where the attack took place
	 * @param to          the country attacked
	 * @param numOfArmies number of armies to be moved
	 */
	public void moveArmies(Player p, Country from, Country to, int numOfArmies) {
		System.out.println("Attacking Country army before: " + from.getCountryArmy());

		if ((numOfArmies >= p.getDiceWins().size()) && (from.getCountryArmy() - numOfArmies) >= 1) {

			from.remCountryArmies(numOfArmies);
			to.addCountryArmies(numOfArmies);
			errorFlag = "false";
		} else
			errorFlag = "Invalid Command!";
		System.out.println("Attacking Country army: " + from.getCountryArmy());
		System.out.println("Attacked Country army: " + to.getCountryArmy());

	}

	public void nextTurn(Player p) {
		setNextPlayerTurn();
		p = playerList.get(getPlayerTurn() - 1);
		System.out.println("Current Player name: " + p.getPlayerName());
		System.out.println("current player" + p.getPlayerName());
		p.setCurrentPhase(GamePhase.REINFORCEMENT);
		p.setPlayerReinforceArmy(p.assign_army());
		if (p.getStrategy().equals("human")) {
			return;
		} else if (p.getStrategy().equals("loa")) {
			System.out.println("in random");
			RandomStrategy.RandomStrategyReinforcement(p);
		} else if (p.getStrategy().equals("cheater")) {
			CheaterStrategy.cheaterStrategyReinforcement(p);
		} else if (p.getStrategy().equals("aggressive")) {
			AggressiveStrategy.AggresiveStrategyReinforcement(p);
		} else if (p.getStrategy().equals("benevolent")) {
			BenevolentStrategy.BenevolentStrategyReinforcement(p);
		}
	}

	public void setupTournament(String mapFileNames, String playerStratergyNames, String numGames, String maxTurns) {
		mode = "tournament";
		String[] mapFiles = mapFileNames.split("-");
		String[] playerStratergies = playerStratergyNames.split("-");
		tournamentObject.setNumGames(Integer.parseInt(numGames));
		tournamentObject.setMaxTurns(Integer.parseInt(maxTurns));

		for (int i = 0; i < playerStratergies.length; i++) {

			tournamentObject.addGameMaps(mapFiles[i]);
		}
		for (int i = 0; i < playerStratergies.length; i++) {

			tournamentObject.addPlayerStratergies(playerStratergies[i]);
		}
		tournamentController = new TournamentController();

	}

	public void resetGame() {
		playerList.clear();
		mapInstance.resetMap();
	}
}