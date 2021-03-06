package ca.concordia.risk.controller;

import ca.concordia.risk.model.GameSave;
import ca.concordia.risk.model.GameSaveBuilder;
import ca.concordia.risk.model.GameSaveScenario;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Player;

import java.io.IOException;
import java.util.ArrayList;

import ca.concordia.risk.view.Console;
//import ca.concordia.risk.view.GameView;

/**
 * This class handles the view and model class values for the same following the
 * observer pattern
 * 
 * @author Karan
 *
 */
public class ConsoleViewHandler {

	static Console c;
	MainClass main;
	Map gm;
	GameSave gs;
	ArrayList<Player> pl;
	Player p;

	public static void main(String[] args) {
		c = new Console();
		c.createConsole();
	}

	/**
	 * constructor to instantiate object
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
					errorFlag = main.readMapFile(fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else
				errorFlag = "Invalid command!";
			break;

		case "gameplayer":
			if (commands[1].equalsIgnoreCase("-remove") && commands.length >= 3 && commands.length % 2 != 0 && !Map.m_instance.getBorders().isEmpty()) {
				errorFlag = "false";
				main.gamePlayer(inputCommand);
			} else if(commands[1].equalsIgnoreCase("-add")) {
				main.gamePlayer(inputCommand);
			}else
				errorFlag = "Invalid command!";
			break;

		case "populatecountries":
			
			if (commands.length == 1 && !MainClass.playerList.isEmpty()) {
				if(MainClass.playerList.size()>Map.getM_instance().getCountries().size()) {
					errorFlag="Too many players for this map!";
					break;
				}
				else if(MainClass.playerList.size()==1) {
					errorFlag="Not enough players!";
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
				errorFlag = main.editneigbor(inputCommand);
				
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
				errorFlag = main.validatemap();
			else errorFlag="Invalid command!";
			break;
		case "editmap":
			if(commands.length==2)
				errorFlag = main.editmap(inputCommand);
			else errorFlag="Invalid command";
			break;
		case "tournament":
			if(commands.length==9) {
				main.setupTournament(commands[2],commands[4],commands[6],commands[8]);
			}
			break;
		case "savegame":
			gm=Map.getM_instance();
			if ((gm.getCountries().size() > 0) && (gm.getContinents().size() > 0)
				&& (gm.getBorders().size() > 0)) {
				try {
					
					GameSaveBuilder gsb=new GameSaveScenario() {
					};
					gs = new GameSave();
					gsb.setGameState(gs);
					gsb.buildGameMap(gm);
					gsb.buildPlayersList(pl);
					gsb.buildPlayer(p);
					gs = gsb.getGameState();
					main.saveGameFile(gs, commands[1]);
				}catch(Exception e) {
					
				}
			}
			break;
			case "loadgame":
				/*if (commands.length == 2) {
						try {
							gs = new GameState();
							result = msc.loadGameReading(gs.getGameMap(), gs.getPlayersList(), gs.getPlayer(),
									inputCommand[1]);
							if (result.contains("Success")) {
								GameStateBuilder gsb = new GameStateScenario();
								gsb.setGameState(gs);
								gs = gsb.getGameState();
								gm = gs.getGameMap();
								pl = gs.getPlayersList();
								p = gs.getPlayer();
								if (p.getGameState().equals("ATTACK")) {
									if (!(p.getAttackerName().equals(""))) {
										addInputCommandList(true, "reinforce");
									} else if (p.getAttackerDice().size() == p.getDiceRolled()) {
										addInputCommandList(true, "attack");
									} else if (p.getDiceRolled() == 0) {
										addInputCommandList(true, "attackmove");
									} else {
										addInputCommandList(true, "reinforce");
									}
								} else if (p.getGameState().equals("FORTIFY")) {
									addInputCommandList(true, "attack");
								} else if (p.getGameState().equals("STARTUP")) {
									addInputCommandList(true, "loadmap");
								}
								if (!(p.getGameState().equals("STARTUP"))) {
									executeBehaviour(pl.getListOfPlayers().get(p.getCurrentPlayerTurn()).getStrategy());
								}
							} else {
								System.out.println("\nUnable to loadgame. Please try again later");
							}
						} catch (Exception ex) {
							System.out.println("\nError Occurred. Please try again later");
						}
					} else {
						System.out.println("\n" + inputCommand[1] + " file does not exist");
					}
				} else {
					System.out.println("\nloadgame command format is incorrect");
				}

*/		
		default:
			// set flag for alert("Wrong Input!");
			errorFlag = "Check commands again!";
		}
		return errorFlag;

	}

}
