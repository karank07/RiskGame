package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a model class for a Player with member variables for id, name,
 * countries, armies assigned and cards collected
 * 
 * @author Pranal
 *
 */

public class Player {

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
	 * @param playerCards -list of cards the player possesses
	 */
	private List<Card> playerCards;

	/**
	 * @param player Armies - total armies of the player
	 */
	private int playerTotalArmies = 0;
	

	
	/**
	 * @param playerTotalCountries- total countries owned by a player 
	 */
	private int playerTotalCountries=0;
	
	/**
	 * @param playerReinforceArmy- armies that player get after reinforcement 
	 */
	private int playerReinforceArmy=3;
	
	/**
	 *@param cardExchangeCount- maintains a count of turns in which player has exchanged cards for armies 
	 */
	private int cardExchangeCount=0;
	
	/**
	 * @param diceResult stores the list of result of the number of dices rolled for the player
	 */
	private List<Integer> diceResult;

	/**
	 * @param diceWins store the number of wins in the list
	 */
	private List<Integer> diceWins;
	/**
	 *
	 * Constructor to instantiate Player object
	 * 
	 * @param playerId
	 * @param playerName
	 */
	public Player(int playerId, String playerName) {
		
		this.playerId = playerId;
		this.playerName = playerName;
		this.playerCountries = new ArrayList<Country>();
		this.playerCards = new ArrayList<Card>();
		this.diceResult = new ArrayList<>();
	}

	public Player() {
		// TODO Auto-generated constructor stub
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
	public List<Card> getPlayerCards() {
		return playerCards;
	}

	/**
	 * @param playerCards the playerCards to set
	 */
	public void setPlayerCards(List<Card> playerCards) {
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
	public void addArmies(int addN)
	{
		this.playerTotalArmies+= addN;
	}
	
	/**
	 * @param n armies deducted from total
	 *
	 */
	public void remArmies(int n)
	{
		this.playerTotalArmies-= n;
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
		return playerId + " "+playerName;
	}
	
	/**
	 * sets initial value of total armies owner by the player
	 */
	public void setPlayerTotalCountries() {
		for(Country obj:playerCountries)
		{
			while(obj.getCountryOwner()==playerId)
			{
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

}
