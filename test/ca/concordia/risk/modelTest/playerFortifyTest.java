package ca.concordia.risk.modelTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ConsoleViewHandler;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;

public class playerFortifyTest {

	
	static MainClass main;
	static ConsoleViewHandler cv;
	
	@Before
	public void beforeMethod() {
		main = new MainClass();
		cv = new ConsoleViewHandler();
	}
	
	@After
	public void afterMethod() {
		main = null;
		cv = null;
	}
	
	@Test
	public void test() {
		try 
		{
			main.readMapFile("risk.map");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		cv.phaseDecider("gameplayer -add k -add r");
		
		assertEquals(0,main.playerList.get(0).getDiceResult().size());
		
		cv.phaseDecider("populatecountries");
		cv.phaseDecider("placeall");
		cv.phaseDecider("reinforce alaska 7");
		cv.phaseDecider("attack -noattack");
		
		Country c1 = Map.getM_instance().getCountryByName("alaska");
		Country c2 = Map.getM_instance().getCountryByName("alberta");
		
		System.out.println("Check Neighbours in fortiy: "+ main.checkNeighbours(c1, c2, 1));
		assertTrue(main.checkNeighbours(c1, c2, 1));
		
		
		
		
	}

}
