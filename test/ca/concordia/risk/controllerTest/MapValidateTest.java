package test.ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class MapValidateTest {

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

	/**
	 * test method to perform tests on Map Phase functions
	 */
	@Test
	public void testMapClass() {
		try {

			// Test: whether loading the map file is working or not
			assertTrue(mW.loadMap(continents, countries, borders, "risk.map"));

			File file = new File(Paths.get("").toAbsolutePath().toString() + "\\maps\\" + "risk.map");

			// Test: check whether map file is valid in terms of format or not. i.e does it
			// contains CONTINENT_HEADER, COUNTRIES_HEADER and BORDERS_HEADER
			assertTrue(mV.validateFile(file));

			// Test : map validation – map is a connected graph
			assertTrue(mO.isConnected(borders));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
