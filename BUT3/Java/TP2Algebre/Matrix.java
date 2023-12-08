// Trame des TP d'IN4 (R5.15) : transformations geometriques
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die
//

/** Matrice homogene 4x4 avec convention vecteur colonne */
public class Matrix {
  /**
   * Representation interne : une matrice 4x4 correspondant aux axes principaux et au vecteur
   * translation
   */
  private float[][] termes = new float[4][4];

  /** Tableau de termes utilises en tampon dans les calculs */
  private static float[][] tmp = new float[4][4];

  /** Construit une matrice identite. */
  public Matrix() {
    setIdentity();
  }

  /**
   * Construit une copie d'une matrice.
   *
   * @param mat La matrice d'origine.
   */
  public Matrix(Matrix mat) {
    set(mat);
  }

  /**
   * Construit une matrice de rotation;
   *
   * @param axis Valeurs possibles : 'X', 'x', 'Y', 'y', 'Z' ou 'z'.
   * @param angleDegre Mesure de l'angle en degres.
   */
  public Matrix(char axis, float angleDegre) {
    set(axis, angleDegre);
  }

  /**
   * Construit une matrice de rotation;
   *
   * @param axis Valeurs possibles : 'X', 'x', 'Y', 'y', 'Z' ou 'z'.
   * @param cosAngle Cosinus de l'angle.
   * @param sinAngle Sinus de l'angle.
   */
  public Matrix(char axis, float cosAngle, float sinAngle) {
    set(axis, cosAngle, sinAngle);
  }

  /**
   * Construit une matrice de translation.
   *
   * @param tx Composante X du vecteur de translation.
   * @param ty Composante Y du vecteur de translation.
   * @param tz Composante Z du vecteur de translation.
   */
  public Matrix(float tx, float ty, float tz) {
    set(tx, ty, tz);
  }

  /**
   * Construit une matrice d'homothetie uniforme selon les 3 axes.
   *
   * @param scale Rapport de l'homothetie.
   */
  public Matrix(float scale) {
    set(scale);
  }

  /**
   * Construit une matrice a partir des composantes des 3 axes X, Y et Z et des composantes d'un
   * vecteur de translation.
   *
   * @param vecX Premiere colonne de la matrice.
   * @param vecY Deuxieme colonne de la matrice.
   * @param vecZ Troisieme colonne de la matrice.
   * @param trsl Quatrieme colonne de la matrice.
   */
  public Matrix(float[] vecX, float[] vecY, float[] vecZ, float[] trsl) {
    set(vecX, vecY, vecZ, trsl);
  }

  /** Transforme la matrice en matrice identite. */
  public void setIdentity() {
    for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) termes[i][j] = ((i == j) ? 1.0f : 0.0f);
  }

  /**
   * Transforme la matrice pour la rendre identique a un modele.
   *
   * @param mat Matrice modele.
   */
  public void set(Matrix mat) {
    for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) termes[i][j] = mat.termes[i][j];
  }

  /**
   * Transforme la matrice en matrice de translation.
   *
   * @param tx Composante X du vecteur de translation.
   * @param ty Composante Y du vecteur de translation.
   * @param tz Composante Z du vecteur de translation.
   */
  public void set(float tx, float ty, float tz) {
    setIdentity();
    termes[0][3] = tx;
    termes[1][3] = ty;
    termes[2][3] = tz;
  }

  /**
   * Transforme la matrice en matrice de rotation.
   *
   * @param axis Valeurs possibles : 'X', 'x', 'Y', 'y', 'Z' ou 'z'.
   * @param angleDegre Mesure de l'angle en degres.
   */
  public void set(char axis, float angleDegre) {
    setIdentity();
    float cos = (float) Math.cos(angleDegre * Math.PI / 180.0);
    float sin = (float) Math.sin(angleDegre * Math.PI / 180.0);
    switch (axis) {
      case 'X' | 'x':
        termes[1][1] = cos;
        termes[1][2] = -sin;
        termes[2][1] = sin;
        termes[2][2] = cos;

        break;
      case 'Y' | 'y':
        termes[0][0] = cos;
        termes[0][2] = sin;
        termes[2][0] = -sin;
        termes[2][2] = cos;
        break;
      case 'Z' | 'z':
        termes[0][0] = cos;
        termes[0][1] = -sin;
        termes[1][0] = sin;
        termes[1][1] = cos;
        break;
      default:
        break;
    }
  }

  /**
   * Transforme la matrice en une matrice de rotation.
   *
   * @param axis Valeurs possibles : 'X', 'x', 'Y', 'y', 'Z' ou 'z'.
   * @param cosAngle Cosinus de l'angle.
   * @param sinAngle Sinus de l'angle.
   */
  public void set(char axis, float cosAngle, float sinAngle) {
    setIdentity();
    switch (axis) {
      case 'X' | 'x':
        termes[1][1] = cosAngle;
        termes[1][2] = -sinAngle;
        termes[2][1] = sinAngle;
        termes[2][2] = cosAngle;
        break;
      case 'Y' | 'y':
        termes[0][0] = cosAngle;
        termes[0][2] = sinAngle;
        termes[2][0] = -sinAngle;
        termes[2][2] = cosAngle;
        break;
      case 'Z' | 'z':
        termes[0][0] = cosAngle;
        termes[0][1] = -sinAngle;
        termes[1][0] = sinAngle;
        termes[1][1] = cosAngle;
        break;
      default:
        break;
    }
  }

  /**
   * Transforme la matrice en matrice d'homothetie uniforme selon les 3 axes.
   *
   * @param scale Rapport de l'homothetie.
   */
  public void set(float scale) {
    setIdentity();
    termes[0][0] = scale;
    termes[1][1] = scale;
    termes[2][2] = scale;
  }

  /**
   * Change les composantes des 3 axes X, Y et Z et le vecteur de translation.
   *
   * @param vecX Premiere colonne de la matrice.
   * @param vecY Deuxieme colonne de la matrice.
   * @param vecZ Troisieme colonne de la matrice.
   * @param trsl Quatrieme colonne de la matrice.
   */
  public void set(float[] vecX, float[] vecY, float[] vecZ, float[] trsl) {
    termes[0][0] = vecX[0];
    termes[1][0] = vecX[1];
    termes[2][0] = vecX[2];
    termes[3][0] = 0.0f;
    termes[0][1] = vecY[0];
    termes[1][1] = vecY[1];
    termes[2][1] = vecY[2];
    termes[3][1] = 0.0f;
    termes[0][2] = vecZ[0];
    termes[1][2] = vecZ[1];
    termes[2][2] = vecZ[2];
    termes[3][2] = 0.0f;
    termes[0][3] = trsl[0];
    termes[1][3] = trsl[1];
    termes[2][3] = trsl[2];
    termes[3][3] = 1.0f;
  }

  /**
   * Remplace la matrice par son produit par une autre matrice.
   *
   * @param mat L'autre matrice.
   */
  public void mult(Matrix mat) {
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++) {
        tmp[i][j] = 0.0f;
        for (int k = 0; k < 4; k++) tmp[i][j] += termes[i][k] * mat.termes[k][j];
      }
    // copie du resultat dans la matrice
    for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) termes[i][j] = tmp[i][j];
  }

  /**
   * Remplace la matrice par le produit d'une autre matrice par la matrice.
   *
   * @param mat L'autre donnee.
   */
  public void multLeft(Matrix mat) {
    for (int j = 0; j < 4; ++j)
      for (int i = 0; i < 4; ++i) {
        tmp[j][i] = 0.0f;
        for (int k = 0; k < 4; ++k) tmp[j][i] += mat.termes[j][k] * termes[k][i];
      }
    // copie du resultat dans la matrice
    for (int j = 0; j < 4; ++j) for (int i = 0; i < 4; ++i) termes[j][i] = tmp[j][i];
  }

  /**
   * Copie les termes de la matrice ligne par ligne dans un tableau.
   *
   * @param array Tableau devant recueillir les 16 termes de la matrice.
   */
  public void toArray(float[] array) {
    for (int j = 0; j < 4; ++j) for (int i = 0; i < 4; ++i) array[j * 4 + i] = termes[j][i];
  }

  /** Convertit la matrice en chaine de caracteres. */
  public String toString() {
    String s = new String();
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) s += "\t" + termes[i][j];
      s += '\n';
    }
    return s;
  }
}
