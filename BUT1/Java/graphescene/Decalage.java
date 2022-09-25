import outils3d.Dessinateur;
import outils3d.Vecteur;

public class Decalage extends Assemblage {
    private Vecteur v;

    public Decalage(Scene a, Scene b, double tx, double ty, double tz) {
        super(a, b);
        v = new Vecteur(new double[] { tx, ty, tz });
    }

    /**
     * @param Dessinateur
     */
    public void assembler(Dessinateur d) {
        d.decaler(v);
    }

}
