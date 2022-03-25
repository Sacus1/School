public class Chemin {
    private double cout;
    private Sommet pointDest;

    public Chemin(Sommet Destination, double cout) {
        pointDest = Destination;
        this.cout = cout;
    }

    public Sommet GetDest() {
        return pointDest;
    }

    public double GetCout() {
        return cout;
    }
}