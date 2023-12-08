// Trame des TP d'IN4 (R5.15) : transformations geometriques
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die
// Complete par :
//
// Objectif : Programer un deplacement circulaire permanent de la cabine
//   pour effectuer un mouvement continu de rotation sous le cable.
//   Le mouvement est declenche par la barre d'espacement.
//
// Mise en oeuvre initiale :
//   Au premier appel de "nextstep", precalculer un increment de rotation.
//   Aux appels suivants de "nextStep", composer la position courante de
//   la cabine par cet increment de rotation.
//
// Geometrie de la scene :
//        Methodes d'instance de la classe RopewayScene
//   - float cableHeight () : hauteur du cable
//   - float[] cableCenter () : centre du cable (tableau de 2 float) 
//   - float cableRadius () : rayon du cable
//
// OBSERVER ET EXPLIQUER LE COMPORTEMENT DE LA CABINE
//   Proposer une amelioration sans remettre en cause le principe de
//   l'increment angulaire.
//




/** Fourth training session on geometric transforms. */
public class Exo4 extends RopewayScene
{
  /** Pre-computed cosine of a 1-degree angle increment. */
  private final float COS_UN_DEGRE = 0.9998f;
  /** Pre-computed sine of a 1-degree angle increment. */
  private final float SIN_UN_DEGRE = 0.0175f;


  /** Ropeway run distance */
  private float distance = 0.0f;

  // Just to avoid garbage collector activation ...
  /** Angle increment matrix. */
  private Matrix angle_inc = null;
  /** Cabin pose matrix. */
  private Matrix cabin_pose = null;
  /** Count of achieved cycles. */
  private int nb_cycles = 0;


  /** Returns the next animation step pose.
    * @param pose Already allocated vector to fill in with new pose values.
    */
  public boolean nextStep (float[] pose)
  {
    cabin_pose.multLeft (angle_inc);
    nb_cycles ++;
    if (nb_cycles % 360 == 0) System.out.println (nb_cycles / 360 + " tours");
    cabin_pose.toArray (pose);
    return (true);
  }

  /** Constructs the 3D scene used for the present exercice.
    * Prepares the intermediate storage objects.
    * @param p1 Type of the loaded scene.
    * @param p2 Parameters of the loaded scene.
    */
  private Exo4 (int p1, String[] p2)
  {
    super (p1, p2);

    // Computation of incremented motion
    angle_inc = new Matrix (cableCenter()[0], cableCenter()[1], 0.0f);
    Matrix mat = new Matrix ('Z', COS_UN_DEGRE, SIN_UN_DEGRE);
    angle_inc.mult (mat);
    mat = new Matrix (- cableCenter()[0], - cableCenter()[1], 0.0f);
    angle_inc.mult (mat);
    cabin_pose = new Matrix (cableCenter()[0],
                             cableCenter()[1] - cableRadius (), cableHeight ());
  }

  /** Runs a cabin animation.
    * @param args Run arguments.
    */
  public static void main (String[] args)
  {
    new ExoFrame (new Exo4 (2, args), 4);
  }
}
