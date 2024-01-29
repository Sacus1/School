// Trame des TP d'IN4 (R5.15) : algebre et modelisation geometrique
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.GL2;

/** Box solid primitive. */
public class Box extends SolidPrimitive {
  /** Half depth (X). */
  private float depth_2 = 1.0f;

  /** Half width (Y). */
  private float width_2 = 1.0f;

  /** Half height (Z). */
  private float height_2 = 1.0f;

  /**
   * Constructs a box with given parameters.
   *
   * @param depth Box depth
   * @param width Box width
   * @param height Box height
   * @param ref Box refence system (REF_CENTER, REF_BASE or REF_TOP)
   */
  public Box(float depth, float width, float height, int ref) {
    depth_2 = depth / 2;
    width_2 = width / 2;
    height_2 = height / 2;
    setReference(ref);
  }

  /**
   * Constructs a box with given parameters * and the reference at basis.
   *
   * @param depth Box depth
   * @param width Box width
   * @param height Box height
   */
  public Box(float depth, float width, float height) {
    this(depth, width, height, REF_BASE);
  }

  /** Constructs a unit box. */
  public Box() {}

  /**
   * Sets the box reference system (REF_CENTER or REF_BASE).
   *
   * @param ref Reference system position wrt the box.
   */
  protected void setReference(int ref) {
    switch (ref) {
      case REF_CENTER:
        refMatrix[11] = 0.0f;
        break;
      case REF_BASE:
        refMatrix[14] = height_2;
        break;
      case REF_TOP:
        refMatrix[14] = -height_2;
        break;
      default:
        break;
    }
  }

  /**
   * Renders the box primitive.
   *
   * @param gl GL2 context.
   */
  public void draw(GL2 gl) {
    gl.glPushMatrix();
    gl.glMultMatrixf(refMatrix, 0);

    gl.glBegin(GL2.GL_QUADS);
    gl.glNormal3f(0.0f, 0.0f, -1.0f);
    gl.glVertex3f(-depth_2, -width_2, -height_2);
    gl.glVertex3f(-depth_2, width_2, -height_2);
    gl.glVertex3f(depth_2, width_2, -height_2);
    gl.glVertex3f(depth_2, -width_2, -height_2);

    gl.glNormal3f(-1.0f, 0.0f, 0.0f);
    gl.glVertex3f(-depth_2, -width_2, -height_2);
    gl.glVertex3f(-depth_2, -width_2, height_2);
    gl.glVertex3f(-depth_2, width_2, height_2);
    gl.glVertex3f(-depth_2, width_2, -height_2);

    gl.glNormal3f(0.0f, -1.0f, 0.0f);
    gl.glVertex3f(-depth_2, -width_2, -height_2);
    gl.glVertex3f(depth_2, -width_2, -height_2);
    gl.glVertex3f(depth_2, -width_2, height_2);
    gl.glVertex3f(-depth_2, -width_2, height_2);

    gl.glNormal3f(0.0f, 0.0f, 1.0f);
    gl.glVertex3f(depth_2, width_2, height_2);
    gl.glVertex3f(-depth_2, width_2, height_2);
    gl.glVertex3f(-depth_2, -width_2, height_2);
    gl.glVertex3f(depth_2, -width_2, height_2);

    gl.glNormal3f(1.0f, 0.0f, 0.0f);
    gl.glVertex3f(depth_2, width_2, height_2);
    gl.glVertex3f(depth_2, -width_2, height_2);
    gl.glVertex3f(depth_2, -width_2, -height_2);
    gl.glVertex3f(depth_2, width_2, -height_2);

    gl.glNormal3f(0.0f, 1.0f, 0.0f);
    gl.glVertex3f(depth_2, width_2, height_2);
    gl.glVertex3f(depth_2, width_2, -height_2);
    gl.glVertex3f(-depth_2, width_2, -height_2);
    gl.glVertex3f(-depth_2, width_2, height_2);
    gl.glEnd();

    gl.glPopMatrix();
  }
}
