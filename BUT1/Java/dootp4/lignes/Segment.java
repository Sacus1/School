package lignes;

import geodessin.geo.Droite;

public class Segment {
    private Point[] extremites;

    /*
    *
    */
    public Segment(Point[] extremites) {
        this.extremites = extremites;
    }

    /*
    *
    */
    Point[] extremites() {
        return extremites;
    }

    /*
    *
    */
    public double longueur() {
        return extremites[0].distance(extremites[1]);
    }

    /*
    *
    */
    Segment inverse() {
        return new Segment(new Point[] { extremites[1], extremites[0] });
    }

    /*
    *
    */
    public Point milieu() {
        return new Point((extremites[0].x() + extremites[1].x()) / 2, (extremites[0].y() + extremites[1].y()) / 2);
    }

    /*
     * vérifie si 2 segments se coupent.
     */
    public boolean coupe(Segment s) {
        Droite d1 = new Droite(extremites[0].x(), extremites[0].y(), extremites[1].x(), extremites[1].y());
        Point inter = new Point(
                d1.intersection(new Droite(s.extremites()[0].x(), s.extremites()[0].y(), s.extremites()[1].x(),
                        s.extremites()[1].y())));
                
        if (this.longueur() < extremites[0].distance(inter))
            return false;
        if (s.longueur() < extremites[0].distance(inter))
            return false;
        return true;

    }

    /*
     * vérifie si le point en paramètre appartient au segment, extrémités
     * y-comprises.
     */
    public boolean contient(Point p) {  
        Droite d1 = new Droite(extremites[0].x(), extremites[0].y(), extremites[1].x(), extremites[1].y());
        if (!d1.passePar(p.x(),p.y())) return false;
        if (this.longueur() < extremites[0].distance(p)) return false;
        return true;
    }

    /*
     * vérifie si le point en paramètre est situé strictement sur la gauche du
     * segment en le parcourant du point de départ au point d'arrivée (le point
     * n'est pas sur le segment).
     */
    public boolean aGauche(Point p) {
        return p.x() > Math.max(extremites[0].x(),extremites[1].x());
    }

    /*
    *
    */
    public Point intersection(Segment s) {
        return new Point((new Droite(extremites[0].x(), extremites[0].y(), extremites[1].x(), extremites[1].y())).intersection(new Droite(s.extremites()[0].x(), s.extremites()[0].y(), s.extremites()[1].x(),s.extremites()[1].y())));

    }

    /*
    *
    */
    public Point projete(Point p) {
        return new Point(0, 0);
    }

    /*
    *
    */
    public String toString() {
        return "" + extremites;
    }

}