import java.util.Date;

public class Tournois {
	private Membre[] membres;
	private Date date;
	private String adresse;
	private Resultat[] resultats;

	public Tournois(Date date, String adresse) {
		this.date = date;
		this.adresse = adresse;
	}

	public void ajouterMembre(Membre membre) {
		// TODO
	}

	public void ajouterResultat(Resultat resultat) {
		// TODO
	}

	public void afficherResultats() {
		// TODO
	}

	public void afficherGagnant() {
		// TODO a calculer a partir des resultats
	}

	public Membre[] getClassement() {
		// TODO a calculer a partir des resultats
		return null;
	}
}
