package ca.concordia.risk.model;

import java.util.List;

public abstract class GameStateBuilder {

	protected GameState gameState;

	public GameState getGameState() {
		return gameState;
	}
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public abstract void buildGameMap(Map gm);

	public abstract void buildPlayersList(List<Player> pl);

	public abstract void buildPlayer(Player p);
}
