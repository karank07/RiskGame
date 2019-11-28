package ca.concordia.risk.modelTest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ConsoleViewHandler;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Dice;
import ca.concordia.risk.model.Player;

/**
 * Test class for model class Dice
 * @author Pranal
 *
 */

public class DiceTest {

	static Dice d;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		d = new Dice(3);
	}


	@Test
	public void testRollDice() {
		Player p = new Player(1,"x","human");
		assertEquals(3,d.rollDice(3, p).size());
	}

}
