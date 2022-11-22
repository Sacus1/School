package Intersection;

public class FabriqueVoiture implements FabriqueVehicule {
    public Voiture creerVehicule() {
        return new Voiture(0);
    }
}
