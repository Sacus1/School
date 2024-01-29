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



/** Surface d'affichage pour tester les traces de base. */
public class Test extends Canvas
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
  /** Couleurs. */
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

  /** Etapes des tests. */
  int etape = 0;
  /** Extremite de depart du segment ou centre du cercle. */
  Pixel p1;
  /** Extremite d'arrivee du segment ou point sur le cercle. */
  Pixel p2;


  /** Cree le composant pour afficher la grille.
   * @param colonnes Nombre de colonnes.
   * @param lignes Nombre de lignes.
   * @param cerclage Indique que l'on trace des cercles
   */
  public Test (int colonnes, int lignes, boolean cerclage)
  {
    this.colonnes = colonnes;
    this.lignes = lignes;
    this.cerclage = cerclage;
    bm = new int[lignes][colonnes];
    for (int i = 0; i < lignes; i++)
      for (int j = 0; j < colonnes; j++)
        bm[i][j] = 0;
    fenetre = new Frame ("Testeur de " + (cerclage ? "cercles" : "segments"));
    fenetre.add (this);
    fenetre.setBackground (backcol[blanc ? 0 : 1]);
    fenetre.setSize (800, 600);
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
    if (bm[pix.y()][pix. x()] == val) bm[pix.y()][pix.x()] = coul.length - 1;
    else
    {
      bm[pix.y()][pix.x()] += val;
      if (bm[pix.y()][pix.x()] >= coul.length)
        bm[pix.y()][pix.x()] = coul.length - 1;
    }
  }

  /** Affichage du damier et des pions.
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
            g.setColor (backcol[blanc ? 0 : 1]);
            g.drawOval (MARGEX + psize / 10 + i * psize,
                        MARGEY + psize / 10 + h * psize,
                        csize, csize);
          }
        }
  }

  /** Traitement des clics souris.
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
        if (cerclage)
        {
          int dx = p2.x () - p1.x ();
          if (dx < 0) dx = -dx;
          int r = p2.y () - p1.y ();
          if (r < 0) r = -r;
          if (r < dx) r = dx;
          Pixel[] cercle2 = p1.tracerCercle (r);
          Pixel[] cercle1 = (new PixelCercle (p1)).tracerCercle (r);
          for (int i = 0; i < cercle2.length; i++) allumer (1, cercle2[i]);
          for (int i = 0; i < cercle1.length; i++) allumer (2, cercle1[i]);
          allumer (2, p1);
        }
        else
        {
          Pixel[] segment2 = p1.tracerSegment (p2, eq);
          Pixel[] segment1 = (new PixelEq (p1)).tracerSegment (
                              new PixelEq (p2), eq);
          for (int i = 0; i < segment1.length; i++) allumer (1, segment1[i]);
          for (int i = 0; i < segment2.length; i++) allumer (2, segment2[i]);
        }
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
      case '1' :
        if (cerclage) testCercle (1);
        else testSegment (1);
        etape = 0;
        break;
      case '2' :
        if (cerclage) testCercle (2);
        else testSegment (2);
        etape = 0;
        break;
      case '3' :
        if (cerclage) testCercle (3);
        else testSegment (3);
        etape = 0;
        break;
      case '4' :
        if (! cerclage)
        {
          testSegment (4);
          etape = 0;
        }
        break;
      case 'x' :
        if (! cerclage) testData ();
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
        fenetre.setTitle ("Testeur de " + (cerclage ? "cercles" : "segments"));
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


  /** Tests predefinis pour les cercles.
   */
  private void testCercle (int num)
  {
    PixelCercle p1 = new PixelCercle (30,25);
    Pixel p2 = new Pixel (30,25);
    int[] ray; 
    switch (num)
    {
      case 1 :
        ray = new int[] {4, 12, 1, 22};
        break;
      case 2 :
        ray = new int[] {6, 11, 16, 20};
        break;
      case 3 : // Cercle avec une diagonale en trop (apres un non-palier)
        ray = new int[] {4};
        break;
      default :
        return;
    }
    eteindre ();
    for (int i = 0; i < ray.length; i++)
    {
      Pixel[] s = p1.tracerCercle (ray[i]);
      for (int j = 0; j < s.length; j++) allumer (1, s[j]);
      s = p2.tracerCercle (ray[i]);
      for (int j = 0; j < s.length; j++) allumer (2, s[j]);
    }
    repaint ();
  }

  /** Test a partir du fichier test.txt.
   */
  private void testData ()
  {
    try
    {
      BufferedReader flot = new BufferedReader (new FileReader ("test.txt"));
      String line = flot.readLine ();
      int dx = Integer.parseInt (line);
      line = flot.readLine ();
      int dy = Integer.parseInt (line);
      flot.close ();
      Pixel pix2[] = new Pixel[] {new Pixel (2, 2 + dy),
                                  new Pixel (2 + dx, 2)};
      Pixel pix1[] = new Pixel[pix2.length];
      for (int i = 0; i < pix2.length; i++) pix1[i] = new PixelEq (pix2[i]);
      Pixel pix4[] = new Pixel[] {new Pixel (4 + dx, 2),
                                  new Pixel (4 + dx + dy, 2 + dx)};
      Pixel pix3[] = new Pixel[pix4.length];
      for (int i = 0; i < pix4.length; i++) pix3[i] = new PixelEq (pix4[i]);
      Pixel pix6[] = new Pixel[] {new Pixel (2, 4 + dy),
                                  new Pixel (2 + dx, 4 + dy * 2)};
      Pixel pix5[] = new Pixel[pix6.length];
      for (int i = 0; i < pix6.length; i++) pix5[i] = new PixelEq (pix6[i]);
      Pixel pix8[] = new Pixel[] {new Pixel (4 + dx, 4 + dy * 2),
                                  new Pixel (4 + dx + dy, 4 + dy * 2 - dx)};
      Pixel pix7[] = new Pixel[pix8.length];
      for (int i = 0; i < pix8.length; i++) pix7[i] = new PixelEq (pix8[i]);
      eteindre ();
      for (int i = 0; i < pix2.length; i +=2)
      {
        Pixel[] s = pix1[i].tracerSegment (pix1[i+1], eq);
        for (int j = 0; j < s.length; j++) allumer (1, s[j]);
        s = pix2[i].tracerSegment (pix2[i+1], eq);
        for (int j = 0; j < s.length; j++) allumer (2, s[j]);
        s = pix3[i].tracerSegment (pix3[i+1], eq);
        for (int j = 0; j < s.length; j++) allumer (1, s[j]);
        s = pix4[i].tracerSegment (pix4[i+1], eq);
        for (int j = 0; j < s.length; j++) allumer (2, s[j]);
        s = pix5[i].tracerSegment (pix5[i+1], eq);
        for (int j = 0; j < s.length; j++) allumer (1, s[j]);
        s = pix6[i].tracerSegment (pix6[i+1], eq);
        for (int j = 0; j < s.length; j++) allumer (2, s[j]);
        s = pix7[i].tracerSegment (pix7[i+1], eq);
        for (int j = 0; j < s.length; j++) allumer (1, s[j]);
        s = pix8[i].tracerSegment (pix8[i+1], eq);
        for (int j = 0; j < s.length; j++) allumer (2, s[j]);
      }
      repaint ();
    }
    catch (IOException e) { System.out.println (e); }
  }

  /** Tests predefinis pour les segments.
   */
  private void testSegment (int num)
  {
    Pixel pix2[];
    switch (num)
    {
      case 1 :
        pix2 = new Pixel[] {new Pixel (12,10), new Pixel (61,10),
                            new Pixel (61,12), new Pixel (61,41),
                            new Pixel (59,41), new Pixel (10,41),
                            new Pixel (10,39), new Pixel (10,10)};
        break;
      case 2 :
        pix2 = new Pixel[] {new Pixel (36,6), new Pixel (55,25),
                            new Pixel (53,27), new Pixel (34,46),
                            new Pixel (32,44), new Pixel (13,25),
                            new Pixel (15,23), new Pixel (34,4)};
        break;
      case 3 :
        pix2 = new Pixel[] {new Pixel (36,11), new Pixel (55,25),
                            new Pixel (53,27), new Pixel (34,41),
                            new Pixel (32,39), new Pixel (13,25),
                            new Pixel (15,23), new Pixel (34,9)};
        break;
      case 4 :
        pix2 = new Pixel[] {new Pixel (36,6), new Pixel (50,25),
                            new Pixel (48,27), new Pixel (34,46),
                            new Pixel (32,44), new Pixel (18,25),
                            new Pixel (20,23), new Pixel (34,4)};
        break;
      default :
        return;
    }

    Pixel pix1[] = new Pixel[pix2.length];
    for (int i = 0; i < pix2.length; i++) pix1[i] = new PixelEq (pix2[i]);
    eteindre ();
    for (int i = 0; i < pix2.length; i +=2)
    {
      Pixel[] s = pix1[i].tracerSegment (pix1[i+1], eq);
      for (int j = 0; j < s.length; j++) allumer (1, s[j]);
      s = pix2[i].tracerSegment (pix2[i+1], eq);
      for (int j = 0; j < s.length; j++) allumer (2, s[j]);
    }
    repaint ();
  }

  /** Lance un test des traces de segments.
   */
  public static void main (String[] args)
  {
    Test tp = new Test (70, 50, args.length != 0);
  }
}
