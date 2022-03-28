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

    /**
     * @param Une liste de chemin
     * @return Chemin le plus court
     */
    public static Chemin min(Chemin[] c) {
        Chemin CheminPlusCourt = c[0];
        for (Chemin chemin : c) {
            CheminPlusCourt = CheminPlusCourt.min(chemin);
        }
        return CheminPlusCourt;
    }
}
