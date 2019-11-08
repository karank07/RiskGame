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
public class MapValidateTest {


	static MapValidate mV;

	static Map mapInstance;

	@BeforeClass
	public static void before() {
		mV = new MapValidate();

	} 

	@Test
	public void test() throws ValidMapException {
		String fileName = "demo.map";

		File file = new File(
				Paths.get("").toAbsolutePath().toString() + File.separator + "maps" + File.separator + fileName);
		try {
			assertTrue(mV.validateFile(file));
		} catch (FileNotFoundException e) {
			System.out.println("File not found for validation");
		}
		

		
	}

}
