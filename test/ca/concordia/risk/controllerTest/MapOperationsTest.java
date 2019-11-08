/**
 * 
 */
package test.ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.controller.MapOperations;
import ca.concordia.risk.controller.MapValidate;
import ca.concordia.risk.controller.MapWriter;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.utilities.ValidMapException;

/**
 * @author Rohan
 *
 */
public class MapOperationsTest {

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
		mC = new MainClass();
		mO = new MapOperations();
		mV = new MapValidate();
		mW = new MapWriter();
		countries = new HashMap<Integer, Country>();
		continents = new HashMap<Integer, Continent>();
		borders = new HashMap<Integer, ArrayList<Integer>>();

	}

	@Test
	public void test() throws ValidMapException {
		// Test: whether loading the map file is working or not
		String fileName = "demo.map";

		try {
			assertTrue(mW.loadMap(continents, countries, borders, fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not Found for loadmap");
		}

		File file = new File(
				Paths.get("").toAbsolutePath().toString() + File.separator + "maps" + File.separator + fileName);
		
		
		assertTrue(mO.isConnected(borders));

		assertTrue(mO.addContinent(Map.getM_instance(), continents, "c1", 3, "blue")); // add c11 to fail addcountry
																						// test
		assertTrue(mO.addContinent(Map.getM_instance(), continents, "c2", 4, "yellow"));

		assertTrue(mO.addCountry(Map.getM_instance(), continents, countries, borders, "a1", "c1"));
		assertTrue(mO.addCountry(Map.getM_instance(), continents, countries, borders, "a2", "c1"));

		assertTrue(mO.addNeighbours(Map.getM_instance(), countries, borders, "a1", "a2"));

		assertEquals("Continents and countries and neighbours under continent removed successfully",
				mO.deleteContinent(continents, countries, borders, "c1"));

		assertEquals("Continent removed successfully", mO.deleteContinent(continents, countries, borders, "c2"));

	}

}