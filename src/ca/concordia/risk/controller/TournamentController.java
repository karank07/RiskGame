package ca.concordia.risk.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.model.TournamentMode;

public class TournamentController {
	int gameTurns = 0;
	int numOfGames = 0;
	MainClass main = MainClass.getM_instance();
	Player current_player;
	Integer reinforcement_armies;
	Country from_country;
	Country to_country;
	List<Player> players = new ArrayList<Player>();
	int player_count = 0;
	List<String> playerStratergies = new ArrayList<String>();
	TournamentMode tournamentObject = TournamentMode.getInstance();
	public String current_map;
	private static int index = 0;

	public TournamentController() {
		main.resetGame();
		playerStratergies = tournamentObject.getPlayerStratergies();
		numOfGames = tournamentObject.getNumGames();
		gameTurns = tournamentObject.getMaxTurns();

		startTournament();

	}

	private void loadmap() {
		String mapFile = tournamentObject.getGameMaps().get(index);
		try {
			main.readMapFile(mapFile);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private void startTournament() {
		int playerName=0;
		for (String playerStratergy : playerStratergies) {
			main.addPlayer("" +playerName , playerStratergy);
			playerName++;
		}
		for (int i = 0; i < tournamentObject.getGameMaps().size(); i++) {
			loadmap();
			main.divideInitialArmies();
			main.generateDeck();
			main.populatecountries();
			main.placeAll();
			for (int x = 0; x < numOfGames; x++) {
				for (int j = 0; j < gameTurns; j++) {
					for (Player p : main.playerList) {
						

					}
					
				}
			}
			index++;

		}
	}
}


	

	
