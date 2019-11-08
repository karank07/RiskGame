package test.ca.concordia.risk.modelTest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ConsoleViewHandler;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Map;

/**
 * Test class for model class Player
 * @author Pranal
 *
 */
public class PlayerTest {

	private static MainClass main;
	private static ConsoleViewHandler cv;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		main = new MainClass();
		cv = new ConsoleViewHandler();
	}

	@Test
	public void testPlayerMethods() 
	{
		try 
		{
			main.readMapFile("risk.map");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		cv.phaseDecider("gameplayer -add k -add r");
		cv.phaseDecider("populatecountries");
		cv.phaseDecider("placeall");
		
		assertEquals("false",main.playerList.get(0).reinforceArmy("alaska", 7));
		//assertEquals("This country does not belongs to you!",main.playerList.get(0).reinforceArmy("north-west-territory", 7));
		
		cv.phaseDecider("reinforce alaska 7"); 
		cv.phaseDecider("attack -noattack");
		int testAlaska = Map.getM_instance().getCountryByName("alaska").getCountryArmy();
		cv.phaseDecider("fortify alaska alberta 3");
		
		assertNotEquals(testAlaska,Map.getM_instance().getCountryByName("alaska").getCountryArmy());
	}
	
	

	

}
