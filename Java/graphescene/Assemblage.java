import outils3d.Dessinateur;

public abstract class Assemblage extends Scene {
    Scene brancheGauche, brancheDroite;

    public Assemblage(Scene bg, Scene bd) {
        super();
        brancheDroite = bd;
        brancheGauche = bg;
    }

    public abstract void assembler(Dessinateur d);

    /**
     * @param Le dessinateur
     *           Assemble 2 scene
     */
    public void instruire(Dessinateur d) {
        brancheGauche.instruire(d);
        d.amorcerAssemblage(true);
        assembler(d);
        brancheDroite.instruire(d);
        d.amorcerAssemblage(false);
    }
}
