// Trame des TP d'IN4 (R5.15) : transformations geometriques
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.GL2;


/** Ropeway scene. */
public abstract class RopewayScene extends ExoScene
{
  /** Cable object. */
  private Cable cable = null;

  /** Ground dimensions: half the depth. */
  private final float depth_2 = 4.0f;
  /** Ground dimensions: half the width. */
  private final float width_2 = 3.0f;
  /** Ground dimensions: half the thickness. */
  private final float thickness_2 = 0.1f;
  /** Ground optical features: material specular component. */
  private float[] soilSpecularity = {0.1f, 0.4f, 0.1f, 1.0f};
  /** Ground optical features: material shininess. */
  private float[] soilShininess = {1.0f};
  /** Ground optical features: material diffuse component. */
  private float[] soilDiffusion = {0.4f, 0.9f, 0.2f, 1.0f};
  /** Ground optical features: material ambient component. */
  private float[] soilAmbiance = {0.4f, 0.7f, 0.2f, 1.0f};

  /** Ropeway dimensions: height of the mast. */
  private float mastHeight = 0.2f;
  /** Ropeway dimensions: width of the mast. */
  private float mastWidth = 0.05f;
  /** Ropeway dimensions: length of the cabin. */
  private float cabinLength = 0.8f;
  /** Ropeway dimensions: width of the cabin. */
  private float cabinWidth = 0.4f;
  /** Ropeway dimensions: height of the cabin. */
  private float cabinHeight = 0.5f;
  /** Ropeway dimensions: height shift of the glasses. */
  private float glassCenterHeight = 0.1f;
  /** Ropeway dimensions: height of the glasses. */
  private float glassHeight = 0.2f;
  /** Ropeway dimensions: side shift of the glasses. */
  private float glassOutOffset = 0.005f;
  /** Ropeway dimensions: width of the front glass. */
  private float frontGlassWidth = 0.36f;
  /** Ropeway dimensions: width of the side glasses. */
  private float sideGlassWidth = 0.5f;
  /** Ropeway dimensions: horizontal shift of the side glasses. */
  private float sideGlassCenter = 0.1f;

  /** Ropeway materials: material specular component of the mast. */
  private float[] mastSpecularity = {0.8f, 0.8f, 0.4f, 1.0f};
  /** Ropeway materials: material shininess of the mast. */
  private float[] mastShininess = {50.0f};
  /** Ropeway materials: material ambient component of the mast. */
  private float[] mastAmbiance = {0.6f, 0.6f, 0.2f, 1.0f};
  /** Ropeway materials: material diffuse component of the mast. */
  private float[] mastDiffusion = {0.8f, 0.8f, 0.4f, 1.0f};
  /** Ropeway materials: material specular component of the cabin. */
  private float[] cabinSpecularity = {0.8f, 0.5f, 0.2f};
  /** Ropeway materials: material shininess of the cabin. */
  private float[] cabinShininess = {2.0f};
  /** Ropeway materials: material ambient component of the cabin. */
  private float[] cabinAmbiance = {0.7f, 0.4f, 0.1f};
  /** Ropeway materials: material diffuse component of the cabin. */
  private float[] cabinDiffusion = {0.8f, 0.5f, 0.2f};
  /** Ropeway materials: material specular component of the glasses. */
  private float[] glassSpecularity = {0.1f, 0.1f, 0.1f};
  /** Ropeway materials: material shininess of the glasses. */
  private float[] glassShininess = {2.0f};
  /** Ropeway materials: material ambient component of the glasses. */
  private float[] glassAmbiance = {0.1f, 0.1f, 0.1f};
  /** Ropeway materials: material diffuse component of the glasses. */
  private float[] glassDiffusion = {0.2f, 0.2f, 0.2f};

  /** Reference frame. */
  private ColorFrame cf = new ColorFrame (1.0f);
  /** Reference frame display modality. */
  private boolean framep = false;

  /** Cabin dynamics: default position. */
  private final static float[] POSE_INI = {0.0f, 0.0f, 1.0f};
  /** Cabin dynamics: default pose matrix. */
  private float[] pose = {1.0f, 0.0f, 0.0f, POSE_INI[0],
                          0.0f, 1.0f, 0.0f, POSE_INI[1],
                          0.0f, 0.0f, 1.0f, POSE_INI[2],
                          0.0f, 0.0f, 0.0f, 1.0f};
  /** Cabin dynamics: Computed pose matrix. */
  private float[] transPose = {1.0f, 0.0f, 0.0f, 0.0f,
                               0.0f, 1.0f, 0.0f, 0.0f,
                               0.0f, 0.0f, 1.0f, 0.0f,
                               POSE_INI[0], POSE_INI[1], POSE_INI[2], 1.0f};

  /** Display lists: model of the cable. */
  private int CABLE_DRAWING = 1;
  /** Display lists: model of the ground. */
  private int GROUND_DRAWING = 2;
  /** Display lists: model of the cabin. */
  private int CABIN_DRAWING = 3;


  /** Constructs a specific ropeway scene.
    * @param type Scene geometry
    * @param params Geometry variability option
    */
  protected RopewayScene (int type, String[] params)
  {
    if (type == 0)
      cable = new Cable (0, params.length != 0); 

    else if (type == 1)
    {
      if (params.length != 0)
      {
        if (params[0].equals ("2")) type = 2;
        else if (params[0].equals ("3")) type = 3;
        else if (params[0].equals ("4")) type = 4;
      }
      cable = new Cable (type, params.length != 0);
    }

    else cable = new Cable (10, params.length != 0);
  }

  /** Initializes the ropewayscene.
    * @param gl GL2 context.
    */
  public void init (GL2 gl)
  {
    gl.glNewList (CABLE_DRAWING, GL2.GL_COMPILE);
      cable.draw (gl);
    gl.glEndList ();

    Box soil = new Box (depth_2 * 2 + thickness_2 * 2,
                        width_2 * 2 + thickness_2 * 2, thickness_2);
    gl.glNewList (GROUND_DRAWING, GL2.GL_COMPILE);
      gl.glPushMatrix ();
        gl.glTranslatef (0.0f, 0.0f, - thickness_2);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, soilSpecularity, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, soilShininess, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT, soilAmbiance, 0);
        gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_DIFFUSE, soilDiffusion, 0);
        soil.draw (gl);
      gl.glPopMatrix ();
    gl.glEndList ();

    Box mastGeo = new Box (mastWidth, mastWidth, mastHeight,
                           SolidPrimitive.REF_CENTER);
    Box cabinGeo = new Box (cabinWidth, cabinLength, cabinHeight,
                          SolidPrimitive.REF_CENTER);
    Box frontGlassGeo = new Box (frontGlassWidth, glassOutOffset * 2,
                                 glassHeight, SolidPrimitive.REF_CENTER);
    Box sideGlassGeo = new Box (cabinWidth + glassOutOffset * 2,
                                sideGlassWidth, glassHeight,
                                SolidPrimitive.REF_CENTER);

    gl.glNewList (CABIN_DRAWING, GL2.GL_COMPILE);
      // Mast
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, mastSpecularity, 0);
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, mastShininess, 0);
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT, mastAmbiance, 0);
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_DIFFUSE, mastDiffusion, 0);
      gl.glTranslatef (0.0f, 0.0f, - mastHeight / 2);
      mastGeo.draw (gl);

      // Cabin
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, cabinSpecularity, 0);
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, cabinShininess, 0);
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT, cabinAmbiance, 0);
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_DIFFUSE, cabinDiffusion, 0);
      gl.glTranslatef (0.0f, 0.0f, - (mastHeight + cabinHeight) / 2);
      cabinGeo.draw (gl);

      // Glasses
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SPECULAR, glassSpecularity, 0);
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_SHININESS, glassShininess, 0);
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_AMBIENT, glassAmbiance, 0);
      gl.glMaterialfv (GL2.GL_FRONT, GL2.GL_DIFFUSE, glassDiffusion, 0);
      gl.glPushMatrix ();
        gl.glTranslatef (0.0f, cabinLength / 2, glassCenterHeight);
        frontGlassGeo.draw (gl);
      gl.glPopMatrix ();
      gl.glTranslatef (0.0f, sideGlassCenter, glassCenterHeight);
      sideGlassGeo.draw (gl);
    gl.glEndList ();
  }

  /** Updates the ropeway scene.
    */
  public void update ()
  {
    // Updates the shuttle pose
    if (alive)
    {
      alive = nextStep (pose);
      for (int i = 0; i < 4; i++)
        for (int j = 0; j < 4; j++)
          transPose[i*4+j] = pose[j*4+i];
    }
  }


  /** Renders the ropewayscene.
    * @param gl GL2 context. 
    */ 
  public void draw (GL2 gl)
  {
    // Floor
    if (framep)
    {
      gl.glDisable (GL2.GL_LIGHTING);
      gl.glPushMatrix ();
        gl.glTranslatef (0.0f, 0.0f, 0.1f);
        cf.draw (gl);
      gl.glPopMatrix ();
      gl.glEnable (GL2.GL_LIGHTING);
    }
    gl.glCallList (GROUND_DRAWING);

    // Cable
    gl.glCallList (CABLE_DRAWING);

    // Cabin
    gl.glMultMatrixf (transPose, 0);
    gl.glRotatef (-90.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList (CABIN_DRAWING);
  }

  /** Switches on or off frame display modality.
    */
  protected void switchFrame ()
  {
    framep = ! framep;
  }

  /** Returns the cable length.
    * @return the cable length.
    */
  protected float cableLength ()
  {
    return (cable.length ());
  }

  /** Returns the cable height to the ground.
    * @return the cable height to the ground.
    */
  protected float cableHeight ()
  {
    return (cable.pos(2));
  }

  /** Returns the cable start point coordinates.
    * @return the cable start point coordinates.
    */
  protected float[] cableStart ()
  {
    return (new float[] {cable.pos (0), cable.pos (1)});
  }

  /** Returns the coordinates of a crossing point on the cable.
    * @return the coordinates of a crossing point on the cable.
    */
  protected float[] cableInter ()
  {
    return (new float[] {cable.inter (0), cable.inter (1)});
  }

  /** Returns the coordinates of the center of the circular cable.
    * @return the coordinates of the center of the circular cable.
    */
  protected float[] cableCenter ()
  {
    return (new float[] {cable.inter (0), cable.inter (1)});
  }

  /** Returns the radius of the circular cable.
    * @return the radius of the circular cable.
    */
  protected float cableRadius ()
  {
    return (cable.radius ());
  }

  /** Returns the ropeway speed.
    * @return the ropeway speed.
    */
  protected float ropewaySpeed ()
  {
    return (speed);
  }

}
