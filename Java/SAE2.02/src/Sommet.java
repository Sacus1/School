import java.util.ArrayList;

public class Sommet {
    private ArrayList<Chemin> chemins;
    private String nom;

    public Sommet(String nom, Chemin[] c) {
        this.nom = nom;
        for (Chemin chemin : c) {
            chemins.add(chemin);
        }
    }

    public Sommet(String nom) {
        this(nom, new Chemin[] {});
    }

    public ArrayList<Chemin> GetChemins() {
        return chemins;
    }

    public void NewChemin(Chemin c) {
        chemins.add(c);
    }

    public String toString() {
        return nom;
    }
}