import javax.swing.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("Enregistrer un nouveau vol");
		frame.add(label, BorderLayout.NORTH);
		JPanel mainPanel = new JPanel();
		// list of panels
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		frame.add(mainPanel, BorderLayout.SOUTH);
		JPanel numVolPanel = new JPanel();
		mainPanel.add(numVolPanel);
		JLabel numeroDeVol = new JLabel("Numéro de vol");
		numVolPanel.add(numeroDeVol);
		JTextField numeroDeVolField = new JTextField(10);
		numVolPanel.add(numeroDeVolField);
		JPanel heureDepartPanel = new JPanel();
		mainPanel.add(heureDepartPanel);
		JLabel heureDeDepart = new JLabel("Heure de départ");
		heureDepartPanel.add(heureDeDepart);
		JTextField heureDeDepartField = new JTextField(10);
		heureDepartPanel.add(heureDeDepartField);
		JPanel heureArrivePanel = new JPanel();
		mainPanel.add(heureArrivePanel);
		JLabel heureArrive = new JLabel("Heure d'arrivée");
		heureArrivePanel.add(heureArrive);
		JTextField heureArriveField = new JTextField(10);
		heureArrivePanel.add(heureArriveField);
		JPanel AeroportDepartPanel = new JPanel();
		mainPanel.add(AeroportDepartPanel);
		JLabel aeroportDepart = new JLabel("Entrer Aeroport de départ");
		AeroportDepartPanel.add(aeroportDepart);
		JTextField aeroportDepartField = new JTextField(10);
		AeroportDepartPanel.add(aeroportDepartField);
		JPanel aeroportArrivePanel = new JPanel();
		mainPanel.add(aeroportArrivePanel);
		JLabel aeroportArrive = new JLabel("Entrer Aeroport d'arrivée");
		aeroportArrivePanel.add(aeroportArrive);
		JTextField aeroportArriveField = new JTextField(10);
		aeroportArrivePanel.add(aeroportArriveField);
		JButton button = new JButton("Enregistrer");
		button.addActionListener((e) -> {
			// check if all fields are filled
			if (numeroDeVolField.getText().isEmpty() || heureDeDepartField.getText().isEmpty() || heureArriveField.getText().isEmpty() || aeroportDepartField.getText().isEmpty() || aeroportArriveField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// check if the number of the flight is a number
			int numVol;
			try {
				numVol = Integer.parseInt(numeroDeVolField.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Le numéro de vol doit être un nombre", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// check if the departure time is 2 numbers separated by :
			String[] heureDepartSplit = heureDeDepartField.getText().split(":");
			int minutesDepart;
			int heuresDepart;
			if (heureDepartSplit.length != 2) {
				JOptionPane.showMessageDialog(frame, "L'heure de départ doit être au format hh:mm", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				heuresDepart = Integer.parseInt(heureDepartSplit[0]);
				minutesDepart = Integer.parseInt(heureDepartSplit[1]);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "L'heure de départ doit être au format hh:mm", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// check if the arrival time is 2 numbers separated by :
			String[] heureArriveSplit = heureArriveField.getText().split(":");
			int minutesArrive;
			int heuresArrive;
			if (heureArriveSplit.length != 2) {
				JOptionPane.showMessageDialog(frame, "L'heure d'arrivée doit être au format hh:mm", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				heuresArrive = Integer.parseInt(heureArriveSplit[0]);
				minutesArrive = Integer.parseInt(heureArriveSplit[1]);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "L'heure d'arrivée doit être au format hh:mm", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// check if the departure time is before the arrival time
			if (heuresDepart > heuresArrive || (heuresDepart == heuresArrive && minutesDepart > minutesArrive)) {
				JOptionPane.showMessageDialog(frame, "L'heure de départ doit être avant l'heure d'arrivée", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// check if the departure airport is a valid airport (number)
			int aeroportDepartValue;
			try {
				aeroportDepartValue = Integer.parseInt(aeroportDepartField.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "L'aéroport de départ doit être un nombre", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// check if the arrival airport is a valid airport (number)
			int aeroportArriveValue;
			try {
				aeroportArriveValue = Integer.parseInt(aeroportArriveField.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "L'aéroport d'arrivée doit être un nombre", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// check if the departure airport is different from the arrival airpor  t
			if (aeroportDepartValue == aeroportArriveValue) {
				JOptionPane.showMessageDialog(frame, "L'aéroport de départ doit être différent de l'aéroport d'arrivée", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// register the flight
			SQL sql = new SQL("jdbc:mysql://localhost:3306/JBDCTP4","root","");
			sql.executeUpdate("INSERT INTO Vol VALUES (" + numVol + ", '" + heureDeDepartField.getText() + "', '" + heureArriveField.getText() + "', " + aeroportDepartValue + ", " + aeroportArriveValue + ")");


		});
		mainPanel.add(button);
		frame.pack();
		frame.setVisible(true);
	}
}
