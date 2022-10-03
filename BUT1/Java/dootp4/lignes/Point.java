package lignes;

import geodessin.dessin.Afficheur;

public class Point {
    // On enregistre les coordoné du point dans un tableau de double. Ce qui nous permettra d'ajuster rapidement le code en cas de 3ème axes
    private double[] coords;

    /*
    Le premier constructeur a partir d'un tableau comportant les coordonées
    @params tableau de double : coordoné
    */
    public Point(double[] coords) {
        this.coords = coords;
    }

    /*
    Second à partir d'argument qui corresponde à deux axes: x et y
    @params 2 double : coordoné x et y
    */
    public Point(double x, double y) {
        this(new double[] { x, y });
    }

    /*
    Troisième constructeur a partir d'un autre point, pour par exemple faire par la suite une copie d'un point
    @params un autre Point : coordoné
    */
    public Point(Point p) {
        this(p.coordonnees());
    }

    /*
    Cette méthode nous retourne un tableau de doubles comportant les coordoné du point.
    */
    double[] coordonnees() {
        return coords;
    }

    /*
    Cette méthode nous retourne les coordonnées x du point seulement
    */
    public double x() {
        return coords[0];
    }

    /*
    Cette méthode nous retourne les coordonnées y du point seulement
    */
    public double y() {
        return coords[1];
    }

    /*
    Cette méthode permet de déplacer un point sur l'axe x et sur l'axe y. 
    @params 2 double : changer les coordoné d'un point
    */
    public void deplacer(double x, double y) {
        coords[0] = x;
        coords[1] = y;
    }

    /*
    Cette méthode nous permet de vérifier si deux point sont confondu.
    On test d'abors si leur x est identique et si leur y l'est aussi
    @params Point 
    */
    public boolean confonduAvec(Point p) {
        return p.x() == coords[0] && p.y() == coords[1];
    }

    /*
    Permet d'afficher la distance entre deux points
    @params Point 
    */
    public double distance(Point p) {
        return Math.sqrt((p.x() - coords[0]) * (p.x() - coords[0]) + (p.y() - coords[1]) * (p.y() - coords[1]));
    }

    /*
     * Le point symétrique de P autour du centre C peut être trouvé en parcourant le
     * double du chemin de P à C.
     * @params Point 
     */
    public Point symetrique(Point p) {
        return new Point(Math.sqrt((p.x() - coords[0]) * (p.x() - coords[0])) * 2 + coords[0],
                Math.sqrt((p.y() - coords[1]) * (p.y() - coords[1])) * 2 + coords[1]);
    }

    /*
    *
    */
    public String toString() {
        return "(" + coords[0] + ";" + coords[1] + ")";
    }

    /*
    *
    */
    public void tracer(Afficheur aff) {
        aff.ajouterPoint(coords[0], coords[1]);
    }

}
