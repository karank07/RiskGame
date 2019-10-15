package ca.concordia.risk.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import ca.concordia.risk.controller.MainClass;

/**
 * @author Karan
 *
 */
public class Console implements ActionListener {
	JTextField textField;
	JButton button1, button2;
	String fileName;
	MainClass m;

	public void createConsole() {
		m = new MainClass();
		JFrame frame = new JFrame("Console");
		textField = new JTextField();
		textField.setBounds(50, 100, 400, 30);

		button1 = new JButton("Submit");
		button1.setBounds(50, 200, 150, 50);
		button2 = new JButton("Exit");
		button2.setBounds(220, 200, 150, 50);
		frame.add(textField);
		frame.add(button1);
		frame.add(button2);
		frame.setSize(600, 600);
		frame.setLayout(null);
		frame.setVisible(true);

		button1.addActionListener(this);
		button2.addActionListener(this);
		

	}

	public void actionPerformed(ActionEvent e) {
		String s1 = textField.getText();
		boolean errorFlag=false;
		if (e.getSource() == button1) {
			errorFlag=m.phaseDecider(s1);
			if(errorFlag)
			{
				alert("Wrong Input!");
			}
		} else if (e.getSource() == button2) {
			System.exit(0);
		}

	}

	public void alert(String arg) {
		JFrame f = new JFrame();

		JOptionPane.showMessageDialog(f, arg);
	}
}
