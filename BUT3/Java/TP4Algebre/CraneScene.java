// Trame des TP d'IN4 (R5.15) : algebre et modelisation geometrique
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.GL2;

/** Crane scene 3D model. */
public abstract class CraneScene extends ExoScene {
  /** Crane geometry: azimuth angle of the mast. */
  private float mast_azimuth = 0.0f;

  /** Crane geometry: azimuth angle speed. */
  private float azimuth_speed = 0.0f;

  /** Crane geometry: azimuth angle max speed. */
  private float azimuth_max_speed = 0.5f;

  /** Crane geometry: azimuth angle gear. */
  private double azimuth_gear = 0.2f;

  /** Crane geometry: wind occurence. */
  private boolean windy = false;

  /** Crane geometry: wind occurence. */
  private boolean north_wind = false;

  /** Crane geometry: wind angle of the mast. */
  private float mast_wind = 0.0f;

  /** Crane geometry: wind angle speed. */
  private float wind_speed = 0.0f;

  /** Crane geometry: maximal wind angle. */
  private float max_wind_angle = 8.0f;

  /** Crane geometry: wind angle max speed. */
  private float wind_max_speed = 0.3f;

  /** Crane geometry: wind angle gear. */
  private double wind_gear = 0.1f;

  /** Crane geometry: jib mobility status. */
  private boolean mobile_jib = true;

  /** Crane geometry: jib height angle. */
  private float jib_height = 0.0f;

  /** Crane geometry: jib minimal height angle. */
  private float min_height_angle = 0.0f;

  /** Crane geometry: jib maximal height angle. */
  private float max_height_angle = 30.0f;

  /** Crane geometry: jib height angle speed. */
  private float height_speed = 0.0f;

  /** Crane geometry: jib max height angle speed. */
  private float height_max_speed = 0.5f;

  /** Crane geometry: jib height angle gear. */
  private double height_gear = 0.2f;

  /** Crane geometry: rope position on the jib. */
  private float rope_shift = 1.4f;

  /** Crane geometry: rope minimal position on the jib. */
  private float rope_min_shift = 0.4f;

  /** Crane geometry: rope maximal position on the jib. */
  private float rope_max_shift = 2.8f;

  /** Crane geometry: rope motion speed. */
  private float rope_shift_speed = 0.0f;

  /** Crane geometry: rope motion maximal speed. */
  private float rope_shift_max_speed = 0.03f;

  /** Crane geometry: rope motion gear. */
  private float rope_shift_gear = 0.01f;

  /** Crane geometry: rope length. */
  private float ropeLength = 1.8f;

  /** Crane geometry: rope minimal length. */
  private float rope_min_length = 0.3f;

  /** Crane geometry: rope maximal length. */
  private float rope_max_length = 2.5f;

  /** Crane geometry: rope extension speed. */
  private float rope_length_speed = 0.0f;

  /** Crane geometry: rope maximal extension speed. */
  private float rope_length_max_speed = 0.03f;

  /** Crane geometry: rope extension gear. */
  private float rope_length_gear = 0.01f;

  /** Ground features: half-depth. */
  private final float depth_2 = 4.0f;

  /** Ground features: half-width. */
  private final float width_2 = 3.0f;

  /** Ground features: half-thickness. */
  private final float thickness_2 = 0.1f;

  /** Ground features: Ground material specular component . */
  private float[] ground_specularity = {0.1f, 0.4f, 0.1f, 1.0f};

  /** Ground features: Ground material shininess . */
  private float[] ground_shininess = {1.0f};

  /** Ground features: Ground material diffuse component . */
  private float[] ground_diffusion = {0.4f, 0.9f, 0.2f, 1.0f};

  /** Ground features: Ground material ambient component . */
  private float[] ground_ambiance = {0.4f, 0.7f, 0.2f, 1.0f};

  /** Barrel features: height. */
  private final float barrel_height = 0.4f;

  /** Barrel features: radius. */
  private final float barrel_radius = 0.2f;

  /** Barrel features: angle resolution. */
  private final int barrel_resolution = 16;

  /** Barrel features: material specular component. */
  private float[] barrel_specularity = {0.3f, 0.2f, 0.2f};

  /** Barrel features: material shininess. */
  private float[] barrel_shininess = {1.0f};

  /** Barrel features: material diffuse component. */
  private float[] barrel_diffusion = {0.5f, 0.3f, 0.3f};

  /** Barrel features: material ambient component. */
  private float[] barrel_ambiance = {0.4f, 0.2f, 0.3f};

  /** Barrel features: barrel pose matrix. */
  private float[] barrel_pose = {
    1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f
  };

  /** Barrel features: barrel auxiliary pose matrix. */
  private float[] barrel_aux = {
    1.0f, 0.0f, 0.0f, 0.0f,
    0.0f, 1.0f, 0.0f, 0.0f,
    0.0f, 0.0f, 1.0f, 0.0f,
    0.0f, 0.0f, 0.0f, 1.0f
  };

  /** Crane features: crane material specular component. */
  private float[] crane_specularity = {0.6f, 0.3f, 0.2f};

  /** Crane features: crane material shininess. */
  private float[] crane_shininess = {50.0f};

  /** Crane features: crane material diffuse component. */
  private float[] crane_diffusion = {0.9f, 0.6f, 0.2f};

  /** Crane features: crane material ambient component. */
  private float[] crane_ambiance = {0.7f, 0.5f, 0.2f};

  /** Mast features: position. */
  private float[] mast_pos = {-1.5f, -1.0f};

  /** Mast features: width. */
  private float mast_width = 0.3f;

  /** Mast features: height. */
  private float mast_height = 3.0f;

  /** Jib features: length. */
  private float jib_length = 4.0f;

  /** Jib features: jib center distance to the mast axis. */
  private float jib_shift = 1.0f;

  /** Jib features: thickness. */
  private float jib_thickness = 0.15f;

  /** Rope features: material specular component. */
  private float[] rope_specularity = {0.2f, 0.1f, 0.1f};

  /** Rope features: material shininess. */
  private float[] rope_shininess = {1.0f};

  /** Rope features: material diffuse component. */
  private float[] rope_diffusion = {0.2f, 0.3f, 0.4f};

  /** Rope features: material ambient component. */
  private float[] rope_ambiance = {0.2f, 0.3f, 0.3f};

  /** Rope features: radius. */
  private float rope_radius = 0.03f;

  /** Rope features: angular resolution. */
  private int rope_resolution = 8;

  /** Hook features: material specular component. */
  private float[] hook_specularity = {0.2f, 0.1f, 0.1f};

  /** Hook features: material shininess. */
  private float[] hook_shininess = {1.0f};

  /** Hook features: material diffuse component. */
  private float[] hook_diffusion = {0.7f, 0.8f, 0.7f};

  /** Hook features: material ambient component. */
  private float[] hook_ambiance = {0.7f, 0.7f, 0.7f};

  /** Hook features: width. */
  private float hook_width = 0.2f;

  /** Hook features: thickness. */
  private float hook_thickness = 0.02f;

  /** Reference frame. */
  private ColorFrame cf = new ColorFrame(1.0f);

  /** Reference frame display modality. */
  private boolean framep = false;

  /** Ropeway scene. */
  private RopewayScene rw = null;

  /** Display lists: ground object. */
  private int GROUND_DRAWING = 11;

  /** Display lists: barrel object. */
  private int BARREL_DRAWING = 12;

  /** Display lists: mast object. */
  private int MAST_DRAWING = 13;

  /** Display lists: jib object. */
  private int JIB_DRAWING = 14;

  /** Display lists: hook object. */
  private int HOOK_DRAWING = 15;

  /** Display lists: rope object. */
  private int ROPE_DRAWING = 16;

  /**
   * Returns the next animation step pose.
   *
   * @param pose Already allocated vector to fill in with new pose values.
   */
  protected abstract boolean nextStep(float[] pose);

  /**
   * Constructs the crane scene.
   *
   * @param type Mobile jib modality.
   * @param nw Wind direction status (north if true, west otherwise).
   */
  protected CraneScene(int type, boolean nw) {
    if (type == 0) mobile_jib = false;
    north_wind = nw;

    speedIni = 1.0f;
    speedMin = 0.01f;
    speedMax = 10.0f;
    speed = speedIni;
  }

  /**
   * Constructs the crane scene.
   *
   * @param xw Extension orientation.
   */
  protected void addRopeway(boolean xw) {
    rw = new RopewayScene(9, xw, new String[] {"t"});
  }

  /** Switches the wind on or off. */
  public void switchWind() {
    if (mobile_jib) windy = !windy;
  }

  /**
   * Gets the ropeway cabin viewpoint.
   *
   * @return the ropeway cabin viewpoint.
   */
  protected float[] getCabinViewpoint() {
    return (rw != null ? rw.getCabinViewpoint() : null);
  }

  /**
   * Initializes the crane scene.
   *
   * @param gl GL2 context.
   */
  public void init(GL2 gl) {
    Box soil = new Box(depth_2 * 2 + thickness_2 * 2, width_2 * 2 + thickness_2 * 2, thickness_2);
    gl.glNewList(GROUND_DRAWING, GL2.GL_COMPILE);
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.0f, -thickness_2);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, ground_specularity, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, ground_shininess, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, ground_ambiance, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, ground_diffusion, 0);
    soil.draw(gl);
    gl.glPopMatrix();
    gl.glEndList();

    gl.glNewList(BARREL_DRAWING, GL2.GL_COMPILE);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, barrel_specularity, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, barrel_shininess, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, barrel_ambiance, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, barrel_diffusion, 0);

    gl.glBegin(GL2.GL_TRIANGLE_STRIP);
    gl.glNormal3f(1.0f, 0.0f, 0.0f);
    gl.glVertex3f(barrel_radius, 0.0f, barrel_height);
    gl.glVertex3f(barrel_radius, 0.0f, 0.0f);
    for (int i = 1; i < barrel_resolution; i++) {
      double angle = 2 * i * Math.PI / barrel_resolution;
      float cx = (float) Math.cos(angle);
      float sx = (float) Math.sin(angle);
      gl.glNormal3f(cx, sx, 0.0f);
      gl.glVertex3f(cx * barrel_radius, sx * barrel_radius, barrel_height);
      gl.glVertex3f(cx * barrel_radius, sx * barrel_radius, 0.0f);
    }
    gl.glNormal3f(1.0f, 0.0f, 0.0f);
    gl.glVertex3f(barrel_radius, 0.0f, barrel_height);
    gl.glVertex3f(barrel_radius, 0.0f, 0.0f);
    gl.glEnd();

    gl.glBegin(GL2.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, 0.0f, 1.0f);
    gl.glVertex3f(0.0f, 0.0f, barrel_height);
    gl.glVertex3f(barrel_radius, 0.0f, barrel_height);
    for (int i = 1; i < barrel_resolution; i++) {
      double angle = 2 * i * Math.PI / barrel_resolution;
      gl.glVertex3f(
          (float) Math.cos(angle) * barrel_radius,
          (float) Math.sin(angle) * barrel_radius,
          barrel_height);
    }
    gl.glVertex3f(barrel_radius, 0.0f, barrel_height);
    gl.glEnd();

    gl.glBegin(GL2.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, 0.0f, -1.0f);
    gl.glVertex3f(0.0f, 0.0f, 0.0f);
    gl.glVertex3f(barrel_radius, 0.0f, 0.0f);
    for (int i = barrel_resolution - 1; i > 0; i--) {
      double angle = 2 * i * Math.PI / barrel_resolution;
      gl.glVertex3f(
          (float) Math.cos(angle) * barrel_radius, (float) Math.sin(angle) * barrel_radius, 0.0f);
    }
    gl.glVertex3f(barrel_radius, 0.0f, 0.0f);
    gl.glEnd();
    gl.glEndList();

    Box mast = new Box(mast_width, mast_width, mast_height, SolidPrimitive.REF_BASE);
    gl.glNewList(MAST_DRAWING, GL2.GL_COMPILE);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, crane_specularity, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, crane_shininess, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, crane_ambiance, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, crane_diffusion, 0);
    mast.draw(gl);
    gl.glTranslatef(0.0f, 0.0f, mast_height);
    gl.glEndList();

    Box jib = new Box(mast_width, jib_length, jib_thickness, SolidPrimitive.REF_CENTER);
    gl.glNewList(JIB_DRAWING, GL2.GL_COMPILE);
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, jib_shift, 0.0f);
    jib.draw(gl);
    gl.glPopMatrix();
    gl.glEndList();

    Box hook = new Box(hook_width, hook_width, hook_thickness, SolidPrimitive.REF_BASE);
    gl.glNewList(HOOK_DRAWING, GL2.GL_COMPILE);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, hook_specularity, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, hook_shininess, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, hook_ambiance, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, hook_diffusion, 0);
    hook.draw(gl);
    gl.glTranslatef(0.0f, 0.0f, hook_thickness);
    gl.glEndList();

    gl.glNewList(ROPE_DRAWING, GL2.GL_COMPILE);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rope_specularity, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, rope_shininess, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rope_ambiance, 0);
    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, rope_diffusion, 0);
    gl.glBegin(GL2.GL_TRIANGLE_STRIP);
    gl.glNormal3f(1.0f, 0.0f, 0.0f);
    gl.glVertex3f(rope_radius, 0.0f, 1.0f);
    gl.glVertex3f(rope_radius, 0.0f, 0.0f);
    for (int i = 0; i < rope_resolution; i++) {
      double angle = 2 * i * Math.PI / rope_resolution;
      float cx = (float) Math.cos(angle);
      float sx = (float) Math.sin(angle);
      gl.glNormal3f(cx, sx, 0.0f);
      gl.glVertex3f(cx * rope_radius, sx * rope_radius, 1.0f);
      gl.glVertex3f(cx * rope_radius, sx * rope_radius, 0.0f);
    }
    gl.glNormal3f(1.0f, 0.0f, 0.0f);
    gl.glVertex3f(rope_radius, 0.0f, 1.0f);
    gl.glVertex3f(rope_radius, 0.0f, 0.0f);
    gl.glEnd();
    gl.glEndList();

    if (rw != null) rw.init(gl);
  }

  /** Updates the crane scene. */
  public void update() {
    if (alive) {
      // Updates the mast rotation
      azimuth_speed += (float) ((Math.random() * azimuth_gear - azimuth_gear / 2));
      if (azimuth_speed > azimuth_max_speed) azimuth_speed = azimuth_max_speed;
      else if (azimuth_speed < -azimuth_max_speed) azimuth_speed = -azimuth_max_speed;
      mast_azimuth += azimuth_speed * speed;
      if (mast_azimuth > 360.0f) mast_azimuth -= 360.0f;
      else if (mast_azimuth < -360.0f) mast_azimuth += 360.0f;

      // Updates the wind rotation
      if (windy) {
        wind_speed += (float) ((Math.random() * wind_gear - wind_gear / 2));
        if (wind_speed > wind_max_speed) wind_speed = wind_max_speed;
        else if (wind_speed < -wind_max_speed) wind_speed = -wind_max_speed;
        mast_wind += wind_speed * speed;
        if (mast_wind > max_wind_angle) {
          mast_wind = max_wind_angle;
          wind_speed = 0.0f;
        } else if (mast_wind < 0.0f) {
          mast_wind = 0.0f;
          wind_speed = 0.0f;
        }
      }

      // Updates the jib rotation
      if (mobile_jib) {
        height_speed += (float) (Math.random() * height_gear - height_gear / 2);
        if (height_speed > height_max_speed) height_speed = height_max_speed;
        else if (height_speed < -height_max_speed) height_speed = -height_max_speed;
        jib_height += height_speed * speed;
        if (jib_height > max_height_angle) {
          jib_height = max_height_angle;
          height_speed = 0.0f;
        } else if (jib_height < min_height_angle) {
          jib_height = min_height_angle;
          height_speed = 0.0f;
        }
      }

      // Updates the rope position
      rope_shift_speed += (float) (Math.random() * rope_shift_gear - rope_shift_gear / 2);
      if (rope_shift_speed > rope_shift_max_speed) rope_shift_speed = rope_shift_max_speed;
      else if (rope_shift_speed < -rope_shift_max_speed) rope_shift_speed = -rope_shift_max_speed;
      rope_shift += rope_shift_speed * speed;
      if (rope_shift > rope_max_shift) {
        rope_shift = rope_max_shift;
        rope_shift_speed = 0.0f;
      } else if (rope_shift < rope_min_shift) {
        rope_shift = rope_min_shift;
        rope_shift_speed = 0.0f;
      }

      // Updates the rope length
      rope_length_speed += (float) (Math.random() * rope_length_gear - rope_length_gear / 2);
      if (rope_length_speed > rope_length_max_speed) rope_length_speed = rope_length_max_speed;
      else if (rope_length_speed < -rope_length_max_speed)
        rope_length_speed = -rope_length_max_speed;
      ropeLength += rope_length_speed * speed;
      if (ropeLength > rope_max_length) {
        ropeLength = rope_max_length;
        rope_length_speed = 0.0f;
      } else if (ropeLength < rope_min_length) {
        ropeLength = rope_min_length;
        rope_length_speed = 0.0f;
      }

      // Updates the barrel pose
      alive = nextStep(barrel_pose);
      for (int i = 0; i < 4; i++)
        for (int j = 0; j < 4; j++) barrel_aux[j * 4 + i] = barrel_pose[i * 4 + j];
    }
    if (rw != null) rw.update();
  }

  /**
   * Renders the crane scene.
   *
   * @param gl GL2 context.
   */
  public void draw(GL2 gl) {
    // Ropeway
    if (rw != null) {
      gl.glPushMatrix();
      rw.draw(gl);
      gl.glPopMatrix();
    }
    // Floor
    else if (framep) {
      gl.glDisable(GL2.GL_LIGHTING);
      gl.glPushMatrix();
      gl.glTranslatef(0.0f, 0.0f, 0.1f);
      cf.draw(gl);
      gl.glPopMatrix();
      gl.glEnable(GL2.GL_LIGHTING);
    }
    gl.glCallList(GROUND_DRAWING);

    // Barrel
    gl.glPushMatrix();
    gl.glMultMatrixf(barrel_aux, 0);
    gl.glCallList(BARREL_DRAWING);
    gl.glPopMatrix();

    // Mast
    gl.glTranslatef(mast_pos[0], mast_pos[1], 0.0f);
    if (windy)
      if (north_wind) gl.glRotatef(mast_wind, 0.0f, 1.0f, 0.0f);
      else gl.glRotatef(mast_wind, -1.0f, 0.0f, 0.0f);
    gl.glRotatef(mast_azimuth, 0.0f, 0.0f, 1.0f);
    gl.glCallList(MAST_DRAWING);

    // Jib
    gl.glRotatef(jib_height, 1.0f, 0.0f, 0.0f);
    gl.glCallList(JIB_DRAWING);

    // Hook
    gl.glTranslatef(0.0f, rope_shift, 0.0f);
    gl.glRotatef(-jib_height, 1.0f, 0.0f, 0.0f);
    if (windy) {
      gl.glRotatef(-mast_azimuth, 0.0f, 0.0f, 1.0f);
      if (north_wind) gl.glRotatef(-mast_wind, 0.0f, 1.0f, 0.0f);
      else gl.glRotatef(-mast_wind, -1.0f, 0.0f, 0.0f);
    }
    gl.glTranslatef(0.0f, 0.0f, -ropeLength - hook_thickness);
    gl.glCallList(HOOK_DRAWING);

    // Rope
    gl.glScalef(1.0f, 1.0f, ropeLength);
    gl.glCallList(ROPE_DRAWING);
  }

  /** Switches on or off frame display modality. */
  protected void switchFrame() {
    framep = !framep;
  }

  /**
   * Gets the crane mast position in the scene.
   *
   * @return the crane mast position in the scene.
   */
  protected float[] cranePos() {
    return (mast_pos);
  }

  /**
   * Gets the current mast angle value.
   *
   * @return the current mast angle value.
   */
  protected float mastAngle() {
    return (mast_azimuth);
  }

  /**
   * Gets the current wind angle value.
   *
   * @return the current wind angle value.
   */
  protected float windAngle() {
    return (mast_wind);
  }

  /**
   * Gets the mast height.
   *
   * @return the mast height.
   */
  protected float mastHeight() {
    return (mast_height);
  }

  /**
   * Gets the mast width.
   *
   * @return the mast width.
   */
  protected float mastWidth() {
    return (mast_width);
  }

  /**
   * Gets the current jib angle value.
   *
   * @return the current jib angle value.
   */
  protected float jibAngle() {
    return (jib_height);
  }

  /**
   * Gets the jib full length.
   *
   * @return the jib full length.
   */
  protected float jibLength() {
    return (jib_length);
  }

  /**
   * Gets the jib center distance to the mast.
   *
   * @return the jib center distance to the mast.
   */
  protected float jibShift() {
    return (jib_shift);
  }

  /**
   * Gets the current rope radius value.
   *
   * @return the current rope radius value.
   */
  protected float ropeRadius() {
    return (rope_radius);
  }

  /**
   * Gets the current rope distance value.
   *
   * @return the current rope distance value.
   */
  protected float ropeShift() {
    return (rope_shift);
  }

  /**
   * Gets the current rope length.
   *
   * @return the current rope length.
   */
  protected float ropeLength() {
    return (ropeLength);
  }

  /**
   * Gets the captain age.
   *
   * @return the captain age.
   */
  protected int captainAge() {
    return (77);
  }

  /**
   * Gets the hook width.
   *
   * @return the hook width.
   */
  protected float hookWidth() {
    return (hook_width);
  }

  /**
   * Gets the hook thickness.
   *
   * @return the hook thickness.
   */
  protected float hookThickness() {
    return (hook_thickness);
  }

  /**
   * Gets the barrel height.
   *
   * @return the barrel height.
   */
  protected float barrelHeight() {
    return (barrel_height);
  }

  /**
   * Gets the barrel diameter.
   *
   * @return the barrel diameter.
   */
  protected float barrelDiameter() {
    return (barrel_radius * 2);
  }
}
