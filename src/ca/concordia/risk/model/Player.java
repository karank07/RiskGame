package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.utilities.GamePhase;
import ca.concordia.risk.view.CardExchangeView;

/**
 * This is a model class for a Player with member variables for id, name,
 * countries, armies assigned and cards collected
 * 
 * @author Pranal
 *
 */

public class Player implements Subject {

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

	/**
	 * @param diceResult stores the list of result of the number of dices rolled for
	 *                   the player
	 */
	private List<Integer> diceResult = new ArrayList<Integer>();

	/**
	 * @param diceWins store the number of wins in the list
	 */
	private List<Integer> diceWins;
	/**
	 * @param playerCards -list of cards the player possesses
	 */
	private HashMap<String, Integer> playerCards;
	
	private int intialArmies;

	private Observer o;

	MainClass main = MainClass.getM_instance();
	public GamePhase gamePhase;

	/**
	 * Constructor to instantiate Player object
	 * 
	 * @param playerId
	 * @param playerName
	 */
	public Player(int playerId, String playerName) {

		this.playerId = playerId;
		this.playerName = playerName;
		this.playerCountries = new ArrayList<Country>();
		this.playerCards = new HashMap<String, Integer>();
		
		this.getPlayerCards().put(Card.ARTILLERY, 0);
		this.getPlayerCards().put(Card.CAVALRY, 0);
		this.getPlayerCards().put(Card.INFANTRY, 0);
		
		
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
	}

	/**
	 * @param n armies deducted from total
	 *
	 */
	public void remArmies(int n) {
		this.playerTotalArmies -= n;
	}

	/**
	 * @return total countries the player own
	 */
	public int getPlayerTotalCountries() {
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
	}

	/**
	 * @return count of times card exhanged by the player
	 */
	public int getCardExchangeCount() {
		return cardExchangeCount;
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

	/**
	 * function for command:- reinforce countryname, number (number is army to be
	 * placed in that country)
	 * 
	 * @param countryName for country name
	 * @param armyNumber  number of armies
	 * @param player      for player entity
	 */
	public String reinforceArmy(String countryName, int armyNumber) {

		int currentlyUnplacedArmy = this.getPlayerReinforceArmy();
		String errorFlag = "false";
		if (currentlyUnplacedArmy > 0) {
			// check whether entered country name (through console) is valid or not
			if (main.countryBelongsToPlayer(this, countryName)) {

				if (armyNumber <= currentlyUnplacedArmy) {
					
					main.PlaceArmy(countryName, armyNumber);
					currentlyUnplacedArmy -= armyNumber;
					this.setPlayerReinforceArmy(currentlyUnplacedArmy);
				}
				errorFlag = "false";

			} else {
				errorFlag = "This country does not belongs to you!";
			}

			System.out.println("Armies available to reinforce for :" + this.getPlayerId() + " " + this.getPlayerName()
					+ " are " + this.getPlayerReinforceArmy());

		}

		// Printing LOG _ The status of new army with number of army it contains
		for (int i = 0; i < this.getPlayerTotalCountries(); i++) {
			System.out.println("Country: " + this.getPlayerCountries().get(i).getCountryName() + "-->>"
					+ this.getPlayerCountries().get(i).getCountryArmy());
		}
		return errorFlag;
	}

	public boolean hasMoreThanFiveCards() {
		if (this.getPlayerCards().get(Card.ARTILLERY) + this.getPlayerCards().get(Card.CAVALRY)
				+ this.getPlayerCards().get(Card.INFANTRY) >= 5) {
			return true;
		}
		return false;
	}

	public String attack(Country from, Country to, Player defender) {
		// notify method to close the card exchange view dialogue if it is still oprn
		notify_observer();

		String resultString = "";
		List<Integer> attackerWins = new ArrayList<Integer>();
		List<Integer> defenderWins = new ArrayList<Integer>();
		int size = this.getDiceResult().size() < defender.getDiceResult().size() ? this.getDiceResult().size()
				: defender.getDiceResult().size();

		System.out.println("Attacker rolls: " + this.diceResult);
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
			resultString = "DRAW";
		} else {
			resultString = attackerWins.size() > defenderWins.size() ? "ATTACKER WINS" : "DEFENDER WINS";
		}
		System.out.println("attacker :" + attackerWins);
		System.out.println("defender: " + defenderWins);
		System.out.println(resultString);

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

		boolean adjFlag = main.checkNeighbours(from, to, this.getPlayerId());
		System.out.println("player " + this.getPlayerId());
		if (adjFlag) {
			if (from.getCountryArmy() - army >= 1) {
				from.remCountryArmies(army);
				to.addCountryArmies(army);
				System.out.println("\nFortification successful");
				

			} else
				System.out.println("There must be atleast one army in a country!");
			
			//now as the phase will be the  reinforcement Card Exchange View should be there
			this.attach(new CardExchangeView(this));
			HashMap<String, Integer> demoCards = new HashMap<String, Integer>();
			demoCards.put(Card.ARTILLERY, Integer.valueOf(5));
			demoCards.put(Card.CAVALRY, Integer.valueOf(2));
			demoCards.put(Card.INFANTRY, Integer.valueOf(0));
			this.setPlayerCards(demoCards);
			
			
			notify_observer();
			
		
		} else
			System.out.println("Move not possible");

	}

	public void setCurrentPhase(GamePhase phase) {
		this.gamePhase = phase;
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

}
