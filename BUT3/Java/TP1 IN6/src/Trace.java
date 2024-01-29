// Trame du TP sur les traces de base
// BUT Info - 2023/2024
// Prepare par Ph. Even (Universite de Lorraine / IUT de Saint-Die, Dpt Info)

import java.awt.Frame;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



/** Surface d'affichage pour le trace de segments ou de cercles.
 */
public class Trace extends Canvas
                   implements MouseListener, KeyListener, WindowListener
{
  /** Marges du composant. */
  private final static int MARGEX = 10, MARGEY = 10;
  /** Dimensions de la grille. */
  private int colonnes, lignes;
  /** Dimension des pixels. */
  private int psize = 10;
  /** Image. */
  private int[][] bm;
  /** Couleurs d'affichage. */
  private Color[] coul = {Color.WHITE, Color.YELLOW, Color.GREEN,
                          Color.BLUE, Color.RED};
  /** Couleurs d'effacement. */
  private Color[] backcol = {Color.LIGHT_GRAY, Color.DARK_GRAY};
  /** Type d'ardoise. */
  private boolean blanc = true;
  /** Fenetre applicative. */
  private Frame fenetre;
  /** Indicateur de type de trace. */
  private boolean cerclage = false;
  /** Indicateur d'affichage des coordonnees. */
  private boolean verbe = false;
  /** Equilibrage du segment. */
  private int eq = 0;

  /** Etapes de la selection du segment. */
  int etape = 0;
  /** Extremite de depart du segment ou centre du cercle. */
  Pixel p1;
  /** Extremite d'arrivee du segment ou point sur le cercle. */
  Pixel p2;


  /** Cree le composant pour afficher l'image binaire.
   * @param colonnes Nombre de colonnes.
   * @param lignes Nombre de lignes.
   * @param cerclage Indique que l'on trace des cercles.
   */
  public Trace (int colonnes, int lignes, boolean cerclage)
  {
    this.colonnes = colonnes;
    this.lignes = lignes;
    this.cerclage = cerclage;
    bm = new int[lignes][colonnes];
    for (int i = 0; i < lignes; i++)
      for (int j = 0; j < colonnes; j++)
        bm[i][j] = 0;
    fenetre = new Frame ("Traceur de " + (cerclage ? "cercles" : "segments"));
    fenetre.add (this);
    fenetre.setBackground (backcol[blanc ? 0 : 1]);
    fenetre.setSize (900, 700);
    fenetre.setVisible (true);
    addMouseListener (this);
    addKeyListener (this);
    fenetre.addWindowListener (this);
    requestFocus ();
  }

  /** Efface la grille.
   */
  public void eteindre ()
  {
    etape = 0;
    for (int i = 0; i < lignes; i++)
      for (int j = 0; j < colonnes; j++)
        bm[i][j] = 0;
  }

  /** Colore un pixel en tenant compte des collisions.
   * @param val Couleur de trace.
   * @param pix Le pixel a colorier.
   */
  public void allumer (int val, Pixel pix)
  {
    if (pix.x () < 0 || pix.y () < 0
        || pix.x () >= colonnes || pix.y () >= lignes) return;
    if (bm[pix.y()][pix.x()] == val) bm[pix.y()][pix.x()] = coul.length - 1;
    else
    {
      bm[pix.y()][pix.x()] += val;
      if (bm[pix.y()][pix.x()] >= coul.length)
        bm[pix.y()][pix.x()] = coul.length - 1;
    }
  }

  /** Affichage du damier et des pions.
   * @param g Surface d'affichage.
   */
  public void paint (Graphics g)
  {
    // Affichage de la grille
    g.setColor (backcol[blanc ? 1 : 0]);
    for (int i = 0; i <= colonnes; i++)
      g.drawLine (MARGEX + i * psize, MARGEY,
                  MARGEX + i * psize, MARGEY + lignes * psize);
    for (int i = 0; i <= lignes; i++)
      g.drawLine (MARGEX, MARGEY + i * psize,
                  MARGEX + colonnes * psize, MARGEY + i * psize);

    // Affichage des pixels
    for (int i = 0; i < colonnes; i++)
      for (int j = 0; j < lignes; j++)
        if (bm[j][i] != 0)
        {
          int h = lignes - 1 - j;
          g.setColor (coul[bm[j][i]]);
          int csize = (psize * 4) / 5;
          g.fillOval (MARGEX + psize / 10 + i * psize,
                      MARGEY + psize / 10 + h * psize,
                      csize, csize);
          if (bm[j][i] == 4)
          {
            g.setColor (backcol[blanc ? 1 : 0]);
            g.drawOval (MARGEX + psize / 10 + i * psize,
                        MARGEY + psize / 10 + h * psize,
                        csize, csize);
          }
        }
  }

  /** Traitement des clics souris.
   * @param e Evenement a traiter.
   */
  public void mouseClicked (MouseEvent e)
  {
    int x = e.getX () - MARGEX;
    int y = e.getY () - MARGEX;
    if (x < 0 || y < 0) return;
    x /= psize;
    y /= psize;
    if (x >= colonnes || y >= lignes) return;
    if (verbe) System.out.println (x + " " + (lignes - 1 - y));

    switch (etape)
    {
      case 0 :
        eteindre ();
        p1 = new Pixel (x, lignes - 1 - y);
        allumer (1, p1);
        etape ++;
        break;
      case 1 :
        p2 = new Pixel (x, lignes - 1 - y);
        if (p2.x () != p1.x () || p2.y () != p1.y ())
        {
          allumer (1, p2);
          etape ++;
        }
        break;
      case 2 :
        eteindre ();
        Pixel[] trace;
        if (cerclage)
        {
          int dx = p2.x () - p1.x ();
          if (dx < 0) dx = -dx;
          int r = p2.y () - p1.y ();
          if (r < 0) r = -r;
          if (r < dx) r = dx;
          trace = p1.tracerCercle (r);
          allumer (2, p1);
        }
        else trace = p1.tracerSegment (p2, eq);
        for (int i = 0; i < trace.length; i++) allumer (3, trace[i]);
        etape = 0;
        break;
    }
    repaint ();
  }

  public void mouseEntered (MouseEvent e) { }
  public void mouseExited (MouseEvent e) { }
  public void mousePressed (MouseEvent e) { }
  public void mouseReleased (MouseEvent e) { }

  public void keyTyped (KeyEvent e)
  {
    switch (e.getKeyChar ())
    {
      case 'p' :
        if (cerclage) testPavage ();
        break;
      case 'e' :
        eq = 0;
        break;
      case 'r' :
        eq = 1;
        break;
      case 'z' :
        eq = -1;
        break;
      case 'b' :
        blanc = ! blanc;
        fenetre.setBackground (backcol[blanc ? 0 : 1]);
        repaint ();
        break;
      case 'c' :
        cerclage = ! cerclage;
        fenetre.setTitle ("Traceur de " + (cerclage ? "cercles" : "segments"));
        repaint ();
        break;
      case 'v' :
        verbe = ! verbe;
        break;
      case 'q' :
        System.exit (0);
        break;
    }
  }

  public void keyPressed (KeyEvent e) { }
  public void keyReleased (KeyEvent e) { }

  public void windowActivated (WindowEvent e) { }
  public void windowClosed (WindowEvent e) { System.exit (0); }
  public void windowClosing (WindowEvent e) { System.exit (0); }
  public void windowDeactivated (WindowEvent e) { }
  public void windowDeiconified (WindowEvent e) { }
  public void windowIconified (WindowEvent e) { }
  public void windowOpened (WindowEvent e) { }


  /** Test de pavage du plan.
   */
  private void testPavage ()
  {
    Pixel centre = new Pixel (30, 25);
    eteindre ();
    Pixel[] cercle = centre.tracerCercle (1);
    for (int j = 0; j < cercle.length; j++) allumer (2, cercle[j]);
    for (int i = 1; i < 10; i ++)
    {
      cercle = centre.tracerCercle (2 * i);
      for (int j = 0; j < cercle.length; j++) allumer (3, cercle[j]);
      cercle = centre.tracerCercle (2 * i + 1);
      for (int j = 0; j < cercle.length; j++) allumer (2, cercle[j]);
    }
    repaint ();
  }

  /** Affichage d'une grille de test des traces.
   * @param args Arguments des tests.
   */
  public static void main (String[] args)
  {
    new Trace (80, 60, args.length != 0);
  }
}
