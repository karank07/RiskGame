package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.utilities.GamePhase;
import ca.concordia.risk.view.CardExchangeView;
import ca.concordia.risk.view.GameView;

/**
 * This is a model class for a Player with member variables for id, name,
 * countries, armies assigned and cards collected
 * 
 * @author Pranal
 *
 */

public class Player implements Subject {

	/**
	 * @param fortifyCountry name
	 */
	String fortifyCountry;
	/**
	 * @param attack result string
	 */
	String attackResult;
	/**
	 * @param playerId - for unique player id
	 */
	private int playerId;

	/**
	 * @param playerName - for assigning name to the player
	 */
	private String playerName;

	/**
	 * @param playerCountries - list of countries owned by the player
	 */
	private List<Country> playerCountries;

	/**
	 * @param player Armies - total armies of the player
	 */
	private int playerTotalArmies = 0;

	/**
	 * @param playerTotalCountries- total countries owned by a player
	 */
	private int playerTotalCountries = 0;

	/**
	 * @param playerReinforceArmy- armies that player get after reinforcement
	 */
	private int playerReinforceArmy = 3;
	/**
	 * @param cardExchangeCount- maintains a count of turns in which player has
	 *                           exchanged cards for armies
	 */
	private int cardExchangeCount = 0;
	private boolean canAttack=true;
	/**
	 * @param strategy - to decide the strategy of the player object
	 */
	private String strategy = "human";

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	/**
	 * @param diceResult stores the list of result of the number of dices rolled for
	 *                   the player
	 */
	private List<Integer> diceResult = new ArrayList<Integer>();
	private List<Integer> defenderDiceResult = new ArrayList<Integer>();
	boolean isFortificationDone = false;
	String fortifiedCountry;
	int fortifyArmies;
	public boolean armyAssigning = false;
	public boolean reinforcingArmy = false;
	private HashMap<String, Integer> playerCards;
	private int intialArmies;
	private Observer o;
	private GameView gameView = GameView.get_instance();
	MainClass main = MainClass.getM_instance();
	public GamePhase gamePhase;

	/**
	 * @return defender dice result
	 */
	public List<Integer> getDefenderDiceResult() {
		return defenderDiceResult;
	}

	/**
	 * @param defenderDiceResult to set defender dice result
	 */
	public void setDefenderDiceResult(List<Integer> defenderDiceResult) {
		this.defenderDiceResult = defenderDiceResult;
	}

	/**
	 * @param diceWins store the number of wins in the list
	 */
	private List<Integer> diceWins;
	/**
	 * @param playerCards -list of cards the player possesses
	 */
	String reinforceCountry;
	String attackingCountry;
	String defendingCountry;

	/**
	 * @return attacking country name
	 */
	public String getAttackingCountry() {
		return attackingCountry;
	}

	/**
	 * @param attackingCountry to set attacking country name
	 */
	public void setAttackingCountry(String attackingCountry) {
		this.attackingCountry = attackingCountry;
	}

	/**
	 * @return defending country name
	 */
	public String getDefendingCountry() {
		return defendingCountry;
	}

	/**
	 * @param defendingCountry to set defending country name
	 */
	public void setDefendingCountry(String defendingCountry) {
		this.defendingCountry = defendingCountry;
	}

	/**
	 * @return reinforce country name
	 */
	public String getReinforceCountry() {
		return reinforceCountry;
	}

	/**
	 * @param reinforceCountry set reinforce country name
	 */
	public void setReinforceCountry(String reinforceCountry) {
		// notify_observer();
		this.reinforceCountry = reinforceCountry;
	}

	/**
	 * Constructor to instantiate Player object
	 * 
	 * @param playerId
	 * @param playerName
	 */
	public Player(int playerId, String playerName, String strategy) {

		this.playerId = playerId;
		this.playerName = playerName;
		this.playerCountries = new ArrayList<Country>();
		this.playerCards = new HashMap<String, Integer>();
		this.fortifyArmies = 0;
		this.getPlayerCards().put(Card.ARTILLERY, 0);
		this.getPlayerCards().put(Card.CAVALRY, 0);
		this.getPlayerCards().put(Card.INFANTRY, 0);
		this.attackResult = new String("null");
		this.strategy = strategy;

	}

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the playerCountries
	 */
	public List<Country> getPlayerCountries() {
		return playerCountries;
	}

	/**
	 * @param playerCountries the playerCountries to set
	 */
	public void setPlayerCountries(List<Country> playerCountries) {
		this.playerCountries = playerCountries;
	}

	/**
	 * @return the playerCards
	 */
	public HashMap<String, Integer> getPlayerCards() {
		return playerCards;
	}

	/**
	 * @param playerCards the playerCards to set
	 */
	public void setPlayerCards(HashMap<String, Integer> playerCards) {
		this.playerCards = playerCards;
	}

	/**
	 * @return the playerTotalArmies
	 */
	public int getPlayerTotalArmies() {
		return playerTotalArmies;
	}

	/**
	 * @param playerTotalArmies the playerTotalArmies to set
	 */
	public void setPlayerTotalArmies(int playerTotalArmies) {
		this.playerTotalArmies = playerTotalArmies;
	}

	/**
	 * @param addN number of armies to be added
	 *
	 */
	public void addArmies(int addN) {
		this.playerTotalArmies += addN;
		attach(gameView);
		// notify_observer();
	}

	/**
	 * This method returns true indicating fortification is done
	 * 
	 * @return true if fortification finishes
	 */
	public boolean isFortificationDone() {
		return isFortificationDone;
	}

	/**
	 * sets the boolean value if fortification is done
	 * 
	 * @param isFortificationDone
	 */
	public void setFortificationDone(boolean isFortificationDone) {
		this.isFortificationDone = isFortificationDone;
		this.armyAssigning = true;
		if (this.getStrategy().equals("human")) {
			checkForExchangeCards();

		}
	}

	/**
	 * @return fortifyCountry from where the armies are fortified
	 */

	public String getFortifyCountry() {
		return fortifyCountry;
	}

	/**
	 * to set from where the armies are fortified
	 * 
	 * @param fortifyCountry from where the armies are fortified
	 */
	public void setFortifyCountry(String fortifyCountry) {
		this.fortifyCountry = fortifyCountry;
	}

	/**
	 * @return fortifiedCountry to where the armies are fortified
	 */
	public String getFortifiedCountry() {
		return fortifiedCountry;
	}

	/**
	 * to set the country where the armies are fortified
	 * 
	 * @param fortifiedCountry
	 */
	public void setFortifiedCountry(String fortifiedCountry) {
		this.fortifiedCountry = fortifiedCountry;
	}

	/**
	 * @return number of armies to be fortified
	 */
	public int getFortifyArmies() {
		return fortifyArmies;
	}

	/**
	 * to set the number of armies to be fortified
	 * 
	 * @param fortifyArmies
	 */
	public void setFortifyArmies(int fortifyArmies) {
		this.fortifyArmies = fortifyArmies;
	}

	/**
	 * @param n armies deducted from total
	 *
	 */
	public void remArmies(int n) {
		this.playerTotalArmies -= n;
		attach(gameView);
		// notify_observer();
	}

	/**
	 * @return total countries the player own
	 */
	public int getPlayerTotalCountries() {
		playerTotalCountries = main.player_country_map.get(this).size();
		System.out.println("total countries" + playerTotalCountries);
		return playerTotalCountries;
	}

	/**
	 * @param playerTotalCountries sets total countries the player own
	 */
	public void setPlayerTotalCountries(int playerTotalCountries) {
		this.playerTotalCountries = playerTotalCountries;
	}

	/**
	 * @return player reinforced armies
	 */
	public int getPlayerReinforceArmy() {
		return playerReinforceArmy;
	}

	/**
	 * @param playerReinforceArmy update reinforce army after reinforcement phase
	 */
	public void setPlayerReinforceArmy(int playerReinforceArmy) {
		this.playerReinforceArmy = playerReinforceArmy;
		// attach(gameView);
		// notify_observer();
	}

	/**
	 * @return count of times card exhanged by the player
	 */
	public int getCardExchangeCount() {
		return cardExchangeCount;
	}

	/**
	 * @return the attackResult
	 */
	public String getAttackResult() {
		return attackResult;
	}

	/**
	 * @param attackResult the attackResult to set
	 */
	public void setAttackResult(String attackResult) {
		attach(gameView);
		// notify_observer();
		this.attackResult = attackResult;
	}

	/**
	 * @param cardExchangeCount sets count of times card exhanged by the player
	 */
	public void setCardExchangeCount(int cardExchangeCount) {
		this.cardExchangeCount = cardExchangeCount;
	}

	public String toString() {
		return playerId + " " + playerName;
	}

	/**
	 * sets initial value of total armies owner by the player
	 */
	public void setPlayerTotalCountries() {
		for (Country obj : playerCountries) {
			while (obj.getCountryOwner() == playerId) {
				playerTotalCountries++;
			}
		}
	}

	/**
	 * @return the diceResult
	 */
	public List<Integer> getDiceResult() {
		return diceResult;
	}

	public void addDiceResult(List<Integer> result) {
		if (!diceResult.isEmpty())
			diceResult.clear();
		diceResult.addAll(result);
	}

	/**
	 * @param diceResult the diceResult to set
	 */
	public void setDiceResult(List<Integer> diceResult) {
		this.diceResult = diceResult;
	}

	/**
	 * @return the diceWins
	 */
	public List<Integer> getDiceWins() {
		return diceWins;
	}

	/**
	 * @param diceWins the diceWins to set
	 */
	public void setDiceWins(List<Integer> diceWins) {
		this.diceWins = diceWins;
	}

	private ca.concordia.risk.model.Map mapInstance = ca.concordia.risk.model.Map.getM_instance();

	public int assign_army() {

		int reinforceAmry = 0;

		reinforceAmry = (MainClass.player_country_map.get(this).size() / 3) >= 3
				? (MainClass.player_country_map.get(this).size() / 3)
				: 3;

		for (Continent c : mapInstance.getContinents().values()) {

			if (MainClass.player_country_map.get(this)
					.containsAll((mapInstance.getCountriesByContinent(c.getContinentName())))) {

				reinforceAmry = reinforceAmry + c.getContinentControlValue();
				c.setRuler(this.getPlayerId() - 1);
			}
		}

		return reinforceAmry;

	}

	/**
	 * function for command:- reinforce countryname, number (number is army to be
	 * placed in that country)
	 * 
	 * @param countryName for country name
	 * @param armyNumber  number of armies
	 * @param player      for player entity
	 */
	public String reinforceArmy(String countryName, int armyNumber) {
		this.armyAssigning = false;
		if (this.getStrategy().equals("human")) {
			checkForExchangeCards();// to close the UI
		} else {

		}

		this.reinforceCountry = countryName;

		int currentlyUnplacedArmy = this.getPlayerReinforceArmy();
		String errorFlag = "false";
		if (currentlyUnplacedArmy > 0) {

			if (main.countryBelongsToPlayer(this, countryName)) {

				if (armyNumber <= currentlyUnplacedArmy) {

					main.PlaceArmy(countryName, armyNumber);
					currentlyUnplacedArmy -= armyNumber;
					this.setPlayerReinforceArmy(currentlyUnplacedArmy);
					this.addArmies(armyNumber);
				}
				errorFlag = "false";

			} else {
				errorFlag = "This country does not belongs to you!";
			}

			System.out.println("Armies available to reinforce for :" + this.getPlayerId() + " " + this.getPlayerName()
					+ " are " + this.getPlayerReinforceArmy());

		}

//		for (int i = 0; i < this.getPlayerTotalCountries(); i++) {
//			System.out.println("Country: " + this.getPlayerCountries().get(i).getCountryName() + "-->>"
//					+ this.getPlayerCountries().get(i).getCountryArmy());
//		}
		return errorFlag;
	}

	/**
	 * Check whether the current player has 5 or more than cards or not
	 * 
	 * @return boolean
	 */
	public boolean hasMoreThanFiveCards() {
		if (this.getPlayerCards().get(Card.ARTILLERY) + this.getPlayerCards().get(Card.CAVALRY)
				+ this.getPlayerCards().get(Card.INFANTRY) >= 5) {
			return true;
		}

		return false;
	}

	/**
	 * This function performs attack phase and return the result of it
	 * 
	 * @param from     the country attacking
	 * @param to       the country being attacked
	 * @param defender the owner of the country being attacked
	 * @return resultString declaring the name of who wins the roll
	 * 
	 */
	public String attack(Country from, Country to, Player defender) {
		// notify method to close the card exchange view dialogue if it is still oprn
		this.attackingCountry = from.getCountryName();
		this.defendingCountry = to.getCountryName();
		String resultString = "";
		List<Integer> attackerWins = new ArrayList<Integer>();
		List<Integer> defenderWins = new ArrayList<Integer>();
		setDefenderDiceResult(defender.getDiceResult());
		int size = this.getDiceResult().size() < defender.getDiceResult().size() ? this.getDiceResult().size()
				: defender.getDiceResult().size();

		System.out.println("Attacker rolls: " + this.diceResult);
		System.out.println("DeFENDER :(in player class attack method) : "+defender);
		System.out.println("Defender rolls: " + defender.diceResult);
		for (int i = 0; i < size; i++) {
			int a = this.getDiceResult().get(i);
			int d = defender.getDiceResult().get(i);

			if (a > d) {

				to.remCountryArmies(1);
				attackerWins.add(1);
				defender.remArmies(1);
				this.setDiceWins(attackerWins);
			} else {

				this.remArmies(1);
				defenderWins.add(1);
				defender.setDiceWins(defenderWins);
				from.remCountryArmies(1);
			}
		}

		if (attackerWins.size() == defenderWins.size()) {
			resultString = "DEFENDER WINS";
		} else {
			resultString = attackerWins.size() > defenderWins.size() ? "ATTACKER WINS" : "DEFENDER WINS";
		}
		
		System.out.println("attacker :" + attackerWins);
		System.out.println("defender: " + defenderWins);
		System.out.println(resultString);
		attach(gameView);
		// notify_observer();

		return resultString;
	}

	/**
	 * fortification-move armies to another country that belongs to the player
	 * 
	 * @param from  country-name from which armies are sent
	 * @param to    country-name to which armies are sent
	 * @param owner the player fortifying
	 * @param army  number of armies sent
	 */

	public void fortify(Country from, Country to, int army) {
		this.fortifiedCountry = to.getCountryName();
		this.fortifyCountry = from.getCountryName();
		this.fortifyArmies = army;
		boolean adjFlag = main.checkNeighbours(from, to, this.getPlayerId());
		if (adjFlag) {
			if (from.getCountryArmy() - army >= 1) {
				from.remCountryArmies(army);
				to.addCountryArmies(army);
				System.out.println("\nFortification successful");
				this.setFortificationDone(true);

			} else
				System.out.println("There must be atleast one army in a country!");

			this.armyAssigning = true;
			if (this.getStrategy().equals("human")) {
				checkForExchangeCards();
			}

		} else
			System.out.println("Move not possible");

	}

	/**
	 * This function sets the current phase of the player model class
	 * 
	 * @param phase
	 */
	public void setCurrentPhase(GamePhase phase) {
		this.gamePhase = phase;
		this.attach(gameView);
		// notify_observer();
	}

	public GamePhase getCurrentPhase() {
		return gamePhase;
	}

	/**
	 * @return the intialArmies
	 */
	public int getIntialArmies() {
		return intialArmies;
	}

	/**
	 * @param intialArmies the intialArmies to set
	 */
	public void setIntialArmies(int intialArmies) {
		this.intialArmies = intialArmies;
	}

		  
	/**
	 * returns list of the countries that can be attacked by the country c
	 * 
	 * @param c is the country for which attackable countries have to searched for
	 * @return countriesAttackable the list of attackable countries
	 */
	public List<Country> attackableCountries(Country c) {
		List<Country> countriesAttackable = new ArrayList<>();
		List<Country> neighbouringCountries = ca.concordia.risk.model.Map.m_instance.getNeighbourCountries(c);
		
		for (Country neighbour : neighbouringCountries) {
			if (neighbour.getCountryOwner() != c.getCountryOwner()) {
				countriesAttackable.add(neighbour);
			}
		}
		return countriesAttackable;
	}

	/**
	 * Returns the weakest country for the player
	 * 
	 * @param p player
	 * @return weakestCountry
	 * 
	 */
	public Country getWeakestCountry() {

		List<Country> playerCountries = this.getPlayerCountries();

		Country weakestCountry = null;

		int minArmy = Integer.MAX_VALUE;

		for (Country country : playerCountries) {

			int playerArmy = country.getCountryArmy();

			if (playerArmy < minArmy) {
				minArmy = playerArmy;
				weakestCountry = country;
			}
		}

		return weakestCountry;

	}

	/**
	 * Notify method is used to notify all observers to
	 */
	@Override
	public void notify_observer() {
		if (o != null) {
			o.update(this);
		}

	}

	@Override
	public void attach(Observer o) {
		this.o = o;
	}

	@Override
	public void detach(Observer o) {

	}

	public void checkForExchangeCards() {
		attach(CardExchangeView.getCardExchangeViewInstance());
		notify_observer();

	}

	/**
	 * @return the canAttack
	 */
	public boolean getCanAttack() {
		return canAttack;
	}

	/**
	 * @param canAttack the canAttack to set
	 */
	public void setCanAttack(boolean canAttack) {
		this.canAttack = canAttack;
	}

}
