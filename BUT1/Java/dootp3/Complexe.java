public class Complexe {
  private double i, reel; // i pour les nombre imaginaire
  private static double EPSILON = 0.0000001; // Une constante très petite

  /*
   * @params le premier pour la partie réel et le second la partie imaginaire
   */

  public Complexe(double x, double i) {
    this.i = i;
    this.reel = x;
  }

  /*
   * @params la distance a l'origine et angle entre l'abscisse et la droite a
   * l'origine
   */
  public Complexe(double m, Angle q) {
    this(m * Math.cos(q.radians()), m * Math.sin(q.radians()));
  }

  /*
   * @params le complexe a copier
   */
  public Complexe(Complexe cp) {
    this(cp.partieReelle(), cp.partieImaginaire());
  }

  public String toString() {
    return "(" + reel + "," + i + "i)";
  }

  /*
   * @return la partie reel du complexe
   */
  public double partieReelle() {
    return reel;
  }

  /*
   * @return la partie imaginaire du complexe
   */
  public double partieImaginaire() {
    return i;
  }

  /* *@return la distance a l'origine du complexe */
  public double module() {
    return Math.sqrt(reel * reel + i * i);
  }

  /*
   * @return l'angle entre l'abscisse et la droite a l'origine
   */
  public Angle argument() {
    return new Angle(Math.acos(reel / module()) * (i >= 0 ? 1 : -1));
  }

  /*
   * @return si la partie imaginaire est proche de 0 a epsilon pres
   */
  public boolean estReelPur() {
    return (i > 0 ? i : -i) <= EPSILON;
  }

  /*
   * @return un complexe avec la meme partie reel mais la partie imaginaire opposé
   */
  public Complexe conjugue() {
    return new Complexe(reel, -i);
  }

  /*
   * @params le complexe a ajouter
   *
   * @return un complexe de la somme des 2 complexe
   */

  public Complexe somme(Complexe c) {
    return new Complexe(reel + c.partieReelle(), i + c.partieImaginaire());
  }

  /*
   * @params le complexe a multiplier
   *
   * @return un complexe de le produit des 2 complexe
   */
  public Complexe produit(Complexe c) {
    return new Complexe(reel * c.partieReelle(), i * c.partieImaginaire());
  }

  /*
   * @params le complexe a ajouter
   * Ajoute un complexe
   */
  public void ajouter(Complexe c) {
    reel += c.partieReelle();
    i += c.partieImaginaire();
  }

  /*
   * @params le complexe a multiplier
   * mulplie un complexe
   */
  public void multiplier(Complexe c) {
    reel *= c.partieReelle();
    i *= c.partieImaginaire();
  }
}
