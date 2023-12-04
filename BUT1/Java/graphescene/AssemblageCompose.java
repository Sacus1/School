import outils3d.Dessinateur;

public class AssemblageCompose extends Scene {
  Assemblage brancheGauche, brancheDroite;

  public AssemblageCompose(Assemblage bg, Assemblage bd) {
    super();
    brancheDroite = bd;
    brancheGauche = bg;
  }

  /**
   * @param Le dessinateur Assemble 2 assemblage
   */
  public void instruire(Dessinateur d) {
    brancheGauche.assembler(d);
    d.amorcerAssemblage(true);
    brancheDroite.instruire(d);
    d.amorcerAssemblage(false);
  }
}
