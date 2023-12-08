// Trame des TP d'IN4 (R5.15) : transformations geometriques
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.GL2;


/** Ring-shaped cable. */
public class Ring extends SolidPrimitive
{
  /** Half-depth (along X). */
  private float radius = 1.0f;
  /** Half-width (along Y). */
  private float width_2 = 0.1f;
  /** Half-height (along Z). */
  private float height_2 = 0.1f;
  /** Discretization: count of meridian cuts. */
  private int resol = 16;
  /** Discretization: default count of meridian cuts. */
  private final static int DEF_RESOL = 16;


  /** Constructs a ring with given parameters.
    * @param radius Ring radius.
    * @param width Ring width.
    * @param height Ring height.
    * @param res Ring resolution.
    */ 
  public Ring (float radius, float width, float height, int res)
  {
    this.radius = radius; 
    width_2 = width / 2; 
    height_2 = height / 2; 
    resol = res;
    setReference (REF_CENTER);
  }

  /** Constructs a ring with given parameters and default resolution.
    * @param radius Ring radius.
    * @param width Ring width.
    * @param height Ring height.
    */ 
  public Ring (float radius, float width, float height)
  {
    this (radius, width, height, DEF_RESOL);
  }

  /** Constructs a default ring.
    */ 
  public Ring ()
  {
  }

  /** Sets the ring reference system (REF_CENTER).
    * @param ref Reference system position wrt the ring.
    */
  protected void setReference (int ref)
  {
  }

  /** Renders the ring primitive.
    * @param gl GL2 context. 
    */ 
  public void draw (GL2 gl)
  {
    double angle;
    float cx, sx;

    gl.glPushMatrix ();
      gl.glMultMatrixf (refMatrix, 0);

      // Exterior circle
      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        gl.glNormal3f (1.0f, 0.0f, 0.0f);
        gl.glVertex3f (radius + width_2, 0.0f, height_2);
        gl.glVertex3f (radius + width_2, 0.0f, - height_2);
        for (int i = 1; i < resol; i++)
        {
          angle = 2 * i * Math.PI / resol;
          cx = (float) Math.cos (angle);
          sx = (float) Math.sin (angle);
          gl.glNormal3f (cx, sx, 0.0f);
          gl.glVertex3f (cx * (radius + width_2),
                         sx * (radius + width_2), height_2);
          gl.glVertex3f (cx * (radius + width_2),
                         sx * (radius + width_2), - height_2);
        }
        gl.glNormal3f (1.0f, 0.0f, 0.0f);
        gl.glVertex3f (radius + width_2, 0.0f, height_2);
        gl.glVertex3f (radius + width_2, 0.0f, - height_2);
      gl.glEnd ();

      // Interior circle
      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        gl.glNormal3f (1.0f, 0.0f, 0.0f);
        gl.glVertex3f (radius - width_2, 0.0f, - height_2);
        gl.glVertex3f (radius - width_2, 0.0f, height_2);
        for (int i = 1; i < resol; i++)
        {
          angle = 2 * i * Math.PI / resol;
          cx = (float) Math.cos (angle);
          sx = (float) Math.sin (angle);
          gl.glNormal3f (cx, sx, 0.0f);
          gl.glVertex3f (cx * (radius - width_2),
                         sx * (radius - width_2), - height_2);
          gl.glVertex3f (cx * (radius - width_2),
                         sx * (radius - width_2), height_2);
        }
        gl.glNormal3f (1.0f, 0.0f, 0.0f);
        gl.glVertex3f (radius - width_2, 0.0f, - height_2);
        gl.glVertex3f (radius - width_2, 0.0f, height_2);
      gl.glEnd ();

      // Superior cut
      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        gl.glNormal3f (0.0f, 0.0f, 1.0f);
        gl.glVertex3f (radius - width_2, 0.0f, height_2);
        gl.glVertex3f (radius + width_2, 0.0f, height_2);
        for (int i = 1; i < resol; i++)
        {
          angle = 2 * i * Math.PI / resol;
          cx = (float) Math.cos (angle);
          sx = (float) Math.sin (angle);
          gl.glVertex3f (cx * (radius - width_2),
                         sx * (radius - width_2), height_2);
          gl.glVertex3f (cx * (radius + width_2),
                         sx * (radius + width_2), height_2);
        }
        gl.glVertex3f (radius - width_2, 0.0f, height_2);
        gl.glVertex3f (radius + width_2, 0.0f, height_2);
      gl.glEnd ();

      // Inferior cut
      gl.glBegin (GL2.GL_TRIANGLE_STRIP);
        gl.glNormal3f (0.0f, 0.0f, -1.0f);
        gl.glVertex3f (radius + width_2, 0.0f, -height_2);
        gl.glVertex3f (radius - width_2, 0.0f, -height_2);
        for (int i = 1; i < resol; i++)
        {
          angle = 2 * i * Math.PI / resol;
          cx = (float) Math.cos (angle);
          sx = (float) Math.sin (angle);
          gl.glVertex3f (cx * (radius + width_2),
                         sx * (radius + width_2), -height_2);
          gl.glVertex3f (cx * (radius - width_2),
                         sx * (radius - width_2), -height_2);
        }
        gl.glVertex3f (radius + width_2, 0.0f, -height_2);
        gl.glVertex3f (radius - width_2, 0.0f, -height_2);
      gl.glEnd ();

    gl.glPopMatrix ();
  }
}
