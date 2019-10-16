package ca.concordia.risk.controller.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

import ca.concordia.risk.controller.ReinforcementPhase;

public class ReinforcementPhaseTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		ReinforcementPhase rP = new ReinforcementPhase();
		rP.beginReinforcement(player);
				
	}

}
