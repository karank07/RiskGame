/**
 * 
 */
package ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Country;

/**
 * @author Rohan
 *
 */
public class MainClassTest {

	static Country c;
	static MainClass mC;

	@BeforeClass
	public static void before() {
		c = new Country("c1", 2, 2, 2);
		mC = MainClass.getM_instance();
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

		mC.removePlayer("D");

		assertEquals(3, mC.playerList.size());
		
		
		
	}
}
