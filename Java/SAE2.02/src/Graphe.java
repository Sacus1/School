public class Graphe {
    private Sommet[] s;

    private int last = 0;

    public Graphe(int taille) {
        s = new Sommet[taille];
    }

    public Graphe(Graphe g) {
        s = new Sommet[g.length()];
        for (int i = 0; i < g.length(); i++) {
            add(g.Get(i));
        }
    }

    public void add(Sommet s) {
        if (this.s.length < last + 1)
            System.err.println("Graphe surchargé");
        this.s[last++] = s;
    }

    public void remove(int indice) {
        for (int i = s.length; i > indice; i--) {
            s[i - 1] = s[i];
        }
    }

    public void remove(Sommet i) {
        for (int j = 0; j < s.length; j++) {
            if (s[j].equals(i)) {
                remove(j);
                return;
            }
        }
    }

    public int length() {
        return last;
    }

    public Sommet Get(int indice) {
        return s[indice];
    }

    public String toString() {
        String s = "";
        for (Sommet sommet : this.s) {
            s += "\n" + sommet.toString();
        }
        return s;
    }

    public Graphe PlusCourtChemin(int Sommet1, int Sommet2) {
        Graphe Sbar = new Graphe(this);
        Graphe S = new Graphe(last);
        Sommet objectif = s[Sommet2];
        Sommet i = s[Sommet1];
        Graphe chemin = new Graphe(last);
        while (last > 0 && !i.equals(objectif)) {
            Sbar.remove(i);
            S.add(i);
            i = Chemin.min(i.GetChemins(), S).GetDest();
        }
        return chemin;
    }
}
