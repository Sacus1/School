public class Membre {
  private boolean isAdmin;
  private int niveau;
  private final String nom;
  private final String prenom;

  public Membre(String nom, String prenom) {
    this.nom = nom;
    this.prenom = prenom;
    this.niveau = 0;
    this.isAdmin = false;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public int getNiveau() {
    return niveau;
  }

  public void setNiveau(int niveau) {
    this.niveau = niveau;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void augmenterNiveau() {
    // TODO
  }

  public void diminuerNiveau() {
    // TODO
  }
}
