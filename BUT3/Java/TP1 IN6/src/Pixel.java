// Trame du TP sur les traces de base
// BUT Info - 2023/2024
// Prepare par Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)
// Complete par X


import java.util.ArrayList;

/**
 * Pixel de la grille.
 */
public class Pixel {
	/**
	 * Abscisse du pixel.
	 */
	protected int x;
	/**
	 * Ordonnee du pixel.
	 */
	protected int y;


	/**
	 * Cree un pixel a partir de ses coordonnees.
	 *
	 * @param x abscisse du pixel.
	 * @param y ordonnee du pixel.
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Fournit l'abscisse du pixel.
	 *
	 * @return L'abscisse du pixel.
	 */
	public int x() {
		return (x);
	}

	/**
	 * Fournit l'ordonnee du pixel.
	 *
	 * @return L'ordonnee du pixel.
	 */
	public int y() {
		return (y);
	}

	/**
	 * Fournit les pixels du segment joignant le pixel a un autre.
	 *
	 * @param p Le pixel d'arrivee.
	 * @return La sequence de pixels du segment.
	 */
	public Pixel[] tracerSegment(Pixel p) {
		int differenceX = Math.abs(p.x() - x());
		int differenceY = Math.abs(p.y() - y());
		int stepX = (this.x < p.x()) ? 1 : -1;
		int stepY = (this.y < p.y()) ? 1 : -1;
		int currentX = this.x;
		int currentY = this.y;
		int k = (differenceX > differenceY ? differenceX : -differenceY) / 2;
		int tempK;
		ArrayList<Pixel> pixelPoints = new ArrayList<>();
		while ((stepX > 0 && currentX < p.x()) || (stepX < 0 && currentX > p.x()) || (stepY > 0 && currentY < p.y()) || (stepY < 0 && currentY > p.y())) {
			pixelPoints.add(new Pixel(currentX, currentY));
			tempK = k;
			if (tempK > -differenceX) {
				k -= differenceY;
				currentX += stepX;
			}
			if (tempK < differenceY) {
				k += differenceX;
				currentY += stepY;
			}
		}
		pixelPoints.add(new Pixel(currentX, currentY));
		return (pixelPoints.toArray(new Pixel[0]));
	}

	/**
	 * Fournit les pixels du segment joignant le pixel a un autre.
	 *
	 * @param p  Le pixel d'arrivee.
	 * @param eq Equilibrage du trace (0 = equilibre, -1 = inf, 1 = sup).
	 * @return La sequence de pixels du segment.
	 */
	public Pixel[] tracerSegment(Pixel p, int eq) {
		return (tracerSegment(p));
	}

	/**
	 * Fournit les pixels du cercle dont ce pixel est le centre.
	 *
	 * @param rayon rayon du cercle.
	 * @return L'ensemble des pixels du cercle.
	 */
	public Pixel[] tracerCercle(int rayon) {
		Pixel[] pts = {this, new Pixel(x + rayon, y)};
		return (pts);
	}
}
