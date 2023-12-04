package Intersection;

public class FabriqueBicyclette implements FabriqueVehicule {
  public Bicyclette creerVehicule() {
    return new Bicyclette(0);
  }
}
