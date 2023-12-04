public class Sudoku {
  private int backward = 0, forward = 0;
  private int[][] grille =
      new int[][] {
        {1, 0, 0, 0, 0, 7, 0, 9, 0},
        {0, 3, 0, 0, 2, 0, 0, 0, 8},
        {0, 0, 9, 6, 0, 0, 5, 0, 0},
        {0, 0, 5, 3, 0, 0, 9, 0, 0},
        {0, 1, 0, 0, 8, 0, 0, 0, 2},
        {6, 0, 0, 0, 0, 4, 0, 0, 0},
        {3, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 4, 0, 0, 0, 0, 0, 0, 7},
        {0, 0, 7, 0, 0, 0, 3, 0, 0}
      }; /*
         private int[][] grille = new int[][]{
                 {0,0,0,0,0,6,0,0,0},
                 {0,5,9,0,0,0,0,0,8},
                 {2,0,0,0,0,8,0,0,0},
                 {0,4,5,0,0,0,0,0,0},
                 {0,0,3,0,0,0,0,0,0},
                 {0,0,6,0,0,3,0,5,4},
                 {0,0,0,3,2,5,0,0,6},
                 {0,0,0,0,0,0,0,0,0},
                 {0,0,0,0,0,0,0,0,0}
         };*/

  /** Affiche la grille dans la console */
  private void afficherGrille() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        System.out.print(grille[i][j] + " ");
        if (j == 2 || j == 5) System.out.print(" ");
      }
      if (i == 2 || i == 5) System.out.println();
      System.out.println();
    }
  }

  /**
   * Indique si une valeur est possible dans une case
   *
   * @param v la valeur
   * @param i la ligne
   * @param j la colonne
   */
  private boolean estPossible(int v, int i, int j) {
    // check if there is a number in this case
    if (grille[i][j] != 0) return false;

    // check if the number is in the line or the column
    for (int k = 0; k < 9; k++) {
      if (grille[i][k] == v || grille[k][j] == v) return false;
    }

    // check square
    int i0 = (i / 3) * 3;
    int j0 = (j / 3) * 3;
    for (int k = 0; k < 3; k++)
      for (int l = 0; l < 3; l++) if (grille[i0 + k][j0 + l] == v) return false;

    return true;
  }

  /**
   * Donne la case suivante
   *
   * @param i la ligne actuel
   * @param j la colonne actuel
   */
  private int[] caseSuivante(int i, int j) {
    // si on est à la fin de la ligne
    if (j < 8) return new int[] {i, j + 1};

    // si on est à la fin de la colonne
    else if (i < 8) return new int[] {i + 1, 0};

    // si on est à la fin de la grille
    else return null;
  }

  /**
   * Résoud la grille
   *
   * @param i la ligne actuel
   * @param j la colonne actuel
   * @return true si la grille est résolue
   */
  private boolean solve(int i, int j) {

    int[] caseSuivante = caseSuivante(i, j);

    // if we are at the end of the grid
    if (caseSuivante == null) {
      // solve the last case
      for (int k = 0; k < 9; k++) {
        if (!estPossible(k + 1, i, j)) continue;

        grille[i][j] = k + 1;
      }

      return true;
    }

    // if the case is not empty we go to the next case
    if (grille[i][j] != 0) return solve(caseSuivante[0], caseSuivante[1]);

    // we try all the possible values
    for (int k = 0; k < 9; k++) {
      // if the value is not possible we go to the next value
      if (!estPossible(k + 1, i, j)) continue;

      grille[i][j] = k + 1;
      forward++;
      if (solve(caseSuivante[0], caseSuivante[1])) return true;

      backward++;
      grille[i][j] = 0;
    }

    return false;
  }

  public static void main(String[] args) {
    Sudoku sudoku = new Sudoku();
    System.out.println(sudoku.solve(0, 0));
    System.out.printf("Forward %d , Backward %d\n", sudoku.forward, sudoku.backward);
    sudoku.afficherGrille();
  }
}
