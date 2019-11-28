/**
 * 
 */
package ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
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
public class MapWriterTest {

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
		mC = MainClass.getM_instance();
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
		String fileName = "usa.map";

		assertEquals("false", mC.editmap(fileName));
		// assertFalse(mW.loadMap(continents, countries, borders, fileName));

	}

}
