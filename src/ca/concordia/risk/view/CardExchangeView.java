package ca.concordia.risk.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.DisplayMode;

import javax.swing.JTextField;
import javax.swing.DropMode;
import javax.swing.SwingConstants;

import org.hamcrest.core.IsInstanceOf;

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.model.Card;
import ca.concordia.risk.model.Observer;
import ca.concordia.risk.model.Player;
import ca.concordia.risk.utilities.GamePhase;

import javax.swing.JButton;

public class CardExchangeView implements ActionListener, Observer {

	private JFrame frame;
	private JTextField textField;
	private JTextField availableArtilleryCards, availableCavalryCards, availableInfantryCards;
	private JButton btnExchangeCards;
	private JTextField userInputArtilleryCards, userInputCavalryCards, userInputInfantryCards;
	private Player p;
	private int aCardCount = 0, cCardCount = 0, iCardCount = 0;

	/**
	 * Launch the application.
	 *//*
		 * public static void main(String[] args) { EventQueue.invokeLater(new
		 * Runnable() { public void run() { try { CardExchangeView window = new
		 * CardExchangeView(); window.frame.setVisible(true); } catch (Exception e) {
		 * e.printStackTrace(); } } }); }
		 */

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public CardExchangeView(Player p) {
		this.p = p;
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */

	private void initialize(Player p) {
		aCardCount = (int) ((boolean) ((p.getPlayerCards().get(Card.ARTILLERY) == null)) ? 0
				: (p.getPlayerCards().get(Card.ARTILLERY)));
		iCardCount = (int) ((boolean) ((p.getPlayerCards().get(Card.INFANTRY) == null)) ? 0
				: (p.getPlayerCards().get(Card.INFANTRY)));
		cCardCount = (int) ((boolean) ((p.getPlayerCards().get(Card.CAVALRY) == null)) ? 0
				: (p.getPlayerCards().get(Card.CAVALRY)));

		frame = new JFrame("Card Exchange View");
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel jLabel = new JLabel("Artillery Card(s): ");
		jLabel.setBounds(26, 41, 150, 50);
		frame.getContentPane().add(jLabel);

		availableArtilleryCards = new JTextField();
		availableArtilleryCards.setBounds(176, 41, 50, 50);
		frame.getContentPane().add(availableArtilleryCards);
		availableArtilleryCards.setColumns(10);
		availableArtilleryCards.setText(String.valueOf(aCardCount));
		availableArtilleryCards.setEditable(false);

		userInputArtilleryCards = new JTextField();
		userInputArtilleryCards.setBounds(266, 41, 50, 50);
		frame.getContentPane().add(userInputArtilleryCards);
		userInputArtilleryCards.setColumns(10);
		userInputArtilleryCards.setEditable(true);

		JLabel JLabel2 = new JLabel("Cavalry Card(s):");
		JLabel2.setBounds(26, 109, 150, 50);
		frame.getContentPane().add(JLabel2);

		availableCavalryCards = new JTextField();
		availableCavalryCards.setBounds(176, 109, 50, 50);
		frame.getContentPane().add(availableCavalryCards);
		availableCavalryCards.setColumns(10);
		availableCavalryCards.setText(String.valueOf(cCardCount));
		availableCavalryCards.setEditable(false);

		userInputCavalryCards = new JTextField();
		userInputCavalryCards.setBounds(266, 109, 50, 50);
		frame.getContentPane().add(userInputCavalryCards);
		userInputCavalryCards.setColumns(10);
		userInputCavalryCards.setEditable(true);

		JLabel JLabel3 = new JLabel("Infantry Card(s):");
		JLabel3.setBounds(26, 179, 160, 50);
		frame.getContentPane().add(JLabel3);

		availableInfantryCards = new JTextField();
		availableInfantryCards.setBounds(176, 179, 50, 50);
		jLabel.setLabelFor(availableInfantryCards);
		availableInfantryCards.setColumns(10);
		availableInfantryCards.setText(String.valueOf(iCardCount));
		availableInfantryCards.setEditable(false);
		frame.getContentPane().add(availableInfantryCards);

		userInputInfantryCards = new JTextField();
		userInputInfantryCards.setBounds(266, 179, 50, 50);
		userInputInfantryCards.setColumns(10);
		userInputInfantryCards.setEditable(true);
		frame.getContentPane().add(userInputInfantryCards);

		btnExchangeCards = new JButton("Exchange Cards");
		btnExchangeCards.setBounds(166, 253, 150, 29);
		frame.getContentPane().add(btnExchangeCards);

		JLabel lblAvailableCards = new JLabel("Available Cards");
		lblAvailableCards.setBounds(151, 23, 100, 16);
		frame.getContentPane().add(lblAvailableCards);

		JLabel lblEnterCardsFor = new JLabel("Enter cards for exchnage");
		lblEnterCardsFor.setBounds(256, 23, 160, 16);
		frame.getContentPane().add(lblEnterCardsFor);

		frame.setVisible(true);
		btnExchangeCards.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnExchangeCards) {
			
			try {

				int inputOfArtilleyCards = Integer.parseInt(userInputArtilleryCards.getText().trim());
				int inputOfCavalryCards = Integer.parseInt(userInputCavalryCards.getText().trim());

				int inputOfInfantryCards = Integer.parseInt(userInputInfantryCards.getText().trim());

				System.out.println(aCardCount + " , input-> " + inputOfArtilleyCards);
				System.out.println(cCardCount + " , input-> " + inputOfCavalryCards);
				System.out.println(iCardCount + " , input-> " + inputOfInfantryCards);

				if ((userInputArtilleryCards.getText().trim() != null && userInputCavalryCards != null
						&& userInputInfantryCards != null)
						&& ((inputOfArtilleyCards <= aCardCount) && (inputOfCavalryCards <= cCardCount)
								&& (inputOfInfantryCards <= iCardCount))) {

					MainClass.getM_instance().exchangeCardsForArmy(p, inputOfArtilleyCards, inputOfCavalryCards,
							inputOfInfantryCards);
					
					frame.setVisible(false);
					if(p.hasMoreThanFiveCards()) {
						initialize(p);
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Operation!");
				}
			}

			catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Invalid Operation!");
				System.out.println("Entered number is in inavlid format!");
			}
		}

	}

	@Override
	public void update(Object o) {
		if (o instanceof Player) {
			Player p = (Player) o;
			if(p.getCurrentPhase() == GamePhase.FORTIFICATION) {
				if (frame != null && frame.isActive()) {
					frame.dispose();
					initialize((Player) o);
				}
				else {
					initialize((Player) o);
				}
			}
			else if(p.getCurrentPhase() == GamePhase.REINFORCEMENT) {
				if (frame != null && frame.isActive()) {
					frame.dispose();
				}
			}
			
			

		}

	}
}
