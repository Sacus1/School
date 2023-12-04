package Main;
import Depot.DepotView;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
	public static SQL sql ;
	public static JFrame frame;
	static String url = "jdbc:mysql://localhost:3306/SAE";
	private static void resetSelectedButton(JButton[] buttons) {
		for (JButton button : buttons) button.setBackground(null);
	}
	public static void main(String[] args) {
		sql = new SQL(url,"root","");
		frame = new JFrame("Gestion");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);
		JPanel mainPanel = new JPanel();
		// create a list of buttons on the side of the frame
		JPanel leftPanel = new JPanel();
		// add buttons
		JButton[] buttons = {new JButton("Depot"), new JButton("Referent"), new JButton("Adresse"),new JButton("Unité"),new JButton("Produit")};
		Arrays.sort(buttons, Comparator.comparing(JButton::getText));
		leftPanel.setLayout(new GridLayout(buttons.length, 1));
		for (JButton button : buttons) {
			button.addActionListener(e -> {
				// clear the frame
				mainPanel.removeAll();
				// add the view to the frame
				switch (button.getText()) {
					case "Depot":
						mainPanel.add(new DepotView());
						resetSelectedButton(buttons);
						button.setBackground(Color.LIGHT_GRAY);
						break;
					case "Referent":
						mainPanel.add(new Referent.ReferentView());
						resetSelectedButton(buttons);
						button.setBackground(Color.LIGHT_GRAY);
						break;
					case "Adresse":
						mainPanel.add(new Adresse.AdresseView());
						resetSelectedButton(buttons);
						button.setBackground(Color.LIGHT_GRAY);
						break;
					case "Unité":
						mainPanel.add(new Unite.UniteView());
						resetSelectedButton(buttons);
						button.setBackground(Color.LIGHT_GRAY);
						break;
					case "Produit":
						mainPanel.add(new Produit.ProduitView());
						resetSelectedButton(buttons);
						button.setBackground(Color.LIGHT_GRAY);
						break;
				}
				// refresh the frame
				frame.revalidate();
				frame.repaint();
			});
			// change the size of the button to fit the frame
			leftPanel.add(button);
		}
		frame.add(leftPanel, "West");
		frame.add(mainPanel, "Center");
		frame.setVisible(true);
		// when the frame is closed, close the sql connection
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				sql.close();
			}
		});
	}

	/**
	 * Converts an InputStream to an image file.
	 *
	 * @param inputStream The InputStream to convert.
	 * @return The image file.
	 * @throws IOException If an I/O error occurs.
	 */
	public static File convertInputStreamToImage(InputStream inputStream) throws IOException {
		if (inputStream == null) return null;
    File tempFile = File.createTempFile("image", ".png");
    tempFile.deleteOnExit();
    try (OutputStream out = new FileOutputStream(tempFile)) {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) out.write(buffer, 0, length);
    }
    return tempFile;
	}
}
