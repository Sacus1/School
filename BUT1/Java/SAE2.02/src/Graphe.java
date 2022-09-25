public class Graphe {
    private Sommet[] s; // Une liste des sommet du graphe
    private final static double INFINITY = 100000; // Une valeur normalement impossible a atteindre est considerer comme infini
    private int taille = 0; // indice du derniere element de la liste de sommet (donne aussi l'ordre du graphe / taille du tableau)


    public Graphe(int taille) {
        s = new Sommet[taille];
    }


    public Graphe(Graphe g) throws Exception { // Constructeur par recopie
        this(g.length());
        for (int i = 0; i < g.length(); i++) {// On parcours tout les sommet du graphe a recopier
            add(g.Get(i));// On l'ajoute au nouveau graphe
        }
    }

    public Graphe(Graphe g, int taille) throws Exception { // Constructeur par recopie mais avec une taille personnalisé
        this(taille);
        for (int i = 0; i < g.length(); i++) { // On parcours tout les sommet du graphe a recopier
            add(g.Get(i)); // On l'ajoute au nouveau graphe
        }
    }

    /**
     * @param le sommet a ajoute
     * @throws Exception(Graphe surchargé)
     *                          ajoute 1 a taille et rajoute le sommet, si le graphe
     *                          est plein throw une exception
     */
    public void add(Sommet s) throws Exception {
        if (this.s.length < taille + 1) {
            throw new Exception("Graphe surchargé");
        } else
            this.s[taille++] = s;
    }

    /**
     * @param indice du sommet a supprimer
     */
    public void remove(int indice) {
        for (int i = indice + 1; i < taille; i++) { //On decalle tout les element du tableau et ecrase l'element a supprimer
            s[i - 1] = s[i];
        }
        taille--;
    }

    /**
     * @param sommet a supprimer
     */
    public void remove(Sommet sommet) {
        for (int i = 0; i < taille; i++) { //On parcours le tableau de sommet pour chercher le sommet
            if (s[i].equals(sommet)) {
                remove(i);
                return;
            }
        }
    }

    /**
     * @return ordre du graphe
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

    public String toString() {
        String s = "";
        for (int i = 0; i < taille; i++) {
            s += "\n" + this.s[i].toString();
        }
        return s;
    }

    /**
     * @param Indice sommet d arrive
     * @param Indice sommet de depart
     * @return Graphe du plus court chemin
     * @throws Exception
     */
    public Graphe PlusCourtChemin(int Sommet1, int Sommet2) throws Exception {
        Graphe Sbar = new Graphe(this); // Sommet a explorer
        Sommet[] Prec = new Sommet[taille]; // Sommet precedant chaque point
        Sommet i = s[Sommet1]; // Sommet actuel
        double coutPrec = 0;
        double coutSommet[] = new double[taille]; // cout d'arrive a chaque sommet
        for (int j = 0; j < coutSommet.length; j++) { // on initialise le tableau a infini pour chaque sommet
            coutSommet[j] = INFINITY;
        }
        coutSommet[Get(i)] = 0; // On met le sommet de depart a 0
        while (Sbar.length() > 0) {
            Sbar.remove(i); // On enleve le sommet actuel des sommet a exploer
            Chemin[] gamma = i.GetChemins(); // On recupere les successeur du sommet
            for (Chemin c : gamma) { // On parcours les successeur
                if (Sbar.Get(c.GetDest()) > -1) { // Verifie si il est dans les sommet a explorer
                    double cout = c.GetCout() + coutPrec;
                    int sommetIndice = Get(c.GetDest());
                    if (coutSommet[sommetIndice] > cout) { // Si le nouveau chemin est plus court
                        coutSommet[sommetIndice] = cout;
                        Prec[sommetIndice] = i;
                    }
                }
            }
            double mini = INFINITY;
            for (int j = 0; j < Sbar.length() && Sbar.Get(j) != (Sommet) null; j++) { // On cherche le plus court chemin dans Sbar
                                                                                      
                int sommetIndice = Get(Sbar.Get(j));
                if (coutSommet[sommetIndice] < mini) {
                    mini = coutSommet[sommetIndice];
                    i = Sbar.Get(j);
                }
            }
            coutPrec = coutSommet[Get(i)];
        }
        Graphe chemin = new Graphe(taille);
        int j = Sommet2;
        chemin.add(Get(j)); // On ajoute le sommet de depart au graphe
        while (j != Sommet1 && Prec[j] != (Sommet) null) { // et on ajoute tout les sommet du chemin juste qu'a
                                                           // l'arriver ou qu'il n'y ai plus de predecesseur
            chemin.add(Prec[j]);
            j = Get(Prec[j]);
        }

        return chemin;
    }
}
