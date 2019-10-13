package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Player;


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
	private ca.concordia.risk.model.Map mapInstance;
	
	/**
	 * 
	 */
	private Map<Player, List<Country>> player_country_map;
	
	/**
	 * Constructor for the StartUpPhase
	 * @param numberOfPlayers stores the number of players in the game
	 */
	public void StartUpPhase(int numberOfPlayers) {
		
		mapInstance= ca.concordia.risk.model.Map.getM_instance();
		for(int i=1; i<= numberOfPlayers; i++)
		{
			players.add(new Player(i,"Player" +i));
		}
		
		populateCountries();
		placeArmiesInitially();
	}

	private void placeArmiesInitially() {
		
		int j = 0;
		int playersLeftForAssign = numberOfPlayers;
		while (playersLeftForAssign > 0) {
			if (players.get(j % numberOfPlayers).getPlayerTotalArmies() > 0) {
				Player p = players.get(j%numberOfPlayers);
				List<Country> playerCountryList = getCountriesConqueredBy(p);
				Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
				randomCountry.addCountryArmies(1);
				p.setPlayerTotalArmies(p.getPlayerTotalArmies() - 1);
				HashMap<String, Object> eventPayload = new HashMap<>();
				eventPayload.put("countryName", randomCountry.getCountryName());
				
			} else {
				playersLeftForAssign--;
			}
			j++;
		}
		
	}

	/**
	 * all countries are randomly assigned to players for initial startup
	 */
	private void populateCountries() {
		
		int j = 0;
		for (Country country : mapInstance.getCountries()) {
			Player player = players.get(j % numberOfPlayers);
			setNewCountryRuler(player, country, 1);
			player.remArmies(1);
			HashMap<String, Object> countryPopulated = new HashMap<>();
			countryPopulated.put("countryName", country.getCountryName());
			j++;	
		}
			
	}
		
	private boolean setNewCountryRuler(Player pl, Country co, int armies) {
		if (co.getCountryArmy() != 0)
			return false;
			co.setOwnerArmy(pl.getPlayerId(),armies);
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
	
	

}

	
