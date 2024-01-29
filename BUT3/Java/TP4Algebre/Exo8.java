// Trame des TP d'IN4 (R5.15) : algebre et modelisation geometrique
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die
// Complete par :
//
// Objectif : programmer le deplacement du tonneau pour l'attacher a
//   l'extremite de la corde de la grue ET AUSSI la position de l'observateur
//   qui doit filmer la scene depuis l'interieur de la cabine en visant
//   le tonneau.
//   On fera l'inverse, du tonneau vers la cabine, dans une deuxieme temps.
//   
//
// Mise en oeuvre : completer la methode "nextStep" appelee en tache de
//   fond par l'application. Cette methode doit mettre dans le parametre
//   "pose" les positions successives du tonneau.
//   DE PLUS, il faut mettre a jour l'observateur a l'aide de la methode
//   "setView" de ExoScene qui retourne un tableau de 16 float correspondant
//   aux termes de la matrice de vue (a determiner).
//   La vue doit etre inversee quand le parametre "reversed" est true.
//   La position de l'observateur dans la cabine peut etre recuperee
//   par la methode getCabinViewpoint ();
//



/** Fourth training session on model/view control. */
public class Exo8 extends CraneScene
{
  /** View matrix terms. */
  private float[] vis = new float[16];


  /** Gets the next animation step pose.
   * @param pose Already allocated vector to fill in with new pose values.
   */
  public boolean nextStep (float[] pose)
  {
    // Deplacement du tonneau (reprendre Exo 3)
    return true;
  }

  /** Returns the viewing matrix to be immediately applied.
   * @param reverse Viewing sense: direct if false.
   */
  public float[] setView (boolean reverse)
  {
    // Fournit la matrice de vue
    // POUR L'INSTANT UNE MATRICE PAR DEFAUT
    return (vis);
  }

  /** Constructs the 3D scene used for training.
   * Prepares the intermediate storage objects.
   * @param p1 Type of the loaded scene.
   * @param rw Type of the loaded scene.
   * @param xw Extension orientation.
   */
  private Exo8 (int p1, boolean rw, boolean xw)
  {
    super (p1, true);
    addRopeway (xw);

    // Preparation du mouvement
    // UNE MATRICE DE VUE PAR DEFAUT
    for (int i = 0; i < 16; i++) vis[i] = 0.f;
    vis[8] = 1.f;
    vis[1] = 1.f;
    vis[6] = 1.f;
    vis[7] = -1.5f;
    vis[11] = -5.f;
    vis[15] = 1.f;
  }

  /** Runs a crane animation.
   * @param args Run arguments.
   */
  public static void main (String[] args)
  {
    new ExoFrame (8, new Exo8 (args.length == 1
                               && args[0].equals ("stop") ? 0 : 1,
                               true, false));
  }
}
