public class Chemin {
    private double cout;
    private Sommet pointDest;
    private boolean Accessible = true;
    public Chemin(Sommet Destination, double cout) {
        pointDest = Destination;
        this.cout = cout;
    }
    public boolean EstAccessible(){return Accessible;}
    public void RendreAccessible(boolean b){Accessible = b;}
    public Sommet GetDest() {
        return pointDest;
    }

    public double GetCout() {
        return cout;
    }
}