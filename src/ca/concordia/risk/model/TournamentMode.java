package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

/**
 * class TournamentMode has all properties for the tournament mode
 * 
 * @author Karan
 *
 */
public class TournamentMode {

	private int numPlayers;
	private int maxTurns;
	private int numGames;
	private List<String> gameMaps = new ArrayList<String>();
	private List<String> playerStratergies = new ArrayList<String>();

	private static TournamentMode tournamentObject = null;

	/**
	 * return the object
	 * 
	 * @return tournamentObject
	 */
	public static TournamentMode getInstance() {
		if (tournamentObject == null) {
			tournamentObject = new TournamentMode();
		}
		return tournamentObject;
	}

	/**
	 * @return the numPlayers
	 */
	public int getNumPlayers() {
		return numPlayers;
	}

	/**
	 * @param numPlayers the numPlayers to set
	 */
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	/**
	 * @return the maxTurns
	 */
	public int getMaxTurns() {
		return maxTurns;
	}

	/**
	 * @param maxTurns the maxTurns to set
	 */
	public void setMaxTurns(int maxTurns) {
		this.maxTurns = maxTurns;
	}

	/**
	 * @return the numGames
	 */
	public int getNumGames() {
		return numGames;
	}

	/**
	 * @param numGames the numGames to set
	 */
	public void setNumGames(int numGames) {
		this.numGames = numGames;
	}

	/**
	 * @return the gameMaps
	 */
	public List<String> getGameMaps() {
		return gameMaps;
	}

	/**
	 * @param gameMaps the gameMaps to set
	 */
	public void setGameMaps(List<String> gameMaps) {
		this.gameMaps = gameMaps;
	}

	/**
	 * @param gameMap the gameMap to add to gameMaps list
	 */
	public void addGameMaps(String gameMap) {
		this.gameMaps.add(gameMap);
	}

	/**
	 * @return the playerStratergies
	 */
	public List<String> getPlayerStratergies() {
		return playerStratergies;
	}

	/**
	 * @param playerStratergies the playerStratergies to set
	 */
	public void setPlayerStratergies(List<String> playerStratergies) {
		this.playerStratergies = playerStratergies;
	}

	/**
	 * @param playerStratergies the playerStratergies to set
	 */
	public void addPlayerStratergies(String playerStratergy) {
		this.playerStratergies.add(playerStratergy);
	}

}
