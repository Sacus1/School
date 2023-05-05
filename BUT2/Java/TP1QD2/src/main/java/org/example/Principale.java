/*
Created by Samy Midouni , Lucas Poirot

*/
package org.example;


import java.util.*;

public class Principale {
	public static void main(String[] args) {
		List<Float> tab = new ArrayList<>();
		CalculTab calculateur;
		UtilisateurTab utilisateur;
		String prenom;
		String nom;
		String email;
		String dateExamen;
		float moyenne;
		float mediane;
		int i;
		int n;
		int Sum = 0;
		// LA TAILLE DU TABLEAU
		do {
			System.out.println("Veuillez entrer la taille du tableau");
			Scanner sc = new Scanner(System.in);
			n = sc.nextInt();
		} while (n > 50);
		// REMPLISSAGE DE TABLEAU
		System.out.println("****DEBUT PROGRAMME****");
		for (i = 0; i < n; i++) {
			System.out.println("Veuillez entrer un nombre");
			Scanner sc1 = new Scanner(System.in);
			tab.add(sc1.nextFloat());
		}
		// TRI DE TABLEAU
		calculateur = new CalculTab(tab);
		calculateur.trierTableau();

		// AFFICHAGE DE TABLEAU
		System.out.println("Les élements de tableau sont : ");
		for (i = 0; i < n; i++) {
			System.out.println(tab.get(i));
		}
        // AFFICHAGE DE SOMME
		for (i = 0; i < n; i++) {
			Sum += tab.get(i);
		}
		try{
			Scanner sc2 = new Scanner(System.in);
			System.out.println("Veuillez entrer votre prenom");
			prenom = sc2.nextLine();
			System.out.println("Veuillez entrer votre nom");
			nom = sc2.nextLine();
			System.out.print("Veuillez saisir votre mail: ");
			email = sc2.next("[\\w.-]+@[\\w.-]+\\.[a-z]{2,}");
			System.out.println(email + " : Email valide !");
			System.out.println("Veuillez entrer la date de l'examen");
			dateExamen = sc2.nextLine();


			moyenne = calculateur.calculMoyenne();
			mediane = calculateur.calculMediane();
			utilisateur = new UtilisateurTab(prenom, nom, email, dateExamen, n, moyenne, mediane);

			utilisateur.saveToFile("danke.txt");
			System.out.println("La somme des éléments est égale à " + Sum);
			System.out.println("****FIN PROGRAMME****");
		}
		catch(EmptyException e){
			System.out.println(e);
		}
		catch (InputMismatchException e) {
			System.out.println("Email non valide");
		}


	}
}
