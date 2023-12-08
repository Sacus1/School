// Trame des TP d'IN4 (R5.15) : transformations geometriques
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.GL2;

/** 3D scene for training. */
public abstract class ExoScene {
  /** Animation status. */
  protected boolean alive = false;

  /** Initial speed value. */
  protected float speedIni = 0.1f;

  /** Minimal speed value. */
  protected float speedMin = 0.001f;

  /** Maximal speed value. */
  protected float speedMax = 1.0f;

  /** Speed increment. */
  protected float speedInc = 1.1f;

  /** Current speed value. */
  protected float speed = speedIni;

  /** Scene viewing handler. */
  protected Observer obs = null;

  /**
   * Returns the position at next animation step.
   *
   * @param pose Already allocated vector to fill in with new pose values.
   * @return the animation status: on if true, off otherwise.
   */
  protected abstract boolean nextStep(float[] pose);

  /**
   * Initializes the scene.
   *
   * @param gl GL2 context.
   */
  public abstract void init(GL2 gl);

  /** Updates the scene. */
  public abstract void update();

  /**
   * Renders the scene.
   *
   * @param gl GL2 context.
   */
  public abstract void draw(GL2 gl);

  /** Switches on or off frame display modality. */
  protected abstract void switchFrame();

  /**
   * Sets the scene observer handler.
   *
   * @param obs Observer handler to be associated to the 3D scene.
   */
  public void setObserver(Observer obs) {
    this.obs = obs;
  }

  /**
   * Returns the visible volume.
   *
   * @return the visible volume.
   */
  public float[] defaultVisibleVolume() {
    return (new float[] {-8.1f, 8.1f, -6.1f, 6.1f, -0.1f, 4.1f});
  }

  /**
   * Returns the volume the observer is allowed to walk through.
   *
   * @return the volume the observer is allowed to walk through.
   */
  public float[] accessibleVolume() {
    return (new float[] {-8.0f, 8.0f, -6.0f, 6.0f, 0.1f, 4.0f});
  }

  /**
   * Provides a relevant observer position in the hall.
   *
   * @return a relevant observer position in the hall.
   */
  public float[] viewPoint() {
    return (new float[] {6.0f, 0.0f, 4.0f});
  }

  /**
   * Sets the current projection center.
   *
   * @param vp The new projection center.
   */
  public void setCurrentViewPoint(float[] vp) {
    obs.setCenter(vp);
  }

  /** Switch on or off the shuttle motion. */
  public void toggleAnimation() {
    alive = !alive;
  }

  /**
   * Speeds up or down the animation.
   *
   * @param val Acceleration value to be applied.
   */
  public void gear(int val) {
    if (val == 0) speed = speedIni;
    else if (val > 0) {
      if (speed < speedMax) speed *= speedInc;
    } else if (speed > speedMin) speed /= speedInc;
  }

  /**
   * Returns a viewing matrix for immediate setting.
   *
   * @return a viewing matrix for immediate setting.
   */
  public float[] setView() {
    return (null);
  }
}
