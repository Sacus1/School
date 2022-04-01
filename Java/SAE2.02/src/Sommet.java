import java.util.ArrayList;

public class Sommet {
    private ArrayList<Chemin> chemins = new ArrayList<Chemin>();
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

    /**
     * @return Tout les chemin relier a ce sommet
     */
    public Chemin[] GetChemins() {
        Chemin[] c = new Chemin[] {};
        return chemins.toArray(c);
    }

    /**
     * @param Le chemin a ajouter
     */
    public void NewChemin(Chemin c) {
        chemins.add(c);
    }

    /**
     * @return Nom du sommet
     */
    public String toString() {
        return new String(nom);
    }

    public boolean equals(Object object) {
        if (!(object instanceof Sommet))
            return false;
        Sommet s = (Sommet) object;
        return nom.equals(s.toString());
    }
}