package Referent;

import Main.BaseView;

import javax.swing.*;
import java.awt.*;

public class ReferentView extends BaseView {
	public ReferentView() {
		super();
		Referent.getFromDatabase();
		setLayout(new BorderLayout());
		add(topPanel, "North");
		add(mainPanel, "Center");
		add(bottomPanel, "South");
		draw(false);
	}
	public void draw(boolean isCreate) {
		if (!isCreate) {
			clear();
			for (Referent referent : Referent.referents) mainPanel.add(createListPanel(referent));
			refresh();
			// rename cancel button to create
			createButton.setText("Create");
			inCreation = false;
		}
		else {
			clear();
			mainPanel.add(createFormPanel());
			refresh();
			// rename create button to cancel
			createButton.setText("Cancel");
			inCreation = true;
		}
	}
	public JPanel createListPanel(Referent referent) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		JLabel label = new JLabel(referent.toString());
		panel.add(label);
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(e -> {
			clear();
			mainPanel.add(createEditPanel(referent));
			refresh();
			// rename create button to cancel
			createButton.setText("Cancel");
			inCreation = true;
		});
		panel.add(editButton);
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(e -> {
			Referent.delete(referent);
			draw(false);
		});
		panel.add(deleteButton);
		return panel;
	}
	public JPanel createFormPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		// create text fields
		JTextField nomField = new JTextField();
		JTextField telephoneField = new JTextField();
		JTextField emailField = new JTextField();
		// create labels
		JLabel nomLabel = new JLabel("Nom *");
		JLabel telephoneLabel = new JLabel("Telephone *");
		JLabel emailLabel = new JLabel("Email *");
		// add labels and fields to the panel
		panel.add(nomLabel);
		panel.add(nomField);
		panel.add(telephoneLabel);
		panel.add(telephoneField);
		panel.add(emailLabel);
		panel.add(emailField);
		// add a button to create the referent
		JButton createButton = new JButton("Create");
		createButton.addActionListener(e -> {
			// check if all required fields are filled
			if (nomField.getText().isEmpty() || telephoneField.getText().isEmpty() || emailField.getText().isEmpty()) {
				Main.Logger.error("All fields must be filled");
				return;
			}
			// check if the email is valid
			if (!emailField.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
				Main.Logger.error("Invalid email");
				return;
			}
			// check if the telephone is valid
			if (!telephoneField.getText().matches("^(?:(?:\\+|00)33|0)[1-9](?:\\s?\\d{2}){4}$")) {
				Main.Logger.error("Invalid telephone");
				return;
			}
			Referent r = new Referent(nomField.getText(), telephoneField.getText(), emailField.getText());
			Referent.create(r);
			draw(false);
		});
		panel.add(createButton);
		return panel;
	}

	public JPanel createEditPanel(Referent referent) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		// create text fields
		JTextField nomField = new JTextField(referent.nom);
		JTextField telephoneField = new JTextField(referent.telephone);
		JTextField emailField = new JTextField(referent.mail);
		// create labels
		JLabel nomLabel = new JLabel("Nom *");
		JLabel telephoneLabel = new JLabel("Telephone *");
		JLabel emailLabel = new JLabel("Email *");
		// add labels and fields to the panel
		panel.add(nomLabel);
		panel.add(nomField);
		panel.add(telephoneLabel);
		panel.add(telephoneField);
		panel.add(emailLabel);
		panel.add(emailField);
		// add a button to create the referent
		JButton createButton = new JButton("Edit");
		createButton.addActionListener(e -> {
			// check if all required fields are filled
			if (nomField.getText().isEmpty() || telephoneField.getText().isEmpty() || emailField.getText().isEmpty()) {
				Main.Logger.error("All fields must be filled");
				return;
			}
			// check if the email is valid
			if (!emailField.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n")) {
				Main.Logger.error("Invalid email");
				return;
			}
			// check if the telephone is valid
			if (!telephoneField.getText().matches("^(?:(?:\\+|00)33|0)[1-9](?:\\s?\\d{2}){4}$\n")) {
				Main.Logger.error("Invalid telephone");
				return;
			}
			referent.nom = nomField.getText();
			referent.telephone = telephoneField.getText();
			referent.mail = emailField.getText();
			Referent.update(referent);
			draw(false);
		});
		panel.add(createButton);
		return panel;
	}
}
