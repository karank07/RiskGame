package ca.concordia.risk.modelTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ConsoleViewHandler;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

public class PlayerAttackPhaseTest {

	static MainClass main;
	static ConsoleViewHandler cView;

	@Before
	public void beforeMethod() {
		main = new MainClass();
		cView = new ConsoleViewHandler();
	}
	
	@After
	public void afterMethod() {
		main = null;
		cView = null;
	
	}

	@Test
	public void test() {

		try {
			main.readMapFile("risk.map");
		} catch (Exception e) {
			e.printStackTrace();
		}

		cView.phaseDecider("gameplayer -add k -add r");

		assertEquals(0, main.playerList.get(0).getDiceResult().size());

		cView.phaseDecider("populatecountries");
		cView.phaseDecider("placeall");
		cView.phaseDecider("reinforce alaska 7");
		cView.phaseDecider("attack alaska north-west-territory 3");

		assertNotEquals(0, main.playerList.get(0).getDiceResult().size());

		cView.phaseDecider("defend 1");

		assertNotEquals(0, main.playerList.get(1).getDiceResult().size());

		assertFalse(main.canAttack(Map.getM_instance().getCountryByName("alaska"),
				Map.getM_instance().getCountryByName("alberta")));

		assertFalse(main.canAttack(Map.getM_instance().getCountryByName("alaska"),
				Map.getM_instance().getCountryByName("ontario")));

		assertFalse(main.checkDiceRA(0, Map.getM_instance().getCountryByName("alaska")));

		List<Country> cc = new ArrayList<Country>();
		MainClass.player_country_map.clear();
		for (Country c : Map.getM_instance().getCountries().values()) {

			cc.add(c);
		}

		MainClass.player_country_map.put(MainClass.playerList.get(0), cc);

		assertTrue(main.gameOver(MainClass.playerList.get(0)));
		// assertFalse(main.checkDiceRD(3,
		// Map.getM_instance().getCountryByName("north-west-territory")));

	}

}
