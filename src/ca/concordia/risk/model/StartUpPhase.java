package ca.concordia.risk.model;

import java.util.List;


/**
 * This class manages the startup phase of the game project and includes data members for allocation initial armies and countries
 * @author Pranal
 *
 */
public class StartUpPhase {
	
	/**
	 * @param numberOfPlayers stores the number of players in the game
	 */
	private int numberOfPlayers=0;
	
	/**
	 * @param players maintains the list of Player entities involved the game
	 */
	private List<Player> players;
	
	/**
	 * @mapInstance an instance for the Map class
	 */
	private Map mapInstance;
	
	/**
	 * Constructor for the StartUpPhase
	 * @param numberOfPlayers stores the number of players in the game
	 */
	public void StartUpPhase(int numberOfPlayers) {
		
		mapInstance= Map.getM_instance();
		for(int i=1; i<= numberOfPlayers; i++)
		{
			players.add(new Player(i,"Player" +i));
		}
		
		populateCountries();
		placeArmiesInitially();
	}

	private void placeArmiesInitially() {
		
		
	}

	/**
	 * all countries are randomly assigned to players for initial startup
	 */
	private void populateCountries() {
		
		
		
		
	}
	
	

}
