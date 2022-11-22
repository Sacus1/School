package Intersection;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Simulateur {
	FabriqueIntersection fabriqueIntersection;
	public Simulateur(FabriqueIntersection fabriqueIntersection) {
		this.fabriqueIntersection = fabriqueIntersection;
	}
	public HashMap<String, Integer> genererStats() {
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		stats.put("Intersection.Voiture", 0);
		stats.put("Intersection.Bus", 0);
		stats.put("Intersection.Bicyclette", 0);
		stats.put("Intersection.Pieton", 0);
		for (int i = 0; i < 100; i++) {
			Vehicule vehicule = fabriqueIntersection.creerVehicule();
			stats.put(vehicule.getType(), stats.get(vehicule.getType()) + 1);
		}
		return stats;
	}
	public void ecrireStats(HashMap<String,Integer> stats){
		System.out.println("Intersection.Voiture: " + stats.get("Intersection.Voiture"));
		System.out.println("Intersection.Bus: " + stats.get("Intersection.Bus"));
		System.out.println("Intersection.Bicyclette: " + stats.get("Intersection.Bicyclette"));
		System.out.println("Intersection.Pieton: " + stats.get("Intersection.Pieton"));
	}
	public void dessinerStats(HashMap<String,Integer> stats){
		JFrame frame = new JFrame("Stats");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int amountOfTypes = stats.size();
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.setLayout(new GridLayout(amountOfTypes, 1));
		for (String type : stats.keySet()) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 2));
			JLabel label = new JLabel(type);
			label.setHorizontalAlignment(JLabel.CENTER);
			panel.add(label);
			JProgressBar progressBar = new JProgressBar(0, 100);
			progressBar.setValue(stats.get(type));
			progressBar.setStringPainted(true);
			panel.add(progressBar);
			frame.add(panel);
		}
	}
}
