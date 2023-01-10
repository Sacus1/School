import java.io.*;
import java.util.ArrayList;

public class Hotel implements java.io.Serializable {
	private static int nbc = 0;
	private ArrayList<Chambre> libres;
	private ArrayList<Chambre> reserves;

	public Hotel() {
		libres = new ArrayList<Chambre>();
		reserves = new ArrayList<Chambre>();
		nbc = 0;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Chambres libres:").append(System.lineSeparator()).append("[").append(System.lineSeparator());

		for (Chambre c : libres) {
			s.append(c).append(", ").append(System.lineSeparator());
		}

		s.append("]").append(System.lineSeparator()).append("Chambres réservées:").append(System.lineSeparator()).append("[").append(System.lineSeparator());

		for (Chambre c : reserves) {
			s.append(c).append(", ").append(System.lineSeparator());
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * Ajoute une chambre à la liste des chambres libres , lui attribue un numéro de chambre et incrémente le nombre de
	 * chambres si la chambre n'a pas déja un numéro de chambre
	 */
	public void ajouterChambre(Chambre c) {
		if (c.getNo() == 0) {
			c.setNo(++nbc);
		}
		libres.add(c);
	}

	/**
	 * @param type le type de chambre à réserver
	 * @return numero de la chambre réservée ou 0 si aucune chambre de ce type n'est disponible
	 */
	public int reserverChambre(String type) {
		int i = 0;
		// Recherche de la première chambre du type demandé
		while (i < libres.size() && !libres.get(i).typeChambre().equals(type)) {
			i++;
		}
		// Si une chambre a été trouvée
		if (i < libres.size()) {
			// On récupère la chambre
			Chambre c = libres.remove(i);
			// On l'ajoute à la liste des chambres réservées
			reserves.add(c);
			// On retourne le numéro de la chambre
			return c.getNo();
		}
		// Si aucune chambre n'a été trouvée on retourne 0
		return 0;
	}

	public boolean libererChambre(int n) {
		int i = 0;
		// Recherche de la chambre à libérer
		while (i < reserves.size() && reserves.get(i).getNo() != n) {
			i++;
		}
		// Si la chambre a été trouvée
		if (i < reserves.size()) {
			// On récupère la chambre
			Chambre c = reserves.remove(i);
			// On l'ajoute à la liste des chambres libres
			libres.add(c);
			return true;
		}
		return false;
	}

	public void sauvegarder(String nomFichier) {
		try {
			// Création du flux de sortie
			FileOutputStream fos = new FileOutputStream(nomFichier);
			// Création du flux d'objet
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// Sauvegarde de l'objet
			oos.writeObject(this);
			// Fermeture des flux
			oos.flush();
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void charger(String fichier) {
		try {
			// Création du flux d’entrée
			FileInputStream fis = new FileInputStream(fichier);
			// Création du flux d’objet
			ObjectInputStream ois = new ObjectInputStream(fis);
			// Chargement de l'objet
			Hotel h = (Hotel) ois.readObject();
			// affectation des attributs
			libres = h.libres;
			reserves = h.reserves;
			// Fermeture des flux
			ois.close();
			fis.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
