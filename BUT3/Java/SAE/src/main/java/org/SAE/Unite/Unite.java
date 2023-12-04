package Unite;

import Main.Logger;
import Main.Main;
import Main.SQL;

import java.sql.ResultSet;
import java.util.ArrayList;
public class Unite {
	public int id;
	String nom;
	public static ArrayList<Unite> unites = new ArrayList<>();
	public Unite(int id, String nom) {
		this.id = id;
		this.nom = nom;
		unites.add(this);
	}
	public Unite(String nom) {
		this.id = unites.size();
		this.nom = nom;
		unites.add(this);
	}
	public String toString() {
		return nom;
	}
	public static void getFromDatabase() {
		unites.clear();
		SQL sql = Main.sql;
		try {
			ResultSet res = sql.select("Unite");
			while (res.next()) {
				int id = res.getInt("idUnite");
				String nom = res.getString("nom");
				new Unite(id, nom);
			}
		} catch (Exception e) {
			Logger.error(String.valueOf(e));
		}
	}
	public static void update(Unite unite) {
		if (!Main.sql.updatePreparedStatement("Unite", new String[]{"idUnite", "nom"},
						new Object[]{unite.id, unite.nom},
						new String[]{"idUnite = " +unite.id})) Logger.error("Failed to update Unite");
		getFromDatabase();
	}
	public static void create(Unite unite) {
		if (!Main.sql.createPrepareStatement("Unite", new String[]{"idUnite", "nom"},
						new Object[]{unite.id, unite.nom}))
			Logger.error("Failed to insert Unite");
		getFromDatabase();
	}
	public static void delete(Unite unite) {
		if (!Main.sql.deletePrepareStatement("Unite", new String[]{"idUnite = " + unite.id}))
			Logger.error("Failed to delete Unite");
		unites.remove(unite);
	}


}
