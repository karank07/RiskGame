package ca.concordia.risk.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import ca.concordia.risk.controller.ConsoleViewHandler;
import ca.concordia.risk.controller.MainClass;

/**
 * This class manages view of the Risk game to get input from console and sets up a interactive console for the user to play the game.
 * @author Karan
 *
 */
public class Console implements ActionListener, KeyListener {
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
 *  @param cvh instance of the ConsoleViewHandler class
 */
	
	ConsoleViewHandler cvh;

/**
* This method creates and sets up a console for getting input commands and displaying messages/alerts accordingly.
*/
	public void createConsole() {
		cvh=new ConsoleViewHandler();
		JFrame frame = new JFrame("Console");
		textField = new JTextField();
		textField.setBounds(50, 100, 400, 30);
		textField.addKeyListener(this);

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
		String errorFlag="no input";
		if (e.getSource() == submit) {
			errorFlag=cvh.phaseDecider(s1);
			if(!errorFlag.contentEquals("false"))
			{
				alert(errorFlag);
			}
			textField.setText("");
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

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyPressed(KeyEvent e) {

	if(e.getKeyCode()==KeyEvent.VK_ENTER) 
	{
		submit.doClick();
	}
	
	else if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
	{
		exit.doClick();
	}
	
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
}
