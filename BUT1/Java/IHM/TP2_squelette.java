import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class TP2_squelette {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FrameSquelette f = new FrameSquelette("TP2 � compl�ter ");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.pack();
				f.setVisible(true);
			}
		});
	}
}

/**
 * la Classe FrameSquelette permet de construire l'interface graphique demand�e
 * dans l'exercice 1 - gestion des �v�nements clavier -
 *
 */
class FrameSquelette extends JFrame {

	private Border raisedbevel;
	private Pan panneau;
	private JPanel rp;
	private int[][] t; // Tableau repr�sentant la grille des cases visit�es
	private int x, y; // Coordonn�es du point courant
	private int score;
	private JLabel scoreL, BScore;

	private static int meilleurScore = 0;

	private void CreateInterface() {

		panneau = new Pan();
		panneau.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (x >= 20)
						x -= 20;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (x <= 460)
						x += 20;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (y <= 160)
						y += 20;
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (y >= 20)
						y -= 20;
				}
				SetScore();
			}
		});

		panneau.setFocusable(true); // Permet de donner le focus � panneau
		panneau.setPreferredSize(new Dimension(500, 210));
		rp = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridheight = 1;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 20, 0);
		BScore = new JLabel("Meilleur score : 0");
		BScore.setBorder(raisedbevel);
		rp.add(BScore, c);
		c.insets = new Insets(0, 0, 0, 0);
		c.gridheight = 1;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 1;
		JButton Haut = new JButton("Haut");
		Haut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (y >= 20)
					y -= 20;
				SetScore();
			}
		});
		rp.add(Haut, c);
		c.gridx = 1;
		c.gridy = 3;
		JButton Bas = new JButton("Bas");
		Bas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (y <= 160)
					y += 20;
				SetScore();
			}
		});
		rp.add(Bas, c);
		c.gridx = 0;
		c.gridy = 2;
		JButton Gauche = new JButton("Gauche");
		Gauche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (x >= 20)
					x -= 20;
				SetScore();
			}
		});
		rp.add(Gauche, c);
		c.gridx = 2;
		c.gridy = 2;
		JButton Droite = new JButton("Droite");
		Droite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (x <= 460)
					x += 20;
				SetScore();
			}
		});
		rp.add(Droite, c);
		c.gridheight = 1;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(20, 0, 0, 0);
		scoreL = new JLabel("votre score : 0");
		scoreL.setBorder(raisedbevel);

		rp.add(scoreL, c);
		this.add(panneau, BorderLayout.CENTER); // A COMPLETER
		this.add(rp, BorderLayout.EAST);
		revalidate();
		repaint();
	}

	private void SetScore() {
		if (t[y / 20][x / 20] == 1)
			score -= 100;
		else
			score += 10;
		if (score > meilleurScore) {
			meilleurScore = score;
			BScore.setText("Meilleur score : " + meilleurScore);
		}
		if (IsFull()) {
			this.remove(panneau);
			this.remove(rp);
			revalidate();
			repaint();
			JPanel p = new JPanel(new FlowLayout());
			JLabel Fgame = new JLabel("Fin de partie");
			JLabel Replay = new JLabel("Voulez vous rejouer");
			JButton Y = new JButton("Oui");
			Y.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					remove(p);
					invalidate();
					repaint();
					score = 0;
					t = new int[10][25]; // Initialisation du tableau des cases visit�es
					for (int i = 0; i < 10; i++)
						for (int j = 0; j < 25; j++)
							t[i][j] = 0;
					t[0][0] = 1; // Premi�re case visit�e
					x = 0;
					y = 0;
					CreateInterface();
				}
			});
			JButton N = new JButton("Non");
			p.add(Fgame);
			p.add(Replay);
			p.add(Y);
			p.add(N);
			this.add(p, BorderLayout.CENTER);
		}
		scoreL.setText("votre Score : " + score);
		t[y / 20][x / 20] = 1;
		panneau.requestFocus();
		panneau.repaint();
	};

	private boolean IsFull() {
		for (int[] y : t) {
			for (int x : y) {
				if (x == 0)
					return false;
			}
		}

		return true;
	}

	public FrameSquelette(String titre) {
		super(titre);
		score = 0;
		t = new int[10][25]; // Initialisation du tableau des cases visit�es
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 25; j++)
				t[i][j] = 0;
		t[0][0] = 1; // Premi�re case visit�e
		x = 0;
		y = 0; // Initialisation du point courant
		raisedbevel = BorderFactory.createRaisedBevelBorder();

		CreateInterface();
	}

	/**
	 * la Classe Pan correspond au JPanel dans lequel le dessin des traces du disque
	 * ainsi que le disque jaune sont r�alis�s
	 *
	 */
	class Pan extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.BLUE);
			g.setColor(Color.YELLOW);
			g.fillOval(x, y, 19, 19);

			for (int i = 0; i < 10; i++)
				for (int j = 0; j < 25; j++)
					if (t[i][j] == 1)
						g.fillOval(20 * j + 10, 20 * i + 10, 5, 5); // Si une case a �t� visit�e, dessiner un rond jaune
		}

	}

}
