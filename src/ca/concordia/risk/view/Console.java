package ca.concordia.risk.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;

import ca.concordia.risk.controller.MainClass;

/**
 * @author Karan
 *
 */
public class Console implements ActionListener {
	JTextField textField;
	JButton submit, exit;
	MainClass m;

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

	public void actionPerformed(ActionEvent e) {
		String s1 = textField.getText();
		String errorFlag;
		
		if (e.getSource() == submit) {
			errorFlag=m.phaseDecider(s1);
			if(!errorFlag.contentEquals("false"))
			{
				alert(errorFlag);
			}
		} else if (e.getSource() == exit) {
			System.exit(0);
		}

	}

	public void alert(String arg) {
		JFrame f = new JFrame();

		JOptionPane.showMessageDialog(f, arg);
	}
}
