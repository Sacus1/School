package Produit;
import Main.Logger;
import Main.Main;
import Main.SQL;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Produit {
	int id, idUnite;
	String nom,description;
	File image;
	double prix;
	static ArrayList<Produit> produits = new ArrayList<>();
	public Produit(int id, String nom, String description, double prix, int idUnite,File image) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.prix = prix;
		this.idUnite = idUnite;
		this.image = image;
		produits.add(this);
	}
	public Produit(String nom, String description, double prix, int idUnite,File image) {
		this.id = produits.size();
		this.nom = nom;
		this.description = description;
		this.prix = prix;
		this.idUnite = idUnite;
		this.image = image;
		produits.add(this);
	}
	public String toString() {
		return nom;
	}
	public static void getFromDatabase() {
		produits.clear();
		SQL sql = Main.sql;
		try {
			ResultSet res = sql.select("Produit");
			while (res.next()) {
				int id = res.getInt("idProduit");
				String nom = res.getString("nom");
				String imagePath = res.getString("imagePath");
				String description = res.getString("description");
				double prix = res.getDouble("prix");
				int idUnite = res.getInt("Unite_idUnite");
				new Produit(id, nom, description, prix, idUnite, new File(imagePath));
			}
		} catch (Exception e) {
			Logger.error(String.valueOf(e));
		}
	}
	public static void update(Produit produit) {
		if (!Main.sql.updatePreparedStatement("Produit", new String[]{"nom", "image", "description", "prix", "Unite_idUnite"},
						new Object[]{produit.nom, produit.image, produit.description, produit.prix, produit.idUnite},
						new String[]{"idProduit = "+produit.id}))
			Logger.error("Update failed");
		getFromDatabase();
	}
	public static void create(Produit produit) {
		if (!Main.sql.createPrepareStatement("Produit", new String[]{"nom", "image", "description", "prix", "Unite_idUnite"},
						new Object[]{produit.nom, produit.image, produit.description, produit.prix, produit.idUnite}))
			Logger.error("Create failed");
		getFromDatabase();
	}
	public static void delete(Produit produit) {
		if (!Main.sql.deletePrepareStatement("Produit", new String[]{"idProduit = " + produit.id}))
			Logger.error("Delete failed");
		produits.remove(produit);
	}
}
