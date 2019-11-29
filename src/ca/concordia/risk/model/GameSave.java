package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the product class for builder pattern that contains the getters and setters
 * for saving the game contents
 * @author Karan
 * @author Pranal
 *
 */
public class GameSave {
	
	
	private Map gameMap;
	private List<Player> playersList;
	private Player player;
	
	/**
	 * default constructor
	 */
	public GameSave() {
		gameMap=Map.getM_instance();
		playersList=new ArrayList<Player>();
		player=new Player();
		}
	/**
	 * 	
	 * @return map instance for the game
	 */
	public Map getGameMap() {
		return gameMap;
	}
	
	/**
	 * 
	 * @param gameMap map instance to be set
	 */
	public void setGameMap(Map gameMap) {
		this.gameMap = gameMap;
	}
	
	/**
	 * returns list of players playing the game when saved
	 * @return playerList 
	 */
	public List<Player> getPlayersList() {
		return playersList;
	}
	
	public void setPlayersList(List<Player> playersList) {
		this.playersList = playersList;
	}
	
	/**
	 * 
	 * @return player instance
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * sets the object for player to reload/restore data
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
}
