package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.List;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.controller.TournamentController;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.model.TournamentMode;
import ca.concordia.risk.model.TournamentResult;

/**
 * IN the cheater strategy, reinforce() method doubles the number of armies on
 * all its countries, whose attack() method automatically conquers all the
 * neighbors of all its countries, and whose fortify() method doubles the number
 * of armies on its countries that have neighbors that belong to other players.
 * 
 * @author Rohan
 *
 */
public class CheaterStrategy {
	static MainClass mainClassInstance = MainClass.getM_instance();

	public static void cheaterStrategyReinforcement(Player p) {

		if (mainClassInstance.endTournament == true) {
			return;
		}

		if (!p.hasMoreThanFiveCards()) {
			for (Country c : p.getPlayerCountries()) {
				c.setCountryArmy(c.getCountryArmy() * 2);
			}
			cheaterStrategyAttack(p);

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
			cheaterStrategyReinforcement(p);
		}

	}

	public static void cheaterStrategyAttack(Player p) {
		for (int i = 0; i < p.getPlayerCountries().size(); i++) {
			Country currentCountry = p.getPlayerCountries().get(i);
			List<Country> neighboutList = Map.getM_instance().getNeighbourCountries(currentCountry);

			for (Country c : neighboutList) {
				// c.setCountryOwner(p.getPlayerId());

				mainClassInstance
						.unmapPlayerToCountry(MainClass.getM_instance().playerList.get(c.getCountryOwner() - 1), c);
				mainClassInstance.mapPlayerToCountry(p, c);
			}
		}
		System.out.println("Cheater Player country Map:" + mainClassInstance.player_country_map.get(p));

		List<String> temp = new ArrayList<>();
		if (mainClassInstance.gameOver(p)) {
			System.out.println("Player " + p.getPlayerName() + " won the game!");
			TournamentResult tournamentResult = TournamentResult.getInstance();
			if (tournamentResult.results.get(TournamentController.currentMap).isEmpty()) {
				temp.add("cheater");
				tournamentResult.results.put(TournamentController.currentMap, temp);
			} else {
				temp = tournamentResult.results.get(TournamentController.currentMap);
				temp.add("cheater");
				tournamentResult.results.replace(TournamentController.currentMap,temp);
			}
			
			TournamentMode tournamentObject = TournamentMode.getInstance() ;
			if (tournamentResult.results.size() == tournamentObject.getGameMaps().size() && tournamentResult.results
					.get(tournamentObject.getGameMaps().get(tournamentObject.getGameMaps().size() - 1))
					.size() == tournamentObject.getNumGames()) {
				tournamentResult.end = true;
				// System.exit(0);
			}

		} else {
			cheaterStrategyFortify(p);
		}
	}

	public static void cheaterStrategyFortify(Player p) {
		for (int i = 0; i < p.getPlayerCountries().size(); i++) {
			Country currentCountry = p.getPlayerCountries().get(i);
			List<Country> neighboutList = Map.getM_instance().getNeighbourCountries(currentCountry);
			for (Country c : neighboutList) {
				if (c.getCountryOwner() != p.getPlayerId()) {
					c.setCountryArmy(c.getCountryArmy() * 2);
				}
			}
		}
		System.out.println("Cheater fortification is done");
		mainClassInstance.nextTurn(p);
	}

}
