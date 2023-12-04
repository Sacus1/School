import outils3d.Dessinateur;

public class Cercle extends Forme {
  private double rayon;

  public Cercle(double rayon) {
    this.rayon = rayon;
  }

  /**
   * @param Dessinateur
   */
  public void instruire(Dessinateur d) {
    super.instruire(d);
    d.dessinerDemiSphere(rayon);
  }
}
