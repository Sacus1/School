public class Chemin {
    private final double cout;
    private final Sommet pointDest;

    public Chemin(Sommet Destination, double cout) {
        pointDest = Destination;
        this.cout = cout;
    }

    public String toString() {
        return pointDest.toString() + " : " + cout;
    }

    /**
     * @return Le sommet de destination
     */
    public Sommet GetDest() {
        return pointDest;
    }

    /**
     * @return le cout du chemin
     */
    public double GetCout() {
        return cout;
    }

}
