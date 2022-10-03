// Fichier  : Damier.java
// Auteur   : P. Even (IUT de St Die, Dpt Info)
// Contexte : BUT INFO 2021/2022 / Ressource "Developpement oriente objets"


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/** Fenetre graphique pour l'affichage d'un damier. */
public class Damier extends JFrame
{
  /** Composant de dessin du damier */
  private DessinDamier dessin;
  /** Numerotation des grilles */
  private boolean numerotationSpecifique = false;

  /** Construit la fenetre permettant d'afficher un damier dont les
    * dimensions sont fournies en parametres, accompagnees d'un titre */
  public Damier (String titre, int hauteur, int largeur)
  {
    // Creation et ajout du composant de dessin
    dessin = new DessinDamier (titre, hauteur, largeur, numerotationSpecifique);
    add (dessin);
    // Coloration du fond de la fenetre
    setBackground (Color.BLACK);
    // Determination de la taille initiale de la fenetre
    setSize (DessinDamier.MARGE_GAUCHE + DessinDamier.MARGE_DROITE
             + DessinDamier.tailleCases () * largeur,
             DessinDamier.MARGE_SUP + DessinDamier.MARGE_INF
             + DessinDamier.tailleCases () * hauteur);
    setVisible (true);
  }

  /** Effacement du damier */
  public void effacer ()
  {
    dessin.colorier (null);
    dessin.repaint ();
  }

  /** Coloriage d'une case du damier */
  public void colorier (int i, int j, Color couleur)
  {
    dessin.colorier (i, j, couleur);
  }

  /** Raffraichissement du damier */
  public void mettreAJour ()
  {
    dessin.repaint ();
  }

  /** Retourne la taille des cases */
  public static int tailleCases ()
  {
    return (DessinDamier.tailleCases ());
  }

  /** Modification de la taille des cases */
  public static void modifierTailleDesCases (int taille)
  {
    DessinDamier.modifierTailleDesCases (taille);
  }
}

/** Dessin du damier */
class DessinDamier extends JPanel
{
  /** Couleur des cases */
  private Color[][] grille;
  /** Le titre accole au damier */
  private String titre;
  /** Marge gauche */
  public final static int MARGE_GAUCHE = 40;
  /** Marge droite */
  public final static int MARGE_DROITE = 30;
  /** Marge haute */
  public final static int MARGE_SUP = 40;
  /** Marge basse */
  public final static int MARGE_INF = 70;
  /** Longueur par defaut du cote des cases */
  public final static int TAILLE_CASE = 20;
  /** Decalage horizontal des numeros de ligne */
  public final static int X_LIG = 26;
  /** Decalage vertical des numeros de ligne */
  public final static int Y_LIG = 15;
  /** Decalage horizontal des numeros de colonne */
  public final static int X_COL = 8;
  /** Decalage vertical des numeros de colonne */
  public final static int Y_COL = 35;
  /** Decalage vertical du titre */
  public final static int X_TITRE = 60;
  /** Decalage vertical du titre */
  public final static int Y_TITRE = 25;
  /** Taille des cases */
  private static int tailleCases = TAILLE_CASE;
  /** Numerotation des grilles */
  private boolean numerotationSpecifique = false;

  /** Cree le composant pour afficher le damier */
  DessinDamier (String titre, int hauteur, int largeur, boolean numSpec)
  {
    this.titre = titre;
    grille = new Color[hauteur][largeur];
    this.numerotationSpecifique = numSpec;
  }

  /** Colorie une case du damier */
  public void colorier (int i, int j, Color couleur)
  {
    if (i >= 0 && i < grille.length
        && j >= 0 && j < grille[0].length)
      grille[i][j] = couleur;
  }

  /** Colorie toutes les cases du damier */
  public void colorier (Color couleur)
  {
    for (int i = 0; i < grille.length; i ++)
      for (int j = 0; j < grille[i].length; j ++)
        grille[i][j] = couleur;
  }

  /** Modification de la taille des cases pour les nouvelles grilles */
  public static void modifierTailleDesCases (int taille)
  {
    tailleCases = taille;
  }

  /** Recuperation de la taille des cases des futures grilles */
  public static int tailleCases ()
  {
    return tailleCases;
  }

  /** Affichage du damier */
  public void paint (Graphics g)
  {
    // Intitules des cases
    char[] numeros = {'a', '1', 'X'};

    // Effacement de la grille
    g.setColor (Color.BLACK);
    g.fillRect (MARGE_GAUCHE, MARGE_SUP,
                tailleCases * grille[0].length,
                tailleCases * grille.length);

    //Trace de la grille
    g.setColor (Color.YELLOW);
    for (int i = 0; i <= grille[0].length; i++)
      g.drawLine (MARGE_GAUCHE + tailleCases * i,
                  MARGE_SUP,
                  MARGE_GAUCHE + tailleCases * i,
                  MARGE_SUP + tailleCases * grille.length);
    for (int i = 0; i <= grille.length; i++)
      g.drawLine (MARGE_GAUCHE,
                  MARGE_SUP + tailleCases * i,
                  MARGE_GAUCHE + tailleCases * grille[0].length,
                  MARGE_SUP + tailleCases * i);

    // Trace des numeros
    g.setColor (Color.WHITE);
    for (int i = 0; i < grille.length; i++)
    {
      if (numerotationSpecifique)
        g.drawChars (numeros, 0, 1,
                     X_LIG, MARGE_SUP + tailleCases * i + Y_LIG);
      else g.drawString (String.valueOf (i), // 0, 1,
                         X_LIG, MARGE_SUP + tailleCases * i + Y_LIG);
      numeros[0] = (char) (numeros[0] + 1);
    }
    for (int i = 0; i < grille[0].length; i++)
    {
      if (numerotationSpecifique)
      {
        if (numeros[1] == '0')
        {
          g.drawChars (numeros, 2, 1,
                       MARGE_GAUCHE + tailleCases * i + X_COL, Y_COL);
          numeros[2] = (char) (numeros[2] + 1);
        }
        else
          g.drawChars (numeros, 1, 1,
                       MARGE_GAUCHE + tailleCases * i + X_COL, Y_COL);
        if (numeros[1] == '9') numeros[1] = '0';
        else numeros[1] = (char) (numeros[1] + 1);
      }
      else
        g.drawString (String.valueOf (i), // 1, 1,
                     MARGE_GAUCHE + tailleCases * i + X_COL, Y_COL);
    }
    g.drawChars (titre.toCharArray (), 0, titre.length (), X_TITRE,
                 MARGE_SUP + tailleCases * grille.length + Y_TITRE);

    // Remplissage de la grille
    for (int i = 0; i < grille.length; i++)
      for (int j = 0; j < grille[i].length; j++)
        if (grille[i][j] != null)
	{
          g.setColor (grille[i][j]);
          g.fillRect (MARGE_GAUCHE + tailleCases * j + 1,
                      MARGE_SUP + tailleCases * i + 1,
                      tailleCases - 2, tailleCases - 2);
        }
  }
}
