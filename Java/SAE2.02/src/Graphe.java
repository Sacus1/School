public class Graphe {
    private Sommet[] s;

    private int last = 0;

    public Graphe(int taille) {
        s = new Sommet[taille];
    }

    public Graphe(Graphe g) throws Exception {
        s = new Sommet[g.length()];
        for (int i = 0; i < g.length(); i++) {
            add(g.Get(i));
        }
    }

    public void add(Sommet s) throws Exception {
        if (this.s.length < last + 1) {
            throw new Exception("Graphe surchargé");
        } else
            this.s[last++] = s;
    }

    public void remove(int indice) {
        for (int i = indice + 1; i < last; i++) {
            s[i - 1] = s[i];
        }
        last--;
    }

    public void remove(Sommet sommet) {
        for (int i = 0; i < last; i++) {
            if (s[i].equals(sommet)) {
                remove(i);
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
        for (int i = 0; i < last; i++) {
            s += "\n" + this.s[i].toString();
        }
        return s;
    }

    public Graphe PlusCourtChemin(int Sommet1, int Sommet2) throws Exception {
        Graphe Sbar = new Graphe(this);
        Graphe S = new Graphe(last);
        Sommet objectif = s[Sommet2];
        Sommet i = s[Sommet1];
        while (Sbar.length() > 0 && !i.equals(objectif)) {
            Sbar.remove(i);
            S.add(i);
            System.out.println("\nGraphe a completer: " + Sbar);
            System.out.println("\nGraphe completer :" + S);
            System.out.println("\n" + i);
            i = Chemin.min(i.GetChemins(), S).GetDest();
        }
        S.add(objectif);
        return S;
    }
}