// Trame des TP d'IN4 (R5.15) : algebre et modelisation geometrique
// BUT Info - 2023/2024
// Prepare par Ph. Even, Universite de Lorraine / IUT de Saint-Die

import java.awt.Frame;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.awt.GLCanvas;
//Deprec import javax.media.opengl.GLCapabilities;


/** Main window for training. */
public class ExoFrame extends Frame
{
  /** Creates a AWT Frame with OpenGL context.
   * @param num Training identifier.
   * @param scene Scene to be displayed.
   */
  public ExoFrame (int num, ExoScene scene)
  {
    // AWT window creation
    super ("Chaines et cameras - Exo " + num);

    // OpenGL display area creation
//Deprec     GLCapabilities capabilities = new GLCapabilities ();
//Deprec     capabilities.setDoubleBuffered (true); 
    GLCanvas canvas = new GLCanvas ();

    // Adding a OpenGL context (view)
    ExoView myView = new ExoView (scene);
    canvas.addGLEventListener (myView);
    // Adding a user event handler (controller)
    ExoController myController = new ExoController (canvas, myView);
    canvas.addKeyListener (myController);
    // canvas.addMouseListener (myController);
    // canvas.addMouseMotionListener (myController);

    // Window closing behavior
    addWindowListener (
      new WindowAdapter ()
      {
        public void windowClosing (WindowEvent e)
        {
          System.exit (0);
        }
      });

    // End of window specification
    add (canvas);
    setSize (600, 600);
    setLocation (0, 0);
    setBackground (Color.white);
    setVisible (true);

    // Start of OpenGL loop
    canvas.requestFocus ();
  }
}
