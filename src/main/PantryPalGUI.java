package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the PantryPalGUI class this is the visual representation of
 * The PantryPal application designed to make the user experience
 * Easier and more enjoyable
 * 
 * @author Keith Haith
 */
public class PantryPalGUI {

	/**
	 * This is the main method *
	 */
	public static void main(String[] args) {
		pantryPal();
	}

	/**
	 * This is the pantryPal method which will create the initial GUI
	 * to display the instructions and lead to the actual application
	 * 
	 */
	public static void pantryPal() {
		JFrame frame = new JFrame("PantryPal");
		JLabel label = new JLabel("Thanks For Using PantryPal");
		Image icon = Toolkit.getDefaultToolkit().getImage("./src/images/smallmainlogo.png");
		frame.setIconImage(icon);
		Object[] options = { "OK", "Help" };
		int answer = JOptionPane.showOptionDialog(null,
				"Welcome to the PantryPal Application, If you are new to the application click the help button, otherwise click ok",
				"Welcome", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);
		System.out.println("Selected Option: " + answer);
		if (answer == 1) {
			help();
		}
		frame.add(label);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
	}

	public static void help() {
		JOptionPane.showMessageDialog(null, "To run this application", "instructions", 1);
	}
}
