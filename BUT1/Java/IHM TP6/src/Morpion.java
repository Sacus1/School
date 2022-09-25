import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * This class contains logic for the Tic-toc-toe game and maintains the UI.
 *
 * @author Abidi S
 */

public class Morpion extends JFrame {
    int i = 0;
    JLabel Score, Leader;
    /**
     * Menu Bar;
     */
    JMenuBar menu = new JMenuBar();

    /**
     * Clickable JButtons for user interactions.
     */
    JButton[] Buttons = new JButton[] {
            new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton()
    };

    /**
     * JPanel to display the buttons for the game
     */
    JPanel panel;

    /**
     * tableau: pour verifier la fin du jeu, tableau[j]= 1 ou 2 (1 = joueur1 ; 2 =
     * joueur2)
     * tab: pour desactiver les buttons deja selectionner (false: activer, true:
     * desactiver)
     */
    private int tableau[] = new int[9];
    private boolean tab[] = new boolean[9];

    /**
     * The current player; False (Player X) True (Player O)
     */
    private boolean qui = false;

    private String messageHelp = "Bienvenue: occuper trois cases alignées!";
    /**
     * Set up the look and feel to make the UI look good.
     */
    // Vous pouvez commenter cette partie de code pour voir la différence
    // Avec et sans "Nimbus Look and Feel"
    static {
        try {
            // Using Nimbus Look and Feel
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            Logger.getLogger(Morpion.class.getName()).log(Level.WARNING, "Unable to set Nimbus Look and Feel", e);
        }
    }

    /**
     * String that stores the name of the current scoreboard leader
     */
    private static String leader = "Nobody";
    /**
     * The number of wins made by the human player.
     */
    private static int joueurXwins = 0;

    /**
     * The number of wins made by the computer.
     */
    private static int joueurOwins = 0;

    /**
     * Constructor: TO DO ..
     *
     */
    public Morpion() {

        /**
         * Using GridLayout to place buttons
         */
        panel = new JPanel(new GridLayout(3, 3));

        // Create an empty border around the panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        // Sets constraints for the main frame
        // To do ..

        // Sets the menu bar for this frame.
        JMenu Menu = new JMenu("Menu");
        JMenu Help = new JMenu("Help");
        JMenuItem NewGame = new JMenuItem("New game");
        JMenuItem Leave = new JMenuItem("Leave");
        JMenuItem messageAide = new JMenuItem("Help message");
        NewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                nouveauJeu();
            }
        });
        Leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        messageAide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                texthelp();
            }
        });
        Menu.add(NewGame);
        Menu.add(Leave);
        Help.add(messageAide);
        menu.add(Menu);
        menu.add(Help);
        setJMenuBar(menu);

        /*
         * Declares the 9 JButtons
         * Adds an action listener to each
         * And adds each of them to the panel
         * Other visual appearance settings are also handled here
         */
        int i = 0;
        for (JButton jButton : Buttons) {
            // On ajoute les boutons au panel
            panel.add(jButton);
            jButton.setPreferredSize(new Dimension(100, 100));
            jButton.setFont(new Font("", Font.BOLD, 40));
            int boutonId = i;
            // Adds an action listener to each button
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // si le case est remplie
                    if (tab[boutonId]) {
                        // Afficher une erreur
                        JOptionPane.showMessageDialog(null, "Chose another case", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    tab[boutonId] = true;
                    tableau[boutonId] = qui ? 1 : 2;
                    jButton.setText(qui ? "O" : "X");
                    if (verifie()) {
                        for (int i = 0; i < tab.length; i++) {
                            tab[i] = true;
                        }
                        gameOver(qui ? "O" : "X");
                    }
                    // Change the current player
                    qui = !qui;
                    boolean selected = false;
                    int r = 0;
                    // Choix de la case par l'ordinateur (IA)
                    while (!selected) {
                        r = (int) (Math.random() * 9);
                        if (!tab[r]) {
                            tab[r] = true;
                            tableau[r] = qui ? 1 : 2;
                            Buttons[r].setText(qui ? "O" : "X");
                            selected = true;
                        }
                    }
                    tab[r] = true;
                    tableau[r] = qui ? 1 : 2;
                    Buttons[r].setText(qui ? "O" : "X");
                    if (verifie()) {
                        for (int i = 0; i < tab.length; i++) {
                            tab[i] = true;
                        }
                        gameOver(qui ? "O" : "X");
                    }
                    qui = !qui;
                }
            });
            i++;
        }

        // Sets the constraints for using GridBagLayout
        JPanel score = new JPanel(new GridBagLayout());
        score.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 2;
        JPanel Affichage = new JPanel(new GridLayout(2, 1));
        // Adds the label to display the current score on the GUI
        Score = new JLabel("Score : 0-0");
        Affichage.add(Score);
        // Displayes the label to show the current leader on the GUI
        Leader = new JLabel("Leader : " + leader);
        Affichage.add(Leader);
        // Sets constraints and components for the main frame
        score.add(Affichage);
        add(panel, BorderLayout.CENTER);
        add(score, BorderLayout.EAST);
    }

    /**
     * Une méthode qui renvoie a showMessageDialog pour explique le principe de jeu
     * Help menu
     */
    public void texthelp() {
        JOptionPane.showMessageDialog(null, messageHelp, "AIDE", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Une méthode pour verifier la fin du jeu
     * et qui renvoie un boolean
     * Vous pouvez proposer votre propre solution, la méthode n'est qu'une
     * proposition!
     */
    public boolean verifie() {
        boolean a = false;

        if ((tableau[0] == 1 && tableau[1] == 1 && tableau[2] == 1) ||
                (tableau[0] == 2 && tableau[1] == 2 && tableau[2] == 2) ||

                (tableau[3] == 1 && tableau[4] == 1 && tableau[5] == 1) ||
                (tableau[3] == 2 && tableau[4] == 2 && tableau[5] == 2) ||

                (tableau[6] == 1 && tableau[7] == 1 && tableau[8] == 1) ||
                (tableau[6] == 2 && tableau[7] == 2 && tableau[8] == 2) ||

                (tableau[0] == 1 && tableau[3] == 1 && tableau[6] == 1) ||
                (tableau[0] == 2 && tableau[3] == 2 && tableau[6] == 2) ||

                (tableau[1] == 1 && tableau[4] == 1 && tableau[7] == 1) ||
                (tableau[1] == 2 && tableau[4] == 2 && tableau[7] == 2) ||

                (tableau[2] == 1 && tableau[5] == 1 && tableau[8] == 1) ||
                (tableau[2] == 2 && tableau[5] == 2 && tableau[8] == 2) ||

                (tableau[0] == 1 && tableau[4] == 1 && tableau[8] == 1) ||
                (tableau[0] == 2 && tableau[4] == 2 && tableau[8] == 2) ||

                (tableau[2] == 1 && tableau[4] == 1 && tableau[6] == 1) ||
                (tableau[2] == 2 && tableau[4] == 2 && tableau[6] == 2))
            a = true;
        return a;

    }

    // On appelle cette méthode quand on va relancer le jeu
    // Vous pouvez proposer votre propre solution, la méthode n'est qu'une
    // proposition!
    public void nouveauJeu() {
        for (int i = 0; i < Buttons.length; i++) {
            tab[i] = false;
            tableau[i] = 0;
            Buttons[i].setText("");
        }
    }

    // On appelle cette méthode à la fin du jeu pour; Update scoreboard,
    // And calculate who's winning right now
    // Vous pouvez proposer votre propre solution, la méthode n'est qu'une
    // proposition!
    private void gameOver(String winner) {
        // Update scoreboard
        if (winner.equals("X"))
            joueurXwins += 1;
        else
            joueurOwins += 1;
        // calculate who's winning right now
        if (joueurOwins > joueurXwins)
            leader = "O";
        else
            leader = "X";
        Leader.setText("Leader : " + leader);
        Score.setText("Score : " + joueurXwins + ":" + joueurOwins);
        JOptionPane.showMessageDialog(null, "Player " + leader + " won", "Win", JOptionPane.INFORMATION_MESSAGE);
        int Oui = JOptionPane.showConfirmDialog(null, "Replay", "Morpion", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (Oui == 0)
            nouveauJeu();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Morpion f = new Morpion();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
            }
        });
    }
}