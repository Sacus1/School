package Unite;

import Main.BaseView;
import javax.swing.*;
import java.awt.*;

public class UniteView extends BaseView{
	public UniteView() {
		super();
		Unite.getFromDatabase();
		setLayout(new BorderLayout());
		// add panels to the frame
		add(topPanel, "North");
		add(mainPanel, "Center");
		add(bottomPanel, "South");
		draw(false);
	}
	public void draw(boolean isCreate) {
		if (!isCreate) {
			clear();
			for (Unite unite : Unite.unites) mainPanel.add(createListPanel(unite));
			refresh();
			// rename cancel button to create
			createButton.setText("Create");
			inCreation = false;
		}
		else {
			clear();
			mainPanel.add(createCreatePanel());
			refresh();
			// rename create button to cancel
			createButton.setText("Cancel");
			inCreation = true;
		}
	}

	private Panel createListPanel(Unite unite) {
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(1, 3));
		Label label = new Label(unite.toString());
		panel.add(label);
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(e -> {
			clear();
			mainPanel.add(createEditPanel(unite));
			refresh();
			// rename create button to cancel
			createButton.setText("Cancel");
			inCreation = true;
		});
		panel.add(editButton);
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(e -> {
			Unite.delete(unite);
			draw(false);
		});
		panel.add(deleteButton);
		return panel;
	}

	private Panel createEditPanel(Unite unite) {
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(1, 2));
		// nom
		Label nomLabel = new Label("nom");
		panel.add(nomLabel);
		TextField nomField = new TextField(unite.nom);
		panel.add(nomField);
		// create button
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(e -> {
			unite.nom = nomField.getText();
			Unite.update(unite);
			draw(false);
		});
		panel.add(updateButton);
		return panel;
	}

	private Panel createCreatePanel() {
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(2, 2));
		// nom
		Label nomLabel = new Label("nom");
		panel.add(nomLabel);
		TextField nomField = new TextField();
		panel.add(nomField);
		// create button
		JButton createButton = new JButton("Create");
		createButton.addActionListener(e -> {
			Unite unite = new Unite(nomField.getText());
			Unite.create(unite);
			draw(false);
		});
		panel.add(createButton);
		return panel;
	}

}
