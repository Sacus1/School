package lignes;

import geodessin.dessin.Afficheur;
import geodessin.geo.Droite;
public class Main {
    public static void main(String[] args) {
        Point pt = new Point(0,1);
        System.out.println(pt);
        System.out.println(pt.x());
        System.out.println(pt.y());
        pt.deplacer(10, 5);
        System.out.println(pt);
        System.out.println(pt.confonduAvec(new Point(10, 5)));
        System.out.println(pt.confonduAvec(new Point(10, 6)));
        System.out.println(pt.confonduAvec(new Point(9, 5)));
        System.out.println(pt.confonduAvec(new Point(9, 4)));
        System.out.println(pt.distance(new Point(10, 4)));
        System.out.println(pt.symetrique(new Point(11,6)));
        Afficheur.zoneVisible(0, 200, 0, 200);
        Afficheur aff = new Afficheur(200, 200);
        aff.activer();
        for (int i = 10; i < 60; i++) {
            new Point(i,i).tracer(aff);
        }
        aff.activer();
    }
}
