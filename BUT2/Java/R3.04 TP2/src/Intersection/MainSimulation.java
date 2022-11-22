package Intersection;

import java.util.HashMap;

public class MainSimulation {
	public static void main(String[] args) {
		FabriqueVehicule fabriqueVoiture = new FabriqueVoiture();
		FabriqueVehicule fabriqueBicyclette = new FabriqueBicyclette();
		FabriqueVehicule fabriqueBus = new FabriqueBus();
		FabriqueVehicule fabriquePieton = new FabriquePieton();
		Voiture voiture = (Voiture) fabriqueVoiture.creerVehicule();
		Bicyclette bicyclette = (Bicyclette) fabriqueBicyclette.creerVehicule();
		Bus bus = (Bus) fabriqueBus.creerVehicule();
		Pieton pieton = (Pieton) fabriquePieton.creerVehicule();

		System.out.println("Intersection.Voiture: " + voiture);
		System.out.println("Intersection.Bicyclette: " + bicyclette);
		System.out.println("Intersection.Bus: " + bus);
		System.out.println("Intersection.Pieton: " + pieton);
		FabriqueIntersection fabriqueIntersection = new FabriqueIntersection();
		Simulateur simulateur = new Simulateur(fabriqueIntersection);
		HashMap<String, Integer> stats = simulateur.genererStats();
		simulateur.ecrireStats(stats);
		simulateur.dessinerStats(stats);
	}
}
