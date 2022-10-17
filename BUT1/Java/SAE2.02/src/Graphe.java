import java.util.Arrays;

public class Graphe {
  private final Sommet[] s; // Une liste du sommet du graphe
  private static final double INFINITY =
      100000; // Une valeur normalement impossible a atteindre est considered comme infini
  private int taille =
      0; // indice du dernier element de la liste de sommet (donne aussi l'ordre du graphe / taille
         // du tableau)

  public Graphe(int taille) {
    s = new Sommet[taille];
  }

  public Graphe(Graphe g) throws GraphException { // Constructeur par recopie
    this(g.taille);
    for (int i = 0; i < g.length(); i++) { // On parcourt tout le sommet du graphe à recopier
      add(g.Get(i)); // On l'ajoute au nouveau graphe
    }
  }

  /**
   * @param s le sommet a ajouté
   * @throws GraphException Graphe surchargé ajoute 1 à taille et rajoute le sommet à la liste
   */
  public void add(Sommet s) throws GraphException {
    if (this.s.length < taille + 1) {
      throw new GraphException("Graphe surchargé");
    } else this.s[taille++] = s;
  }

  /**
   * @param indice du sommet à supprimer
   */
  public void remove(int indice) {
    for (int i = indice + 1;
        i < taille;
        i++) { // On décale tout les element du tableau et écrase l'élément à supprimer
      s[i - 1] = s[i];
    }
    taille--;
  }

  /**
   * @param sommet a supprimer
   */
  public void remove(Sommet sommet) {
    for (int i = 0; i < taille; i++) { // On parcourt le tableau de sommet pour chercher le sommet
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
   * @param indice indice du sommet a retourner
   * @return Sommet a l'indice
   */
  public Sommet Get(int indice) {
    return s[indice];
  }

  /**
   * @param sommet sommet a chercher
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
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < taille; i++) {
      s.append("\n").append(this.s[i].toString());
    }
    return s.toString();
  }

  /**
   * @param Sommet1 Indice sommet d'arriver
   * @param Sommet2 Indice sommet de depart
   * @return Graphe du plus court chemin
   * @throws GraphException Erreur impromptus
   */
  public Graphe PlusCourtChemin(int Sommet1, int Sommet2) throws GraphException {
    Graphe sBar = new Graphe(this); // Sommet a explorer
    Sommet[] Precedents = new Sommet[taille]; // Sommet precedent chaque point
    Sommet i = s[Sommet1]; // Sommet actuel
    double coutPre = 0;
    double[] coutSommet = new double[taille]; // cout d'arriver à chaque sommet
    // on initialise le tableau à infini pour chaque sommet
    Arrays.fill(coutSommet, INFINITY);
    coutSommet[Get(i)] = 0; // On met le sommet de depart à zero
    while (sBar.length() > 0) {
      sBar.remove(i); // On enlève le sommet actuel des sommets à explorer
      Chemin[] gamma = i.GetChemins(); // On récupère les successeurs du sommet
      for (Chemin c : gamma) { // On parcourt les successeurs
        if (sBar.Get(c.GetDest()) > -1) { // Vérifie s'il est dans les sommets à explorer
          double cout = c.GetCout() + coutPre;
          int sommetIndice = Get(c.GetDest());
          if (coutSommet[sommetIndice] > cout) { // Si le nouveau chemin est plus court
            coutSommet[sommetIndice] = cout;
            Precedents[sommetIndice] = i;
          }
        }
      }
      double mini = INFINITY;
      for (int j = 0;
          j < sBar.length() && sBar.Get(j) != null;
          j++) { // On cherche le plus court chemin dans sBar

        int sommetIndice = Get(sBar.Get(j));
        if (coutSommet[sommetIndice] < mini) {
          mini = coutSommet[sommetIndice];
          i = sBar.Get(j);
        }
      }
      coutPre = coutSommet[Get(i)];
    }
    Graphe chemin = new Graphe(taille);
    int j = Sommet2;
    chemin.add(Get(j)); // On ajoute le sommet de depart au graphe
    while (j != Sommet1
        && Precedents[j]
            != null) { // et on ajoute tous les sommets du chemin juste qu'à l'arrivée ou qu'il n'y
                       // ait plus de predecessor
      chemin.add(Precedents[j]);
      j = Get(Precedents[j]);
    }

    return chemin;
  }
}
