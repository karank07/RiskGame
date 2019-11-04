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

import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Dice;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.utilities.GamePhase;
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
	public static List<Player> playerList;
	private Map mapInstance;

	private MapOperations mapOperations;
	private MapWriter mapWriter;
	static int turn = 1;
	// static String phase = "loadmap";
	// static String mapPhase = "editmap";
	private boolean gamePlayerSet = false;
	private boolean placeArmyFlag = false;
	public static String errorFlag = "false";

	public static MainClass main_instance = null;

	boolean adjFlag = false;
	List<Country> visited = new ArrayList<Country>();

	/**
	 * @param player_country_map a hash-map for a player and its owned countries
	 */
	private static HashMap<Player, List<Country>> player_country_map = new HashMap<Player, List<Country>>();
	public static HashMap<String, Integer> globalCardDeck;

	static int playerTurn = 0;// place army

	public MainClass() {
		playerList = new ArrayList<Player>();
		globalCardDeck = new HashMap<String, Integer>();
		// globalCardDeck = new ArrayList<Card>();

		mapInstance = Map.getM_instance();
		mapOperations = new MapOperations();
		mapWriter = new MapWriter();

	}

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
		fileName = Paths.get("").toAbsolutePath().toString() + "\\maps\\" + fileName;

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
			List<String> borderString = new ArrayList<String>();
			while (fileData != null) {
				fileData = br.readLine();

				if (fileData.equals("[continents]")) {
					fileData = br.readLine();

					while (!fileData.isEmpty()) {
						continentString.add(fileData);
						fileData = br.readLine();
					}
					// stringToContinent(continentString, continentList);
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
					// stringToCountry(countryString, countryList);
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
						System.out.println(o.getCountryID() + o.getCountryName());

					}

					break;
				}
				errorFlag = "false";
			}

		} catch (FileNotFoundException e) {
			errorFlag = "Given Map file doesnot exist!";
		} finally {

		}
		// phase = "gameplayer";
	}

	int getPlayerTurn() {
		return turn;
	}

	void setNextPlayerTurn() {
		turn++;
		turn = turn > playerList.size() ? 1 : turn;
	}

	void resetPlayerTurn() {
		turn = 1;
	}

	public void gamePlayer(String s1) {
		errorFlag = "false";
		s1 = s1 + " stop";
		String[] temp = s1.split(" ");

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
	}

	/**
	 * 
	 * @param playerName the player to be added in the game
	 */
	public void addPlayer(String playerName) {
		for (Player p : playerList) {
			if (p.getPlayerName().equalsIgnoreCase(playerName)) {
				errorFlag = "Name Already exists!";
				return;
			}
		}
		int playerID = playerList.size() + 1;
		Player p = new Player(playerID, playerName);
		playerList.add(p);
		errorFlag = "false";
		// System.out.println(p.toString());
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
		generateDeck();
		resetPlayerTurn();
		for (Player p : playerList)
			p.setPlayerTotalArmies(getInitialArmies());
		int playerCount = playerList.size();
		int j = 0;
		for (Country country : mapInstance.getCountries().values()) {
			Player player = playerList.get(j % playerCount);
			setNewCountryRuler(player, country, 1);
//			HashMap<String, Object> countryPopulated = new HashMap<>();
//			countryPopulated.put("countryName", country.getCountryName());
			player.setPlayerCountries(player_country_map.get(player));

			j++;
		}

		for (Player p : playerList) {
			System.out.println(
					"\nCountries Allocated to Player " + p.getPlayerId() + " " + p.getPlayerName() + " are : ");
			for (Country c : player_country_map.get(p)) {
				System.out.println(c.getCountryName());
			}
			p.setPlayerReinforceArmy(assign_army(p));
		}
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
		pl.remArmies(1);
		;
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
		if (p.getPlayerTotalArmies() > 0) {
			Country c1 = mapInstance.getCountryByName(countryName);
			List<Country> playerCountryList = getCountriesConqueredBy(p);
			for (Country obj : playerCountryList) {
				if (obj.equals(c1)) {
					c1.addCountryArmies(1);
					playerList.get(p.getPlayerId() - 1).setPlayerTotalArmies(p.getPlayerTotalArmies() - 1);
					System.out.println("Remaining Armies: " + p.getPlayerTotalArmies());
					errorFlag = "false";
					break;

				} else
					errorFlag = "Not your country!";
				if (p.getPlayerTotalArmies() == 0) {
					p.setCurrentPhase(GamePhase.REINFORCEMENT);
				}
			}

		} else {
			errorFlag = "No armies left!";
		}
		if (errorFlag.equals("false")) {
			setNextPlayerTurn();
			if (playerList.get(playerTurn).getPlayerTotalArmies() == 0) {
				resetPlayerTurn();
			}
		}
		return errorFlag;

	}

	/**
	 * Randomly assigns the armies to each player in round robin fashion
	 */
	public void placeAll() {

		int playersLeftForAssign = playerList.size();

		while (playersLeftForAssign > 0) {

			if (playerList.get(getPlayerTurn() % playerList.size()).getPlayerTotalArmies() > 0) {

				Player p = playerList.get(getPlayerTurn() - 1);
				List<Country> playerCountryList = getCountriesConqueredBy(p);
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				p.remArmies(1);
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

	}

	Country countryAttacking = null;
	Country countryDefending = null;
	Player attacker = null;
	Player defender = null;

	public String startGamePhase(String inputCommand) {

		String[] commands = inputCommand.split(" ");
		Player p = playerList.get(getPlayerTurn() - 1);
		System.out.println("player army in startgamephase: " + p.getPlayerTotalArmies());

		switch (commands[0]) {

		case "reinforce":
			if (commands.length == 3 && p.getCurrentPhase() == GamePhase.REINFORCEMENT) {
				errorFlag = p.reinforceArmy(commands[1], Integer.parseInt(commands[2]));
				if (p.getPlayerReinforceArmy() == 0) {
					p.setCurrentPhase(GamePhase.ATTACK);
				}

				} else 	errorFlag = "Invalid command!";
			break;
		case "attack":
			if (p.getCurrentPhase() != GamePhase.ATTACK) {
				errorFlag = "Invalid command!";

			} else if (commands.length == 2 && commands[1].equals("-noattack")) {
				System.out.println("Attack Over!");
				p.setCurrentPhase(GamePhase.FORTIFICATION);

			} else if (commands.length == 4) {

				countryAttacking = mapInstance.getCountryByName(commands[1]);
				countryDefending = mapInstance.getCountryByName(commands[2]);

				attacker = playerList.get(countryAttacking.getCountryOwner() - 1);
				defender = playerList.get(countryDefending.getCountryOwner() - 1);
				if (commands[3].equals("-allout")) {
					alloutAttack(countryAttacking, countryDefending, attacker, defender);
					System.out.println(attackResult(countryAttacking, countryDefending, attacker));

				} else {
					doAttack(countryAttacking, countryDefending, Integer.parseInt(commands[3]), attacker);
					System.out.println("Defender's Turn :" + defender.getPlayerName());
				}
			} else
				errorFlag = "Invalid command!";

			break;
		case "defend":
			if (commands.length == 2 && p.getCurrentPhase()==GamePhase.ATTACK) {
				if (!attacker.getDiceResult().isEmpty()) {
					doDefend(Integer.parseInt(commands[1]), attacker, defender, countryAttacking, countryDefending);
					System.out.println(attackResult(countryAttacking, countryDefending, attacker));
				} else
					errorFlag = "wait for attacker's turn";
			} else
				errorFlag = "Invalid flag!";

			break;
		case "attackmove":
			if (commands.length == 2 && p.getCurrentPhase()==GamePhase.ATTACK) {
				if (countryDefending.getCountryArmy() == 0) {
					moveArmies(attacker, countryAttacking, countryDefending, Integer.parseInt(commands[1]));
				}
			} else
				errorFlag = "Invalid command!";
			break;
		case "fortify":
			if (p.getCurrentPhase() == GamePhase.FORTIFICATION) {
				if (commands.length == 2 && commands[1].equals("none")) {
					System.out.println("Fortification over!");
				} else if (commands.length == 4) {
					Country from = mapInstance.getCountryByName(commands[1]);
					Country to = mapInstance.getCountryByName(commands[2]);
					p.fortify(from, to, Integer.parseInt(commands[3]));
					p.setCurrentPhase(GamePhase.REINFORCEMENT);
					p.setPlayerReinforceArmy(assign_army(p));
					setNextPlayerTurn();

				} else
					errorFlag = "Invalid command!";

			} else
				errorFlag = "Invalid command!";
			break;
		}
		return errorFlag;

	}

	public String attackResult(Country attackingCountry, Country defendingCountry, Player attacker) {
		if (countryAttacking.getCountryArmy() == 1) {
			return "Defender won!";
		} else if (countryDefending.getCountryArmy() == 0) {
			mapPlayerToCountry(attacker, countryDefending);
			unmapPlayerToCountry(defender, countryDefending);
			return "Attacker won! Country conquered";

		} else
			return "continue attacking?";
	}

	private void unmapPlayerToCountry(Player player, Country country) {
		player_country_map.get(player).remove(country);
	}

	void doDefend(int numDice, Player attacker, Player defender, Country countryAttacking, Country countryDefending) {
		if (!checkDiceRD(numDice, countryDefending)) {
			errorFlag = "invalid defender dice";
			return;
		}
		roll(defender, numDice);
		attacker.attack(countryAttacking, countryDefending, defender);
	}

	public int assign_army(Player player) {
		int reinforceAmry;

		// 1st rule: country/3...if it's less then 3; then assign 3 army minimum
		reinforceAmry = (player.getPlayerCountries().size() / 3) >= 3 ? (player.getPlayerCountries().size() / 3) : 3;

		// 2nd rule: check if player owns all countries of any of the continents
		for (int i = 0; i < mapInstance.getContinents().size(); i++) {
			if (player.getPlayerCountries().equals(mapInstance.getCountriesOfContinent().get(i))) {
				reinforceAmry = reinforceAmry + mapInstance.getContinents().get(i).getContinentControlValue();
			}
		}

		return reinforceAmry;

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
	private void exchangeCardsForArmy(Player player, int countOfArtillery, int countOfCavalry, int countOfInfantry) {

		// display cards owned by player
		// Then player will select the card from UI
		// selected card are given in arguments

		if (countOfArtillery <= player.getPlayerCards().get(Card.ARTILLERY)
				&& countOfCavalry <= player.getPlayerCards().get(Card.CAVALRY)
				&& countOfInfantry <= player.getPlayerCards().get(Card.INFANTRY)) {
			// now it's valid card selection

			// do exchange cards for army

			// if user wants to exchange same type of any 3 cards
			if (countOfArtillery == 3 || countOfCavalry == 0 || countOfInfantry == 0) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
				// increase cardExchangeCount of player object
				player.setCardExchangeCount(player.getCardExchangeCount() + 1);

				// remove 3 artillery cards from player's cards
				player.getPlayerCards().replace(Card.ARTILLERY, (player.getPlayerCards().get(Card.ARTILLERY) - 3));

				// add these cards in global deck
				MainClass.globalCardDeck.replace(Card.ARTILLERY, MainClass.globalCardDeck.get(Card.ARTILLERY) + 3);

			} else if (countOfArtillery == 0 || countOfCavalry == 3 || countOfInfantry == 0) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
				// increase cardExchangeCount of player object
				player.setCardExchangeCount(player.getCardExchangeCount() + 1);

				// remove 3 cavalry cards from player's cards
				player.getPlayerCards().replace(Card.CAVALRY, (player.getPlayerCards().get(Card.CAVALRY) - 3));

				// add these cards in global deck
				MainClass.globalCardDeck.replace(Card.CAVALRY, MainClass.globalCardDeck.get(Card.CAVALRY) + 3);

			} else if (countOfArtillery == 0 || countOfCavalry == 0 || countOfInfantry == 3) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
				// increase cardExchangeCount of player object
				player.setCardExchangeCount(player.getCardExchangeCount() + 1);

				// remove 3 Infantry cards from player's cards
				player.getPlayerCards().replace(Card.INFANTRY, (player.getPlayerCards().get(Card.INFANTRY) - 3));

				// add these cards in global deck
				MainClass.globalCardDeck.replace(Card.INFANTRY, MainClass.globalCardDeck.get(Card.INFANTRY) + 3);
			}
			// if user wants to exchange 3 different cards
			else if (countOfArtillery == 1 && countOfCavalry == 1 && countOfInfantry == 1) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
				// increase cardExchangeCount of player object
				player.setCardExchangeCount(player.getCardExchangeCount() + 1);

				// remove 1 card of each type from player's cards
				player.getPlayerCards().replace(Card.ARTILLERY, (player.getPlayerCards().get(Card.ARTILLERY) - 1));
				player.getPlayerCards().replace(Card.CAVALRY, (player.getPlayerCards().get(Card.CAVALRY) - 1));
				player.getPlayerCards().replace(Card.INFANTRY, (player.getPlayerCards().get(Card.INFANTRY) - 1));

				// add these cards in global deck
				MainClass.globalCardDeck.replace(Card.ARTILLERY, MainClass.globalCardDeck.get(Card.ARTILLERY) + 1);
				MainClass.globalCardDeck.replace(Card.CAVALRY, MainClass.globalCardDeck.get(Card.CAVALRY) + 1);
				MainClass.globalCardDeck.replace(Card.INFANTRY, MainClass.globalCardDeck.get(Card.INFANTRY) + 1);

			} else {
				System.out.println("cards selection is invalid! Please choose it again");
			}

		}

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
		// System.out.println("in generate deck: :: Countries size: " +
		// countryList.size());
		// gloabalCardArray = new int[3];

		// int numberOfTotalCards = countryList.size();
		int numberOfTotalCards = 40;

		if (numberOfTotalCards > 0) {

			// preparing the global card deck
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
			// if it already contains that type of card than we need to increase to count of
			// that card
			player.getPlayerCards().replace(card.getCardType(), player.getPlayerCards().get(card.getCardType()) + 1);
		} else {
			// add new entry of this type of card
			player.getPlayerCards().put(card.getCardType(), 1);
		}

		removeCardFromDeck(card.getCardType());
	}

	void alloutAttack(Country countryAttacking, Country countryDefending, Player attacker, Player defender) {
		int numDice = 3;
		while (countryAttacking.getCountryArmy() > 1 && countryDefending.getCountryArmy() > 0) {
			numDice = (numDice <= countryAttacking.getCountryArmy() - 1) ? 3 : countryAttacking.getCountryArmy() - 1;
			if (!canAttack(countryAttacking, countryDefending)) {
				return;
			}
			if (!checkDiceRA(numDice, countryAttacking)) {
				return;
			}
			System.out.println("numDice for attacker: " + numDice);
			roll(attacker, numDice);
			System.out.println(attacker.getDiceResult());
			numDice = 2;

			numDice = (numDice <= countryDefending.getCountryArmy()) ? numDice : 1;
			if (!checkDiceRD(numDice, countryDefending)) {
				return;
			}
			System.out.println("numDice for defender: " + numDice);
			roll(defender, numDice);
			System.out.println(defender.getDiceResult());
			attacker.attack(countryAttacking, countryDefending, defender);

		}
	}

	void doAttack(Country countryAttacking, Country countryDefending, int numDice, Player attacker) {

		if (!canAttack(countryAttacking, countryDefending)) {
			errorFlag = "invalid attack";
			return;
		}

		if (!checkDiceRA(numDice, countryAttacking)) {
			errorFlag = "invalid attacker dice";
			return;
		}
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
		/*
		 * if (!phase.contentEquals("fortify")) return;
		 */
		Country countryTo = null, countryFrom = null;
		for (Country obj : mapInstance.getCountries().values()) {

			if (obj.getCountryName().equalsIgnoreCase(from)) {
				countryFrom = obj;
			}
			if (obj.getCountryName().equalsIgnoreCase(to)) {
				countryTo = obj;
			}

		}
		playerList.get(playerTurn - 1).fortify(countryFrom, countryTo, army);
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
		ArrayList<Integer> listOfNeighbours = mapInstance.getBorders().get(from.getCountryID());
		visited.clear();
		visited.add(from);
		// System.out.println(visited.toString());
		if (from.getCountryOwner() == to.getCountryOwner()) {
			searchNeighbors(visited, from, to, owner);
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
		ArrayList<Integer> listOfNeighbours = mapInstance.getBorders().get(from.getCountryID());

		if (!listOfNeighbours.contains(to.getCountryID())) {
			visited.add(from);
			for (int i = 0; i < listOfNeighbours.size(); i++) {
				if (mapInstance.getCountries().get(i).getCountryOwner() == owner) {

					Country mayBecomeFrom = mapInstance.getCountries().get(i);
					if (!visited.contains(mayBecomeFrom)) {
						searchNeighbors(visited, mayBecomeFrom, to, owner);
					}
				}
			}
		} else {
			adjFlag = true;
		}
	}

	public void showmap() {
		if (mapInstance.getBorders().isEmpty()) {
			errorFlag = "Invalid command! Cannot show map!";
		} else
			showmapForMapPhase();
		if (playerList.isEmpty()) {
			errorFlag = "Invalid command!";
		} else
			showmapForGamePhase();

	}

	public void showmapForMapPhase() {
		for (Country c : mapInstance.getCountries().values()) {
			System.out.println("\nCountry: " + c.getCountryName() + " || Continent: "
					+ mapInstance.getContinents().get(c.getContinentID()).getContinentName() + " ||\nNeighbours :");
			showNeighbors(c);
		}
	}

	public void showmapForGamePhase() {

		for (Country c : mapInstance.getCountries().values()) {
			System.out.println("\nCountry: " + c.getCountryName() + " || Continent: "
					+ mapInstance.getContinents().get(c.getContinentID()).getContinentName() + " || Country army: "
					+ c.getCountryArmy() + " || Owner Name:" + playerList.get(c.getCountryOwner() - 1).getPlayerName()
					+ " ||\nNeighbours :");
			showNeighbors(c);

		}
	}

	public void showNeighbors(Country c) {
		for (int b : mapInstance.getBorders().get(c.getCountryID())) {
			System.out.println(mapInstance.getCountries().get(b).getCountryName());
		}

	}

	public void validatemap() {
		if (mapInstance.getBorders().isEmpty()) {
			errorFlag = "Invalid!";
		} else if (mapOperations.isConnected(mapInstance.getBorders())) {
			System.out.println("Map valid!");
		} else
			errorFlag = "Invalid map!";

	}

	public void editmap(String s1) {
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
			e.printStackTrace();
		}

	}

	private List<String> getNeighboursName(int[] neighbours) {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i < neighbours.length; i++) {
			list.add(mapInstance.getCountries().get(neighbours[i] - 1).getCountryName());
		}
		return list;
	}

	public void editcontinent(String s1) {
		s1 = s1 + " stop";
		String[] temp = s1.split(" ");

		for (int i = 1; i < temp.length; i++) {
			if (temp[i].contentEquals("-add")) {
				if (!(temp[i + 1].contentEquals("stop")) && !(temp[i + 2].contentEquals("stop"))) {
					errorFlag = "false";
					try {
						mapOperations.addContinent(mapInstance, mapInstance.getContinents(), temp[i + 1],
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

	}

	public void editcountry(String s1) {
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
						e.printStackTrace();
					}
				} else {
					errorFlag = "Enter a valid command";
				}
			} else if (temp[i].contentEquals("-remove")) {

			}
		}

	}

	public void editneigbor(String s1) {
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
						e.printStackTrace();
					}
				} else {
					errorFlag = "Enter a valid command";
				}
			} else if (temp[i].contentEquals("-remove")) {

			}
		}

	}

	public void savemap(String s1) {
		String[] temp = s1.split(" ");
		try {
			try {
				mapWriter.writeMapFile(mapInstance.getContinents(), mapInstance.getCountries(),
						mapInstance.getBorders(), temp[1]);
				errorFlag = "false";
				// mapPhase = "end";
			} catch (ValidMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean canAttack(Country from, Country to) {

		boolean canAttack=false;
		boolean neighbourFlag=false;
//		System.out.println(from.getCountryArmy());
//		System.out.println(to.getCountryArmy());
		
		if(mapInstance.getBorders().get(from.getCountryID()).contains(to.getCountryID()))
			neighbourFlag=true;
		
		canAttack =neighbourFlag && from.getCountryOwner() != to.getCountryOwner()
				&& from.getCountryArmy() >= 2 && to.getCountryArmy() > 0 ? true : false;

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

	public void moveArmies(Player p, Country from, Country to, int numOfArmies) {
		System.out.println("from army before: " + from.getCountryArmy());
		if (numOfArmies >= p.getDiceWins().size() && (from.getCountryArmy() - numOfArmies) > 1) {
			from.setCountryArmy(from.getCountryArmy() - numOfArmies);
			to.setCountryArmy(to.getCountryArmy() + numOfArmies);
			p.setPlayerTotalCountries(p.getPlayerTotalCountries() + 1);
			p.getPlayerCountries().add(to);

//			  if(map.getCountriesByContinent(MainClass.continents.get(to.getContinentID()-1).getContinentName()).equals(p.getPlayerCountries())) {
//				  
//			  }

		}
		System.out.println("from army: " + from.getCountryArmy());
		System.out.println(to.getCountryArmy());

		// CHANGE THE LIST OF COUNTRY IN MAP PLAYER/////

	}

	// Continue till attacker says NOATTACK
	// Successful attack pr check kro about continent conquered or not and CARD

	/**
	 * 
	 * @param s1 phase command taken as input from console
	 * @return errorFlag to indicate successful execution or not
	 */
	/*
	 * public String phaseDecider(String s1) { String[] temp = new String[10]; temp
	 * = s1.split(" "); int j = 0; System.out.println("\n" + s1); for (int i = 0; i
	 * < temp.length; i++) { temp[i] = temp[i].toLowerCase(); }
	 * 
	 * switch (temp[0]) { case "editcontinent": if (!mapPhase.contentEquals("edit"))
	 * { errorFlag = "Invalid command!"; return errorFlag; } s1 = s1 + " stop"; temp
	 * = s1.split(" ");
	 * 
	 * for (int i = 1; i < temp.length; i++) { if (temp[i].contentEquals("-add")) {
	 * if (!(temp[i + 1].contentEquals("stop")) && !(temp[i +
	 * 2].contentEquals("stop"))) { errorFlag = "false"; try {
	 * mapOperations.addContinent(mapInstance, continents, temp[i + 1],
	 * Integer.parseInt(temp[i + 2]), null); // mapWriter.writeMapFile(continents,
	 * countries, borders, "risk1.txt"); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * } else { errorFlag = "Enter a valid command"; } } else if
	 * (temp[i].contentEquals("-remove")) {
	 * 
	 * } } break;
	 * 
	 * case "editcountry": if (!mapPhase.contentEquals("edit")) { errorFlag =
	 * "Invalid command!"; return errorFlag; } s1 = s1 + " stop"; temp =
	 * s1.split(" "); for (int i = 0; i < temp.length; i++) { if
	 * (temp[i].contentEquals("-add")) { if (!(temp[i + 1].contentEquals("stop")) &&
	 * !(temp[i + 2].contentEquals("stop"))) { errorFlag = "false"; try {
	 * mapOperations.addCountry(mapInstance, continents, countries, borders, temp[i
	 * + 1], temp[i + 2]);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } else { errorFlag =
	 * "Enter a valid command"; } } else if (temp[i].contentEquals("-remove")) {
	 * 
	 * } } break;
	 * 
	 * case "editneighbor": if (!mapPhase.contentEquals("edit")) { errorFlag =
	 * "Invalid command!"; return errorFlag; }
	 * 
	 * for (int i = 0; i < temp.length; i++) { if (temp[i].contentEquals("-add")) {
	 * if (temp[i + 1] != null || temp[i + 2] != null) { // country name and
	 * neighbour country name should // not // be null errorFlag = "false"; try {
	 * mapOperations.addNeighbours(mapInstance, countries, borders, temp[i + 1],
	 * temp[i + 2]); mapOperations.addNeighbours(mapInstance, countries, borders,
	 * temp[i + 2], temp[i + 1]); } catch (Exception e) { e.printStackTrace(); } }
	 * else { errorFlag = "Enter a valid command"; } } else if
	 * (temp[i].contentEquals("-remove")) {
	 * 
	 * } } break;
	 * 
	 * case "savemap": if (mapPhase.contentEquals("end")) { errorFlag =
	 * "Invalid command!"; return errorFlag; }
	 * 
	 * try { try { mapWriter.writeMapFile(continents, countries, borders, temp[1]);
	 * errorFlag = "false"; mapPhase = "end"; } catch (ValidMapException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } } catch (IOException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } break;
	 * 
	 * case "showmap": if (!mapPhase.contentEquals("end")) { if (borders.isEmpty())
	 * { errorFlag = "Invalid command!"; } else showmapForMapPhase(); } else if
	 * (playerList.isEmpty()) { errorFlag = "Invalid command!"; } else
	 * showmapForGamePhase();
	 * 
	 * break; case "validatemap": if (borders.isEmpty()) { errorFlag = "Invalid!"; }
	 * else if (mapOperations.isConnected(borders)) {
	 * System.out.println("Map valid!"); } else errorFlag = "Invalid map!"; break;
	 * case "editmap": if (!mapPhase.contentEquals("editmap")) { errorFlag =
	 * "Invalid command!"; return errorFlag; }
	 * 
	 * try { if (mapWriter.loadMap(continents, countries, borders, temp[1])) {
	 * System.out.println("Loaded"); errorFlag = "false"; mapPhase = "edit"; } else
	 * { System.out.println("Not Loaded!"); errorFlag = "false"; } } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } break;
	 * 
	 * case "loadmap": if (!phase.contentEquals("loadmap")) { errorFlag =
	 * "Invalid command!"; return errorFlag; } phase = "loadmap"; mapPhase = "end";
	 * String fileName = temp[1]; try { readMapFile(fileName); } catch (IOException
	 * e1) { e1.printStackTrace(); // set flag for alert("File Not Found!");
	 * errorFlag = "Check map file name again!"; } break;
	 * 
	 * case "gameplayer": if (!phase.contentEquals("gameplayer")) { errorFlag =
	 * "Invalid command!"; return errorFlag; } errorFlag = "false"; s1 = s1 +
	 * " stop"; temp = s1.split(" ");
	 * 
	 * sp = new StartUpPhase(); for (int i = 1; i < temp.length; i++) { if
	 * (temp[i].contentEquals("-add")) { if (!temp[i + 1].contentEquals("stop")) {
	 * addPlayer(temp[i + 1]); errorFlag = "false"; } else { errorFlag =
	 * "add a valid name"; } } else if (temp[i].contentEquals("-remove")) { if
	 * (!temp[i + 1].contentEquals("stop")) { removePlayer(temp[i + 1]); errorFlag =
	 * "false"; } else { errorFlag = "add a valid name"; }
	 * 
	 * } } System.out.println("\nPlayers:");
	 * 
	 * for (Player p : playerList) { System.out.println(p.getPlayerId() + " " +
	 * p.getPlayerName()); } if (!playerList.isEmpty()) { gamePlayerSet = true;
	 * 
	 * } break; case "populatecountries": if (gamePlayerSet == true &&
	 * !playerList.isEmpty()) { phase = "populatecountries"; } if
	 * (!phase.contentEquals("populatecountries")) { errorFlag = "Invalid command!";
	 * return errorFlag; } errorFlag = "false"; sp = new StartUpPhase();
	 * 
	 * populateCountries(); case "dividearmies": divideInitialArmies(); for (Country
	 * c : countries.values()) { for (Player p : playerList) { if
	 * (c.getCountryOwner() == p.getPlayerId()) {
	 * p.setPlayerTotalArmies(p.getPlayerTotalArmies() - 1); } } } break;
	 * 
	 * case "placearmy":
	 * 
	 * if (!phase.contentEquals("placearmy")) { errorFlag = "Invalid command!";
	 * return errorFlag; } // currentPlayer=1; errorFlag = "false"; if (temp[1] !=
	 * "") { placeArmyByCountry(temp[1]); } else { errorFlag =
	 * "Check the country name entered!"; } if
	 * (playerList.get(currentPlayer).getPlayerTotalArmies() == 0) placeArmyFlag =
	 * true; break;
	 * 
	 * case "placeall": if (!phase.contentEquals("placearmy")) { errorFlag =
	 * "Invalid command!"; return errorFlag; } errorFlag = "false"; placeAll(); if
	 * (playerList.get(currentPlayer).getPlayerTotalArmies() == 0) phase =
	 * "reinforce";
	 * 
	 * case "caclulate armies for reinforcement": for (Player p : playerList) {
	 * rp.beginReinforcement(p);
	 * 
	 * } currentPlayer = 1;// for build 1 implemented for single player
	 * System.out.println("\nTurn for Player " + (currentPlayer)); break; case
	 * "reinforce": if (!phase.contentEquals("reinforce")) { errorFlag =
	 * "Invalid command!"; return errorFlag; } errorFlag = "false"; if (temp[1] ==
	 * "" || temp[2] == "") { errorFlag = "Invalid command!"; break; } else {
	 * setReinforce(temp[1], Integer.parseInt(temp[2]));// temp[1]-countryName,
	 * temp[2]- armyCount
	 * 
	 * // for printing list of all player's countries and armies for (int i = 0; i <
	 * playerList.get(currentPlayer - 1).getPlayerCountries().size(); i++) {
	 * System.out.println(playerList.get(currentPlayer -
	 * 1).getPlayerCountries().get(i).getCountryName() + " " +
	 * playerList.get(currentPlayer -
	 * 1).getPlayerCountries().get(i).getCountryArmy());
	 * 
	 * }
	 * 
	 * } break; case "fortify": if (!phase.contentEquals("fortify")) { errorFlag =
	 * "Invalid command!"; return errorFlag; } errorFlag = "false"; currentPlayer =
	 * 1;// for build 1 static player if (temp[1] == "") { errorFlag =
	 * "Invalid command!";
	 * 
	 * } else if (temp[1].contentEquals("none")) {
	 * System.out.println("Fortification skipped!"); phase = "reinforce";
	 * 
	 * } else { // temp[1]- countryFrom, temp[2]- countryTo, temp[3]- armyCount
	 * setFortify(temp[1], temp[2], Integer.parseInt(temp[3]));
	 * 
	 * }
	 * 
	 * break;
	 * 
	 * default: // set flag for alert("Wrong Input!"); errorFlag =
	 * "Check commands again!"; } return errorFlag; }
	 */
}