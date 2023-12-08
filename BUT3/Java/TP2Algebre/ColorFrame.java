// Trame des TP d'IN4 (R5.15) : chaines cinematiques et suivi
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.GL2;


/** Colored reference frame to help align the 3D scenes. */
public class ColorFrame
{
  /** Default arrow body length. */
  private final static float AXIS = 0.02f;
  /** Default arrow head radius. */
  private final static float RADIUS = 0.08f;
  /** Default arrow head length. */
  private final static float LENGTH = 0.8f;
  /** Default resolution for revolution steps. */
  private final static int RESOL = 16;
  /** Default X axis color. */
  private final static float[] X_COLOR = {0.3f, 0.6f, 0.8f};
  /** Default Y axis color. */
  private final static float[] Y_COLOR = {0.3f, 0.6f, 0.8f};
  /** Default Z axis color. */
  private final static float[] Z_COLOR = {0.7f, 0.5f, 0.3f};

  /** Size of the reference frame. */
  private float size = 1.0f;
  /** Arrow body length. */
  private float axis = AXIS;
  /** Arrow head radius. */
  private float radius = RADIUS;
  /** Arrow axis length. */
  private float length = LENGTH;
  /** Revolution steps resolution. */
  private int resol = RESOL;
  /** X axis color. */
  private final static float[] x_col = {X_COLOR[0], X_COLOR[1], X_COLOR[2]};
  /** Y axis color. */
  private final static float[] y_col = {Y_COLOR[0], Y_COLOR[1], Y_COLOR[2]};
  /** Z axis color. */
  private final static float[] z_col = {Z_COLOR[0], Z_COLOR[1], Z_COLOR[2]};



  /** Creates a reference frame with given size.
    * @param size Size of the reference frame.
    */
  public ColorFrame (float size)
  {
    this.size = size;
    axis = AXIS * size;
    radius = RADIUS * size;
    length = LENGTH * size;
  }

  /** Sets one axis color.
    * @param axis Frame axis to paint (0, 1 or 2).
    * @param r Red component.
    * @param g Green component.
    * @param b Blue component.
    */
  public void setColor (int axis, float r, float g, float b)
  {
    switch (axis)
    {
      case 0 :
        x_col[0] = r;
        x_col[1] = g;
        x_col[2] = b;
        break;
      case 1 :
        y_col[0] = r;
        y_col[1] = g;
        y_col[2] = b;
        break;
      case 2 :
        z_col[0] = r;
        z_col[1] = g;
        z_col[2] = b;
        break;
    }
  }

  /** Sets one axis color.
    * @param axis Frame axis to paint (x, y or z).
    * @param r Red component.
    * @param g Green component.
    * @param b Blue component.
    */
  public void setColor (char axis, float r, float g, float b)
  {
    switch (axis)
    {
      case 'x' :
      case 'X' :
        x_col[0] = r;
        x_col[1] = g;
        x_col[2] = b;
        break;
      case 'y' :
      case 'Y' :
        y_col[0] = r;
        y_col[1] = g;
        y_col[2] = b;
        break;
      case 'z' :
      case 'Z' :
        z_col[0] = r;
        z_col[1] = g;
        z_col[2] = b;
        break;
    }
  }

  /** Sets the revolution steps resolution.
    * @param value New resolution value.
    */
  public void setResolution (int value)
  {
    resol = value;
  }

  /** Draws the reference frame.
    * @param gl OpenGL2 context.
    */
  public void draw (GL2 gl)
  {
    // Y axis
    gl.glColor3f (y_col[0], y_col[1], y_col[2]);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        float ca = (float) Math.cos (a);
        float sa = (float) Math.sin (a);
        gl.glVertex3f (axis * ca, 0.0f, axis * sa);
        gl.glVertex3f (axis * ca, length, axis * sa);
      }
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, length, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = resol - 1; i > 0; i --)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3d (0.0f, size, 0.0f);
      gl.glVertex3d (radius, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();

    // X axis
    gl.glColor3f (x_col[0], x_col[1], x_col[2]);
    gl.glPushMatrix ();
    gl.glRotatef (- 90.0f, 0.0f, 0.0f, 1.0f);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        float ca = (float) Math.cos (a);
        float sa = (float) Math.sin (a);
        gl.glVertex3f (axis * ca, 0.0f, axis * sa);
        gl.glVertex3f (axis * ca, length, axis * sa);
      }
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, length, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = resol - 1; i > 0; i --)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, size, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glPopMatrix ();
 
    // Z axis
    gl.glColor3f (z_col[0], z_col[1], z_col[2]);
    gl.glPushMatrix ();
    gl.glRotatef (90.0f, 1.0f, 0.0f, 0.0f);
    gl.glBegin (GL2.GL_TRIANGLE_STRIP);
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        float ca = (float) Math.cos (a);
        float sa = (float) Math.sin (a);
        gl.glVertex3f (axis * ca, 0.0f, axis * sa);
        gl.glVertex3f (axis * ca, length, axis * sa);
      }
      gl.glVertex3f (axis, 0.0f, 0.0f);
      gl.glVertex3f (axis, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, length, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = resol - 1; i > 0; i --)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glBegin (GL2.GL_TRIANGLE_FAN);
      gl.glVertex3f (0.0f, size, 0.0f);
      gl.glVertex3f (radius, length, 0.0f);
      for (int i = 1; i < resol; i ++)
      {
        double a = 2 * i * Math.PI / resol;
        gl.glVertex3f (radius * (float) Math.cos (a), length,
                       radius * (float) Math.sin (a));
      }
      gl.glVertex3f (radius, length, 0.0f);
    gl.glEnd ();
    gl.glPopMatrix ();
  }
}
