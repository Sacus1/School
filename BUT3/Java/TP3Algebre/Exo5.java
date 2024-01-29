// Trame des TP d'IN4 (R5.15) : algebre et modelisation geometrique
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die
// Complete par :
//
// Objectif : programer le deplacement du tonneau pour l'attacher a
//   l'extremite de la corde de la grue.
//
// Mise en oeuvre : completer la methode "nextStep" appelee en tache de
//   fond par l'application. Cette methode doit mettre dans le parametre
//   "pose" les positions successives du tonneau.
//   Il faut pour cela "parcourir" la chaine cinematique constituee par
//   la grue.
//   A cet effet des valeurs articulaires de la grue sont fournies par
//   des methodes d'instance de CraneScene :
//     - mastAngle () retourne l'angle d'azimuth de la grue en degres
//     - ropeShift () retourne la distance de la corde au mat de la grue
//     - ropeLength () retourne la longueur de la corde (crochet compris)
//
// Geometrie de la scene :
//   Methodes d'instance de CraneScene
//     - float[] cranePos () : position2D de la base de la grue
//     - float mastWidth () : largeur du mat de la grue (de section carree)
//     - float mastHeight () : hauteur au sol de la fleche
//     - float jibShift () : distance du centre de la fleche au mat
//     - float jibLength () : longueur de la fleche
//     - float jibThickness () : epaisseur de la fleche
//     - float ropeRadius () : rayon de la corde
//     - float hookWidth () : largeur du crochet
//     - float hookThickness () : hauteur du crochet
//     - int captainAge () :  age du capitaine
//     - float barrelHeight () : hauteur du tonneau
//     - float barrelRadius () : rayon du tonneau
//


import java.util.Arrays;

/** First training session on model/view control. */
public class Exo5 extends CraneScene
{


  /** Returns the next animation step pose.
   * @param pose Already allocated vector to fill in with new pose values.
   */
  public boolean nextStep (float[] pose)
  {
    Matrix m = new Matrix();
    m.set(cranePos()[0],cranePos()[1],0);
    m.mult(new Matrix('z', mastAngle()));
    m.mult(new Matrix(0,ropeShift(),mastHeight()-ropeLength()-hookThickness()-barrelHeight()));
    m.toArray(pose);
    return true;
  }

  /** Constructs the 3D scene used for training.
   * Prepares the intermediate storage objects.
   * @param p1 Type of the loaded scene.
   */
  private Exo5 (int p1)
  {
    super (p1, true);

    // Preparation du mouvement (a completer)
  }

  /** Runs a crane animation.
   * @param args Run arguments.
   */
  public static void main (String[] args)
  {
    new ExoFrame (5, new Exo5 (0));
  }
}
