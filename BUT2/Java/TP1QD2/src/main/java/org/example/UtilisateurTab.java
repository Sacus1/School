package org.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UtilisateurTab {
	//Prénom, nom, email, date de l’examen, nombre d’étudiants, la moyenne et la médiane.
	private String prenom;
	private String nom;
	private String email;
	private String date;
	private int nombreEtudiants;
	private float moyenne;
	private float mediane;

	UtilisateurTab(String prenom, String nom, String email, String date, int nombreEtudiants, float moyenne,
	               float mediane) {
		// check prenom et nom (don't contain numbers)
		if (prenom == null || prenom.matches("^[^a-zA-Z'-]+([\\s]+[^a-zA-Z'-]+)*$")) {
			throw new IllegalArgumentException("Prénom invalide");
		}
		if (nom == null || nom.matches("^[^a-zA-Z'-]+([\\s]+[^a-zA-Z'-]+)*$")) {
			throw new IllegalArgumentException("Nom invalide");
		}
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		// check format de date : "jj-mm-aaaa"
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(false);
		System.out.println(date);
		try {
			sdf.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Date invalide");
		}
		this.date = date;
		this.nombreEtudiants = nombreEtudiants;
		this.moyenne = moyenne;
		this.mediane = mediane;
	}

	static UtilisateurTab loadFromFile(String filename) {
		// load from JSON
		FileReader file = null;
		try {
			file = new FileReader(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			assert file != null;
			// read file
			String prenom = String.valueOf(file.read());
			String nom = String.valueOf(file.read());
			String email = String.valueOf(file.read());
			String date = String.valueOf(file.read());
			int nombreEtudiants = file.read();
			float moyenne = file.read();
			float mediane = file.read();
			file.close();
			return new UtilisateurTab(prenom, nom, email, date, nombreEtudiants, moyenne, mediane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	String getPrenom() {
		return prenom;
	}

	String getNom() {
		return nom;
	}

	String getEmail() {
		return email;
	}

	String getDate() {
		return date;
	}

	int getNombreEtudiants() {
		return nombreEtudiants;
	}

	float getMoyenne() {
		return moyenne;
	}

	float getMediane() {
		return mediane;
	}

	void saveToFile(String filename) {
		FileWriter file = null;
		try {
			file = new FileWriter(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			assert file != null;
			file.write("Prénom: " + prenom + "\n");
			file.write("Nom: " + nom + "\n");
			file.write("Email: " + email + "\n");
			file.write("Date: " + date + "\n");
			file.write("Nombre d'étudiants: " + nombreEtudiants + "\n");
			file.write("Moyenne: " + moyenne + "\n");
			file.write("Mediane: " + mediane + "\n");
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
