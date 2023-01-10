public class Appartement extends Chambre {
    public Appartement() {
        super(4);
    }

    @Override
    public String typeChambre() {
        return "Famille";
    }
}
