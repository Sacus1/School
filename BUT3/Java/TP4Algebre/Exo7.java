// Trame des TP d'IN4 (R5.15) : algebre et modelisation geometrique
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die
//
// Objectif : programmer le deplacement du tonneau pour l'attacher a
//   l'extremite de la corde de la grue ET AUSSI l'orientation de
//   l'observateur pour suivre en continu le tonneau.
//
// Mise en oeuvre : completer la methode "nextStep" appelee en tache de
//   fond par l'application. Cette methode doit mettre dans le parametre
//   "pose" les positions successives du tonneau.
//
//   DE PLUS, il faut mettre a jour l'observateur a l'aide de la methode
//   "setView" de ExoScene qui retourne un tableau de 16 float correspondant
//   aux termes de la matrice de vue (a determiner).
//   Cette matrice donne la position/orientation de la scene par rapport
//   a l'observateur.
//
//   En plus des valeurs articulaires et de la geometrie de la grue,
//   on dispose de la position de l'observateur fournie par une methode
//   d'instance de ExoScene
//     - void getCameraPosition (float[] vp) :
//          place dans vp les coordonnees du centre de projection
//

/** Third training session on model/view control. */
public class Exo7 extends CraneScene {
  /** View matrix terms. */
  private float[] vis = new float[16];

  /**
   * Returns the next animation step pose.
   *
   * @param pose Already allocated vector to fill in with new pose values.
   */
  public boolean nextStep(float[] pose) {
    // Deplacement du tonneau
    Matrix m = new Matrix();
    m.set(cranePos()[0], cranePos()[1], 0);
    m.mult(new Matrix('z', mastAngle()));
    m.mult(new Matrix(0, 0, mastHeight() - ropeLength() - hookThickness() - barrelHeight()));
    m.mult(new Matrix('x', jibAngle()));
    m.mult(new Matrix(0, ropeShift(), 0));
    m.mult(new Matrix('x', -jibAngle()));
    m.toArray(pose);
    return true;
  }

  public float[] lookAt(float[] pos, float[] target) {
    float[] viewMatrix = new float[16];
    float[] direction = normalize(subtract(target, pos));
    float[] right = normalize(cross(direction, new float[] {0, 1, 0}));
    float[] up = normalize(cross(right, direction));

    Matrix m = new Matrix(right, up, direction, new float[] {0, 0, 0});
    Matrix t = new Matrix(-pos[0], -pos[1], -pos[2]);
    m.mult(t);
    m.toArray(viewMatrix);
    return viewMatrix;
  }

  private static float[] subtract(float[] a, float[] b) {
    return new float[] {a[0] - b[0], a[1] - b[1], a[2] - b[2]};
  }

  private static float[] cross(float[] a, float[] b) {
    return new float[] {
      a[1] * b[2] - a[2] * b[1], a[2] * b[0] - a[0] * b[2], a[0] * b[1] - a[1] * b[0]
    };
  }

  private static float dot(float[] a, float[] b) {
    return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
  }

  private static float[] normalize(float[] a) {
    float length = (float) Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    return new float[] {a[0] / length, a[1] / length, a[2] / length};
  }

  /**
   * Returns the viewing matrix to be immediately applied.
   *
   * @param reverse Unused here.
   */
  public float[] setView(boolean reverse) {
    float[] camPos = new float[3];
    float[] target = new float[3];
    float[] up = new float[] {0, 1, 0};
    getCameraPosition(camPos);
    target[0] = camPos[0];
    target[1] = camPos[1];
    target[2] = camPos[2];
    vis = lookAt(camPos, target);
    return (vis);
  }

  private void getBarrelPosition(float[] barrelPos) {
    float[] pose = new float[16];
    Matrix m = new Matrix();
    m.set(cranePos()[0], cranePos()[1], 0);
    m.mult(new Matrix('z', mastAngle()));
    m.mult(new Matrix(0, 0, mastHeight() - ropeLength() - hookThickness() - barrelHeight()));
    m.mult(new Matrix('x', jibAngle()));
    m.mult(new Matrix(0, ropeShift(), 0));
    m.mult(new Matrix('x', -jibAngle()));
    m.toArray(pose);
    barrelPos[0] = pose[12];
    barrelPos[1] = pose[13];
    barrelPos[2] = pose[14];
  }

  /**
   * Constructs the 3D scene used for training. Prepares the intermediate storage objects.
   *
   * @param p1 Type of the loaded scene.
   */
  private Exo7(int p1) {
    super(p1, true);

    // Preparation du mouvement
    // MATRICE DE VUE PAR DEFAUT
    for (int i = 0; i < 16; i++) vis[i] = 0.f;
    vis[8] = 1.f;
    vis[1] = 1.f;
    vis[6] = 1.f;
    vis[7] = -1.5f;
    vis[11] = -5.f;
    vis[15] = 1.f;
  }

  /**
   * Runs a crane animation.
   *
   * @param args Run arguments.
   */
  public static void main(String[] args) {
    new ExoFrame(7, new Exo7(args.length == 1 && args[0].equals("stop") ? 0 : 1));
  }
}
