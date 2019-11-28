package ca.concordia.risk.modelTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.concordia.risk.controller.ConsoleViewHandler;
import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Country;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

public class PlayerReinforcementTest {

	static MainClass main;
	static ConsoleViewHandler cVH;

	@BeforeClass
	public static void beforeMethod() {
		main = new MainClass();
		cVH = new ConsoleViewHandler();
		//MainClass.player_country_map = new HashMap<Player, List<Country>>();
			
	}
	
	@After
	public void afterMethod() {
		main = null;
		cVH = null;
	}

	@Test
	public void test() {
		
		try {
			main.readMapFile("risk.map");
		} catch (Exception e) {
			e.printStackTrace();
		}

		cVH.phaseDecider("gameplayer -add k human -add r human");

		
		
		main.divideInitialArmies();
		
		List<Country> cList1 = new ArrayList<>();
		List<Country> cList2 = new ArrayList<>();
		
		MainClass.player_country_map.clear();
	for(Country c: Map.getM_instance().getCountries().values()) {
		if(c.getCountryID()%2==0) {
			cList1.add(c);
		}
		else {
			cList2.add(c);
		}
	}
	
	
	 MainClass.player_country_map.put(MainClass.playerList.get(0), cList1);
		System.out.println(cList1.size());

		 assertEquals(7, MainClass.playerList.get(0).assign_army());
		// cv.phaseDecider("reinforce alaska 7");

		List<Country> cc = new ArrayList<Country>();
		
		
		MainClass.player_country_map.clear();
		for (Country c : Map.getM_instance().getCountriesByContinent("Asia")) {

			cc.add(c);
		}
		Country c = new Country("c1", 2, 1, 1);
		cc.add(c);
		MainClass.player_country_map.put(MainClass.playerList.get(0), cc);
		assertEquals(11, MainClass.playerList.get(0).assign_army());

	}

}
