// Trame des TP d'IN4 (R5.15) : transformations geometriques
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Event handler for OpenGL training. */
public class ExoController implements KeyListener {
  /** Controlled display area. */
  private GLCanvas canvas;

  /** Controlled OpenGL context. */
  private ExoView myView;

  /** Background task. */
  private FPSAnimator animator = null;

  /**
   * Constructs a event handler.
   *
   * @param canvas display area.
   * @param myView OpenGL context.
   */
  public ExoController(GLCanvas canvas, ExoView myView) {
    this.myView = myView;
    this.canvas = canvas;
    animator = new FPSAnimator(canvas, 60, true);
    // Deprec     animator.setRunAsFastAsPossible (false);
    animator.start();
  }

  /**
   * Invoked when a key has been pressed. Implementation from KeyListener.
   *
   * @param e detected key event.
   */
  public void keyPressed(KeyEvent e) {
    processKeyEvent(e, true);
  }

  /**
   * Invoked when a key has been released. Implementation from KeyListener.
   *
   * @param e detected key event.
   */
  public void keyReleased(KeyEvent e) {
    processKeyEvent(e, false);
  }

  /**
   * Invoked when a key has been pressed or released. Local implementation from KeyListener.
   *
   * @param e detected key event.
   * @param pressed pressed or released key status.
   */
  private void processKeyEvent(KeyEvent e, boolean pressed) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_Q:
      case KeyEvent.VK_ESCAPE:
        if (!pressed) System.exit(0);
        break;

      case KeyEvent.VK_SPACE:
        if (!pressed) myView.toggleAnimation();
        break;

      case KeyEvent.VK_HOME:
        if (!pressed) myView.observer().resetProjection();
        break;

      case KeyEvent.VK_UP:
        if (pressed)
          if (e.isControlDown()) myView.observer().move(Observer.MOVE_FRONT);
          else myView.observer().move(Observer.TURN_UP);
        else {
          myView.observer().move(Observer.NO_FRONT_BACK_MOVE);
          myView.observer().move(Observer.NO_UP_DOWN_TURN);
        }
        break;

      case KeyEvent.VK_DOWN:
        if (pressed)
          if (e.isControlDown()) myView.observer().move(Observer.MOVE_BACK);
          else myView.observer().move(Observer.TURN_DOWN);
        else {
          myView.observer().move(Observer.NO_FRONT_BACK_MOVE);
          myView.observer().move(Observer.NO_UP_DOWN_TURN);
        }
        break;

      case KeyEvent.VK_LEFT:
        if (pressed)
          if (e.isControlDown()) myView.observer().move(Observer.MOVE_LEFT);
          else myView.observer().move(Observer.TURN_LEFT);
        else {
          myView.observer().move(Observer.NO_RIGHT_LEFT_MOVE);
          myView.observer().move(Observer.NO_RIGHT_LEFT_TURN);
        }
        break;

      case KeyEvent.VK_RIGHT:
        if (pressed)
          if (e.isControlDown()) myView.observer().move(Observer.MOVE_RIGHT);
          else myView.observer().move(Observer.TURN_RIGHT);
        else {
          myView.observer().move(Observer.NO_RIGHT_LEFT_MOVE);
          myView.observer().move(Observer.NO_RIGHT_LEFT_TURN);
        }
        break;

      case KeyEvent.VK_PAGE_UP:
        if (pressed) myView.observer().move(Observer.MOVE_UP);
        else myView.observer().move(Observer.NO_UP_DOWN_MOVE);
        break;

      case KeyEvent.VK_PAGE_DOWN:
        if (pressed) myView.observer().move(Observer.MOVE_DOWN);
        else myView.observer().move(Observer.NO_UP_DOWN_MOVE);
        break;
    }
  }

  /**
   * Invoked when a key has been typed. Implementation from KeyListener.
   *
   * @param e detected key event.
   */
  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()) {
      case 'f':
        myView.scene().switchFrame();
        break;
      case 'z':
        myView.observer().zoomIn();
        break;
      case 'Z':
        myView.observer().zoomOut();
        break;
      case '+':
        myView.gear(1);
        break;
      case '-':
        myView.gear(-1);
        break;
      case '0':
        myView.gear(0);
        break;
    }
  }
}
