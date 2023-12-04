import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Main {
  public static void main(String[] args) {
    JPanel panneauSud;
    ImageView pims;
    CoupsView nbCoups;
    PairsView nbPaires;
    Border compound;

    /**********************************
     * Création des bords des composants
     ***********************************/
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);

    /********************************************************************
     * Le JPanel panneauSud du Sud qui s'ouvre à l'ouverture du jeu
     ********************************************************************/
    panneauSud = new JPanel(new GridLayout(2, 1));

    nbCoups = new CoupsView(" Nombre de coups joués : 0 ", JLabel.CENTER);
    nbCoups.setPreferredSize(new Dimension(605, 55));
    nbCoups.setOpaque(true);
    nbCoups.setForeground(Color.blue);
    nbCoups.setBorder(compound);
    panneauSud.add(nbCoups);

    nbPaires = new PairsView("Nombre de paires découvertes :  0 ", JLabel.CENTER);
    nbPaires.setPreferredSize(new Dimension(605, 55));
    nbPaires.setOpaque(true);
    nbPaires.setForeground(Color.blue);
    nbPaires.setBorder(compound);
    panneauSud.add(nbPaires);

    pims = new ImageView();
    pims.setPreferredSize(new Dimension(605, 485));
    pims.setBorder(compound);

    /**************************************
     * Construction de l'IG dans une JFrame
     ***************************************/
    JFrame frame = new JFrame("Memory MVC");
    frame.getContentPane().setBackground(Color.BLUE);

    frame.getContentPane().add(pims, BorderLayout.CENTER);
    frame.getContentPane().add(panneauSud, BorderLayout.SOUTH);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    Model m = new Model();
    m.addObserver(pims);
    frame.setVisible(true);
    m.notifyObservers();
    m.addObserver(nbCoups);
    m.addObserver(nbPaires);
    Controller c = new Controller(m);
    pims.addMouseListener(c);
  }
}
