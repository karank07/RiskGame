package ca.concordia.risk.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ca.concordia.risk.model.Observer;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.utilities.GamePhase;

import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;

/**
 * @author Karan
 *
 */
public class GameView implements Observer {
	JTextField nameField;
	JTextField phaseField;
	JTextField armyField;
	JTextField infantryField;
	JTextField cavalryField;
	JTextField artilleryField;
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
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	private JTable table_1;

	public GameView() {
		createView();
	}

	static GameView gv;

	public static GameView get_instance() {
		if (gv == null) {
			gv = new GameView();
			return gv;

		} else
			return gv;
	}

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

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(337, 39, 33, 22);
		playerPanel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Cavalry");
		lblNewLabel.setBounds(261, 74, 56, 16);
		playerPanel.add(lblNewLabel);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(337, 71, 33, 22);
		playerPanel.add(textField_1);

		JLabel lblArtillery_1 = new JLabel("Artillery");
		lblArtillery_1.setBounds(261, 107, 56, 16);
		playerPanel.add(lblArtillery_1);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(337, 104, 33, 22);
		playerPanel.add(textField_2);
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

		table = new JTable();
		table.setBorder(new CompoundBorder());
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Player", "Armies", "Map Control%" }));
		table.getColumnModel().getColumn(2).setPreferredWidth(99);
		table.setBounds(480, 225, 669, 254);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(480, 225, 669, 254);
		frame.getContentPane().add(scrollPane);
		scrollPane.add(table);

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Continent", "Owned By" }));
		table_1.setBounds(480, 520, 669, 164);

		JScrollPane scrollPaneTable2 = new JScrollPane();
		scrollPaneTable2.setBounds(480, 520, 669, 164);
		frame.getContentPane().add(scrollPaneTable2);
		scrollPaneTable2.add(table_1);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(scrollPaneTable2);
		frame.setVisible(true);
		frame.setBounds(10, 10, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	@Override
	public void update(Object o) {
		if (o instanceof Player) {
			Player p = (Player) o;
			System.out.println(p.getCurrentPhase().toString());
			phaseField.setText(p.getCurrentPhase().toString());
			if (p.getCurrentPhase().equals(GamePhase.REINFORCEMENT)) {

				reinforceArmiesField.setText(String.valueOf(p.getPlayerReinforceArmy()));
				reinforceCountryField.setText(p.getReinforceCountry());
			}
			if (p.getCurrentPhase().equals(GamePhase.ATTACK) && !p.getDefenderDiceResult().isEmpty()) {
				System.out.println(p.getAttackingCountry());
				System.out.println(p.getDefendingCountry());
				attackingCountryField.setText(p.getAttackingCountry());
				defendingCountryField.setText(p.getDefendingCountry());
				attackerRollsField.setText(p.getDiceResult().toString());
				defenderRollsField.setText(p.getDefenderDiceResult().toString());
				//resultField.setText(p.getDiceWins().toString());
				// }
				if (p.getFortifyArmies() != 0) {
					fortifyingCountryField.setText(p.getFortifyCountry());
					fortifiedCountryField.setText(p.getFortifiedCountry());
					fortifyArmiesField.setText(String.valueOf(p.getFortifyArmies()));

				}

			} else
				System.out.println("in else");
		}

	}
}
