package Main;

import javax.swing.*;
import java.awt.*;

/**
 * This abstract class represents a base view with a layout and some panels.
 * It extends JPanel, which is a generic lightweight container.
 */
public abstract class BaseView extends JPanel {
	public JButton createButton = new JButton("Create");
	public boolean inCreation = false;
	protected static JPanel mainPanel, topPanel, bottomPanel;

	/**
	 * Constructor for BaseView.
	 * Sets up the layout and initializes the panels and the create button.
	 */
	public BaseView() {
		setLayout(new BorderLayout());
		initializePanels();
		setupCreateButton();
	}

	/**
	 * This method initializes the panels used in the view.
	 */
	private void initializePanels() {
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

	}

	/**
	 * This method sets up the create button and its action listener.
	 */
	private void setupCreateButton() {
		topPanel.add(createButton);
		createButton.addActionListener(e -> draw(!inCreation));
	}

	/**
	 * Abstract method to be implemented by subclasses.
	 * It is triggered when the create button is clicked.
	 *
	 * @param isCreate A boolean indicating whether to create or not.
	 */
	public abstract void draw(boolean isCreate);

	/**
	 * This method clears all components from the main panel.
	 */
	public static void clear() {
		mainPanel.removeAll();
	}

	/**
	 * This method refreshes the main panel by revalidating and repainting it.
	 */
	public static void refresh() {
		mainPanel.revalidate();
		mainPanel.repaint();
	}
}
