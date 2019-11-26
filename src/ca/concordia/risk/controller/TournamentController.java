package ca.concordia.risk.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.model.TournamentMode;
import ca.concordia.risk.model.TournamentResult;

public class TournamentController {
	int gameTurns = 0;
	int numOfGames = 0;
	MainClass main = MainClass.getM_instance();
	Player current_player;
	Integer reinforcement_armies;
	Country from_country;
	Country to_country;
	List<Player> players = new ArrayList<Player>();
	List<String> playerStratergies = new ArrayList<String>();
	TournamentMode tournamentObject = TournamentMode.getInstance();
	TournamentResult tournamentResult = TournamentResult.getInstance();
	public String currentMap;
	private static int index = 0;

	public TournamentController() {
		main.resetGame();
		playerStratergies = tournamentObject.getPlayerStratergies();
		numOfGames = tournamentObject.getNumGames();
		gameTurns = tournamentObject.getMaxTurns();

		startTournament();

	}

	private void loadmap() {
		currentMap = tournamentObject.getGameMaps().get(index);
		try {
			main.readMapFile(currentMap);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private void startTournament() {
		int playerName = 0;
		for (String playerStratergy : playerStratergies) {
			playerName++;
			main.addPlayer("" + playerName, playerStratergy);
			
		}
		for (int i = 0; i < tournamentObject.getGameMaps().size(); i++) {
			loadmap();
			main.divideInitialArmies();
			main.generateDeck();
			main.populatecountries();
			main.placeAll();
//			Player p = MainClass.playerList.get(0);
//			main.resetPlayerTurn();
//			main.nextTurn(p);
			if (tournamentResult.end) {
				break;
			}
			index++;

		}
		showResult();
	}

	private void showResult() {
		Iterator iterator = tournamentResult.results.entrySet().iterator();
		 while (iterator.hasNext()) { 
	            Map.Entry mapElement = (Map.Entry)iterator.next(); 
	             
	            System.out.println(mapElement.getKey() + " : " + mapElement.getValue()); 
	        } 
	}
}
