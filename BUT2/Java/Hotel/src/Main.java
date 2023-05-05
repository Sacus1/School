public class Main {
  public static void main(String[] args) {
    // instanciation de l'hotel

    Hotel h = new Hotel();
    h.ajouterChambre(new ChambreDouble());
    h.ajouterChambre(new ChambreDoubleDouche());
    h.ajouterChambre(new Appartement());
    // creation suite presidentielle
    h.ajouterChambre(
        new Appartement() {
          @Override
          public String typeChambre() {
            return "Suite présidentielle 2";
          }
        });

    // reservation

    int r = h.reserverChambre("Chambre double");
    h.reserverChambre("Famille");
    System.out.println(h);

    // annulation de la reservation

    h.libererChambre(r);
    System.out.println("\n\n\n" + h);

    // sauvegarde de l'hotel

    h.sauvegarder("aujourdhui");

    // chargement de l'hotel

    h = new Hotel();
    h.charger("aujourdhui");
    System.out.println("\n\n\n" + h);
  }
}
