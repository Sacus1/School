package Adresse;

import Main.Main;
import Main.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class represents an address with properties like id, address, city, and postal code.
 * It provides methods to create, update, delete, and retrieve addresses from the database.
 */
public class Adresse {
 public int id;
 String adresse,ville,codePostal;

 // A static list to hold all the addresses
 public static ArrayList<Adresse> adresses = new ArrayList<>();

 /**
  * Constructor to create an address object with id, address, city, and postal code.
  * The address object is then added to the static list of addresses.
  */
 public Adresse(int id, String adresse, String ville, String codePostal) {
  this.id = id;
  this.adresse = adresse;
  this.ville = ville;
  this.codePostal = codePostal;
  adresses.add(this);
 }

 /**
  * Constructor to create an address object with address, city, and postal code.
  * The id is automatically set to the current size of the static list of addresses.
  * The address object is then added to the static list of addresses.
  */
 public Adresse(String adresse, String ville, String codePostal) {
  this.id = adresses.size();
  this.adresse = adresse;
  this.ville = ville;
  this.codePostal = codePostal;
  adresses.add(this);
 }

 /**
  * Method to update an address in the database.
  * If the update fails, it prints “Update failed” to the console.
  */
 public static void update(Adresse adresse) {
  if (!(Main.sql.updatePreparedStatement("Adresse", new String[]{"adresse", "ville", "codePostal"},
      new Object[]{adresse.adresse, adresse.ville, adresse.codePostal},
      new String[]{"idAdresse = " + adresse.id}))) System.out.println("Update failed");
  getFromDatabase();
 }

 /**
  * Method to delete an address from the database.
  * If the deletion fails, it prints “Delete failed” to the console.
  */
 static void delete(Adresse adresse) {
  if (!Main.sql.deletePrepareStatement("Adresse", new String[]{"idAdresse = " + adresse.id}))
   System.out.println("Delete failed");
  adresses.remove(adresse);
 }

 /**
  * Method to return a string representation of the address object.
  */
 public String toString() {
  return adresse + ", " + ville;
 }

 /**
  * Method to retrieve all addresses from the database and add them to the static list of addresses.
  */
 public static void getFromDatabase() {
  SQL sql = Main.sql;
  adresses.clear();
  try {
   ResultSet res = sql.select("Adresse");
   while (res.next()) {
    int id = res.getInt("idAdresse");
    String adresse = res.getString("adresse");
    String ville = res.getString("ville");
    String codePostal = res.getString("codePostal");
    new Adresse(id, adresse, ville, codePostal);
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }

 }

 /**
  * Method to create an address in the database.
  * If the creation fails, it prints “Create failed” to the console.
  */
 public static void create(Adresse adresse) {
  if (!Main.sql.createPrepareStatement("Adresse", new String[]{"idAdresse", "adresse", "ville", "codePostal"},
      new Object[]{adresse.id, adresse.adresse, adresse.ville, adresse.codePostal}))
   System.out.println("Create failed");
  getFromDatabase();
 }
}
