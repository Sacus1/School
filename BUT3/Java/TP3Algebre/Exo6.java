// Trame des TP d'IN4 (R5.15) : algebre et modelisation geometrique
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die
// Complete par :
//
// Objectif : programer le deplacement du tonneau pour l'attacher a
//   l'extremite de la corde de la grue.
// Difference par rapport a l'exo 5 : la fleche se dresse.
//
// Mise en oeuvre : completer la methode "nextStep" appelee en tache de
//   fond par l'application. Cette methode doit mettre dans le parametre
//   "pose" les positions successives du tonneau.
//   Il faut pour cela "parcourir" la chaine cinematique constituee par
//   la grue.
//   A cet effet des valeurs articulaires de la grue sont fournies par
//   des methodes d'instance de CraneScene :
//     - mastAngle () retourne l'angle d'azimuth de la grue en degres
//     - jibAngle () retourne l'angle d'elevation de la fleche en degres
//     - ropeShift () retourne la distance de la corde au mat de la grue
//     - ropeLength () retourne la longueur de la corde (crochet compris)
//     - windAngle () retourne l'inclinaison due au vent
//
// Geometrie de la scene : cf Exo5
//



/** Second training session on model/view control. */
public class Exo6 extends CraneScene
{
  /** Returns the next animation step pose.
   * @param pose Already allocated vector to fill in with new pose values.
   */
  public boolean nextStep (float[] pose)
{
    Matrix m = new Matrix();
    m.set(cranePos()[0],cranePos()[1],0);
    m.mult(new Matrix('z', mastAngle()+90));
    float h = mastHeight() - ropeLength() - hookThickness() - barrelHeight();
    // lenght of the jib
    float l = jibLength() - ;
    // use trigonometry to compute the height of the jib
    float diff = l * (float)Math.sin(Math.toRadians(jibAngle()));
    m.mult(new Matrix(ropeShift(),0, h + diff));
    m.toArray(pose);
    // print height of the jib
    return true;
}

  /** Constructs the 3D scene used for training.
   * Prepares the intermediate storage objects.
   * @param p1 Type of the loaded scene.
   */
  private Exo6 (int p1)
  {
    super (p1, true);

    // Preparation du mouvement (a completer)
  }

  /** Runs a crane animation.
   * @param args Run arguments.
   */
  public static void main (String[] args)
  {
    new ExoFrame (6, new Exo6 (1));
  }
}
