package Depot;

import Adresse.Adresse;
import Main.BaseView;
import Main.Logger;
import Referent.Referent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
/**
 * This class represents the view for the Depot module.
 * It extends the BaseView class and provides the UI for managing depots.
 */
public class DepotView extends BaseView {
	boolean showArchived = false;
	JCheckBox archivedCheckBox;
	/**
	 * Constructor for DepotView.
	 * Initializes the UI components and layout.
	 */
	public DepotView() {
		super();
		Depot.getFromDatabase();
		setLayout(new BorderLayout());
		archivedCheckBox = new JCheckBox("Show archived");
		archivedCheckBox.addActionListener(e -> {
			showArchived = !showArchived;
			draw(false);
		});
		topPanel.add(archivedCheckBox);
		// add panels to the frame
		add(topPanel, "North");
		add(mainPanel, "Center");
		add(bottomPanel, "South");
		draw(false);
	}

	/**
	 * Draws the view based on the isCreate flag.
	 * If isCreate is false, it shows the list of depots.
	 * If isCreate is true, it shows the form to create a new depot.
	 * @param isCreate Flag to determine whether to show the create form or the list of depots.
	 */
	public void draw(boolean isCreate) {
		if (!isCreate) {
			// show archived checkbox
			archivedCheckBox.setVisible(true);
			clear();
			for (Depot depot : Depot.depots) {
				if (depot.isArchived && !showArchived) continue;
				mainPanel.add(createListPanel(depot));
			}
			refresh();
			// rename cancel button to create
			createButton.setText("Create");
			inCreation = false;
		}
		else {
			// hide archived checkbox
			archivedCheckBox.setVisible(false);
			clear();
			mainPanel.add(createCreatePanel());
			refresh();
			// rename create button to cancel
			createButton.setText("Cancel");
			inCreation = true;
		}
	}

	/**
	 * Creates and returns a panel for creating a new depot.
	 * @return Panel for creating a new depot.
	 */
	private Panel createCreatePanel() {
		Panel panel = new Panel();
		Panel[] panels = new Panel[Depot.fields.length+2];
		Adresse.getFromDatabase();
		Referent.getFromDatabase();
		// adresse choice
		panels[0] = new Panel();
		panels[0].setLayout(new GridLayout(1, 2));
		panels[0].add(new Label("Adresse *"));
		Choice adresseChoice = new Choice();
		for (int i = 0; i < Adresse.adresses.size(); i++) adresseChoice.add(Adresse.adresses.get(i).toString());
		panels[0].add(adresseChoice);
		panel.add(panels[0]);
		// referent choice
		panels[1] = new Panel();
		panels[1].setLayout(new GridLayout(1, 2));
		panels[1].add(new Label("Referent *"));
		Choice referentChoice = new Choice();
		for (int i = 0; i < Referent.referents.size(); i++) referentChoice.add(Referent.referents.get(i).toString());
		panels[1].add(referentChoice);
		panel.add(panels[1]);
		for (int i = 2; i < Depot.fields.length; i++) {
			panels[i] = createField(Depot.fields[i], Depot.requiredFieldsList.contains(Depot.fields[i]));
			panel.add(panels[i]);
		}
		panels[Depot.fields.length] = new Panel();
		panels[Depot.fields.length].setLayout(new GridLayout(1, 2));
		panels[Depot.fields.length].add(new Label("Jour de livraison"));
		// Create components
		JButton button = new JButton("Select");
		JPopupMenu popupMenu = new JPopupMenu();
		ArrayList<JourSemaine> joursLivraisons = new ArrayList<>();
		JList<String> list = new JList<>(new String[]{"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"});
		// Set list to multiple interval selection
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// Add list to popup
		popupMenu.add(new JScrollPane(list));
		// Add action listener to button to show popup
		button.addActionListener(e -> popupMenu.show(button, 0, button.getHeight()));
		list.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				joursLivraisons.clear();
				for (int i : list.getSelectedIndices()) joursLivraisons.add(JourSemaine.values()[i]);
			}
		});
		panels[Depot.fields.length].add(button);
		panel.add(panels[Depot.fields.length]);
		// add image chooser
		panels[Depot.fields.length+1] = new Panel();
		panels[Depot.fields.length+1].setLayout(new GridLayout(1, 2));
		panels[Depot.fields.length+1].add(new Label("Image"));
		JButton imageButton = new JButton("Select");
		AtomicReference<File> image = new AtomicReference<>();
		imageButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				image.set(fileChooser.getSelectedFile());
				imageButton.setText(image.get().getName());
			}
		});
		panels[Depot.fields.length+1].add(imageButton);
		panel.add(panels[Depot.fields.length+1]);
		panel.setLayout(new GridLayout(panels.length/2 + 2, 2));
		JButton createButton = new JButton("Create");
		createButton.addActionListener(e -> create(panels,joursLivraisons, image.get()));
		panel.add(createButton);
		return panel;
	}

	/**
	 * Creates and returns a panel for listing a depot.
	 * @param depot The depot to be listed.
	 * @return Panel for listing a depot.
	 */
	private Panel createListPanel(Depot depot) {
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(1, 3));
		// add image if exists
		if (depot.image != null) {
			Panel panel2 = new Panel();
			panel2.setLayout(new GridLayout(2, 1));
			panel2.add(new JLabel(depot.nom, JLabel.CENTER));
			panel2.add(new JLabel(new ImageIcon(depot.image.getPath())));
			panel.add(panel2);
		} else panel.add(new JLabel(depot.nom, JLabel.CENTER));
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(e -> {
			clear();
			mainPanel.add(createEditPanel(depot));
			refresh();
		});
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(e -> {
			depot.delete();
			draw(false);
		});
		JButton archiveButton = new JButton(depot.isArchived ? "Unarchive" : "Archive");
		archiveButton.addActionListener(e -> {
			depot.archive();
			draw(false);
		});
		panel.add(editButton);
		panel.add(archiveButton);
		panel.add(deleteButton);
		return panel;
	}

	/**
	 * Creates a new depot based on the data entered in the form.
	 * @param panels The panels containing the data.
	 * @param joursLivraisons The days of delivery.
	 * @param image The image of the depot.
	 */
	private void create(Panel[] panels, ArrayList<JourSemaine> joursLivraisons, File image) {
		String[] values = new String[panels.length];
		for (int i = 2; i < Depot.fields.length; i++) {
			boolean isRequired = Depot.requiredFieldsList.contains(Depot.fields[i]);
			String textFieldValue = ((TextField) panels[i].getComponent(1)).getText();

			if (isRequired && textFieldValue.isEmpty()) {
				Logger.error("Please fill all required fields");
				return;
			}
			values[i] = textFieldValue;
			// replace empty strings with “NULL”
			if (values[i].isEmpty()) values[i] = "NULL";
		}
		// adresse
		values[0] = Integer.toString(Adresse.adresses.get(((Choice) panels[0].getComponent(1)).getSelectedIndex()).id);
		// referent
		values[1] = Integer.toString(Referent.referents.get(((Choice) panels[1].getComponent(1)).getSelectedIndex()).id);
		Depot depot = new Depot(Integer.parseInt(values[0]), Integer.parseInt(values[1]), values[2], values[3], values[4],
						values[5], values[6], values[7], image);
		depot.jourLivraison = joursLivraisons.toArray(new JourSemaine[0]);
		Depot.create(depot);
		draw(false);
	}

	/**
	 * Creates and returns a panel for a field.
	 * @param name The name of the field.
	 * @param required Whether the field is required or not.
	 * @return Panel for a field.
	 */
	private Panel createField(String name, boolean required) {
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(new Label(name + (required ? " *" : "")));
		panel.add(new TextField());
		return panel;
	}

	/**
	 * Creates and returns a panel for editing a depot.
	 * @param depot The depot to be edited.
	 * @return Panel for editing a depot.
	 */
	private Panel createEditPanel(Depot depot) {
		Panel panel = new Panel();
		Panel[] panels = new Panel[Depot.fields.length+2];
		// adresse choice
		panels[0] = new Panel();
		panels[0].setLayout(new GridLayout(1, 2));
		panels[0].add(new Label("Adresse *"));
		Choice adresseChoice = new Choice();
		for (int i = 0; i < Adresse.adresses.size(); i++) adresseChoice.add(Adresse.adresses.get(i).toString());
		// select adresse
		for (int i = 0; i < Adresse.adresses.size(); i++)
			if (Adresse.adresses.get(i).id == depot.Adresse_idAdresse) {
				adresseChoice.select(i);
				break;
			}
		panels[0].add(adresseChoice);
		panel.add(panels[0]);
		// referent choice
		panels[1] = new Panel();
		panels[1].setLayout(new GridLayout(1, 2));
		panels[1].add(new Label("Referent *"));
		Choice referentChoice = new Choice();
		for (int i = 0; i < Referent.referents.size(); i++) referentChoice.add(Referent.referents.get(i).toString());
		panels[1].add(referentChoice);
		// select referent
		for (int i = 0; i < Referent.referents.size(); i++)
			if (Referent.referents.get(i).id == depot.Referent_idReferent) {
				referentChoice.select(i);
				break;
			}
		panel.add(panels[1]);
		for (int i = 2; i < Depot.fields.length; i++) {
			panels[i] = createField(Depot.fields[i], Depot.requiredFieldsList.contains(Depot.fields[i]));
			Field field;
			try {
				field = depot.getClass().getDeclaredField(Depot.dbFields[i]);
			} catch (NoSuchFieldException e) {
				System.err.println("Depot don't have field " + Depot.dbFields[i]);
				throw new RuntimeException(e);
			}
			try {
				Object value = field.get(depot);
				if (value != null) ((TextField) panels[i].getComponent(1)).setText(value.toString());
			} catch (IllegalAccessException e) {
				System.err.println("Can't access field " + Depot.dbFields[i]);
				throw new RuntimeException(e);
			}
			panel.add(panels[i]);
		}
		// livraison
		panels[Depot.fields.length] = new Panel();
		panels[Depot.fields.length].setLayout(new GridLayout(1, 2));
		panels[Depot.fields.length].add(new Label("Jour de livraison"));
		// Create components
		JButton button = new JButton("Select");
		JPopupMenu popupMenu = new JPopupMenu();
		ArrayList<JourSemaine> joursLivraisons = new ArrayList<>();
		JList<String> list = new JList<>(new String[]{"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"});
		// Set list to multiple interval selection
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// select joursLivraisons
		for (int i = 0; i < depot.jourLivraison.length; i++)
			list.setSelectedIndex(depot.jourLivraison[i].ordinal());
		// Add list to popup
		popupMenu.add(new JScrollPane(list));
		// Add action listener to button to show popup
		button.addActionListener(e -> popupMenu.show(button, 0, button.getHeight()));
		list.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				joursLivraisons.clear();
				for (int i : list.getSelectedIndices()) joursLivraisons.add(JourSemaine.values()[i]);
			}
		});
		panels[Depot.fields.length].add(button);
		// add image chooser
		panels[Depot.fields.length+1] = new Panel();
		panels[Depot.fields.length+1].setLayout(new GridLayout(1, 2));
		panels[Depot.fields.length+1].add(new Label("Image"));
		JButton imageButton = new JButton("Select");
		AtomicReference<File> image = new AtomicReference<>();
		imageButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) image.set(fileChooser.getSelectedFile());
		});
		panel.add(panels[Depot.fields.length]);
		panel.setLayout(new GridLayout(panels.length/2 + 1, 2));

		JButton createButton = new JButton("Create");
		createButton.addActionListener(e -> {
			String[] values = new String[panels.length];
			for (int i = 2; i < panels.length; i++) {
				String textFieldValue = ((TextField) panels[i].getComponent(1)).getText();
				values[i] = textFieldValue.isEmpty() ? null : textFieldValue;
			}
			// adresse
			values[0] = Integer.toString(Adresse.adresses.get(((Choice) panels[0].getComponent(1)).getSelectedIndex()).id);
			// referent
			values[1] = Integer.toString(Referent.referents.get(((Choice) panels[1].getComponent(1)).getSelectedIndex()).id);
			depot.Adresse_idAdresse = Integer.parseInt(values[0]);
			depot.Referent_idReferent = Integer.parseInt(values[1]);
			depot.nom = values[2];
			depot.telephone = values[3];
			depot.presentation = values[4];
			depot.commentaire = values[5];
			depot.mail = values[6];
			depot.website = values[7];
			depot.image = image.get();
			depot.jourLivraison = joursLivraisons.toArray(new JourSemaine[0]);
			Depot.update(depot);
			Logger.log("Depot edited");

		});

		panel.add(createButton);
		return panel;
	}
}
