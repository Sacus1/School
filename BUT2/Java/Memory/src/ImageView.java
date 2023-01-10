import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class ImageView extends JPanel implements Observer {
	Image[] images;
	int[] type = new int[5 * 4];
	private boolean[] discovered;

	public ImageView() {
		super();
		images = new Image[11];
		setPreferredSize(new Dimension(120 * 5, 120 * 4));
		// Images are stored in the Images folder
		try {
			images[0] = ImageIO.read(new File("Images/fond.png"));
			for (int i = 0; i < 10; i++) {
				images[i + 1] = ImageIO.read(new File("Images/im" + i + ".png"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is called whenever the observed object is changed. An
	 * application calls an {@code Observable} object's
	 * {@code notifyObservers} method to have all the object's
	 * observers notified of the change.
	 *
	 * @param o   the observable object.
	 * @param arg an argument passed to the {@code notifyObservers}
	 *            method.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Model m)) return;
		type = m.typeImages;
		discovered = m.discovered;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.white);
		int x = 5, y = 5; //coordonnees des positions ou les images doivent etre dessinees

		// Affiche l'image fond dans chaque zone
		for (int i = 0; i < 20; i++) {
			int typeImage = discovered[i] ? type[i] + 1 : 0;
			g.drawImage(images[typeImage], x, y, this); //Dessine l'image fond a la position (x,y)
			x += 120;
			if (x == 605) {
				x = 5;
				y += 120;
			}
		}
	}
}
