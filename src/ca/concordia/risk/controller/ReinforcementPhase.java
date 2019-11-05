
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
 * 
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
		int reinforcementArmyNumber = assign_army(player);

	}

	public int assign_army(Player player) {
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
		System.out.println("Cannot move! Armies available for player " + player.getPlayerId() + " "
				+ player.getPlayerName() + " to reinforce: " + player.getPlayerReinforceArmy());

		return player.getPlayerReinforceArmy();

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
	 * 
	 * Method to execute the 3rd rule: to exchange cards with army, and remove those
	 * cards from player's cardList
	 * 
	 * @param player
	 * @param countOfArtillery
	 * @param countOfCavalry
	 * @param countOfInfantry
	 */
	public void exchangeCardsForArmy(Player player, int countOfArtillery, int countOfCavalry, int countOfInfantry) {

		// display cards owned by player
		// Then player will select the card from UI
		// selected card are given in arguments

		if (countOfArtillery <= player.getPlayerCards().get(Card.ARTILLERY)
				&& countOfCavalry <= player.getPlayerCards().get(Card.CAVALRY)
				&& countOfInfantry <= player.getPlayerCards().get(Card.INFANTRY)) {
			// now it's valid card selection

			// do exchange cards for army

			// if user wants to exchange same type of any 3 cards
			if (countOfArtillery == 3 || countOfCavalry == 0 || countOfInfantry == 0) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
				// increase cardExchangeCount of player object
				player.setCardExchangeCount(player.getCardExchangeCount() + 1);

				// remove 3 artillery cards from player's cards
				player.getPlayerCards().replace(Card.ARTILLERY, (player.getPlayerCards().get(Card.ARTILLERY) - 3));

				// add these cards in global deck
				MainClass.globalCardDeck.replace(Card.ARTILLERY, MainClass.globalCardDeck.get(Card.ARTILLERY) + 3);

			} else if (countOfArtillery == 0 || countOfCavalry == 3 || countOfInfantry == 0) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
				// increase cardExchangeCount of player object
				player.setCardExchangeCount(player.getCardExchangeCount() + 1);

				// remove 3 cavalry cards from player's cards
				player.getPlayerCards().replace(Card.CAVALRY, (player.getPlayerCards().get(Card.CAVALRY) - 3));

				// add these cards in global deck
				MainClass.globalCardDeck.replace(Card.CAVALRY, MainClass.globalCardDeck.get(Card.CAVALRY) + 3);

			} else if (countOfArtillery == 0 || countOfCavalry == 0 || countOfInfantry == 3) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
				// increase cardExchangeCount of player object
				player.setCardExchangeCount(player.getCardExchangeCount() + 1);

				// remove 3 Infantry cards from player's cards
				player.getPlayerCards().replace(Card.INFANTRY, (player.getPlayerCards().get(Card.INFANTRY) - 3));

				// add these cards in global deck
				MainClass.globalCardDeck.replace(Card.INFANTRY, MainClass.globalCardDeck.get(Card.INFANTRY) + 3);
			}
			// if user wants to exchange 3 different cards
			else if (countOfArtillery == 1 && countOfCavalry == 1 && countOfInfantry == 1) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + (player.getCardExchangeCount() + 1) * 5);
				// increase cardExchangeCount of player object
				player.setCardExchangeCount(player.getCardExchangeCount() + 1);

				// remove 1 card of each type from player's cards
				player.getPlayerCards().replace(Card.ARTILLERY, (player.getPlayerCards().get(Card.ARTILLERY) - 1));
				player.getPlayerCards().replace(Card.CAVALRY, (player.getPlayerCards().get(Card.CAVALRY) - 1));
				player.getPlayerCards().replace(Card.INFANTRY, (player.getPlayerCards().get(Card.INFANTRY) - 1));

				// add these cards in global deck
				MainClass.globalCardDeck.replace(Card.ARTILLERY, MainClass.globalCardDeck.get(Card.ARTILLERY) + 1);
				MainClass.globalCardDeck.replace(Card.CAVALRY, MainClass.globalCardDeck.get(Card.CAVALRY) + 1);
				MainClass.globalCardDeck.replace(Card.INFANTRY, MainClass.globalCardDeck.get(Card.INFANTRY) + 1);

			} else {
				System.out.println("cards selection is invalid! Please choose it again");
			}

		}

	}

	/**
	 * 
	 * @param player      the player object to be checked whether he owns the given
	 *                    country
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
	 * @param armyNumber  number of armies
	 * @param player      for player entity
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
	 * @param armyNumber  the number of armies to be assigned
	 */
	private void PlaceArmy(String countryName, int armyNumber) {

		Country country = mapInstance.getCountryByName(countryName);
		country.setCountryArmy(armyNumber + country.getCountryArmy());
	}

}
