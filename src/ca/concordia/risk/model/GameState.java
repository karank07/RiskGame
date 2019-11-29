package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	
	
	private Map gameMap;
	private List<Player> playersList;
	private Player player;
	
	public GameState() {
		gameMap=Map.getM_instance();
		playersList=new ArrayList<Player>();
		player=new Player();
		}
		
	public Map getGameMap() {
		return gameMap;
	}
	
	public void setGameMap(Map gameMap) {
		this.gameMap = gameMap;
	}
	
	public List<Player> getPlayersList() {
		return playersList;
	}
	
	public void setPlayersList(List<Player> playersList) {
		this.playersList = playersList;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
}
