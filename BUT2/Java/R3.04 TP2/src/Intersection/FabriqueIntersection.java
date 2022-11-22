package Intersection;

public class FabriqueIntersection implements FabriqueVehicule {
	protected double probaVoiture;
	protected double probaBus;
	protected double probaBic;
	protected double probaPieton;

	/**
	 * @param probaVoiture Probabilité d'avoir une voiture (entre 0 et 1)
	 * @param probaBus     Probabilité d'avoir un bus (entre 0 et 1)
	 * @param probaBic     Probabilité d'avoir une bicyclette (entre 0 et 1)
	 * @param probaPieton  Probabilité d'avoir un piéton (entre 0 et 1)
	 */
	public FabriqueIntersection(double probaVoiture, double probaBus, double probaBic, double probaPieton) {
		this.probaVoiture = probaVoiture;
		this.probaBus = probaBus;
		this.probaBic = probaBic;
		this.probaPieton = probaPieton;
		// check if the sum of the probabilities is 1
		double sum = probaVoiture + probaBus + probaBic + probaPieton;
		if (sum > 1 + 0.0001 || sum < 1 - 0.0001) {
			throw new IllegalArgumentException("The sum of the probabilities must be 1 but is " + sum);
		}
	}

	public FabriqueIntersection() {
		this(.8, .05, .05, .1);
	}

	public Vehicule creerVehicule() {
		double rand = Math.random();
		FabriqueVehicule fabriqueVehicule;
		if (rand < probaVoiture)
			fabriqueVehicule = new FabriqueVoiture();
		else if (rand < probaVoiture + probaBus)
			fabriqueVehicule = new FabriqueBus();
		else if (rand < probaVoiture + probaBus + probaBic)
			fabriqueVehicule = new FabriqueBicyclette();
		else
			fabriqueVehicule = new FabriquePieton();
		return fabriqueVehicule.creerVehicule();
	}

}
