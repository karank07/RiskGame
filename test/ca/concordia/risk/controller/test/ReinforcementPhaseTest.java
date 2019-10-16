package ca.concordia.risk.controller.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ReinforcementPhase;
import ca.concordia.risk.model.Player;

public class ReinforcementPhaseTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@BeforeClass{
		Player p = new Player(1,"test_player");
		
	}

	@Test
	public void test() {
		ReinforcementPhase rP = new ReinforcementPhase();
		rP.beginReinforcement();
				
	}

}
