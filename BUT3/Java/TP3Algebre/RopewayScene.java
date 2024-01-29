// Trame des TP d'IN4 (R5.15) : algebre et modelisation geometrique
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.GL2;

/** Ropeway scene. */
public class RopewayScene extends ExoScene {
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

  /** Ropeway dimensions: height of the viewpoint from the cabin floor. */
  private float cabinViewpoint = 0.4f;

  /** Ropeway dimensions: height shift of the glasses. */
  private float glassCenterHeight = 0.1f;

  /** Ropeway dimensions: height of the glasses. */
  private float glassHeight = 0.2f;

  /** Ropeway dimensions: side shift of the glasses. */
  private float glassOutOffset = 0.01f;

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
  private ColorFrame cf = new ColorFrame(1.0f);

  /** Reference frame display modality. */
  private boolean framep = false;

  /** Reference frame display modality. */
  private boolean controlp = false;

  /** Reference cabin orientation. */
  private boolean xwards = false;

  /** Cabin dynamics: default position. */
  private static final float[] POSE_INI = {0.0f, 0.0f, 1.0f};

  /** Cabin dynamics: viewpoint position inside the cabin. */
  private float[] cabin_vp = new float[3];

  /** Cabin dynamics: cabin rotation angle. */
  private float rotangle = 0.0f;

  /** Cabin dynamics: default pose matrix. */
  private float[] pose = {
    1.0f, 0.0f, 0.0f, POSE_INI[0],
    0.0f, 1.0f, 0.0f, POSE_INI[1],
    0.0f, 0.0f, 1.0f, POSE_INI[2],
    0.0f, 0.0f, 0.0f, 1.0f
  };

  /** Cabin dynamics: computed pose matrix. */
  private float[] transPose = {
    1.0f,
    0.0f,
    0.0f,
    0.0f,
    0.0f,
    1.0f,
    0.0f,
    0.0f,
    0.0f,
    0.0f,
    1.0f,
    0.0f,
    POSE_INI[0],
    POSE_INI[1],
    POSE_INI[2],
    1.0f
  };

  /** Display lists: model of the cable. */
  private int CABLE_DRAWING = 1;

  /** Display lists: model of the ground. */
  private int GROUND_DRAWING = 2;

  /** Display lists: model of the cabin. */
  private int CABIN_DRAWING = 3;

  /**
   * Constructs a specific ropeway scene.
   *
   * @param type Scene geometry.
   * @param xw Scene variation.
   * @param params Geometry variability option.
   */
  protected RopewayScene(int type, boolean xw, String[] params) {
    if (type == 0) cable = new Cable(xw ? 5 : 0, params.length != 0);
    else if (type == 1) {
      if (params.length != 0) {
        if (params[0].equals("2")) type = 2;
        else if (params[0].equals("3")) type = 3;
        else if (params[0].equals("4")) type = 4;
      }
      cable = new Cable(type, params.length != 0);
    } else cable = new Cable(type, params.length != 0);
    if (type == 9) controlp = true;
    xwards = xw;
  }

  /**
   * Initializes the ropewayscene.
   *
   * @param gl GL2 context.
   */
  public void init(GL2 gl) {
    gl.glNewList(CABLE_DRAWING, GL2.GL_COMPILE);
    cable.draw(gl);
    gl.glEndList();

    Box soil = new Box(depth_2 * 2 + thickness_2 * 2, width_2 * 2 + thickness_2 * 2, thickness_2);
    gl.glNewList(GROUND_DRAWING, GL2.GL_COMPILE);
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.0f, -thickness_2);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, soilSpecularity, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, soilShininess, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, soilAmbiance, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, soilDiffusion, 0);
    soil.draw(gl);
    gl.glPopMatrix();
    gl.glEndList();

    Box mastGeo = new Box(mastWidth, mastWidth, mastHeight, SolidPrimitive.REF_CENTER);
    Box cabinGeo = new Box(cabinWidth, cabinLength, cabinHeight, SolidPrimitive.REF_CENTER);
    Box frontGlassGeo =
        new Box(frontGlassWidth, cabinLength, glassHeight, SolidPrimitive.REF_CENTER);
    Box sideGlassGeo =
        new Box(
            cabinWidth + glassOutOffset * 2,
            sideGlassWidth,
            glassHeight,
            SolidPrimitive.REF_CENTER);

    gl.glNewList(CABIN_DRAWING, GL2.GL_COMPILE);
    // Mast
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mastSpecularity, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, mastShininess, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mastAmbiance, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mastDiffusion, 0);
    gl.glTranslatef(0.0f, 0.0f, -mastHeight / 2);
    mastGeo.draw(gl);

    // Cabin
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, cabinSpecularity, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, cabinShininess, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, cabinAmbiance, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, cabinDiffusion, 0);
    gl.glTranslatef(0.0f, 0.0f, -(mastHeight + cabinHeight) / 2);
    cabinGeo.draw(gl);

    // Glasses
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, glassSpecularity, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, glassShininess, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, glassAmbiance, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, glassDiffusion, 0);
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, glassOutOffset, glassCenterHeight);
    frontGlassGeo.draw(gl);
    gl.glPopMatrix();
    gl.glTranslatef(0.0f, sideGlassCenter, glassCenterHeight);
    sideGlassGeo.draw(gl);
    gl.glEndList();
  }

  /** Updates the ropeway scene. */
  public void update() {
    if (controlp) {
      pose[0] = (float) Math.cos(rotangle * Math.PI / 180);
      pose[4] = (float) Math.sin(rotangle * Math.PI / 180);
      pose[1] = -pose[4];
      pose[5] = pose[0];
      pose[2] = 0.0f;
      pose[6] = 0.0f;
      pose[8] = 0.0f;
      pose[9] = 0.0f;
      pose[10] = 1.0f;
      pose[12] = 0.0f;
      pose[13] = 0.0f;
      pose[14] = 0.0f;
      pose[15] = 1.0f;
      pose[11] = cable.pos(2);
      pose[3] = cable.inter(0) + cable.radius() * pose[xwards ? 4 : 0];
      pose[7] = cable.inter(1) - cable.radius() * pose[xwards ? 0 : 1];
      rotangle++;
      for (int i = 0; i < 4; i++)
        for (int j = 0; j < 4; j++) transPose[i * 4 + j] = pose[j * 4 + i];
    } else if (alive) {
      alive = nextStep(pose);
      for (int i = 0; i < 4; i++)
        for (int j = 0; j < 4; j++) transPose[i * 4 + j] = pose[j * 4 + i];
    }
  }

  /**
   * Gets the cabin viewpoint position.
   *
   * @return the cabin viewpoint position.
   */
  public float[] getCabinViewpoint() {
    cabin_vp[0] = transPose[12];
    cabin_vp[1] = transPose[13];
    cabin_vp[2] = transPose[14] + cabinViewpoint - mastHeight - cabinHeight;
    return cabin_vp;
  }

  /**
   * Renders the ropewayscene.
   *
   * @param gl GL2 context.
   */
  public void draw(GL2 gl) {
    // Floor
    if (framep) {
      gl.glDisable(GL2.GL_LIGHTING);
      gl.glPushMatrix();
      gl.glTranslatef(0.0f, 0.0f, 0.1f);
      cf.draw(gl);
      gl.glPopMatrix();
      gl.glEnable(GL2.GL_LIGHTING);
    }
    gl.glCallList(GROUND_DRAWING);

    // Cable
    gl.glCallList(CABLE_DRAWING);

    // Cabin
    gl.glMultMatrixf(transPose, 0);
    if (xwards) gl.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(CABIN_DRAWING);
  }

  /** Switches on or off frame display modality. */
  protected void switchFrame() {
    framep = !framep;
  }

  /**
   * Gets the cable length.
   *
   * @return the cable length.
   */
  protected float cableLength() {
    return (cable.length());
  }

  /**
   * Gets the cable height to the ground.
   *
   * @return the cable height to the ground.
   */
  protected float cableHeight() {
    return (cable.pos(2));
  }

  /**
   * Gets the cable start point coordinates.
   *
   * @return the cable start point coordinates.
   */
  protected float[] cableStart() {
    return (new float[] {cable.pos(0), cable.pos(1)});
  }

  /**
   * Gets the coordinates of a crossing point on the cable.
   *
   * @return the coordinates of a crossing point on the cable.
   */
  protected float[] cableInter() {
    return (new float[] {cable.inter(0), cable.inter(1)});
  }

  /**
   * Gets the coordinates of the center of the circular cable.
   *
   * @return the coordinates of the center of the circular cable.
   */
  protected float[] cableCenter() {
    return (new float[] {cable.inter(0), cable.inter(1)});
  }

  /**
   * Gets the radius of the circular cable.
   *
   * @return the radius of the circular cable.
   */
  protected float cableRadius() {
    return (cable.radius());
  }

  /**
   * Gets the ropeway speed.
   *
   * @return the ropeway speed.
   */
  protected float ropewaySpeed() {
    return (speed);
  }

  /**
   * Gets the next animation step pose.
   *
   * @param pose Already allocated vector to fill in with new pose values.
   */
  public boolean nextStep(float[] pose) {
    for (int i = 0; i < pose.length; i++) pose[i] = (i % 5 == 0 ? 1.0f : 0.0f);
    for (int i = 0; i < 4; i++) pose[5 * i] = 1.0f;
    return (false);
  }
}
