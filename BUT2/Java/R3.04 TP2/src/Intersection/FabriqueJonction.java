package Intersection;

public class FabriqueJonction {
  private FabriqueIntersection f1;
  private FabriqueIntersection f2;
  private boolean f1EstPrioritaire;

  public FabriqueJonction(FabriqueIntersection f1, FabriqueIntersection f2) {
    this.f1 = f1;
    this.f2 = f2;
  }

  public Vehicule creerVehicule() {
    if (f1EstPrioritaire) {
      f1EstPrioritaire = false;
      return f1.creerVehicule();
    } else {
      f1EstPrioritaire = true;
      return f2.creerVehicule();
    }
  }
}
