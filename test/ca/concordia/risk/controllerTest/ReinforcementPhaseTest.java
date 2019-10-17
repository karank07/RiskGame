package test.ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.controller.MapOperations;
import ca.concordia.risk.controller.MapValidate;
import ca.concordia.risk.controller.MapWriter;
import ca.concordia.risk.controller.ReinforcementPhase;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

public class ReinforcementPhaseTest {

	static ReinforcementPhase rP;
	static Player p;
	static List<Country> c;
	static Map m;
	static MapOperations mO;
	static MainClass mC;
	static MapWriter mW;
	static MapValidate mV;
	static HashMap<Integer, Country> countries;
	static HashMap<Integer, Continent> continents;
	static HashMap<Integer, ArrayList<Integer>> borders;
	
	static Map mapInstance;

	@BeforeClass
	public static void before() {

		p = new Player(3, "test_player");

		rP = new ReinforcementPhase();
		c = new ArrayList<Country>();
		// generate demo countries
		for (int i = 0; i < 20; i++) {
			c.add(new Country(i, "country" + i, i + 1, 100, 100));
		}
		p.setPlayerCountries(c);
		mC = new MainClass();
		mO = new MapOperations();

		mV = new MapValidate();
		mW = new MapWriter();
		countries = new HashMap<Integer, Country>();
		continents = new HashMap<Integer, Continent>();
		borders = new HashMap<Integer, ArrayList<Integer>>();
		
		

	}

	@Test
	public void testMapClass() {
		try {
			assertTrue(mW.loadMap(continents, countries, borders, "risk.map"));
			
			assertTrue(mO.isConnected(borders));
			
			File file = new File(Paths.get("").toAbsolutePath().toString() + "\\maps\\" + "risk.map");
			
			assertTrue(mV.validateFile(file));
			
			 mapInstance.setContinents(continents);
			 mapInstance.setCountries(countries);
			 mapInstance.setBorders(borders);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testReinforcementArmy() {
		// initial army should be 3 for for each player
		assertEquals(3, p.getPlayerReinforceArmy());
		// assertEquals("rohan", "rohan");

		 mapInstance.setContinents(continents);
		 
		 rP.assign_army(p);
		System.out.println(p.getPlayerReinforceArmy());
		// assertEquals(20 / 3, p.getPlayerReinforceArmy());
		// as player has now 3 countries

	}

}
