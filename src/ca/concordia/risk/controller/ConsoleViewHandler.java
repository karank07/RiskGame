package ca.concordia.risk.controller;

import ca.concordia.risk.utilities.*;
import java.io.IOException;

import ca.concordia.risk.view.Console;

/**
 * @author Karan
 *
 */
public class ConsoleViewHandler {

	static Console c;
	MainClass main;

	public static void main(String[] args) {
		c = new Console();
		c.createConsole();

	}

	public ConsoleViewHandler() {
		main = new MainClass();

	}

	public String phaseDecider(String inputCommand) {
		inputCommand = inputCommand.toLowerCase();
		System.out.println(inputCommand);
		String[] commands = inputCommand.split(" ");
		String errorFlag = "false";

		switch (commands[0]) {
		case "loadmap":
			/*
			 * if (!phase.contentEquals("loadmap")) { errorFlag = "Invalid command!"; return
			 * errorFlag; } phase = "loadmap"; mapPhase = "end";
			 */
			if (commands.length == 2) {
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
			/*
			 * if (!phase.contentEquals("gameplayer")) { errorFlag = "Invalid command!";
			 * return errorFlag; }
			 */
			if (commands.length>3 && commands.length % 2 != 0) {
				errorFlag="false";
				main.gamePlayer(inputCommand);
			}
			else errorFlag="Invalid command!";
			break;

		case "populatecountries":
			/*
			 * if (gamePlayerSet == true && !playerList.isEmpty()) { phase =
			 * "populatecountries"; }
			 */
			/*
			 * if (!phase.contentEquals("populatecountries")) { errorFlag =
			 * "Invalid command!"; return errorFlag; }
			 */
			if (commands.length == 1) {
				errorFlag = "false";
				main.startupPhase();

			} else
				errorFlag = "Invalid command!";
			break;

		case "placearmy":

			/*
			 * if (!phase.contentEquals("placearmy")) { errorFlag = "Invalid command!";
			 * return errorFlag; }
			 */
			// currentPlayer=1;

			if (commands.length == 2) {
				errorFlag = "false";
				errorFlag = main.placeArmyByCountryName(commands[1]);
			} else {
				errorFlag = "Check the country name entered!";
			}
			/*
			 * if (playerList.get(currentPlayer).getPlayerTotalArmies() == 0) placeArmyFlag
			 * = true;
			 */
			break;

		case "placeall":
			/*
			 * if (!phase.contentEquals("placearmy")) { errorFlag = "Invalid command!";
			 * return errorFlag; }
			 */
			if (commands.length == 1) {
				errorFlag = "false";
				main.placeAll();
				
			} else
				errorFlag = "Invalid command!";
			/*
			 * if (playerList.get(currentPlayer).getPlayerTotalArmies() == 0) phase =
			 * "reinforce";
			 */
			break;
		case "reinforce":
		case "attack":
		case "fortify":
		case "defend":
		case "attackmove":
			main.startGamePhase(inputCommand);
			break;
		case "editcontinent":
			main.editcontinent(inputCommand);
			break;

		case "editcountry":
			/*
			 * if (!mapPhase.contentEquals("edit")) { errorFlag = "Invalid command!"; return
			 * errorFlag; }
			 */
			main.editcountry(inputCommand);
			break;

		case "editneighbor":
			/*
			 * if (!mapPhase.contentEquals("edit")) { errorFlag = "Invalid command!"; return
			 * errorFlag; }
			 */
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
