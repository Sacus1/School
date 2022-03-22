import java.util.ArrayList;

public class Point {
    private ArrayList<Chemin> chemins;
    private String nom;

    public Point(String nom, Chemin[] c) {
        this.nom = nom;
        for (Chemin chemin : c) {
            chemins.add(c);
        }
    }

    public Point(String nom) {
        this(nom, new Chemin[] {});
    }

    public ArrayList<Chemin> GetChemins() {
        return chemins;
    }

    public void NewChemin(Chemin c) {
        chemins.add(c);
    }

    public String GetNom() {
        return nom;
    }
}
