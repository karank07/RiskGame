/**
 * 
 */
package ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ConsoleViewHandler;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.controller.TournamentController;
import ca.concordia.risk.model.TournamentMode;
import ca.concordia.risk.model.TournamentResult;

/**
 * @author Rohan
 *
 */
public class TournamentControllerTest {
	static MainClass mC;
	static ConsoleViewHandler cVH;
	static TournamentController tournamentController;
	static TournamentMode tournamentObject;
	static TournamentResult tournamentResult;

	@Before
	public void before() {
		mC = MainClass.getM_instance();
		cVH = new ConsoleViewHandler();
		tournamentObject = TournamentMode.getInstance();
		tournamentResult = TournamentResult.getInstance();
	}

	@After
	public void tearDown() {
		mC.resetGame();
		
		mC.playerList.clear();
		mC.player_country_map.clear();
		cVH = null;
		tournamentResult.results.clear();
		
		tournamentObject = null;

	}
	@Test
	public void WinTest() {
		String s = cVH.phaseDecider("tournament -m risk.map -p cheater-benevolent -g 1 -d 4");

		// tournamentController.showResult();
		System.out.println("=======================================");
		List<String> results_2 = new ArrayList<String>();
		results_2 = tournamentResult.results.get(new String("risk.map"));
		System.out.println(results_2.get(0));
		// System.out.println(results.get(1));
		assertEquals("1", results_2.get(0));
	}
	@Test
	public void DrawTest() {
		String s = cVH.phaseDecider("tournament -m risk.map -p benevolent-benevolent -g 1 -d 4");

		// tournamentController.showResult();
		System.out.println("======================================");
		List<String> results = new ArrayList<String>();
		results = tournamentResult.results.get(new String("risk.map"));
		System.out.println("draw result: " + results);
		// System.out.println(results.get(1));
		assertEquals("Draw", results.get(0));
		// assertEquals("Draw",results.get(1));
	}



}
