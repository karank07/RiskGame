package ca.concordia.risk.controller;

import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.utilities.*;
import java.io.IOException;

import ca.concordia.risk.view.Console;
//import ca.concordia.risk.view.GameView;

/**
 * This class handles the view and model class values for the same following the observer pattern
 * @author Karan
 *
 */
public class ConsoleViewHandler {

	static Console c;
	MainClass main;
	// static GameView gameView;

	public static void main(String[] args) {
		c = new Console();
		c.createConsole();
	}

	/**
	 * Default constructor
	 */
	public ConsoleViewHandler() {
		main = new MainClass();

	}

	/**
	 * This method handles the input passed from the console and calling appropriate phase method
	 *  from  model and other controller classes
	 * @param inputCommand String of command passed from Console
	 * @return errorFlag indicating valid or invalid command and phase flow
	 */
	public String phaseDecider(String inputCommand) {
		inputCommand = inputCommand.toLowerCase().trim();
		System.out.println(inputCommand + "\n");
		String[] commands = inputCommand.split(" ");
		String errorFlag = "false";

		switch (commands[0]) {

		case "loadmap":
			if (commands.length == 2 && MainClass.playerList.isEmpty()) {
				String fileName = commands[1];
				try {
					main.readMapFile(fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else
				errorFlag = "Invalid command!";
			break;

		case "gameplayer":
			if (commands.length >= 3 && commands.length % 2 != 0 && !Map.m_instance.getBorders().isEmpty()) {
				errorFlag = "false";
				main.gamePlayer(inputCommand);
			} else
				errorFlag = "Invalid command!";
			break;

		case "populatecountries":
			
			if (commands.length == 1 && !MainClass.playerList.isEmpty()) {
				if(MainClass.playerList.size()>Map.getM_instance().getCountries().size()) {
					errorFlag="Too many players for this map!";
					break;
				}
				errorFlag = "false";
				main.startupPhase();
			} else
				errorFlag = "Invalid command!";
			break;

		case "placearmy":
			if (commands.length == 2 && !MainClass.playerList.isEmpty() && !MainClass.player_country_map.isEmpty()) {
				errorFlag = "false";
				errorFlag = main.placeArmyByCountryName(commands[1]);
			} else {
				errorFlag = "Check the country name entered!";
			}
			break;

		case "placeall":
			if (commands.length == 1 && !MainClass.playerList.isEmpty() && !MainClass.player_country_map.isEmpty()) {
				errorFlag = "false";
				main.placeAll();

			} else
				errorFlag = "Invalid command!";
			break;
		case "reinforce":
		case "exchangecards":
		case "attack":
		case "fortify":
		case "defend":
		case "attackmove":
			errorFlag = main.startGamePhase(inputCommand);
			break;
		case "editcontinent":
			if (commands.length >= 3 ) {
				errorFlag = main.editcontinent(inputCommand);
			} else
				errorFlag = "Invalid command!";
			break;

		case "editcountry":
			if (commands.length >= 3 && !Map.m_instance.getContinents().isEmpty()) {
				errorFlag =main.editcountry(inputCommand);
			} else
				errorFlag = "Invalid command!";
			break;

		case "editneighbor":
			if (commands.length >= 3 && !Map.m_instance.getCountries().isEmpty()) {
				errorFlag = errorFlag = main.editneigbor(inputCommand);
				
			} else
				errorFlag = "Invalid command!";
			
			break;

		case "savemap":
			if(commands.length==2 )
				errorFlag = main.savemap(inputCommand);
			else errorFlag="Invalid command";
			break;

		case "showmap":
			if(commands.length==1)
				main.showmap();
			else errorFlag="Invalid command";
			break;
		case "validatemap":
			if(commands.length==1 && !Map.m_instance.getBorders().isEmpty())
				main.validatemap();
			else errorFlag="Invalid command!";
			break;
		case "editmap":
			if(commands.length==2)
				main.editmap(inputCommand);
			else errorFlag="Invalid command";
			break;

		default:
			// set flag for alert("Wrong Input!");
			errorFlag = "Check commands again!";
		}
		return errorFlag;

	}

}
