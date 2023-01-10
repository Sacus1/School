import java.util.Observable;

public class Model extends Observable {
	/**
	 * contient l'indice correspondant a l'image a cette emplacement
	 */
	int[] typeImages = new int[5 * 4];
	/**
	 * contient true si l'image a cette emplacement a ete decouverte
	 */
	boolean[] discovered = new boolean[5 * 4];
	/**
	 * contient l'indice des deux images selectionnees
	 */
	int lastSelected, lastSelected2;
	/**
	 * contient le nombre de paires decouvertes
	 */
	int nDecouverts;
	/**
	 * contient le nombre de coups joues
	 */
	int nCoup;
	/**
	 * contient true si le jeu est fini
	 */
	boolean fini;
	/**
	 * taille des images
	 */
	int imageSizeX, imageSizeY;

	public Model() {
		for (int i = 0; i < 5 * 4; i++) {
			typeImages[i] = i / 2;
			discovered[i] = false;
		}
		lastSelected = -1;
		lastSelected2 = -1;
		nCoup = 0;
		nDecouverts = 0;
		fini = false;
		imageSizeX = 120;
		imageSizeY = 120;
		shuffle();
	}

	/**
	 * Rearrange aleatoirement les images
	 */
	public void shuffle() {
		// Suffle the order of the cards in the array o and return it as a new array o2
		int[] o2 = new int[typeImages.length];
		System.arraycopy(typeImages, 0, o2, 0, typeImages.length);
		for (int i = 0; i < typeImages.length; i++) {
			int r = (int) (Math.random() * typeImages.length);
			int tmp = o2[i];
			o2[i] = o2[r];
			o2[r] = tmp;
		}
		typeImages = o2;
	}

	/**
	 * Decouvre l'image a cette emplacement
	 * @param x coordonnee x de l'emplacement
	 * @param y coordonnee y de l'emplacement
	 */
	public void decouvrir(int x, int y) {
		int _x = x / imageSizeX;
		int _y = y / imageSizeY;
		int i = _x + _y * 5;
		if (discovered[i]) return;
		if (lastSelected2 != -1 && lastSelected != -1) {
			discovered[lastSelected] = false;
			discovered[lastSelected2] = false;
			lastSelected = -1;
			lastSelected2 = -1;
		}
		if (lastSelected == -1) {
			lastSelected = i;
			discovered[i] = true;
			nCoup++;
		} else {
			if (typeImages[i] == typeImages[lastSelected]) {
				discovered[i] = true;
				nDecouverts += 1;
				if (nDecouverts == 5 * 4 / 2) fini = true;
				lastSelected = -1;
			} else {
				discovered[lastSelected] = true;
				lastSelected2 = i;
				discovered[i] = true;
			}
		}
		setChanged();
		notifyObservers();
	}

	@Override
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}
