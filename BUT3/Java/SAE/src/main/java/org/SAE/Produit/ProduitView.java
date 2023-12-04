package Produit;
import Main.BaseView;
import Unite.Unite;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class ProduitView extends BaseView{
	public ProduitView() {
		super();
		Produit.getFromDatabase();
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
			for (Produit produit : Produit.produits) mainPanel.add(createListPanel(produit));
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
	private JPanel createListPanel(Produit produit) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		JLabel label = new JLabel(produit.toString());
		panel.add(label);
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(e -> {
			clear();
			mainPanel.add(createEditPanel(produit));
			refresh();
			// rename create button to cancel
			createButton.setText("Cancel");
			inCreation = true;
		});
		panel.add(editButton);
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(e -> {
			Produit.delete(produit);
			draw(false);
		});
		panel.add(deleteButton);
		return panel;
	}

	public JPanel createCreatePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 1));
		// create the form
		JTextField nomField = new JTextField();
		panel.add(new JLabel("Nom"));
		panel.add(nomField);
		JTextField descriptionField = new JTextField();
		panel.add(new JLabel("Description"));
		panel.add(descriptionField);
		JTextField prixField = new JTextField();
		panel.add(new JLabel("Prix"));
		panel.add(prixField);
		Unite.getFromDatabase();
		Choice uniteChoice = new Choice();
		for (int i = 0; i < Unite.unites.size(); i++) uniteChoice.add(Unite.unites.get(i).toString());
		panel.add(new JLabel("Unite"));
		panel.add(uniteChoice);
		// image choice
		JButton imageButton = new JButton("Choose image");
		AtomicReference<File> image = new AtomicReference<>();
		imageButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				image.set(fileChooser.getSelectedFile());
			}
		});
		// create a button to create the produit
		JButton createButton = new JButton("Create");
		createButton.addActionListener(e -> {
			Produit produit = new Produit(nomField.getText(), descriptionField.getText(), Double.parseDouble(prixField.getText()), Unite.unites.get(uniteChoice.getSelectedIndex()).id, image.get());
			draw(false);
		});
		panel.add(createButton);
		return panel;
	}
	private Panel createEditPanel(Produit produit) {
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(5, 2));
		// nom
		Label nomLabel = new Label("nom");
		panel.add(nomLabel);
		TextField nomField = new TextField(produit.nom);
		panel.add(nomField);
		// description
		Label descriptionLabel = new Label("description");
		panel.add(descriptionLabel);
		TextField descriptionField = new TextField(produit.description);
		panel.add(descriptionField);
		// prix
		Label prixLabel = new Label("prix");
		panel.add(prixLabel);
		TextField prixField = new TextField(Double.toString(produit.prix));
		panel.add(prixField);
		// unite
		Label uniteLabel = new Label("unite");
		panel.add(uniteLabel);
		Choice uniteChoice = new Choice();
		for (int i = 0; i < Unite.unites.size(); i++) uniteChoice.add(Unite.unites.get(i).toString());
		uniteChoice.select(produit.idUnite);
		panel.add(uniteChoice);
		// image choice
		JButton imageButton = new JButton("Choose image");
		AtomicReference<File> image = new AtomicReference<>();
		imageButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				image.set(fileChooser.getSelectedFile());
			}
		});
		// create button
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(e -> {
			produit.nom = nomField.getText();
			produit.description = descriptionField.getText();
			produit.prix = Double.parseDouble(prixField.getText());
			produit.idUnite = Unite.unites.get(uniteChoice.getSelectedIndex()).id;
			produit.image = image.get();
			Produit.update(produit);
			draw(false);
		});
		panel.add(updateButton);
		return panel;
	}
}
