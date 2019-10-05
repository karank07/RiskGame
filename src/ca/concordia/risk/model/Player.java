package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a model class for a Player with member variables for id, name, countries, armies assigned and cards collected
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
	 * Constructor to instantiate Player object
	 * @param playerId
	 * @param playerName
	 */
	public Player(int playerId, String playerName) {
		super();
		this.playerId = playerId;
		this.playerName = playerName;
		this.playerCountries = new ArrayList<Country>();
		this.playerCards = new ArrayList<Card>();
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

}
