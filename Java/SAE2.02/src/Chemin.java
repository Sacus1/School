public class Chemin {
    private double cout;
    private Sommet pointDest;

    public Chemin(Sommet Destination, double cout) {
        pointDest = Destination;
        this.cout = cout;
    }

    public Chemin() {
        pointDest = new Sommet("");
        cout = 0;
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

    /**
     * @param Le chemin a compare
     * @return Le chemin le plus court
     */
    public Chemin min(Chemin c) {
        if (c.GetCout() > cout)
            return this;
        return c;
    }
}
