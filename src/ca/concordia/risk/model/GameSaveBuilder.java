package ca.concordia.risk.model;

import java.util.List;

/**
 * Builder class for the gamesave product class according to the builder pattern
 * @author Pranal
 *
 */
public abstract class GameSaveBuilder {

	protected GameSave gameState;

	public GameSave getGameState() {
		return gameState;
	}
	public void setGameState(GameSave gameState) {
		this.gameState = gameState;
	}

	/**
	 * 
	 * @param gm mapinstance from the Map model class
	 */
	public abstract void buildGameMap(Map gm);

	/**
	 * 
	 * @param pl playerlist from the mainController class
	 */
	public abstract void buildPlayersList(List<Player> pl);
	
	
	/**
	 * 
	 * @param p player object from the player model class
	 */
	public abstract void buildPlayer(Player p);
}
