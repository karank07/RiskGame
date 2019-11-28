/**
 * 
 */
package ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import org.junit.After;
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

	@BeforeClass
	public static void before() {
		mC = MainClass.getM_instance();
		cVH = new ConsoleViewHandler();
		tournamentObject = TournamentMode.getInstance();
		tournamentResult = TournamentResult.getInstance();
	}

	@After
	public void tearDown() {
		mC = null;
		mC.playerList.clear();
	}

	@Test
	public void test() {
		String s = cVH.phaseDecider("tournamant -m risk.map -p benevolent-benevolent -g 1 -d 4");
		tournamentController = new TournamentController();
		tournamentController.showResult();
		
	}

}
