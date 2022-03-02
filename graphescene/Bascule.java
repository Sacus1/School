import outils3d.Dessinateur;
import outils3d.Vecteur;

public class Bascule extends Assemblage {
    private Vecteur v;

    private double angle;

    public Bascule(Scene a, Scene b, double tx, double ty, double tz, double angle) {
        super(a, b);
        this.angle = angle;
        v = new Vecteur(new double[] { tx, ty, tz });
    }

    /**
     * @param dessinateur
     */
    public void assembler(Dessinateur d) {
        d.basculer(v, angle);
    }

}
