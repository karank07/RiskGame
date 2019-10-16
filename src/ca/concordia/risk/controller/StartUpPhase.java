package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;

/**
 * This class manages the startup phase of the game project and includes data
 * members for allocation initial armies and countries
 * 
 * @author Pranal
 *
 */
public class StartUpPhase {

	/**
	 * @mapInstance an instance for the Map class
	 */
	private ca.concordia.risk.model.Map mapInstance;
	/**
	 * @param playerCount stores the number of players in the game
	 */
	static int playerCount = 0;
	/**
	 * 
	 */
	private Map<Player, List<Country>> player_country_map = new HashMap<Player, List<Country>>();

	static int playerTurn = 0;// place army

	/**
	 * Constructor for the StartUpPhase
	 * 
	 * @param numberOfPlayers stores the number of players in the game
	 */
	public StartUpPhase() {

		mapInstance = ca.concordia.risk.model.Map.getM_instance();

	}

	/**
	 * all countries are randomly assigned to players for initial startup
	 */
	public void populateCountries() {

		playerCount = MainClass.playerList.size();
		int j = 0;
		for (Country country : mapInstance.getCountries()) {
			Player player = MainClass.playerList.get(j % playerCount);
			setNewCountryRuler(player, country, 1);
			player.remArmies(1);
			HashMap<String, Object> countryPopulated = new HashMap<>();
			countryPopulated.put("countryName", country.getCountryName());
			player.setPlayerCountries(player_country_map.get(player));

			j++;
		}

		for (Player p : MainClass.playerList) {
			System.out.println("\nCountries Allocated to Player " + p.getPlayerId() + " " + p.getPlayerName() + " are : ");
			for (Country c : player_country_map.get(p)) {
				System.out.println(c.getCountryName());
			}

		}
	}

	/**
	 * 
	 * Getting the initial armies according the number of players playing
	 * 
	 * @param numberOfPlayers the players playing
	 * 
	 * @return the initial armies
	 */

	public int getInitialArmies() {
		int numberOfPlayers = MainClass.playerList.size();
		int setArmy = 0;
		switch (numberOfPlayers) {
		case 2:
			setArmy = 40;
			break;
		case 3:
			setArmy = 35;
			break;
		case 4:
			setArmy = 30;
			break;
		case 5:
			setArmy = 25;
			break;
		case 6:
			setArmy = 20;
			break;
		}
		return setArmy;

	}

/**
 * This method allows the player to choose and place the armies in a particular country
 * @param countryName the country to be assigned armies
 */
	public void placeArmyByCountryName(String countryName) {

		if (MainClass.playerList.get(playerTurn % playerCount).getPlayerTotalArmies() > 0) {

			Player p = MainClass.playerList.get(playerTurn % playerCount);
			Country c1 = mapInstance.getCountryByName(countryName);
			List<Country> playerCountryList = getCountriesConqueredBy(p);
			for (Country obj : playerCountryList) {
				if (obj.equals(c1)) {
					c1.addCountryArmies(1);
					MainClass.playerList.get(p.getPlayerId() - 1).setPlayerTotalArmies(p.getPlayerTotalArmies() - 1);
					System.out.println(p.getPlayerTotalArmies());
					break;

				}
			}

		}
		playerTurn++;

	}
	
/**
 * Randomly assigns the armies initially to each player in round robin fashion
 */
	public void placeArmiesInitialRandom() {

		int playersLeftForAssign = playerCount;

		while (playersLeftForAssign > 0) {
			if (MainClass.playerList.get(playerTurn % playerCount).getPlayerTotalArmies() > 0) {

				Player p = MainClass.playerList.get(playerTurn % playerCount);
				List<Country> playerCountryList = getCountriesConqueredBy(p);
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				MainClass.playerList.get(p.getPlayerId() - 1).setPlayerTotalArmies(p.getPlayerTotalArmies() - 1);
				HashMap<String, Object> eventPayload = new HashMap<>();
				eventPayload.put("countryName", randomCountry.getCountryName());
			} else {
				playersLeftForAssign--;
			}
			playerTurn++;
		}
		for(Player p:MainClass.playerList)
		{
			System.out.println("\nFor player : "+p.getPlayerId()+" "+p.getPlayerName());
			for(Country c:player_country_map.get(p))
			{
				System.out.println("Owns Country : "+c.getCountryName()+" and has Armies : "+c.getCountryArmy());
			}
		}

	}

/**
 * 
 * @param pl player to be assigned army for
 * @param co country
 * @param armies the number of armies assigned
 * @return
 */
	private boolean setNewCountryRuler(Player pl, Country co, int armies) {
		if (co.getCountryArmy() != 0)
			return false;
		co.setOwnerArmy(pl.getPlayerId(), armies);
		mapPlayerToCountry(pl, co);
		return true;

	}

/**
 * 
 * @param p player to be assigned a country
 * @param c country being assigned to the player
 */
	public void mapPlayerToCountry(Player p, Country c) {
		List<Country> cList = player_country_map.get(p);
		c.setCountryOwner(p.getPlayerId());
		if (cList == null) {
			cList = new ArrayList<>();
			player_country_map.put(p, cList);
		}
		cList.add(c);

	}
/**
 * 	
 * @param p player whose conquered countries are to be known
 * @return the list of countries owned by the player p
 */
	public List<Country> getCountriesConqueredBy(Player p) {
		return player_country_map.get(p);
	}

/**
 * 
 * @param playerName the player to be added in the game
 */
	public void addPlayer(String playerName) {
		playerCount = MainClass.playerList.size() + 1;
		Player p = new Player(playerCount, playerName);
		MainClass.playerList.add(p);
		System.out.println(p.toString());
	}

/**
 * 
 * @param playerName the player to be removed from the game
 */
	public void removePlayer(String playerName) {
		for (Player obj : MainClass.playerList) {

			if (playerName.contentEquals(obj.getPlayerName())) {
				MainClass.playerList.remove(obj.getPlayerId() - 1);
				System.out.println("\nplayer deleted");
				for (int i = 0; i < MainClass.playerList.size(); i++) {
					MainClass.playerList.get(i).setPlayerId(i + 1);
				}
				break;
			}

		}

		System.out.println(MainClass.playerList.toString());
	}
}