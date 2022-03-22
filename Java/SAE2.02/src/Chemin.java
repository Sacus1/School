public class Chemin {
    private double cout;
    private Point pointDest;

    public Chemin(Point Destination, double cout) {
        pointDest = Destination;
        this.cout = cout;
    }

    public Point GetDest() {
        return pointDest;
    }

    public double GetCout() {
        return cout;
    }
}
