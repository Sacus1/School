package Intersection;

public abstract class Vehicule {
  protected double vitesse;
  protected double vitesseMax;
  /*
  Désigne le type du véhicule des classes concrètes
   */
  protected String type;

  public Vehicule(double vitesse, String type) {
    this.vitesse = vitesse;
    this.type = type;
  }

  public double getVitesse() {
    return vitesse;
  }

  public String getType() {
    return type;
  }

  public void accelerer(double vitesse) {
    this.vitesse += vitesse;
    if (this.vitesse > vitesseMax) {
      this.vitesse = vitesseMax;
    }
  }

  public void ralentir(double vitesse) {
    this.vitesse -= vitesse;
    if (this.vitesse < 0) {
      this.vitesse = 0;
    }
  }

  @Override
  public String toString() {
    return "Vitesse: " + vitesse + " Type: " + type;
  }
}
