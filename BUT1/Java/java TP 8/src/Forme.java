import outils3d.Dessinateur;

public abstract class Forme extends Scene {
  // Couleur rouge
  private double r;
  // Couleur vert
  private double g;
  // Couleur bleu
  private double b;

  public Forme() {
    super();
  }

  /**
   * @param valeur de la couleur rouge comprise dans l'intervale [0,1]
   * @param valeur de la couleur vert comprise dans l'intervale [0,1]
   * @param valeur de la couleur bleu comprise dans l'intervale [0,1]
   */
  public void colorier(double rouge, double vert, double bleue) {
    this.r = rouge > 1 ? 1 : (rouge < 0 ? 0 : rouge);
    this.g = vert > 1 ? 1 : (vert < 0 ? 0 : vert);
    this.b = bleue > 1 ? 1 : (bleue < 0 ? 0 : bleue);
  }

  public void instruire(Dessinateur d) {
    d.changerPinceau(r, g, b);
  }
}
