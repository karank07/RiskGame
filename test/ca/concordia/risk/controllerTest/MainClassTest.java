/**
 * 
 */
package test.ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;

/**
 * @author Rohan
 *
 */
public class MainClassTest {

	static MainClass mC;

	@BeforeClass
	public static void before() {
		mC = MainClass.getM_instance();
	}
	
	@After
	public void tearDown() {
	    mC = null;
	}

	@Test
	public void test() {
		// add players

		try {
			mC.readMapFile("risk.map");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mC.addPlayer("rv");
		mC.addPlayer("k");
		mC.addPlayer("D");
		mC.removePlayer("k");

		assertEquals(2, MainClass.playerList.size());

		// Global card deck test
		mC.generateDeck();

		assertTrue(mC.removeCardFromDeck(Card.ARTILLERY));
		List<Country> c_list=new ArrayList<Country>();
		Country c;
		c= Map.getM_instance().getCountries().get(0);
		c_list.add(c);
		c= Map.getM_instance().getCountries().get(1);
		c_list.add(c);
		
		c= Map.getM_instance().getCountries().get(2);
		c_list.add(c);
		
		mC.player_country_map.put(MainClass.playerList.get(0), c_list);

		assertTrue(mC.countryBelongsToPlayer(MainClass.playerList.get(0), "alaska"));

	}
}
