package ca.concordia.risk.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

/**
 * Random strategy that reinforces random a random country, attacks a random
 * number of times a random country, and fortifies a random country, all
 * following the standard rules for each phase.
 * 
 * @author Rohan
 *
 */

public class RandomStrategy {
	static Random r = new Random();
	static MainClass mainClassInstance = MainClass.getM_instance();

	/**
	 * @param p This function does the select country and number army to be placed
	 *          in that country randomly.
	 * 
	 *          After that it will call function for doing reinforcement
	 *  
	 */
	public static void RandomStrategyReinforcement(Player p) {
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

		Country country = MainClass.player_country_map.get(p)
				.get(r.nextInt(MainClass.player_country_map.get(p).size()));

		String flag = p.reinforceArmy(country.getCountryName(), p.getPlayerReinforceArmy());

		RandomStrategyAttack(p);

	}

	private static void RandomStrategyAttack(Player p) {

		System.out.println(
				"PLAYER COUNTRY MAP SIZE BEFORE ATTACK : " + MainClass.player_country_map.get(p).size());

		List<Country> cList = new ArrayList<Country>();
		for (Country c : MainClass.player_country_map.get(p)) {
			if (c.getCountryArmy() > 1 && !p.attackableCountries(c).isEmpty()) {

				cList.add(c);

			}
		}
		
		Country attackerCountry = cList.get(r.nextInt(cList.size()));
		Country defenderCountry = null;

//		while (p.attackableCountries(attackerCountry).size() == 0) {
//			attackerCountry = cList.get(r.nextInt(cList.size()));
//			if (p.attackableCountries(attackerCountry).size() != 0) {
//				break;
//			}
//		}
		
		List<Country> defenderCountryList = new ArrayList<Country>();
		for (int i = 1; i <= Map.getM_instance().getCountries().size(); i++) {
			//System.out.println(""+Map.getM_instance().getCountries().get(i)+" Country Owner : "+ Map.getM_instance().getCountries().get(i).getCountryOwner()  +" and current player: "+ p.getPlayerId());
			if (Map.getM_instance().getCountries().get(i).getCountryOwner() != p.getPlayerId()) {
			
				Country c = Map.getM_instance().getCountries().get(i);
				//System.out.println("This Can be Defender COuntry: " + c.getCountryName());
				
				//System.out.println(">>>>>>>"+ attackerCountry.getCountryName()+" and " +c.getCountryName() +" Is Neighbour: "+ mainClassInstance.isNeighbour(attackerCountry, c) + " army in "+ attackerCountry.getCountryName() +" is "+ attackerCountry.getCountryArmy());
				if (attackerCountry.getCountryArmy()>=2 && mainClassInstance.isNeighbour(attackerCountry, c)) {
					//System.out.println("In defender list making: "+ "neighbour of "+ attackerCountry.getCountryName() + " is "+ c.getCountryName());
					defenderCountryList.add(c);
				}
			}
		}

		System.out.println("In RANDOM : ATTACKABLE COUNTRY SIZE: " + p.attackableCountries(attackerCountry).size());

		/*
		 * if (p.attackableCountries(attackerCountry).size() != 0) {
		 * 
		 * defenderCountry = p.attackableCountries(attackerCountry)
		 * .get(r.nextInt(p.attackableCountries(attackerCountry).size())); } else {
		 * RandomStrategyFortify(p); }
		 */
		if(defenderCountryList.size() == 0) {
			RandomStrategyFortify(p);
		}

		System.out.println("attacker country: " + attackerCountry.getCountryName() +" Defender country List: "+ defenderCountryList);
		int temp_index = r.nextInt(defenderCountryList.size());
		if(temp_index == 0) {
			defenderCountry = defenderCountryList.get(0);
		}
		else {
			defenderCountry = defenderCountryList.get(temp_index);

		}
		
		int randomTimesAttack = r.nextInt(5) + 1; // Bw. 1 and 5

		for (int i = 0; i < randomTimesAttack; i++) {

			
			int dice = attackerCountry.getCountryArmy() == 2 ? 1 : attackerCountry.getCountryArmy() == 3 ? 2 : 3;
			mainClassInstance.doAttack(attackerCountry, defenderCountry, dice, p);
			Player defender = MainClass.playerList.get(defenderCountry.getCountryOwner() - 1);

			mainClassInstance.doDefend(defenderCountry.getCountryArmy() == 1 ? 1 : 2, p, defender, attackerCountry,
					defenderCountry);
			p.setAttackResult(mainClassInstance.attackResult(attackerCountry, defenderCountry, p));
			if (p.getAttackResult().equalsIgnoreCase("Attacker won! Country conquered")) {
				mainClassInstance.moveArmies(p, attackerCountry, defenderCountry, p.getDiceWins().size());
				break;
			}
//				dice = defenderCountry.getCountryArmy() == 1 ? 1 : 2;
//				mainClassInstance.doDefend(dice, p, defender, attackerCountry, defenderCountry);

		}
		RandomStrategyFortify(p);
	}

	private static void RandomStrategyFortify(Player p) {
		// Country fromCountry =
		// p.getPlayerCountries().get(r.nextInt(mainClassInstance.player_country_map.get(p).size()));

		List<Country> fromCountryList = new ArrayList<Country>();
		for (int i = 0; i < MainClass.player_country_map.get(p).size(); i++) {
			Country c = MainClass.player_country_map.get(p).get(i);
			if (c.getCountryArmy() > 1) {
				fromCountryList.add(c);
			}
		}

		Country fromCountry = fromCountryList.get(r.nextInt(fromCountryList.size()));
//		System.out.println("Trying to fortify...");
//		System.out.println("Player country map size: "+ mainClassInstance.player_country_map.get(p).size());
//		int temp = r.nextInt(mainClassInstance.player_country_map.get(p).size());

		// System.out.println("From COuntry: " + fromCountry.getCountryName());
		Country toCountry = null;

		List<Country> toCountryList = new ArrayList<Country>();

		for (int i = 0; i < mainClassInstance.player_country_map.get(p).size(); i++) {
			Country c = mainClassInstance.player_country_map.get(p).get(i);
			//if (mainClassInstance.isConnected(fromCountry, c, p, new ArrayList<Country>())) {
			if(mainClassInstance.checkNeighbours(fromCountry, c, fromCountry.getCountryOwner())) {
				toCountryList.add(c);
			}
		}

		if (toCountryList.isEmpty()) {
			mainClassInstance.setNextPlayerTurn();
			p = MainClass.playerList.get(mainClassInstance.getPlayerTurn() - 1);

			mainClassInstance.nextTurn(p);
		} else {
			if (toCountryList.size() == 0) {
				toCountry = toCountryList.get(0);
			} else {
				toCountry = toCountryList.get(r.nextInt(toCountryList.size()));
			}

		}

		int army = fromCountry.getCountryArmy() - r.nextInt(fromCountry.getCountryArmy() - 1);
		p.fortify(fromCountry, toCountry, army);

//		while (true) {
//			// System.out.println("in while");
//			toCountry = Map.getM_instance().getNeighbourCountries(fromCountry)
//					.get(r.nextInt(Map.getM_instance().getNeighbourCountries(fromCountry).size()));
//
//			if (toCountry.getCountryOwner() == p.getPlayerId()) {
//				break;
//			} else {
//				fromCountry = mainClassInstance.player_country_map.get(p)
//						.get(r.nextInt(mainClassInstance.player_country_map.get(p).size()));
//			}
//		}
//		System.out.println("To country: " + toCountry.getCountryName());
//		if (fromCountry.getCountryArmy() > 0) {
//			army = r.nextInt(fromCountry.getCountryArmy()) + 1;
//
//		} else {
//			army = 1;
//		}

		// if (mainClassInstance.checkNeighbours(fromCountry, toCountry,
		// p.getPlayerId())) {
//		if (fromCountry.getCountryArmy() - 1 >= 1) {
//			// System.out.println("in randomStrategy File: "+ "From country: " +
//			// fromCountry.getCountryName() + " to country:"+toCountry.getCountryName() +"
//			// player name "+ p.getPlayerName() );
//			// move 1 army
//			p.fortify(fromCountry, toCountry, 1);
//		}
//		} else {
//			System.out.println("Fortification not done: not neighbours");
//		}
		mainClassInstance.setNextPlayerTurn();
		p = MainClass.playerList.get(mainClassInstance.getPlayerTurn() - 1);

		mainClassInstance.nextTurn(p);
		// }
	}

}
