package ca.concordia.risk.model;

import java.util.List;

public abstract class GameStateScenario extends GameStateBuilder {
	
	public void buildGameMap(Map gm) {
		gameState.setGameMap(gm);
	}

	public void buildPlayersList(List<Player> pl) {
		gameState.setPlayersList(pl);
	}

	public void buildPlayer(Player p) {
		gameState.setPlayer(p);
	}
}
