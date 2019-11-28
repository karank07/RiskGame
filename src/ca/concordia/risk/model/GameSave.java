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
	public HashMap<Player, List<Country>> player_country_map;
	public HashMap<String, Integer> globalCardDeck;
	public List<Player> playerList;
	public String mode;
	public GamePhase phase;
	public int turn;
	public HashMap<Integer, Continent> continents;
	public HashMap<Integer, ArrayList<Integer>> borders;
	public HashMap<Integer, Country> countries;
	public int turnCounter;
	public TournamentMode tournamentmode;
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
	 * @return the player_country_map
	 */
	public HashMap<Player, List<Country>> getPlayer_country_map() {
		return player_country_map;
	}
	/**
	 * @param player_country_map the player_country_map to set
	 */
	public void setPlayer_country_map(HashMap<Player, List<Country>> player_country_map) {
		this.player_country_map = player_country_map;
	}
	/**
	 * @return the globalCardDeck
	 */
	public HashMap<String, Integer> getGlobalCardDeck() {
		return globalCardDeck;
	}
	/**
	 * @param globalCardDeck the globalCardDeck to set
	 */
	public void setGlobalCardDeck(HashMap<String, Integer> globalCardDeck) {
		this.globalCardDeck = globalCardDeck;
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
	public int getTurn() {
		return turn;
	}
	/**
	 * @param turn the turn to set
	 */
	public void setTurn(int turn) {
		this.turn = turn;
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
