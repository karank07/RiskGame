import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import ca.concordia.risk.controllerTest.MainClassTest;
import ca.concordia.risk.controllerTest.MapOperationsTest;
import ca.concordia.risk.controllerTest.MapValidateTest;
import ca.concordia.risk.controllerTest.MapWriterTest;
import ca.concordia.risk.modelTest.DiceTest;
import ca.concordia.risk.modelTest.PlayerAttackPhaseTest;
import ca.concordia.risk.modelTest.PlayerReinforcementTest;
import ca.concordia.risk.modelTest.playerFortifyTest;

@RunWith(Suite.class)
@SuiteClasses({MainClassTest.class, MapOperationsTest.class, MapValidateTest.class, MapWriterTest.class, PlayerAttackPhaseTest.class, PlayerReinforcementTest.class, playerFortifyTest.class, DiceTest.class})
public class TestSuit {

}
