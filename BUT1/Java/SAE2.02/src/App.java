public class App {
  private static Graphe FranceModerne() throws GraphException {
    Graphe regions = new Graphe(13);
    int i = 0;
    for (String Region :
        new String[] {
          /* 0 */
          "Bretagne",
          /* 1 */ "Normandie",
          /* 2 */ "Pays de La Loire",
          /* 3 */ "Nouvelle-Aquitaine",
          /* 4 */ "Occitanie",
          /* 5 */ "Centre-Val de Loire",
          /* 6 */ "Ile-de-France",
          /* 7 */ "Provence-Alpes-Côte d'Azur",
          /* 8 */ "Auvergne-Rhône-Alpes",
          /* 9 */ "Grand-Est",
          /* 10 */ "Hauts-de-France",
          /* 11 */ "Corse",
          /* 12 */ "Bourgogne-Franche-Compté"
        }) {
      regions.add(new Sommet(Region));
    }
    for (int[] Destinations :
        new int[][] {
          /* Bretagne */
          {1, 2},
          /* Normandie */ {2, 5, 6, 10},
          /* Pays de la Loire */ {3, 5},
          /* Nouvelle-Aquitaine */ {4, 5, 8},
          /* Occitanie */ {7, 8, 11},
          /* Centre-Val de Loire */ {6, 8, 12},
          /* Ile-de-France */ {9, 10, 12},
          /* Provence-Alpes-Côte d'Azur" */ {8, 11},
          /* Auvergne-Rhône-Alpes */ {12},
          /* Grand-Est */ {10, 12}
        }) {
      for (int Dest : Destinations) {
        double cout = 1; // Math.random() * 10;
        regions.Get(i).NewChemin(new Chemin(regions.Get(Dest), cout));
        regions.Get(Dest).NewChemin(new Chemin(regions.Get(i), cout));
      }
      i++;
    }
    return regions;
  }

  public static void main(String[] args) throws Exception {
    System.out.println(FranceModerne().PlusCourtChemin(0, 11));
  }
}
