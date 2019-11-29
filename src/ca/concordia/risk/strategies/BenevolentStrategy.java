package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;

import ca.concordia.risk.model.Player;
import java.util.Map;

/**
 * This class implements the benevolent strategy for our game
 * @author Pranal
 *
 */
public class BenevolentStrategy {
	static MainClass mainClassInstance = MainClass.getM_instance();
	static ca.concordia.risk.model.Map mapInstance = ca.concordia.risk.model.Map.getM_instance();
	static List<Country> weakestCountries = new ArrayList<Country>();

	/**
	 * reinforces the weakest country of the player
	 * @param p benevolent player
	 */
	public static void BenevolentStrategyReinforcement(Player p) {
		if(mainClassInstance.endTournament == true) {
			return;
		}
		getWeakestCountry(p);
		if (!p.hasMoreThanFiveCards()) {
			Country weakest = weakestCountries.get(0);
			int reinforceArmy = p.getPlayerReinforceArmy();
			p.reinforceArmy(weakest.getCountryName(), reinforceArmy);
			BenevolentStrategyAttack(p);
		} else {
			if (p.getPlayerCards().size() >= 5) {
				if (p.getPlayerCards().get(Card.ARTILLERY) == 3) {
					mainClassInstance.exchangeCardsForArmy(p, 3, 0, 0);
				} else if (p.getPlayerCards().get(Card.CAVALRY) == 3) {
					mainClassInstance.exchangeCardsForArmy(p, 0, 3, 0);
				} else if (p.getPlayerCards().get(Card.INFANTRY) == 3) { 
					mainClassInstance.exchangeCardsForArmy(p, 0, 0, 3);
				} else if ((p.getPlayerCards().get(Card.ARTILLERY) >= 1) && (p.getPlayerCards().get(Card.CAVALRY) >= 1)
						&& (p.getPlayerCards().get(Card.INFANTRY) >= 1)) {
					mainClassInstance.exchangeCardsForArmy(p, 1, 1, 1);
				}
			}
			BenevolentStrategyReinforcement(p);
		}
	}
/**
 * this function never attacks
 * @param p benevolent player
 */
	public static void BenevolentStrategyAttack(Player p) {
		p.setCanAttack(false);
		if(mainClassInstance.endTournament == true) {
			return;
		}
		BenevolentStrategyFortify(p);

	}

	/**
	 * Fortifies the weakest country and aggregates maximum army there
	 * @param p benevolent player
	 */
	public static void BenevolentStrategyFortify(Player p) {
		Country to = weakestCountries.get(0);
		List<Country> cList = MainClass.player_country_map.get(p);
		Country from = cList.get(0);
		int i = 0;
		if(mainClassInstance.endTournament == true) {
		    
		}
		while (!mainClassInstance.isConnected(from, to, p, new ArrayList<Country>()) || from.getCountryArmy() <= 1
				|| from == to) {
			
			from = cList.get(i);
			i = (i >= cList.size() - 1) ? 0 : ++i;
			if (i == 0) {
				weakestCountries.remove(to);
				//BenevolentStrategyFortify(p);
				to = weakestCountries.get(0);
				//break;;
			}
		}

		System.out.println("Fortification move : " + from.getCountryName() + " " + to.getCountryName());
		p.fortify(from, to, from.getCountryArmy() - 1);
		mainClassInstance.setNextPlayerTurn();
		p = MainClass.playerList.get(mainClassInstance.getPlayerTurn() - 1);
		mainClassInstance.nextTurn(p);

	}

	/**
	 * Returns the strongest country for the player
	 * 
	 * @param p player
	 * @return strongestCountry
	 * 
	 */
	public static void getWeakestCountry(Player p) {
		HashMap<Country, Integer> cMap = new HashMap<Country, Integer>();
		System.out.println("coutries for player :" + MainClass.player_country_map.get(p));
		List<Country> playerCountries = MainClass.player_country_map.get(p);
		cMap.clear();
		for (Country country : playerCountries) {
			cMap.put(country, country.getCountryArmy());

		}

		System.out.println("cMap :" + cMap.size());
		cMap = sortByValue(cMap);
		for (Country c : cMap.keySet()) {
			weakestCountries.add(c);
		}

		System.out.println("weakest countries list: " + weakestCountries);

	}

	public static HashMap<Country, Integer> sortByValue(HashMap<Country, Integer> hm) {
		// Create a list from elements of HashMap
		List<Map.Entry<Country, Integer>> list = new LinkedList<Map.Entry<Country, Integer>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<Country, Integer>>() {
			public int compare(Map.Entry<Country, Integer> o1, Map.Entry<Country, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<Country, Integer> temp = new LinkedHashMap<Country, Integer>();
		for (Map.Entry<Country, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

}
