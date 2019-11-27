package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;

import ca.concordia.risk.model.Player;

/**
 * This class implements the aggressive strategy wherein it will always focus on
 * attacking until it can no longer attack
 * 
 * @author Pranal
 *
 */
public class AggressiveStrategy {
	static MainClass mainClassInstance = MainClass.getM_instance();
	static ca.concordia.risk.model.Map m = ca.concordia.risk.model.Map.getM_instance();
	static List<Country> strongestCountries = new ArrayList<Country>();
	static boolean mapSet = false;

	public AggressiveStrategy() {

	}

/**
 * This function always reinforces the strongest country of the player p.
 * @param p the player following the aggressive strategy
 */
	public static void AggresiveStrategyReinforcement(Player p) {
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
		if (!mapSet) {
			getStrongestCountry(p);
			mapSet = true;

		}
		Country strongest;
		if (strongestCountries.isEmpty()) {
			strongest = p.getPlayerCountries().get(0);
			int max = strongest.getCountryArmy();
			for (Country c : p.getPlayerCountries()) {
				if (c.getCountryArmy() > max) {
					strongest = c;
				}
			}
		} else
			strongest = strongestCountries.get(0);

		int reinforceArmy = p.getPlayerReinforceArmy();
		p.reinforceArmy(strongest.getCountryName(), reinforceArmy);
		AggressiveStrategyAttack(p);

	}
	
	/**
	 * This function always attacks with the strongest country until it cannot
	 * attack anymore
	 * @param p the player following the aggressive strategy
	 */
	public static void AggressiveStrategyAttack(Player p) {
		if (p.getCanAttack()) {
			Country strongest = strongestCountries.get(0);
			System.out.println("Strongest country: " + strongest.getCountryName());
			System.out.println("attackable countries: " + p.attackableCountries(strongest));
			if (p.attackableCountries(strongest).isEmpty()) {
				// if (c.getCountryName().equals(strongest.getCountryName()) ||
				// c.getCountryArmy() < 2) {
				strongestCountries.remove(0);
				if (strongestCountries.isEmpty()) {
					p.setCanAttack(false);
					AggressiveStrategyFortify(p);
					return;

				}
				System.out.println("Strongest list after: " + strongestCountries);
				strongest = strongestCountries.get(0);
			}

			System.out.println("strongest country name: " + strongest.getCountryName());
			System.out.println("attackable countries: " + p.attackableCountries(strongest));

			if (strongest != null) {
				if (!p.attackableCountries(strongest).isEmpty()) {

					Country defendingCountry = p.attackableCountries(strongest).get(0);
					mainClassInstance.alloutAttack(strongest, defendingCountry, p,
							MainClass.getM_instance().playerList.get(defendingCountry.getCountryOwner() - 1));
					p.setAttackResult(mainClassInstance.attackResult(strongest, defendingCountry, p));

					if (p.getAttackResult().equalsIgnoreCase("Attacker won! Country conquered")) {
						mainClassInstance.moveArmies(p, strongest, defendingCountry, p.getAttackResult().length());
					}
				}
			}

		} else
			System.out.println("Player " + p.getPlayerName() + " can not attack more");
		AggressiveStrategyFortify(p);
	}

	/**
	 * This function fortifies the strongest country by moving
	 * armies from the country having the highest aggregation of armies
	 * @param p player following the aggressive strategy
	 */
	public static void AggressiveStrategyFortify(Player p) {
		List<Country> countryConquered = MainClass.player_country_map.get(p);
		List<Country> countryFromList = new ArrayList<>();
		Country to;
		if (strongestCountries.isEmpty()) {
			to = p.getPlayerCountries().get(0);
			int max = to.getCountryArmy();
			for (Country c : p.getPlayerCountries()) {
				if (c.getCountryArmy() > max) {
					to = c;
				}
			}
		} else
			to = strongestCountries.get(0);

		for (Country c : countryConquered) {
			if (c.getCountryArmy() > 1 && c != to) {
				countryFromList.add(c);
			}
		}

		int maxArmy = 0;
		Country from = countryFromList.get(0);
		boolean flag = false;
		for (Country country : countryFromList) {
			flag = mainClassInstance.checkNeighbours(country, to, p.getPlayerId());
			if (flag) {

				int playerArmy = country.getCountryArmy();

				if (playerArmy >= maxArmy) {
					maxArmy = playerArmy;
					from = country;
				}
			}
		}

		System.out.println("from:" + from.getCountryName());
		System.out.println("to:" + to.getCountryName());
		p.fortify(from, to, from.getCountryArmy() - 1);
		mainClassInstance.setNextPlayerTurn();
		p=MainClass.playerList.get(mainClassInstance.getPlayerTurn()-1);
		mainClassInstance.nextTurn(p);

	}

	/**
	 * Returns the strongest country for the player
	 * @param p player
	 * @return strongestCountry
	 * 
	 */
	public static void getStrongestCountry(Player p) {
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
			strongestCountries.add(c);
		}
		Collections.reverse(strongestCountries);
		System.out.println("Strongest countries list: " + strongestCountries);

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
