package ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ReinforcementPhase;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

public class ReinforcementPhaseTest {

	ReinforcementPhase rP;
	Player p;
	List<Country> c;
	Map m;

	@BeforeClass
	public void before() {
		
		p = new Player(3, "test_player");

		rP = new ReinforcementPhase();
		c = new ArrayList<Country>();
		// generate demo countries
		for (int i = 0; i < 20; i++) {
			c.add(new Country(i, "country" + i, i + 1, 100, 100));
		}

	}

	@Test
	public void testMap() {
		
	}
	
	@Test
	public void test() {
		// intital army should be 3 for for each player
		assertEquals(3, p.getPlayerReinforceArmy());
		// assertEquals("rohan", "rohan");

		//rP.assign_army(p);
		System.out.println(p.getPlayerReinforceArmy());
		assertEquals(20/3, p.getPlayerReinforceArmy());
		// as player has now 3 countries

	}

}
