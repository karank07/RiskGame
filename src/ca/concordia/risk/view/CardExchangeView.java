package ca.concordia.risk.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.DropMode;
import javax.swing.SwingConstants;<<<<<<<Updated upstream=======

import ca.concordia.risk.controller.MainClass;
import ca.concordia.risk.controller.ReinforcementPhase;

>>>>>>>Stashed changes
import javax.swing.JButton;

public class CardExchangeView implements ActionListener {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1, textField_2, textField_3;
	private JButton btnExchangeCards;
	private JTextField textField_input1, textField_input2, textField_input3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardExchangeView window = new CardExchangeView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CardExchangeView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame("Card Exchange View");
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel jLabel = new JLabel("Artillery Card(s): ");
		jLabel.setBounds(26, 41, 150, 50);
		frame.getContentPane().add(jLabel);

		textField_1 = new JTextField();
		textField_1.setBounds(176, 41, 50, 50);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("0");
		textField_1.setEditable(false);

		textField_input1 = new JTextField();
		textField_input1.setBounds(266, 41, 50, 50);
		frame.getContentPane().add(textField_input1);
		textField_input1.setColumns(10);
		textField_input1.setEditable(true);

		JLabel JLabel2 = new JLabel("Cavalry Card(s):");
		JLabel2.setBounds(26, 109, 150, 50);
		frame.getContentPane().add(JLabel2);

		textField_2 = new JTextField();
		textField_2.setBounds(176, 109, 50, 50);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText("0");
		textField_2.setEditable(false);

		textField_input2 = new JTextField();
		textField_input2.setBounds(266, 109, 50, 50);
		frame.getContentPane().add(textField_input2);
		textField_input2.setColumns(10);
		textField_input2.setEditable(true);

		JLabel JLabel3 = new JLabel("Infantry Card(s):");
		JLabel3.setBounds(26, 179, 160, 50);
		frame.getContentPane().add(JLabel3);

		textField_3 = new JTextField();
		textField_3.setBounds(176, 179, 50, 50);
		jLabel.setLabelFor(textField_3);
		textField_3.setColumns(10);
		textField_3.setText("0");
		textField_3.setEditable(false);
		frame.getContentPane().add(textField_3);

		textField_input3 = new JTextField();
		textField_input3.setBounds(266, 179, 50, 50);
		textField_input3.setColumns(10);
		textField_input3.setEditable(true);
		frame.getContentPane().add(textField_input3);

		btnExchangeCards = new JButton("Exchange Cards");
		btnExchangeCards.setBounds(166, 253, 150, 29);
		frame.getContentPane().add(btnExchangeCards);

		JLabel lblAvailableCards = new JLabel("Available Cards");
		lblAvailableCards.setBounds(151, 23, 100, 16);
		frame.getContentPane().add(lblAvailableCards);

		JLabel lblEnterCardsFor = new JLabel("Enter cards for exchnage");
		lblEnterCardsFor.setBounds(256, 23, 160, 16);
		frame.getContentPane().add(lblEnterCardsFor);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnExchangeCards) {
			String aCards = textField_input1.getText().strip().toString();
			String cCards = textField_input2.getText().strip().toString();
			String iCards = textField_input3.getText().strip().toString();

			try {
				if ((Integer.parseInt(aCards) <= Integer.parseInt(textField_1.getText().toString().strip()))
						&& (Integer.parseInt(cCards) <= Integer.parseInt(textField_2.getText().toString().strip()))
						&& (Integer.parseInt(iCards) <= Integer.parseInt(textField_3.getText().toString().strip()))) {

				}

				// r.exchangeCardsForArmy(, countOfArtillery, countOfCavalry, countOfInfantry);
			}

			catch (NumberFormatException n) {
				System.out.println("Entered number is in inavlid format!");
			}
		}

	}
}
