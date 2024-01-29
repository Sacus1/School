// Trame des TP d'IN4 (R5.15) : algebre et modelisation geometrique
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;


/** Scene viewing control. */
public class Observer
{
  /** Observer motion control: right turn enabled. */
  public final static int TURN_RIGHT = 1;
  /** Observer motion control: left turn enabled. */
  public final static int TURN_LEFT = 2;
  /** Observer motion control: right/left turn disabled. */
  public final static int NO_RIGHT_LEFT_TURN = 3;
  /** Observer motion control: up move enabled. */
  public final static int TURN_UP = 4;
  /** Observer motion control: down move enabled. */
  public final static int TURN_DOWN = 5;
  /** Observer motion control: up/down move disabled. */
  public final static int NO_UP_DOWN_TURN = 6;
  /** Observer motion control: front move enabled. */
  public final static int MOVE_FRONT = 7;
  /** Observer motion control: back move enabled. */
  public final static int MOVE_BACK = 8;
  /** Observer motion control: front/back move disabled. */
  public final static int NO_FRONT_BACK_MOVE = 9;
  /** Observer motion control: right move enabled. */
  public final static int MOVE_RIGHT = 10;
  /** Observer motion control: left move enabled. */
  public final static int MOVE_LEFT = 11;
  /** Observer motion control: right/left move disabled. */
  public final static int NO_RIGHT_LEFT_MOVE = 12;
  /** Observer motion control: up move enabled. */
  public final static int MOVE_UP = 13;
  /** Observer motion control: down move enabled. */
  public final static int MOVE_DOWN = 14;
  /** Observer motion control: up/down move disabled. */
  public final static int NO_UP_DOWN_MOVE = 15;

  /** View matrix to be applied (transposed). */
  public float[] transView = new float[16];

  /** Perspective projection on (orthographic when set to off). */
  private boolean perspectiveOn = true;

  /** Projection modification to be processed. */
  private boolean projectionChanged = false;
  /** Azimuth angle modification. */
  private int azimuthMobility = 0;
  /** Height angle modification. */
  private int heightMobility = 0;
  /** Front move modification. */
  private int frontMobility = 0;
  /** Side move modification. */
  private int rightMobility = 0;
  /** Height move modification. */
  private int upMobility = 0;

  /** Perspective angle (used in perspective mode). */
  private static final double DEFAULT_PERSPECTIVE_ANGLE = 60.0;
  /** Perspective projection angle increment. */
  private static final double PERSPECTIVE_INC = 1.0;
  /** Minimal perspective projection angle. */
  private static final double PERSPECTIVE_MIN = 1.0;
  /** Maximal perspective projection angle. */
  private static final double PERSPECTIVE_MAX = 150.0;
  /** Default perspective projection angle. */
  private double perspectiveAngle = DEFAULT_PERSPECTIVE_ANGLE;

  /** Azimuth angle (site) (used in perspective mode). */
  private static final float DEFAULT_AZIMUTH_ANGLE = 0.0f;
  /** Azimuth angle increment. */
  private static final float AZIMUTH_ANGLE_INC = 1.0f;
  /** Default azimuth angle. */
  private float azimuthAngle = DEFAULT_AZIMUTH_ANGLE;

  /** Height angle (used in perspective mode). */
  private static final float DEFAULT_HEIGHT_ANGLE = -20.0f;
  /** Azimuth height increment. */
  private static final float HEIGHT_ANGLE_INC = 1.0f;
  /** Default height angle. */
  private float heightAngle = DEFAULT_HEIGHT_ANGLE;

  /** Projection center (used in perspective mode) */
  private float[] defaultCenter = {0.0f, 7.0f, 0.0f};
  /** Current perspective center. */
  private float[] center = {0.0f, 0.0f, 0.0f};
  /** Center translation increment. */
  private static final float TRANSLATION_INC = 0.1f;
  /** Center translation minimal X value. */
  private float centerXmin = -1.0f;
  /** Center translation maximal X value. */
  private float centerXmax = 1.0f;
  /** Center translation minimal Y value. */
  private float centerYmin = -1.0f;
  /** Center translation maximal Y value. */
  private float centerYmax = 1.0f;
  /** Center translation minimal Z value. */
  private float centerZmin = -1.0f;
  /** Center translation maximal Z value. */
  private float centerZmax = 1.0f;

  /** Viewport size. */
  private int vpWidth = 100, vpHeight = 100;

  /** Default size of the displayed scene (used in orthographic mode). */
  private double defaultSceneSize = 2.0f;
  /** Current size of the displayed scene. */
  private double sceneSize = defaultSceneSize;
  /** Scene size increment. */
  private static final double SCENE_SIZE_INC = 1.01;
  /** Scene side size minimal value. */
  private static final double SCENE_SIZE_MIN_RATIO = 0.1;
  /** Scene side size maximal value. */
  private static final double SCENE_SIZE_MAX_RATIO = 1.5;
  /** Scene depth size minimal value. */
  private double zMin = 0.0f;
  /** Scene depth size maximal value. */
  private double zMax = 1.0f;

  /** Default focus point (used in orthographic mode). */
  private float[] defaultFocus = {0.0f, 0.0f};
  /** Current focus point. */
  private float[] focus = {0.0f, 0.0f};
  /** Focus increment. */
  private static final float FOCUS_INC = 0.05f;

  /** Access to GLU functions. */
  private GLU glu = new GLU ();


  /** Resets the projection matrix according to new viewport and
   * projection angle values.
   * @param gl OpenGL2 context.
   * @param width viewport width.
   * @param height viewport height.
   */ 
  public void setProjection (GL2 gl, int width, int height)
  {
    vpWidth = width;
    vpHeight = height;
    setProjection (gl);
  }

  /** Resets the projection matrix according to new projection angle.
   * @param gl OpenGL2 context.
   */ 
  public void setProjection (GL2 gl)
  {
    gl.glMatrixMode (GL2.GL_PROJECTION);
    gl.glLoadIdentity ();
    if (perspectiveOn)
      glu.gluPerspective (perspectiveAngle, (double) vpWidth / vpHeight,
                          0.1, 20.0);
    else
      gl.glOrtho (- sceneSize * vpWidth / (2 * vpHeight),
                  sceneSize * vpWidth / (2 * vpHeight),
                  - sceneSize / 2, sceneSize / 2, zMin, zMax);
    gl.glMatrixMode (GL2.GL_MODELVIEW);
  }

  /** Sets the projection and viewing matrices before diplaying a scene.
   * @param gl GL2 context
   * @param view Viewing matrix to be immediately applied (when provided)
   */
  public void apply (GL2 gl, float[] view)
  {
    // Restore current projection
    if (projectionChanged)
    {
      setProjection (gl);
      projectionChanged = false;
    }

    // Reset The View
    gl.glLoadIdentity ();

    if (view != null)
    {
      for (int i = 0; i < 4; i ++)
        for (int j = 0; j < 4; j ++)
          transView[i*4+j] = view[j*4+i];
      gl.glMultMatrixf (transView, 0);
    }

    else if (perspectiveOn)
    {
      boolean moved = false;

      // Update azimuth and height angles
      if (azimuthMobility != 0)
      {
        azimuthAngle += AZIMUTH_ANGLE_INC * azimuthMobility;
        if (azimuthAngle > 180.0f) azimuthAngle -= 360.0f;
        else if (azimuthAngle < -180.0f) azimuthAngle += 360.0f;
      }
      if (heightMobility != 0)
      {
        heightAngle += HEIGHT_ANGLE_INC * heightMobility;
        if (heightAngle > 90.0f) heightAngle = 90.0f;
        else if (heightAngle < -90.0f) heightAngle = -90.0f;
      }

      // Process front/back motions
      if (frontMobility != 0)
      {
        float cb = TRANSLATION_INC * frontMobility
                     * (float) Math.cos (heightAngle * Math.PI / 180);
        center[0] -= cb * (float) Math.cos (azimuthAngle * Math.PI / 180);
        center[1] -= cb * (float) Math.sin (azimuthAngle * Math.PI / 180); 
        center[2] += TRANSLATION_INC * frontMobility
                     * (float) Math.sin (heightAngle * Math.PI / 180);
        moved = true;
      }

      // Process right/left motions
      if (rightMobility != 0)
      {
        center[0] -= TRANSLATION_INC * rightMobility
                     * (float) Math.sin (azimuthAngle * Math.PI / 180);
        center[1] += TRANSLATION_INC * rightMobility
                     * (float) Math.cos (azimuthAngle * Math.PI / 180);
        moved = true;
      }
 
      // Process up/down motions
      if (upMobility != 0)
      {
        float sa = TRANSLATION_INC * upMobility
                   * (float) Math.sin (heightAngle * Math.PI / 180);
        center[0] += sa * (float) Math.cos (azimuthAngle * Math.PI / 180);
        center[1] += sa * (float) Math.sin (azimuthAngle * Math.PI / 180); 
        center[2] += TRANSLATION_INC * upMobility
                     * (float) Math.cos (heightAngle * Math.PI / 180);
        moved = true;
      }

      // Threshold the projection center within the accessibility volume
      if (moved)
      {
        if (center[0] > centerXmax) center[0] = centerXmax;
        else if (center[0] < centerXmin) center[0] = centerXmin;
        if (center[1] > centerYmax) center[1] = centerYmax;
        else if (center[1] < centerYmin) center[1] = centerYmin;
        if (center[2] > centerZmax) center[2] = centerZmax;
        else if (center[2] < centerZmin) center[2] = centerZmin;
      }
    
      // Comes to the observation point,
      // then looks at -X direction, with Y direction on the right,
      // finally apply azimuth and height angles.
      gl.glRotatef (- heightAngle, 1.0f, 0.0f, 0.0f);
      gl.glRotatef (- azimuthAngle, 0.0f, 1.0f, 0.0f);
      gl.glRotatef (- 90.0f, 1.0f, 0.0f, 0.0f);
      gl.glRotatef (- 90.0f, 0.0f, 0.0f, 1.0f);
      gl.glTranslatef (- center[0], - center[1], - center[2]);
    }

    else // orthographic projection
    {
      // Update and threshold focus point
      if (azimuthMobility != 0)
      {
        focus[1] += FOCUS_INC * azimuthMobility;
	if (focus[1] > centerYmax) focus[1] = centerYmax;
	else if (focus[1] < centerYmin) focus[1] = centerYmin;
      }
      if (heightMobility != 0)
      {
        focus[0] += FOCUS_INC * heightMobility;
	if (focus[0] > centerXmax) focus[0] = centerXmax;
	else if (focus[0] < centerXmin) focus[0] = centerXmin;
      }

      // Comes to the focus and looks downwards
      gl.glTranslatef (focus[1], focus[0], 0.0f);
      gl.glRotatef (-90.0f, 0.0f, 0.0f, 1.0f);
    }
  }

  /** Toggles the projection type (perspective / orthographic).
   */ 
  public void toggleProjection ()
  {
    perspectiveOn = ! perspectiveOn;
    projectionChanged = true; // to be processed at nex display
  }

  /** Narrows the projection field of view.
   */
  public void zoomIn ()
  {
    if (perspectiveOn)
    {
      perspectiveAngle -= PERSPECTIVE_INC;
      if (perspectiveAngle < PERSPECTIVE_MIN)
        perspectiveAngle = PERSPECTIVE_MIN;
    }
    else // orthographic
    {
      sceneSize *= SCENE_SIZE_INC;
      if (sceneSize > defaultSceneSize * SCENE_SIZE_MAX_RATIO)
        sceneSize = defaultSceneSize * SCENE_SIZE_MAX_RATIO;
    }
    projectionChanged = true; // to be processed at nex display
  }

  /** Enlarges the projection field of view.
   */
  public void zoomOut ()
  {
    if (perspectiveOn)
    {
      perspectiveAngle += PERSPECTIVE_INC;
      if (perspectiveAngle > PERSPECTIVE_MAX)
        perspectiveAngle = PERSPECTIVE_MAX;
    }
    else
    {
      sceneSize /= SCENE_SIZE_INC;
      if (sceneSize < defaultSceneSize * SCENE_SIZE_MIN_RATIO)
        sceneSize = defaultSceneSize * SCENE_SIZE_MIN_RATIO;
    }
    projectionChanged = true; // to be processed at nex display
  }

  /** Controls the observer position and orientation.
   * @param control motion type:
   *	- TURN_RIGHT, TURN_LEFT, NO_RIGHT_LEFT_TURN,
   *	- TURN_UP, TURN_DOWN, NO_UP_DOWN_TURN,
   *	- MOVE_FRONT, MOVE_BACK, NO_FRONT_BACK_MOVE,
   *	- MOVE_RIGHT, MOVE_LEFT, NO_RIGHT_LEFT_MOVE,
   *	- MOVE_UP, MOVE_DOWN, NO_UP_DOWN_MOVE
   */	
  public void move (int control)
  {
    switch (control)
    {
      case TURN_RIGHT : azimuthMobility = -1; break;
      case TURN_LEFT : azimuthMobility = 1; break;
      case NO_RIGHT_LEFT_TURN : azimuthMobility = 0; break;
      case TURN_UP : heightMobility = 1; break;
      case TURN_DOWN : heightMobility = -1; break;
      case NO_UP_DOWN_TURN : heightMobility = 0; break;
      case MOVE_FRONT : frontMobility = 1; break;
      case MOVE_BACK : frontMobility = -1; break;
      case NO_FRONT_BACK_MOVE : frontMobility = 0; break;
      case MOVE_RIGHT : rightMobility = 1; break;
      case MOVE_LEFT : rightMobility = -1; break;
      case NO_RIGHT_LEFT_MOVE : rightMobility = 0; break;
      case MOVE_UP : upMobility = 1; break;
      case MOVE_DOWN : upMobility = -1; break;
      case NO_UP_DOWN_MOVE : upMobility = 0; break;
    }
  }

  /** Resets current projection with default parameters.
   */
  public void resetProjection ()
  {
    if (perspectiveOn)
    {
      perspectiveAngle = DEFAULT_PERSPECTIVE_ANGLE;
      azimuthAngle = DEFAULT_AZIMUTH_ANGLE;
      heightAngle = DEFAULT_HEIGHT_ANGLE;
      center[0] = defaultCenter[0];
      center[1] = defaultCenter[1];
      center[2] = defaultCenter[2];
    }
    else // orthographic
    {
      sceneSize = defaultSceneSize;
      focus[0] = defaultFocus[0];
      focus[1] = defaultFocus[1];
    }
    projectionChanged = true; // to be processed at next display
  }

  /** Sets the current projection center in the scene.
   * @param vp Coordinates of the projection center.
   */
  public void setCenter (float[] vp)
  {
    vp[0] = center[0];
    vp[1] = center[1];
    vp[2] = center[2];
  }

  /** Sets the orthographic projection parameters (default and bounds).
   * @param volume Default volume to display
   *   (float array containing xmin, xmax, ymin, ymax, zmin the zmax)
   */ 
  public void setViewingVolume (float[] volume)
  {
    // The default size displays the given volume
    float xSize = volume[1] - volume[0];
    float ySize = volume[3] - volume[2];
    if (xSize < 0.0f) xSize = - xSize;
    if (ySize < 0.0f) ySize = - ySize;
    defaultSceneSize = (xSize > ySize ? (double) xSize : (double) ySize);
    sceneSize = defaultSceneSize;
    zMin = - (double) volume[5];
    zMax = - (double) volume[4];

    // The default focus point is the center of the volume.
    defaultFocus[0] = (volume[1] + volume[0]) / 2;
    defaultFocus[1] = (volume[3] + volume[2]) / 2;
    focus[0] = defaultFocus[0];
    focus[1] = defaultFocus[1];
  }

  /** Sets the observer bounds.
   * @param volume Accessible volume bounds.
   *   (float array containing xmin, xmax, ymin, ymax, zmin the zmax)
   */ 
  public void setAccessibleVolume (float[] volume)
  {
    centerXmin = volume[0];
    centerXmax = volume[1];
    centerYmin = volume[2];
    centerYmax = volume[3];
    centerZmin = volume[4];
    centerZmax = volume[5]; }

  /** Sets the projection center to default position.
   * @param pos Default projection center coordinates.
   */ 
  public void setDefaultPosition (float[] pos)
  {
    defaultCenter[0] = pos[0];
    defaultCenter[1] = pos[1];
    defaultCenter[2] = pos[2];
    center[0] = pos[0];
    center[1] = pos[1];
    center[2] = pos[2];
  }
}
