// Trame du TP sur les traces 2D
// BUT Info - 2023/2024
// Prepare par Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)


/** Pixel utilisant des algos naifs pour les cercles.
 */
public class PixelCercle extends Pixel
{
  /** Cree un pixel a partir de ses coordonnees.
   * @param x abscisse du pixel.
   * @param y ordonnee du pixel.
   */
  public PixelCercle (int x, int y)
  {
    super (x, y);
  }

  /** Cree un pixel par recopie d'un autre.
   * @param p l'autre.
   */
  public PixelCercle (Pixel p)
  {
    this (p.x, p.y);
  }

  /** Retourne les pixels du cercle dont ce pixel est le centre.
   * @param rayon rayon du cercle.
   */
  public Pixel[] tracerCercle (int rayon)
  {
    int[] forme = new int[rayon];
    int n = 0;
    int cx = 1;
    int cy = (int) (Math.sqrt (rayon * rayon - cx * cx) + 0.5);
    while (cy > cx)
    {
      forme[n++] = cy;
      cx ++;
      cy = (int) (Math.sqrt (rayon * rayon - cx * cx) + 0.5);
    }
    int nb = 4 + n * 8;
    if (cx == cy && cy != forme[n-1]) nb += 4;

    Pixel pts[] = new Pixel[nb];
    pts[0] = new Pixel (x + rayon, y);
    pts[1] = new Pixel (x, y + rayon);
    pts[2] = new Pixel (x - rayon, y);
    pts[3] = new Pixel (x, y - rayon);
    for (int i = 0; i < n; i++)
    {
      pts[4 + i * 8] = new Pixel (x + i + 1, y + forme[i]);
      pts[5 + i * 8] = new Pixel (x + i + 1, y - forme[i]);
      pts[6 + i * 8] = new Pixel (x - i - 1, y + forme[i]);
      pts[7 + i * 8] = new Pixel (x - i - 1, y - forme[i]);
      pts[8 + i * 8] = new Pixel (x + forme[i], y + i + 1);
      pts[9 + i * 8] = new Pixel (x - forme[i], y + i + 1);
      pts[10 + i * 8] = new Pixel (x + forme[i], y - i - 1);
      pts[11 + i * 8] = new Pixel (x - forme[i], y - i - 1);
    }
    if (nb % 8 == 0)
    {
      pts[nb - 4] = new Pixel (x + cx, y + cy);
      pts[nb - 3] = new Pixel (x + cx, y - cy);
      pts[nb - 2] = new Pixel (x - cx, y - cy);
      pts[nb - 1] = new Pixel (x - cx, y + cy);
    }

    return (pts);
  }
}
