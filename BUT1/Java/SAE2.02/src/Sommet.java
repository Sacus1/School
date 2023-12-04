import java.util.ArrayList;
import java.util.Collections;

public class Sommet {
  private final ArrayList<Chemin> chemins =
      new ArrayList<>(); // Liste des chemins accessible depuis ce sommet
  private final String nom;

  public Sommet(String nom, Chemin[] c) {
    this.nom = nom;
    Collections.addAll(chemins, c);
  }

  public Sommet(String nom) {
    this(nom, new Chemin[] {});
  }

  /**
   * @return Tous les chemins relier à ce sommet
   */
  public Chemin[] GetChemins() {
    Chemin[] c = new Chemin[] {};
    return chemins.toArray(c);
  }

  /**
   * @param c Le chemin a ajouter
   */
  public void NewChemin(Chemin c) {
    chemins.add(c);
  }

  /**
   * @return Nom du sommet
   */
  public String toString() {
    return nom;
  }

  public boolean equals(Object object) {
    if (!(object instanceof Sommet s)) return false;
    return nom.equals(s.toString());
  }
}
