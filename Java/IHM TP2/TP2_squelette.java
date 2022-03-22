import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
public class TP2_squelette {

public static void main(String[] args) {

 EventQueue.invokeLater(new Runnable() {
	public void run() {
		FrameSquelette  f = new FrameSquelette ("TP2 � compl�ter ");
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
class FrameSquelette extends JFrame  {

	private Border raisedbevel;
	private Pan panneau;
	private int[][] t; //Tableau repr�sentant la grille des cases visit�es
	private int x,y;   //Coordonn�es du point courant



	/**
	*  Constructeur permettant de cr�er l'interface graphique demand�e - A COMPLETER
	* @param titre
	*	titre de la fen�tre
	*/
	public FrameSquelette(String titre){
		super(titre);

		t=new int[10][25]; //Initialisation du tableau des cases visit�es
		for(int i=0;i<10;i++)
			for(int j=0;j<25;j++)
				t[i][j]=0;
		t[0][0]=1;		//Premi�re case visit�e
		x=0;y=0;		//Initialisation du point courant

		raisedbevel = BorderFactory.createRaisedBevelBorder();

		panneau=new Pan();
		panneau.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode()==KeyEvent.VK_LEFT){
					if(x>=20) x-=20;
				}
				else if (e.getKeyCode()==KeyEvent.VK_RIGHT){
						if(x<=460)	x+=20;
					}
					else if (e.getKeyCode()==KeyEvent.VK_DOWN){
							if(y<=160) ;	//A COMPLETER
						}
						else if (e.getKeyCode()==KeyEvent.VK_UP){
								if(y>=20) ;	//A COMPLETER
							}

				t[y/20][x/20]=1;
				panneau.repaint();
			}
		});

		panneau.setFocusable(true);   //Permet de donner le focus � panneau
		panneau.setPreferredSize(new Dimension(500,210));

		this.add(panneau,BorderLayout.CENTER); //A COMPLETER
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
		g.fillOval(x,y,19,19);

		for(int i=0;i<10;i++)
			for(int j=0;j<25;j++)
				if(t[i][j]==1) g.fillOval(20*j+10,20*i+10,5,5); // Si une case a �t� visit�e, dessiner un rond jaune
	}

}

}
