package Main;

import javax.swing.*;
import java.awt.*;

public class Logger {
	public static void log(String message, Color color) {
		// make a jframe to display the message
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// set size to fit the message
		frame.setSize(message.length() * 11, 100);
		frame.setVisible(true);
		// make a jlabel to display the message
		JLabel label = new JLabel(message);
		label.setForeground(color);
		// set font size
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		frame.add(label);

	}
	public static void log(String message) {
		log(message, Color.BLACK);
	}
	public static  void log(String message, Object... args) {
		log(String.format(message, args));
	}

	public static void error(String message) {
		log(message, Color.RED);
	}
}
