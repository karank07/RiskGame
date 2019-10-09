/**
 * 
 */
package ca.concordia.risk.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes.Name;

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

	static List<Country>[] set_of_contries = new List[Map.getContinents().size()];
	static ArrayList<Continent> name_of_continents = new ArrayList<Continent>();
	// set this in player's object;_ public int min_army = 3;

	// make sets of countries belong to each continents

	// List<Country> [] set_of_contries = Map.getCountriesByContinent();

	public void beginReinforcement(Player player) {
		initialize();
		// army assignment begins for this player whose turn is happening now
		// player.setPlayerReinforceArmy((player.getPlayerReinforceArmy() >= ?
		// Math.floor(player.getPlayerCountries().size() / 3));

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
		exchangeCardsForArmy(player);

	}

	private void initialize() {

		name_of_continents = (ArrayList<Continent>) Map.getContinents();
		for (int i = 0; i < Map.getContinents().size(); i++) {
			set_of_contries[i] = new ArrayList<Country>();
			set_of_contries[i] = Map.getCountriesByContinent(name_of_continents.get(i).toString());
		}

	}

	private void exchangeCardsForArmy(Player player) {
		List<Card> playerCardsList = new ArrayList<Card>();

		if (playerCardsList.size() >= 3) {
			// take input which cards player want to exchange
			// player will enter countryname
			String[] countryNameOfCards = new String[3];
			int validCountryCount = 0;
			String[] cardType = new String[3];

			//to remove the card later when player exchange it for army
			int[] used_card_index = new int[3];
			
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < player.getPlayerCards().size(); j++) {
					if (countryNameOfCards[i]
							.equals(player.getPlayerCards().get(j).getCountryCard().getCountryName())) {
						validCountryCount += 1;
						cardType[i] = player.getPlayerCards().get(j).getCardType();
						
						used_card_index[i] = j;
					}
				}
			}

			// check if entered countries are valid (belongs to this player or not)
			if (validCountryCount == 3) {
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

			}

			// remove those cards from player's cards list
			List<Card> removedCardsList = new ArrayList<Card>();
			
			for(int i=0;i<3;i++) {
				player.getPlayerCards().remove(used_card_index[i]);	
			}
			

		} else {
			System.out.println("Exchange not allowd because you have less than 3 cards!");
		}

		// increase cardExchangeCount of player object
		player.setCardExchangeCount(player.getCardExchangeCount() + 1);

	}

}
