/**
 * Reinforcement class provides functions to perform Reinforcement phase in Game 
 */
package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.List;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

/**
 * @author rohan
 *
 */
public class ReinforcementPhase {

	public static final String INFANTRY = "INFANTRY";
	public static final String CAVALRY = "Cavalry";
	public static final String ARTILLERY = "Artillery";

	static List<Country>[] set_of_contries = new ArrayList[Map.getContinents().size()];
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
		// 1st rule: country/3...if it's less then 3; then assign 3 army minimum
		player.setPlayerReinforceArmy(
				(player.getPlayerCountries().size() / 3) >= 3 ? (player.getPlayerCountries().size() / 3) : 3);

		// 2nd rule: check if player owns all countries of any of the continents
		for (int i = 0; i < Map.getContinents().size(); i++) {
			if (player.getPlayerCountries().equals(set_of_contries[i])) {
				player.setPlayerReinforceArmy(
						player.getPlayerReinforceArmy() + Map.getContinents().get(i).getContinentControlValue());

			}
		}

		// 3rd rule: if player owns 3 cards and want them to exchange with army
		String[] countryNameOfCards = new String[3]; // Initialize when user selects countirs from thr dropdown menu in
														// GUI
		exchangeCardsForArmy(player, countryNameOfCards);

	}

	/**
	 * Method to make the sets of countries in each continents - will be use later
	 * to check the 2nd rule of the reinforcement phase.
	 */
	private void initialize() {

		name_of_continents = (ArrayList<Continent>) Map.getContinents();
		for (int i = 0; i < Map.getContinents().size(); i++) {
			set_of_contries[i] = new ArrayList<Country>();
			set_of_contries[i] = Map.getCountriesByContinent(name_of_continents.get(i).toString());
		}

	}

	/**
	 * Method to execute the 3rd rule: to exchange cards with army, and remove those cards from player's cardList
	 * @param player
	 * @param countryNameOfCards
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

			// remove those cards from player's cards list
			// List<Card> removedCardsList = new ArrayList<Card>();

			for (int i = 0; i < 3; i++) {
				player.getPlayerCards().remove(used_card_index[i]);
			}

		} else {
			System.out.println("Exchange not allowd because you have less than 3 cards!");
		}

		// increase cardExchangeCount of player object
		player.setCardExchangeCount(player.getCardExchangeCount() + 1);
		
		//command:- reinforce countryname number (number - army to be placed in that country)
		int currentlyUnplacedArmy = player.getPlayerReinforceArmy();
		while(currentlyUnplacedArmy <= 0) {
			
			//take the country name from the GUI use DROPDOWN MENu
			String countryName = null;
			
			//check whether entered country name (through consol) is valid or not
			if(countryBelongsToPlayer(player, countryName)) {
				//int take number from GUI of how many army to placed in the selected country use DROPDOWN MENu
				int armyNumber=0;
				
				//check whether entered country name (through consol) is valid or not
				if(armyNumber<=currentlyUnplacedArmy) {
					reinforceArmy(countryName, armyNumber);
					currentlyUnplacedArmy--;
					if(/*user press stop in GUI then break*/) {
						break;
					}
				}
				
				
			}
			else {
				System.out.println("display msg that entered countr name is not valid");
			}
			
			
		}
		
		
		
	}

	private boolean countryBelongsToPlayer(Player player, String countryName) {
		// TODO Auto-generated method stub
		for (Country c : player.getPlayerCountries()) {
			if (countryName.equals(c.getCountryName())) {
				return true;
			}

		}
		return false;
	}

	/**
	 * @param countryName
	 * @param armyNumber
	 */
	private void reinforceArmy(String countryName, int armyNumber) {
		// TODO Auto-generated method stub
		Country country = Map.getCountryByName(countryName);
		country.setCountryArmy(armyNumber);
	}

}
