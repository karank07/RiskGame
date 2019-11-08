package ca.concordia.risk.controllerTest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ConsoleViewHandler;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

public class AttackPhaseTest {
	
	static MainClass main;
	static ConsoleViewHandler cv;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		main = new MainClass();
		cv = new ConsoleViewHandler();
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
		cv.phaseDecider("attack alaska north-west-territory 3");
		
		assertNotEquals(0,main.playerList.get(0).getDiceResult().size());
		
		cv.phaseDecider("defend 1");
		
		assertNotEquals(0,main.playerList.get(1).getDiceResult().size());
		
		assertFalse(main.canAttack(Map.getM_instance().getCountryByName("alaska"), Map.getM_instance().getCountryByName("alberta")));
		
		assertFalse(main.canAttack(Map.getM_instance().getCountryByName("alaska"), Map.getM_instance().getCountryByName("ontario")));
		
		assertFalse(main.checkDiceRA(0, Map.getM_instance().getCountryByName("alaska")));
		
		//assertFalse(main.checkDiceRD(3, Map.getM_instance().getCountryByName("north-west-territory")));
		
		
		
		
	}

}
