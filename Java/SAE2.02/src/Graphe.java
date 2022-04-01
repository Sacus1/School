public class Graphe {
    private Sommet[] s;
    private final static double INFINITY = 10000;
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

    public Graphe(Graphe g, int taille) throws Exception {
        s = new Sommet[taille];
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

    /**
     *
     * @param sommet
     * @return l'indice du sommet ou -1 si inexistant
     */
    public int Get(Sommet sommet) {
        for (int i = 0; i < last; i++) {
            if (s[i].equals(sommet)) {
                return i;
            }
        }
        return -1;
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
        Graphe chemin = new Graphe(last);
        Graphe[] cheminMin = new Graphe[last];
        Sommet i = s[Sommet1];
        double coutTotal = 0;
        double min[] = new double[last];
        for (int j = 0; j < min.length; j++) {
            min[j] = INFINITY;
        }
        min[Get(i)] = 0;
        while (Sbar.length() > 0) {
            Sbar.remove(i);
            chemin.add(i);
            Chemin[] gamma = i.GetChemins();
            Chemin[] gamIntSbar = new Chemin[gamma.length];
            int index = 0;
            for (Chemin c : gamma) {
                if (Sbar.Get(c.GetDest()) > -1) {
                    gamIntSbar[index] = c;
                    index++;
                }
            }
            for (Chemin chemin2 : gamIntSbar) {
                if (chemin2 == (Chemin) null)
                    continue;
                double couts = chemin2.GetCout() + coutTotal;
                int sommetIndice = Get(chemin2.GetDest());
                if (min[sommetIndice] > couts) {
                    min[sommetIndice] = couts;
                    cheminMin[sommetIndice] = new Graphe(chemin, last);
                    cheminMin[sommetIndice].add(i);
                }
            }
            double mini = INFINITY;
            i = Sbar.Get(0);
            double c = 0;
            for (Chemin chemin2 : gamIntSbar) {
                if (chemin2 == (Chemin) null)
                    continue;
                for (int j = 0; j < min.length; j++) {
                    if (Get(chemin2.GetDest()) == j) {
                        if (min[j] < mini) {
                            c = chemin2.GetCout();
                            mini = min[j];
                            i = Get(j);
                        }
                    }
                }
            }
            coutTotal += c;
        }
        return cheminMin[Sommet2];
    }
}