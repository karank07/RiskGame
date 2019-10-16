package ca.concordia.risk.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;

import ca.concordia.risk.controller.MainClass;

/**
 * This class manages view of the Risk game to get input from console and sets up a interactive console for the user to play the game.
 * @author Karan
 *
 */
public class Console implements ActionListener {
/**
 * @param textField manages the text-field for console
 */
	JTextField textField;
/**
 * @param submit for submitting the command
 * @param exit for exiting the gaming console
 */
	JButton submit, exit;
/**
 *  @param m instance of the main class
 */
	MainClass m;

/**
* This method creates and sets up a console for getting input commands and displaying messages/alerts accordingly.
*/
	public void createConsole() {
		m = new MainClass();
		JFrame frame = new JFrame("Console");
		textField = new JTextField();
		textField.setBounds(50, 100, 400, 30);

		submit = new JButton("Submit");
		submit.setBounds(50, 150, 100, 30);
		exit = new JButton("Exit");
		exit.setBounds(180, 150, 100, 30);
		frame.add(textField);
		frame.add(submit);
		frame.add(exit);
		frame.setSize(600, 300);
		frame.setLayout(null);
		frame.setVisible(true);
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.BLUE);
		frame.setBackground(Color.black);
		
		submit.addActionListener(this);
		//frame.getRootPane().setDefaultButton();
		exit.addActionListener(this);
		

	}

/**
 * Manages the actions being performed by the user
 * @param e holds action that occurs
 */
	public void actionPerformed(ActionEvent e) {
		String s1 = textField.getText();
		String errorFlag;
		
		if (e.getSource() == submit) {
			errorFlag=m.phaseDecider(s1+" stop");
			if(!errorFlag.contentEquals("false"))
			{
				alert(errorFlag);
			}
		} else if (e.getSource() == exit) {
			System.exit(0);
		}

	}
/**
 * Sets up an alert dialogue box
 * @param arg the message to be printed in the alert box
 */
	public void alert(String arg) {
		JFrame f = new JFrame();

		JOptionPane.showMessageDialog(f, arg);
	}
}
