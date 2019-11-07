package ca.concordia.risk.view;

import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Continent;
import ca.concordia.risk.model.Map;
import ca.concordia.risk.model.Observer;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.utilities.GamePhase;

import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;

/**
 * This class manages the player's World Domination View and all the information necessary to 
 * play the phases
 * @author Karan
 *
 */
public class GameView implements Observer {
	JTextField nameField;
	JTextField phaseField;
	JTextField armyField;
	JTextField reinforceCountryField;
	JTextField reinforceArmiesField;
	JTextField attackingCountryField;
	JTextField defendingCountryField;
	JTextField attackerRollsField;
	JTextField defenderRollsField;
	JTextField resultField;
	JTextField fortifyingCountryField;
	JTextField fortifiedCountryField;
	JTextField fortifyArmiesField;
	private JTextField infantryField;
	private JTextField cavalryField;
	private JTextField artilleryField;
	private JTable playerTable;
	private JTable continentTable;

	/**
	 * default constructor
	 */
	public GameView() {
		createView();
	}

	static GameView gv;

	/**
	 * @return the static instance of the Game View
	 */
	public static GameView get_instance() {
		if (gv == null) {
			gv = new GameView();
			return gv;

		} else
			return gv;
	}

	/**
	 * This method creates the actual Jframe to display the information for the Domination View
	 */
	public void createView() {
		JFrame frame = new JFrame("Player World Domination View");
		frame.getContentPane().setLayout(null);

		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(null);
		playerPanel.setBounds(480, 30, 663, 151);
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBounds(12, 48, 96, 23);
		playerPanel.add(nameLabel);

		nameField = new JTextField();
		nameField.setEditable(false);
		nameField.setBounds(76, 48, 146, 23);
		nameLabel.setLabelFor(nameField);
		playerPanel.add(nameField);

		JLabel armyLabel = new JLabel("Armies");
		armyLabel.setBounds(12, 100, 69, 23);
		playerPanel.add(armyLabel);

		armyField = new JTextField();
		armyField.setEditable(false);
		armyField.setBounds(76, 100, 146, 23);
		armyLabel.setLabelFor(armyField);
		playerPanel.add(armyField);

		JPanel reinforcePanel = new JPanel();
		reinforcePanel.setLayout(null);
		reinforcePanel.setBounds(12, 74, 434, 112);

		JLabel reinforceCountryLabel = new JLabel("Reinforce Country");
		reinforceCountryLabel.setBounds(12, 23, 130, 22);
		reinforcePanel.add(reinforceCountryLabel);

		reinforceCountryField = new JTextField();
		reinforceCountryField.setEditable(false);
		reinforceCountryField.setBounds(154, 23, 198, 22);
		reinforceCountryLabel.setLabelFor(reinforceCountryField);
		reinforcePanel.add(reinforceCountryField);

		JLabel reinforceArmyLabel = new JLabel("Reinforce Army");
		reinforceArmyLabel.setBounds(12, 58, 130, 22);
		reinforcePanel.add(reinforceArmyLabel);

		reinforceArmiesField = new JTextField();
		reinforceArmiesField.setEditable(false);
		reinforceArmiesField.setBounds(154, 58, 198, 22);
		reinforceArmyLabel.setLabelFor(reinforceArmiesField);
		reinforcePanel.add(reinforceArmiesField);

		JPanel attackPanel = new JPanel();
		attackPanel.setLayout(null);
		attackPanel.setBounds(12, 225, 434, 254);

		JLabel attackingCountryLabel = new JLabel("Attacking Country");
		attackingCountryLabel.setBounds(12, 44, 126, 23);
		attackPanel.add(attackingCountryLabel);

		attackingCountryField = new JTextField();
		attackingCountryField.setEditable(false);
		attackingCountryField.setBounds(150, 44, 200, 23);
		attackingCountryLabel.setLabelFor(attackingCountryField);
		attackPanel.add(attackingCountryField);

		JLabel defendingCountryLabel = new JLabel("Defending Country");
		defendingCountryLabel.setBounds(12, 80, 126, 23);
		attackPanel.add(defendingCountryLabel);

		defendingCountryField = new JTextField();
		defendingCountryField.setEditable(false);
		defendingCountryField.setBounds(150, 80, 200, 23);
		defendingCountryLabel.setLabelFor(defendingCountryField);
		attackPanel.add(defendingCountryField);

		JLabel attackerRollsLabel = new JLabel("Attacker Rolls");
		attackerRollsLabel.setBounds(12, 116, 126, 23);
		attackPanel.add(attackerRollsLabel);

		attackerRollsField = new JTextField();
		attackerRollsField.setEditable(false);
		attackerRollsField.setBounds(150, 116, 200, 23);
		attackerRollsLabel.setLabelFor(attackerRollsField);
		attackPanel.add(attackerRollsField);

		JLabel defenderRollsLabel = new JLabel("Defender Rolls");
		defenderRollsLabel.setBounds(12, 152, 126, 23);
		attackPanel.add(defenderRollsLabel);

		defenderRollsField = new JTextField();
		defenderRollsField.setEditable(false);
		defenderRollsField.setBounds(150, 152, 200, 23);
		defenderRollsLabel.setLabelFor(defenderRollsField);
		attackPanel.add(defenderRollsField);

		JLabel resultLabel = new JLabel("Result");
		resultLabel.setBounds(12, 188, 126, 23);
		attackPanel.add(resultLabel);

		resultField = new JTextField();
		resultField.setEditable(false);
		resultField.setBounds(150, 188, 200, 23);
		resultLabel.setLabelFor(resultField);
		attackPanel.add(resultField);

		JPanel fortifyPanel = new JPanel();
		fortifyPanel.setLayout(null);
		fortifyPanel.setBounds(12, 520, 434, 164);
		JLabel fortifyingCountry = new JLabel("Fortifying Country");
		fortifyingCountry.setBounds(12, 13, 128, 30);
		fortifyPanel.add(fortifyingCountry);

		fortifyingCountryField = new JTextField();
		fortifyingCountryField.setEditable(false);
		fortifyingCountryField.setBounds(150, 17, 201, 23);
		fortifyingCountry.setLabelFor(fortifyingCountryField);
		fortifyPanel.add(fortifyingCountryField);

		JLabel fortifiedCountryLabel = new JLabel("Fortified Country");
		fortifiedCountryLabel.setBounds(12, 56, 128, 23);
		fortifyPanel.add(fortifiedCountryLabel);

		fortifiedCountryField = new JTextField();
		fortifiedCountryField.setEditable(false);
		fortifiedCountryField.setBounds(150, 56, 201, 23);
		fortifiedCountryLabel.setLabelFor(fortifiedCountryField);
		fortifyPanel.add(fortifiedCountryField);

		JLabel fortifyArmiesLabel = new JLabel("Fortify Armies");
		fortifyArmiesLabel.setBounds(10, 92, 128, 23);
		fortifyPanel.add(fortifyArmiesLabel);

		fortifyArmiesField = new JTextField();
		fortifyArmiesField.setEditable(false);
		fortifyArmiesField.setBounds(150, 92, 201, 23);
		fortifyArmiesLabel.setLabelFor(fortifyArmiesField);
		fortifyPanel.add(fortifyArmiesField);

		frame.getContentPane().add(playerPanel);

		JLabel lblCards = new JLabel("Cards");
		lblCards.setBounds(248, 13, 56, 16);
		playerPanel.add(lblCards);

		JLabel lblArtillery = new JLabel("Infantry");
		lblArtillery.setBounds(261, 42, 56, 16);
		playerPanel.add(lblArtillery);

		infantryField = new JTextField();
		infantryField.setEditable(false);
		infantryField.setBounds(337, 39, 33, 22);
		playerPanel.add(infantryField);
		infantryField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Cavalry");
		lblNewLabel.setBounds(261, 74, 56, 16);
		playerPanel.add(lblNewLabel);

		cavalryField = new JTextField();
		cavalryField.setEditable(false);
		cavalryField.setColumns(10);
		cavalryField.setBounds(337, 71, 33, 22);
		playerPanel.add(cavalryField);

		JLabel lblArtillery_1 = new JLabel("Artillery");
		lblArtillery_1.setBounds(261, 107, 56, 16);
		playerPanel.add(lblArtillery_1);

		artilleryField = new JTextField();
		artilleryField.setEditable(false);
		artilleryField.setColumns(10);
		artilleryField.setBounds(337, 104, 33, 22);
		playerPanel.add(artilleryField);
		frame.getContentPane().add(reinforcePanel);
		frame.getContentPane().add(attackPanel);
		frame.getContentPane().add(fortifyPanel);

		JLabel phaseLabel = new JLabel("Phase");
		phaseLabel.setBounds(23, 30, 120, 22);
		frame.getContentPane().add(phaseLabel);

		phaseField = new JTextField();
		phaseField.setBounds(166, 30, 200, 22);
		frame.getContentPane().add(phaseField);
		phaseField.setEditable(false);
		phaseLabel.setLabelFor(phaseField);

		playerTable = new JTable();
		playerTable.setBorder(new CompoundBorder());
		playerTable.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Player", "Armies", "Map Control%" }));
		playerTable.getColumnModel().getColumn(2).setPreferredWidth(99);
		playerTable.setBounds(480, 225, 669, 254);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(480, 225, 669, 254);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(playerTable);

		continentTable = new JTable();
		continentTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Continent", "Owned By" }));
		continentTable.setBounds(480, 520, 669, 164);

		JScrollPane scrollPaneTable2 = new JScrollPane();
		scrollPaneTable2.setBounds(480, 520, 669, 164);
		frame.getContentPane().add(scrollPaneTable2);
		scrollPaneTable2.add(continentTable);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(scrollPaneTable2);
		frame.setVisible(true);
		frame.setBounds(10, 10, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	/**
	 * This method clears the various Jframe textFields mentioned
	 */
	private void clearall() {
		phaseField.setText("");
		infantryField.setText("");
		cavalryField.setText("");
		artilleryField.setText("");
		reinforceCountryField.setText("");
		reinforceArmiesField.setText("");
		attackingCountryField.setText("");
		defendingCountryField.setText("");
		attackerRollsField.setText("");
		defenderRollsField.setText("");
		resultField.setText("");
		fortifyingCountryField.setText("");
		fortifiedCountryField.setText("");
		fortifyArmiesField.setText("");

	}

	/**
	 *Updates various Jframe field according to the observer pattern flow that is 
	 *when notified about a change
	 */
	@Override
	public void update(Object o) {
		if (o instanceof Player) {
			DefaultTableModel playerModel = (DefaultTableModel) playerTable.getModel();
			playerModel.setRowCount(0);
			DefaultTableModel continentModel = (DefaultTableModel) continentTable.getModel();
			continentModel.setRowCount(0);
			int totalCountries = Map.getM_instance().getCountries().size();
			if (!MainClass.player_country_map.isEmpty()) {
				for (Player player : MainClass.playerList) {
					int playerCountryCount = MainClass.player_country_map.get(player).size();
					playerModel.addRow(new Object[] { player.getPlayerName(), player.getPlayerTotalArmies(),
							(int) ((playerCountryCount / totalCountries) * 100) });

				}

			}
			if(!Map.getM_instance().getBorders().isEmpty()) {
				for(Continent c:Map.getM_instance().getContinents().values()) {
					continentModel.addRow(new Object[] {c.getContinentName(), MainClass.playerList.get(c.getRuler()).getPlayerName()});
				}
			}

			Player p = (Player) o;

			nameField.setText(p.getPlayerName());
			armyField.setText(String.valueOf(p.getPlayerTotalArmies()));
			phaseField.setText(p.getCurrentPhase().toString());
			artilleryField.setText(String.valueOf(p.getPlayerCards().get(Card.ARTILLERY)));
			cavalryField.setText(String.valueOf(p.getPlayerCards().get(Card.CAVALRY)));
			infantryField.setText(String.valueOf(p.getPlayerCards().get(Card.INFANTRY)));
			if (p.getCurrentPhase().equals(GamePhase.REINFORCEMENT)) {
				clearall();
				phaseField.setText(p.getCurrentPhase().toString());

				reinforceArmiesField.setText(String.valueOf(p.getPlayerReinforceArmy()));
				reinforceCountryField.setText(p.getReinforceCountry());
			}
			if (p.getCurrentPhase().equals(GamePhase.ATTACK) && !p.getDefenderDiceResult().isEmpty()) {
				phaseField.setText(p.getCurrentPhase().toString());
				attackingCountryField.setText(p.getAttackingCountry());
				defendingCountryField.setText(p.getDefendingCountry());
				attackerRollsField.setText(p.getDiceResult().toString());
				defenderRollsField.setText(p.getDefenderDiceResult().toString());
				// resultField.setText(p.getDiceWins().toString());
			}
			if (p.getFortifyArmies() != 0) {
				phaseField.setText(p.getCurrentPhase().toString());

				fortifyingCountryField.setText(p.getFortifyCountry());
				fortifiedCountryField.setText(p.getFortifiedCountry());
				fortifyArmiesField.setText(String.valueOf(p.getFortifyArmies()));

			}

		} else
			System.out.println("in else");
	}

}
