package ca.concordia.risk.model;

import java.util.List;

/**
 * Concrete Builder class for the buildeer pattern adaptation
 * @author Pranal
 * @author Karan
 *
 */
public abstract class GameSaveScenario extends GameSaveBuilder {
	
	/**
	 * 
	 * @param gm mapinstance from the Map model class
	 */
	public void buildGameMap(Map gm) {
		gameState.setGameMap(gm);
	}

	/**
	 * 
	 * @param pl playerlist from the mainController class
	 */
	public void buildPlayersList(List<Player> pl) {
		gameState.setPlayersList(pl);
	}
	
	/**
	 * 
	 * @param p player object from the player model class
	 */
	public void buildPlayer(Player p) {
		gameState.setPlayer(p);
	}
}
