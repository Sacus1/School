// Trame des TP d'IN4 (R5.15) : transformations geometriques
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import java.util.Locale;


/** Matrix class tester. */
class TestMatrix
{
  /** Tests the Matrix class.
   * Run arguments.
   */
  public static void main (String[] args)
  {
    Locale.setDefault (Locale.US);
    System.out.println ("Test de la translation");
    System.out.println (
      "Colonnes attendues (1,0,0,0)(0,1,0,0)(0,0,1,0)(1,2,3,1)");
    Matrix t = new Matrix (1.0f, 2.0f, 3.0f);
    System.out.println (new Matrix (1.0f, 2.0f, 3.0f));

    System.out.println ("Test d'une rotation de 30 degres sur X");
    System.out.println (
      "Colonnes attendues (1,0,0,0)(0,0.87,0.5,0)(0,-0.5,0.87,0)(0,0,0,1)");
    System.out.println (new Matrix ('x', 30.0f));

    System.out.println ("Test d'une rotation de 30 degres sur Y");
    System.out.println (
      "Colonnes attendues (0.87,0,-0.5,0)(0,1,0,0)(0.5,0,0.87,0)(0,0,0,1)");
    System.out.println (new Matrix ('Y', 30.0f));

    System.out.println ("Test d'une rotation de 30 degres sur Z");
    System.out.println (
      "Colonnes attendues (0.87,0.5,0,0)(-0.5,0.87,0,0)(0,0,1,0)(0,0,0,1)");
    System.out.println (new Matrix ('z', 30.0f));

    System.out.println ("Test d'une rotation par cos et sin sur X");
    System.out.println (
      "Colonnes attendues (1,0,0,0)(0,0.87,0.5,0)(0,-0.5,0.87,0)(0,0,0,1)");
    System.out.println (new Matrix ('X', (float) Math.cos (Math.PI / 6),
                                         (float) Math.sin (Math.PI / 6)));

    System.out.println ("Test d'une rotation par cos et sin sur Y");
    System.out.println (
      "Colonnes attendues (0.87,0,-0.5,0)(0,1,0,0)(0.5,0,0.87,0)(0,0,0,1)");
    System.out.println (new Matrix ('y', (float) Math.cos (Math.PI / 6),
                                         (float) Math.sin (Math.PI / 6)));

    System.out.println ("Test d'une rotation par cos et sin sur Z");
    System.out.println (
      "Colonnes attendues (0.87,0.5,0,0)(-0.5,0.87,0,0)(0,0,1,0)(0,0,0,1)");
    System.out.println (new Matrix ('Z', (float) Math.cos (Math.PI / 6),
                                         (float) Math.sin (Math.PI / 6)));

    System.out.println ("Test d'une homothetie");
    System.out.println (
      "Colonnes attendues (3,0,0,0)(0,3,0,0)(0,0,3,0)(0,0,0,1)");
    System.out.println (new Matrix (3.0f));

    //System.out.println ("Test d'un remplissage par colonnes");
    //System.out.println (
    //  "Colonnes attendues (1,2,3,0)(4,5,6,0)(7,8,9,0)(10,11,12,1)");
    float[] vecx = {1.0f, 2.0f, 3.0f};
    float[] vecy = {4.0f, 5.0f, 6.0f};
    float[] vecz = {7.0f, 8.0f, 9.0f};
    float[] vect = {10.0f, 11.0f, 12.0f};
    Matrix x = new Matrix (vecx, vecy, vecz, vect);
    //System.out.println (x);

    System.out.println ("Test d'une multiplication");
    System.out.println (
      "Colonnes attendues (13,11,28,0)(37,32,76,0)(61,53,124,0)(92,82,181,1)");
    float[] vecx2 = {5.0f, 3.0f, 7.0f};
    float[] vecy2 = {1.0f, 4.0f, 6.0f};
    float[] vecz2 = {2.0f, 0.0f, 3.0f};
    float[] vect2 = {7.0f, 8.0f, 9.0f};
    Matrix x2 = new Matrix (vecx2, vecy2, vecz2, vect2);
    x2.mult (x);
    System.out.println (x2);

    System.out.println ("Test d'une pre-multiplication");
    System.out.println (
      "Colonnes attendues (66,81,96,0)(59,70,81,0)(23,28,33,0)(112,137,162,1)");
    float[] vecx3 = {5.0f, 3.0f, 7.0f};
    float[] vecy3 = {1.0f, 4.0f, 6.0f};
    float[] vecz3 = {2.0f, 0.0f, 3.0f};
    float[] vect3 = {7.0f, 8.0f, 9.0f};
    Matrix x3 = new Matrix (vecx3, vecy3, vecz3, vect3);
    x3.multLeft (x);
    System.out.println (x3);

    System.out.println ("Test d'integrite de la tribu");
    System.out.println (
      "Colonnes attendues (1,0,0,0)(0,1,0,0)(0,0,1,0)(6,9,2,1)");
    Matrix y1 = new Matrix (1.0f, 2.0f, 3.0f);
    Matrix y2 = new Matrix (2.0f, 3.0f, 4.0f);
    Matrix y3 = new Matrix (3.0f, 4.0f, -5.0f);
    y1.mult (y2);
    y1.mult (y3);
    System.out.println (y1);

    System.out.println ("Test de non-referencement de la matrice tempo");
    System.out.println (
      "Colonnes attendues (1,0,0,0)(0,1,0,0)(0,0,1,0)(6,9,2,1)");
    y2.mult (y3);
    System.out.println (y1);

    System.out.println ("Test d'integrite de l'autre tribu");
    System.out.println (
      "Colonnes attendues (1,0,0,0)(0,1,0,0)(0,0,1,0)(6,9,2,1)");
    y1 = new Matrix (1.0f, 2.0f, 3.0f);
    y2 = new Matrix (2.0f, 3.0f, 4.0f);
    y3 = new Matrix (3.0f, 4.0f, -5.0f);
    y1.multLeft (y2);
    y1.multLeft (y3);
    System.out.println (y1);

    System.out.println ("2e test de non-referencement de la matrice tempo");
    System.out.println (
      "Colonnes attendues (1,0,0,0)(0,1,0,0)(0,0,1,0)(6,9,2,1)");
    y2.multLeft (y3);
    System.out.println (y1);
  }
}
