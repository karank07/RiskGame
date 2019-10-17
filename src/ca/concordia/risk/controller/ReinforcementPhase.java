
package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.List;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

/**
 * Reinforcement class provides functions to perform Reinforcement phase in Game 
 * @author rohan
 *
 */
public class ReinforcementPhase {

	public static final String INFANTRY = "INFANTRY";
	public static final String CAVALRY = "Cavalry";
	public static final String ARTILLERY = "Artillery";
	private ca.concordia.risk.model.Map mapInstance;

	static ArrayList<ArrayList<Country>> set_of_contries;
	static ArrayList<Continent> name_of_continents = new ArrayList<Continent>();

	/**
	 * beginReinforcement function to start the reinforcement phase It checks all 3
	 * rules for reinforcement in the game and decides how many number of army
	 * plater will get for this turn.
	 * 
	 * @param player Player
	 */
	public void beginReinforcement(Player player) {
		initialize();
		assign_army(player);

	}

	public void assign_army(Player player) {
		// 1st rule: country/3...if it's less then 3; then assign 3 army minimum
		player.setPlayerReinforceArmy(
				(player.getPlayerCountries().size() / 3) >= 3 ? (player.getPlayerCountries().size() / 3) : 3);
 
		// 2nd rule: check if player owns all countries of any of the continents
		for (int i = 0; i < mapInstance.getContinents().size(); i++) {
			if (player.getPlayerCountries().equals(set_of_contries.get(i))) {
				player.setPlayerReinforceArmy(player.getPlayerReinforceArmy()
						+ mapInstance.getContinents().get(i).getContinentControlValue());

			}
		} 
		// 3rd rule: if player owns 3 cards and want them to exchange with army
		// String[] countryNameOfCards = new String[3]; // Initialize when user selects
		// countirs from thes dropdown menu in
		// GUI
		// exchangeCardsForArmy(player, countryNameOfCards);
		System.out.println("Cannot move! Armies available for player " + player.getPlayerId() + " " + player.getPlayerName()
				+ " to reinforce: " + player.getPlayerReinforceArmy());

	}

	/**
	 * Method to make the sets of countries in each continents - will be use later
	 * to check the 2nd rule of the reinforcement phase.
	 */
	private void initialize() {
		mapInstance = ca.concordia.risk.model.Map.getM_instance();
		for (Continent c : mapInstance.getContinents().values()) {
			name_of_continents.add(c);
		}
		set_of_contries = new ArrayList<ArrayList<Country>>(mapInstance.getContinents().size());

		for (int i = 0; i < mapInstance.getContinents().size(); i++) {
			ArrayList<Country> temp_country_list = new ArrayList<Country>();
			temp_country_list = (ArrayList<Country>) mapInstance
					.getCountriesByContinent(name_of_continents.get(i).getContinentName().toString());
			set_of_contries.add(temp_country_list);
		}

		// printing set of countries
		/*
		 * for(ArrayList<Country> c : set_of_contries) {
		 * System.out.println(c.toString()); }
		 */

	}

	/**
	 * Method to execute the 3rd rule: to exchange cards with army, and remove those
	 * cards from player's cardList
	 * 
	 * @param player for player
	 * @param countryNameOfCards for cards of country
	 */
	private void exchangeCardsForArmy(Player player, String[] countryNameOfCards) {
		List<Card> playerCardsList = new ArrayList<Card>();

		if (playerCardsList.size() >= 3) {
			String[] cardType = new String[3];

			// to remove the card later when player exchange it for army
			int[] used_card_index = new int[3];

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < player.getPlayerCards().size(); j++) {
					if (countryNameOfCards[i]
							.equals(player.getPlayerCards().get(j).getCountryCard().getCountryName())) {
						// validCountryCount += 1;
						cardType[i] = player.getPlayerCards().get(j).getCardType();

						used_card_index[i] = j;
					}
				}
			}

			// now to exchange is validated and initiate the exchange
			if (cardType[0].equals(Card.INFANTRY) && cardType[1].equals(Card.ARTILLERY)
					&& cardType[2].equals(Card.CAVALRY)) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
			} else if (cardType[0].equals(Card.INFANTRY) && cardType[1].equals(Card.INFANTRY)
					&& cardType[2].equals(Card.INFANTRY)) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
			} else if (cardType[0].equals(Card.ARTILLERY) && cardType[1].equals(Card.ARTILLERY)
					&& cardType[2].equals(Card.ARTILLERY)) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
			} else if (cardType[0].equals(Card.CAVALRY) && cardType[1].equals(Card.CAVALRY)
					&& cardType[2].equals(Card.CAVALRY)) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
			}

			for (int i = 0; i < 3; i++) {
				player.getPlayerCards().remove(used_card_index[i]);
			}

		} else {
			System.out.println("Exchange is not allowd because you have less than 3 cards!");
		}

		// increase cardExchangeCount of player object
		player.setCardExchangeCount(player.getCardExchangeCount() + 1);

	}

	/**
	 * 
	 * @param player the player object to be checked whether he owns the given country
	 * @param countryName the country to be checked
	 * @return true if the player owns the country 
	 */
	private boolean countryBelongsToPlayer(Player player, String countryName) {

		for (Country c : player.getPlayerCountries()) {
			if (countryName.equalsIgnoreCase(c.getCountryName())) {
				return true;
			}

		}
		return false;
	}

	/**
	 * function for command:- reinforce countryname, number (number is army to be
	 * placed in that country)
	 * 
	 * @param countryName for country name
	 * @param armyNumber number of armies
	 * @param player for player entity
	 */
	public void reinforceArmy(Player player, String countryName, int armyNumber) {

		int currentlyUnplacedArmy = player.getPlayerReinforceArmy();

		if (currentlyUnplacedArmy > 0) {
			// check whether entered country name (through console) is valid or not
			if (countryBelongsToPlayer(player, countryName)) {

				if (armyNumber <= currentlyUnplacedArmy) {

					PlaceArmy(countryName, armyNumber);
					currentlyUnplacedArmy -= armyNumber;
					player.setPlayerReinforceArmy(currentlyUnplacedArmy);
				}

			} else {
				System.out.println("This country does not belongs to you!");
			}

			System.out.println("Armies available to reinforce for :" + player.getPlayerId() + " "
					+ player.getPlayerName() + " are " + player.getPlayerReinforceArmy());

		}

		// Printing LOG _ The status of new army with number of army it contains
		for (int i = -0; i < player.getPlayerTotalCountries(); i++) {
			System.out.println("Country: " + player.getPlayerCountries().get(i).getCountryName() + "-->>"
					+ player.getPlayerCountries().get(i).getCountryArmy());
		}
	}

/**
 * 
 * @param countryName the country to be assigned an army
 * @param armyNumber the number of armies to be assigned
 */
	private void PlaceArmy(String countryName, int armyNumber) {

		Country country = mapInstance.getCountryByName(countryName);
		country.setCountryArmy(armyNumber + country.getCountryArmy());
	}

}
