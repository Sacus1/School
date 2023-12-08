// Trame des TP d'IN4 (R5.15) : transformations geometriques
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.GL2;


/** Abstract class for solid primitives */
public abstract class SolidPrimitive
{
  /** Reference system positions wrt the solid primitive: at center. */
  public final static int REF_CENTER = 0;
  /** Reference system positions wrt the solid primitive: at basis. */
  public final static int REF_BASE = 1;
  /** Reference system positions wrt the solid primitive: at top. */
  public final static int REF_TOP = 2;


  /** Reference system positions wrt the solid primitive: general case. */
  protected float[] refMatrix = {1.0f, 0.0f, 0.0f, 0.0f,
                                 0.0f, 1.0f, 0.0f, 0.0f,
                                 0.0f, 0.0f, 1.0f, 0.0f,
                                 0.0f, 0.0f, 0.0f, 1.0f};


  /** Sets the solid primitive reference system.
    * @param ref Reference system position wrt the box.
    */
  protected abstract void setReference (int ref);

  /** Renders the solid primitive.
    * @param gl GL2 context. 
    */ 
  public abstract void draw (GL2 gl);
}
