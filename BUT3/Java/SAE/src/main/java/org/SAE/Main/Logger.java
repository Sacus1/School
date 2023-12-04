package Main;

import javax.swing.*;
import java.awt.*;
/**
 * Logger class for displaying messages in a JFrame.
 */
public class Logger {

	/**
	 * Logs a message with a specified color and duration.
	 *
	 * @param message The message to be displayed.
	 * @param color   The color of the message text.
	 * @param time    The duration for which the message is displayed (in seconds).
	 */
	public static void log(String message, Color color, float time) {
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
		// close the frame after a certain time
		new java.util.Timer().schedule(
						new java.util.TimerTask() {
							@Override
							public void run() {
								frame.dispose();
							}
						},
						(int) (time * 1000)
		);
	}

	/**
	 * Logs a message with default color (black) and duration based on message length.
	 *
	 * @param message The message to be displayed.
	 */
	public static void log(String message) {
		log(message, Color.BLACK, message.length() / 10f);
	}

	/**
	 * Logs a formatted message with default color and duration.
	 *
	 * @param message The message format string.
	 * @param args    The arguments for the format string.
	 */
	public static  void log(String message, Object... args) {
		log(String.format(message, args));
	}

	/**
	 * Logs an error message with red color.
	 *
	 * @param message The error message to be displayed.
	 */
	public static void error(String message) {
		log(message, Color.RED);
	}
}
