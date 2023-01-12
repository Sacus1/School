import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class CoupsView extends JLabel implements Observer {

  public CoupsView(String s, int center) {
    super(s, center);
  }

  /**
   * This method is called whenever the observed object is changed. An application calls an {@code
   * Observable} object's {@code notifyObservers} method to have all the object's observers notified
   * of the change.
   *
   * @param o the observable object.
   * @param arg an argument passed to the {@code notifyObservers} method.
   */
  @Override
  public void update(Observable o, Object arg) {
    if (!(o instanceof Model m)) return;
    if (m.fini) {
      setText("Vous avez gagné en " + m.nCoup + " coups");
    } else {
      setText("Nombre de coups joués : " + m.nCoup);
    }
  }
}
