// Trame du TP sur les traces de base
// BUT Info - 2023/2024
// Prepare par Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)


/** Pixel de la grille (solution naive basee-equation pour les segments).
 */
public class PixelEq extends Pixel
{
  /** Petit valeur pour les tests d'egalite de reels. */
  public final static double EPS = 0.0001;

  /** Cree un pixel a partir de ses coordonnees.
   * @param x abscisse du pixel.
   * @param y ordonnee du pixel.
   */
  public PixelEq (int x, int y)
  {
    super (x, y);
  }

  /** Cree un pixel a partir d'un autre.
   * @param p l'autre.
   */
  public PixelEq (Pixel p)
  {
    super (p.x, p.y);
  }

  /** Retourne les pixels du segment joignant le pixel a un autre.
   * @param p Le pixel d'arrivee.
   */
  public Pixel[] tracerSegment (Pixel p)
  {
    int dx = p.x () - x ();
    int dy = p.y () - y ();
    int adx = (dx > 0 ? dx : -dx);
    int ady = (dy > 0 ? dy : -dy);
    Pixel[] pts = new Pixel[adx > ady ? adx + 1 : ady + 1];

    if (adx < ady)
    {
      int y1 = (dy < 0 ? p.y () : y ());
      int x1 = (dy < 0 ? p.x () : x ());
      if (dy < 0) dx = -dx;
      dx *= 2;
      dy = ady * 2;
      int c = ady + dy * x1 - dx * y1;
      for (int i = 0; i < ady + 1; i ++)
        pts[i] = new Pixel ((dx * (y1 + i) + c) / dy, y1 + i);
    }
    else
    {
      int x1 = (dx < 0 ? p.x () : x ());
      int y1 = (dx < 0 ? p.y () : y ());
      if (dx < 0) dy = -dy;
      dy *= 2;
      dx = 2 * adx;
      int c = adx + dx * y1 - dy * x1;
      for (int i = 0; i < adx + 1; i ++)
        pts[i] = new Pixel (x1 + i, (dy * (x1 + i) + c) / dx);
    }
    return (pts);
  }

  /** Retourne les pixels du segment joignant le pixel a un autre.
   * @param p Le pixel d'arrivee.
   * @param eq Equilibrage du trace (0 = equilibre, -1 = inf, 1 = sup).
   */
  public Pixel[] tracerSegment (Pixel p, int eq)
  {
    int dx = p.x () - x ();
    int dy = p.y () - y ();
    int idx = dx, idy = dy;
    int adx = (dx > 0 ? dx : -dx);
    int ady = (dy > 0 ? dy : -dy);
    Pixel[] pts = new Pixel[adx > ady ? adx + 1 : ady + 1];
    int valc = idy * x () - idx * y ();

    if (adx < ady)
    {
      int y1 = (dy < 0 ? p.y () : y ());
      int x1 = (dy < 0 ? p.x () : x ());
      if (dy < 0) dx = -dx;
      dx *= 2;
      dy = ady * 2;
      int c = ady + dy * x1 - dx * y1;
      for (int i = 0; i < ady + 1; i ++)
        if (eq == 0)
          pts[i] = new Pixel ((dx * (y1 + i) + c) / dy, y1 + i);
        else
        {
          double posx = (valc + idx * (y1 + i)) / (double) idy;
          if (eq == 1)
            pts[i] = new Pixel (1 + (int) (posx - EPS), y1 + i);
          else // if (eq == -1)
            pts[i] = new Pixel ((int) (posx + EPS), y1 + i);
        }
    }
    else
    {
      int x1 = (dx < 0 ? p.x () : x ());
      int y1 = (dx < 0 ? p.y () : y ());
      if (dx < 0) dy = -dy;
      dy *= 2;
      dx = 2 * adx;
      int c = adx + dx * y1 - dy * x1;
      for (int i = 0; i < adx + 1; i ++)
        if (eq == 0)
          pts[i] = new Pixel (x1 + i, (dy * (x1 + i) + c) / dx);
        else
        {
          double posy = (idy * (x1 + i) - valc) / (double) idx;
          if (eq == 1)
            pts[i] = new Pixel (x1 + i, 1 + (int) (posy - EPS));
          else // if (eq == -1)
            pts[i] = new Pixel (x1 + i, (int) (posy + EPS));
        }
    }
    return (pts);
  }
}
