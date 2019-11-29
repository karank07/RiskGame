package ca.concordia.risk.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.utilities.GamePhase;



/**
 * This class handle the event of saving the game
 * @author Pranal
 *
 */
public class GameSave 
{
	
	
	public static HashMap<String, Integer> globalCardDeck;
	public List<Player> playerList;
	public static String mode;
	public static GamePhase phase;
	public static int turn;
	public  HashMap<Integer, Continent> continents;
	public  HashMap<Integer, ArrayList<Integer>> borders;
	public  HashMap<Integer, Country> countries;
	public 	int turnCounter;
	public 	TournamentMode tournamentmode;
	
	
	public static HashMap<Player, List<Country>> player_country_map;
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
	public List<Player> getPlayerList() {
		return playerList;
	}






	/**
	 * @param playerList the playerList to set
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}






	/**
	 * @return the mode
	 */
	public static String getMode() {
		return mode;
	}






	/**
	 * @param mode the mode to set
	 */
	public static void setMode(String mode) {
		GameSave.mode = mode;
	}






	/**
	 * @return the phase
	 */
	public static GamePhase getPhase() {
		return phase;
	}






	/**
	 * @param phase the phase to set
	 */
	public static void setPhase(GamePhase phase) {
		GameSave.phase = phase;
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






	/**
	 * @return the continents
	 */
	public HashMap<Integer, Continent> getContinents() {
		return continents;
	}






	/**
	 * @param continents the continents to set
	 */
	public void setContinents(HashMap<Integer, Continent> continents) {
		this.continents = continents;
	}






	/**
	 * @return the borders
	 */
	public HashMap<Integer, ArrayList<Integer>> getBorders() {
		return borders;
	}






	/**
	 * @param borders the borders to set
	 */
	public void setBorders(HashMap<Integer, ArrayList<Integer>> borders) {
		this.borders = borders;
	}






	/**
	 * @return the countries
	 */
	public HashMap<Integer, Country> getCountries() {
		return countries;
	}






	/**
	 * @param countries the countries to set
	 */
	public void setCountries(HashMap<Integer, Country> countries) {
		this.countries = countries;
	}






	/**
	 * @return the turnCounter
	 */
	public int getTurnCounter() {
		return turnCounter;
	}






	/**
	 * @param turnCounter the turnCounter to set
	 */
	public void setTurnCounter(int turnCounter) {
		this.turnCounter = turnCounter;
	}






	/**
	 * @return the tournamentmode
	 */
	public TournamentMode getTournamentmode() {
		return tournamentmode;
	}






	/**
	 * @param tournamentmode the tournamentmode to set
	 */
	public void setTournamentmode(TournamentMode tournamentmode) {
		this.tournamentmode = tournamentmode;
	}

	



	public void saveThisGame(String saveFileName){
		File saveFile=new File(saveFileName);
		Map.getM_instance().copySavedData(this);
		MainClass.getM_instance().copySaveData(this);
		Gson gson = GameSaveBuilder.getGSONInstance();
		FileWriter fw = null;
		try {
			fw = new FileWriter(saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			gson.toJson(this, fw);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	

}
