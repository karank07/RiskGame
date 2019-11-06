package ca.concordia.risk.controller;

import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.utilities.*;
import java.io.IOException;

import ca.concordia.risk.view.Console;
//import ca.concordia.risk.view.GameView;

/**
 * @author Karan
 *
 */
public class ConsoleViewHandler {

	static Console c;
	MainClass main;
	//static GameView gameView;

	public static void main(String[] args) {
		c = new Console();
		c.createConsole();
	}

	public ConsoleViewHandler() {
		main = new MainClass();

	}

	public String phaseDecider(String inputCommand) {
		inputCommand = inputCommand.toLowerCase();
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
		case "attack":
		case "fortify":
		case "defend":
		case "attackmove":
			errorFlag = main.startGamePhase(inputCommand);
			break;
		case "editcontinent":
			main.editcontinent(inputCommand);
			break;

		case "editcountry":
			main.editcountry(inputCommand);
			break;

		case "editneighbor":
			main.editneigbor(inputCommand);
			break;

		case "savemap":
			/*
			 * if (mapPhase.contentEquals("end")) { errorFlag = "Invalid command!"; return
			 * errorFlag; }
			 */
			main.savemap(inputCommand);
			break;

		case "showmap":
			// if (!mapPhase.contentEquals("end")) {
			main.showmap();
			break;
		case "validatemap":
			main.validatemap();
			break;
		case "editmap":
			/*
			 * if (!mapPhase.contentEquals("editmap")) { errorFlag = "Invalid command!";
			 * return errorFlag; }
			 */
			main.editmap(inputCommand);
			break;

		default:
			// set flag for alert("Wrong Input!");
			errorFlag = "Check commands again!";
		}
		return errorFlag;

	}

}
