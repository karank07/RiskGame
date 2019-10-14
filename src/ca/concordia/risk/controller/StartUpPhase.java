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
	 * @param players maintains the list of Player entities involved the game
	 */
	private List<Player> players;

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
	private Map<Player, List<Country>> player_country_map;

	static int playerTurn = 0;// place army

	/**
	 * Constructor for the StartUpPhase
	 * 
	 * @param numberOfPlayers stores the number of players in the game
	 */
	public void StartUpPhase() {

		mapInstance = ca.concordia.risk.model.Map.getM_instance();

	}

	/**
	 * all countries are randomly assigned to players for initial startup
	 */
	public void populateCountries() {

		int j = 0;
		for (Country country : mapInstance.getCountries()) {
			Player player = players.get(j % playerCount);
			setNewCountryRuler(player, country, 1);
			player.remArmies(1);
			HashMap<String, Object> countryPopulated = new HashMap<>();
			countryPopulated.put("countryName", country.getCountryName());
			j++;
		}

	}

	public void placeArmyByCountryName(String counrtyName) {
		playerTurn++;
		int playerId = playerTurn;
		if (players.get(playerId).getPlayerTotalArmies() > 0) {
			Player p = players.get(playerId);
			Country c1 = mapInstance.getCountryByName(counrtyName);
			List<Country> playerCountryList = getCountriesConqueredBy(p);
			for (Country obj : playerCountryList) {
				if (obj.equals(c1)) {
					c1.addCountryArmies(1);
					p.setPlayerTotalArmies(p.getPlayerTotalArmies() - 1);
					break;

				}
			}

		}
	}

	public void placeArmiesInitialRandom() {

		int j = 0;
		int playersLeftForAssign = playerCount;

		while (playersLeftForAssign > 0) {
			if (players.get(j % playerCount).getPlayerTotalArmies() > 0) {
				Player p = players.get(j % playerCount);
				List<Country> playerCountryList = getCountriesConqueredBy(p);
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				p.setPlayerTotalArmies(p.getPlayerTotalArmies() - 1);
				HashMap<String, Object> eventPayload = new HashMap<>();
				eventPayload.put("countryName", randomCountry.getCountryName());

			} else {
				playersLeftForAssign--;
			}
			j++;
		}

	}

	private boolean setNewCountryRuler(Player pl, Country co, int armies) {
		if (co.getCountryArmy() != 0)
			return false;
		co.setOwnerArmy(pl.getPlayerId(), armies);
		mapPlayerToCountry(pl, co);
		return true;

	}

	public void mapPlayerToCountry(Player p, Country c) {
		List<Country> cList = player_country_map.get(p);
		if (cList == null) {
			cList = new ArrayList<>();
			player_country_map.put(p, cList);
		}
		cList.add(c);
	}

	public List<Country> getCountriesConqueredBy(Player p) {
		return player_country_map.get(p);
	}

	public void addPlayer(String playerName) {
		playerCount++;
		Player p = new Player(playerCount, playerName);
		players.add(p);
	}

	public void removePlayer(String playerName) {
		for(Player obj:players)
		{
			if(playerName.contentEquals(obj.getPlayerName()))
			{
				players.remove(obj.getPlayerId());
			}
		}
	}
}

