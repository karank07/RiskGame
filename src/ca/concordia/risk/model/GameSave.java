package ca.concordia.risk.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.utilities.GamePhase;

/**
 * This class handle the event of saving the game
 * @author Pranal
 *
 */
public class GameSave 
{
	public static HashMap<Player, List<Country>> player_country_map;
	public static HashMap<String, Integer> globalCardDeck;
	public static List<Player> playerList;
	public String mode;
	public GamePhase phase;
	public static int turn;
	/**
	 * @return the player_country_map
	 */
	public static HashMap<Player, List<Country>> getPlayer_country_map() {
		return player_country_map;
	}
	/**
	 * @param player_country_map the player_country_map to set
	 */
	public static void setPlayer_country_map(HashMap<Player, List<Country>> player_country_map) {
		GameSave.player_country_map = player_country_map;
	}
	/**
	 * @return the globalCardDeck
	 */
	public static HashMap<String, Integer> getGlobalCardDeck() {
		return globalCardDeck;
	}
	/**
	 * @param globalCardDeck the globalCardDeck to set
	 */
	public static void setGlobalCardDeck(HashMap<String, Integer> globalCardDeck) {
		GameSave.globalCardDeck = globalCardDeck;
	}
	/**
	 * @return the playerList
	 */
	public static List<Player> getPlayerList() {
		return playerList;
	}
	/**
	 * @param playerList the playerList to set
	 */
	public static void setPlayerList(List<Player> playerList) {
		GameSave.playerList = playerList;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return the phase
	 */
	public GamePhase getPhase() {
		return phase;
	}
	/**
	 * @param phase the phase to set
	 */
	public void setPhase(GamePhase phase) {
		this.phase = phase;
	}
	/**
	 * @return the turn
	 */
	public static int getTurn() {
		return turn;
	}
	/**
	 * @param turn the turn to set
	 */
	public static void setTurn(int turn) {
		GameSave.turn = turn;
	}
	
	

}
