/**
 * 
 */
package ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ConsoleViewHandler;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

/**
 * @author Rohan
 *
 */
public class ReinforcementPhaseTest {

	static MainClass mC;
	static ConsoleViewHandler cv;
	static Player p;

	@BeforeClass
	public static void initReinforcementTest() {
		mC = new MainClass();
		cv = new ConsoleViewHandler();
	

	}

	@Test
	public void testReinforcementArmy() {
		try {
			mC.readMapFile("risk.map");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cv.phaseDecider("gameplayer -add r -add k");
		
		p = mC.playerList.get(0);

		cv.phaseDecider("placeall");

		assertEquals("Invalid command!", mC.errorFlag);

		// Before populating countries, player's country list should contain nothing
		assertEquals(0, mC.playerList.get(0).getPlayerTotalCountries());

		cv.phaseDecider("populatecountries");

		// after populate countries, as there are 42 total countries, player 1 will get
		// 21 countries
		assertEquals(21, mC.playerList.get(0).getPlayerCountries().size());

		// place armies by placeAll
		// mC.phaseDecider("placearmylaska"); //placing 1 army to alaska
		cv.phaseDecider("placeall");

		// check whether ever country has minimum 1 army or not
		boolean allCountryHasMinOneArmy = true;
		for (int i = 0; i < Map.getM_instance().getCountries().size(); i++) {
			{
				if (Map.getM_instance().getCountries().get(i).getCountryArmy() < 1) {
					allCountryHasMinOneArmy = false;
				}
			}
		}

		assertTrue(allCountryHasMinOneArmy);

		int beforeArmy = Map.getM_instance().getCountries().get(0).getCountryArmy();
		// System.out.println(beforeArmy);
		// test: reinforcement - chk whether armys being assigned or not at the time of
		// reinforcement
		// player 1 will get 7 army (21 countries / 3 = 7 army)
		assertEquals(8, p.assign_army());

		cv.phaseDecider("reinforce Alaska 7"); // max is 7 -- so it should return false

		// now alaska should have army
		assertEquals(beforeArmy + 7, Map.getM_instance().getCountries().get(0).getCountryArmy());

		// Test: Fortification - whether 2 countries entered are following rules for
		// fortification or not
		assertTrue(mC.checkNeighbours(Map.getM_instance().getCountryByName("alaska"),
				Map.getM_instance().getCountryByName("alberta"), 1));

		cv.phaseDecider("fortify Alaska Alberta " + String.valueOf(beforeArmy));

		// now alaska should have left with army it had at beginning
		assertEquals(7, Map.getM_instance().getCountries().get(0).getCountryArmy());
	}

}
