public class Graphe {
    private Sommet[] s;
    private final static double INFINITY = 10000;
    private int taille = 0;

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

    /**
     * @param s
     * @throws Exception
     */
    public void add(Sommet s) throws Exception {
        if (this.s.length < taille + 1) {
            throw new Exception("Graphe surchargé");
        } else
            this.s[taille++] = s;
    }

    /**
     * @param indice
     */
    public void remove(int indice) {
        for (int i = indice + 1; i < taille; i++) {
            s[i - 1] = s[i];
        }
        taille--;
    }

    /**
     * @param sommet
     */
    public void remove(Sommet sommet) {
        for (int i = 0; i < taille; i++) {
            if (s[i].equals(sommet)) {
                remove(i);
                return;
            }
        }
    }

    /**
     * @return int
     */
    public int length() {
        return taille;
    }

    /**
     * @param indice
     * @return Sommet
     */
    public Sommet Get(int indice) {
        return s[indice];
    }

    /**
     *
     * @param sommet
     * @return l'indice du sommet ou -1 si inexistant
     */
    public int Get(Sommet sommet) {
        for (int i = 0; i < taille; i++) {
            if (s[i].equals(sommet)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return String
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < taille; i++) {
            s += "\n" + this.s[i].toString();
        }
        return s;
    }

    /**
     * @param Sommet1
     * @param Sommet2
     * @return Graphe
     * @throws Exception
     */
    public Graphe PlusCourtChemin(int Sommet1, int Sommet2) throws Exception {
        Graphe Sbar = new Graphe(this);
        Sommet[] Prec = new Sommet[taille];
        Sommet i = s[Sommet1];
        double coutPrec = 0;
        double coutSommet[] = new double[taille];
        for (int j = 0; j < coutSommet.length; j++) {
            coutSommet[j] = INFINITY;
        }
        coutSommet[Get(i)] = 0;
        while (Sbar.length() > 0) {
            Sbar.remove(i);
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
                    break;
                double couts = chemin2.GetCout() + coutPrec;
                int sommetIndice = Get(chemin2.GetDest());
                if (coutSommet[sommetIndice] > couts) {
                    coutSommet[sommetIndice] = couts;
                    Prec[sommetIndice] = i;
                }
            }
            double mini = INFINITY;
            i = Sbar.Get(0);
            for (int j = 0; j < Sbar.length(); j++) {
                if (Sbar.Get(j) == (Sommet) null)
                    break;
                int sommetIndice = Get(Sbar.Get(j));
                if (coutSommet[sommetIndice] < mini) {
                    mini = coutSommet[sommetIndice];
                    i = Sbar.Get(j);
                }
            }
            coutPrec = coutSommet[Get(i)];
            System.out.println();
        }
        Graphe chemin = new Graphe(taille);
        int j = Sommet2;
        chemin.add(Get(j));
        while (j != Sommet1) {
            chemin.add(Prec[j]);
            j = Get(Prec[j]);
        }

        return chemin;
    }
}