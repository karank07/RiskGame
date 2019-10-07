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
		button1.addActionListener(this);
		button2.addActionListener(this);

		frame.add(textField);
		frame.add(button1);
		frame.add(button2);
		frame.setSize(600, 600);
		frame.setLayout(null);
		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {  
	    String s1=textField.getText();
	    String[] temp = new String[2];
	    temp=s1.split(" ");
	    if(e.getSource()==button1){  
	        if(temp[0].contentEquals("loadmap"))
	        {
	        		fileName=temp[1];
	        	    try {
						m.readMapFile(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
	        else {
    			alert();
    		}
	    }else if(e.getSource()==button2){  
	    	System.exit(0);
	    }  
	    
	}

	public void alert() {
		JFrame f = new JFrame();
		
		JOptionPane.showMessageDialog(f,"Wrong Input!");
	}
}