/**
 * 
 */
package test.ca.concordia.risk.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.FortificationPhase;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.controller.ReinforcementPhase;
import ca.concordia.risk.model.Map;

/**
 * @author rohan
 *
 */
public class ReinforcementPhaseTest extends MainClass {

	static MainClass mC;
	static ReinforcementPhase rP;
	static FortificationPhase fP;

	@BeforeClass
	public static void initReinforcementTest() {
		mC = new MainClass();
		rP = new ReinforcementPhase();
		fP = new FortificationPhase();

	}

	@Test
	public void testReinforcementArmy() {
		try {
			mC.readMapFile("risk.map");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(mC.countryList);

		// player list should be 0 initially
		assertEquals(0, MainClass.playerList.size());

		// now adding 2 players and final playerList size should be 2

		mC.phaseDecider("gameplayer -add r -add k");

		assertEquals(2, mC.playerList.size());

		mC.phaseDecider("placeall");

		assertEquals("Invalid command!", mC.errorFlag);

		// Before populating countries, player's country list should contain nothing
		assertEquals(0, mC.playerList.get(0).getPlayerTotalCountries());

		mC.phaseDecider("populatecountries");

		// after populate countries, as there are 42 total countries, player 1 will get
		// 21 countries
		assertEquals(21, mC.playerList.get(0).getPlayerCountries().size());

		// place armies by placeAll
		//mC.phaseDecider("placearmylaska");		//placing 1 army to alaska
		mC.phaseDecider("placeall");

		// check whether ever country has minimum 1 army or not
		boolean allCountryHasMinOneArmy = true;
		for (int i = 0; i < mC.countries.size(); i++) {
			{
				if(mC.countryList.get(i).getCountryArmy() < 1) {
					allCountryHasMinOneArmy = false;
				}
			}
		}
		
		assertTrue(allCountryHasMinOneArmy);
		
		int beforeArmy = MainClass.countryList.get(0).getCountryArmy();
		//System.out.println(beforeArmy);
		//test: reinforcement - chk whether armys being assigned or not at the time of reinforcement
		//player 1 will get 7 army (21 countries / 3 = 7 army)
		assertEquals( 8, rP.assign_army(MainClass.playerList.get(0)));
		
		mC.phaseDecider("reinforce Alaska 7");		//max is 7 -- so it should return false
		
		//now alaska should have army
		assertEquals(beforeArmy + 7, MainClass.countryList.get(0).getCountryArmy());

		//Test: Fortification  - whether 2 countries entered are following rules for fortification or not
		assertTrue(fP.checkNeighbours(Map.getM_instance().getCountryByName("alaska"), Map.getM_instance().getCountryByName("alberta"), 1));

		mC.phaseDecider("fortify Alaska Alberta "+ String.valueOf(beforeArmy));
		
		//now alaska should have left with army it had at beginning
		assertEquals( 7,  MainClass.countryList.get(0).getCountryArmy());
	}

}
