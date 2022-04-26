import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.*;
import javax.swing.*;

public class App {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Frame f = new Frame("TP4");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
            }
        });
    }
}

class Frame extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu;
    private JMenuItem newBouton, openBouton, saveBouton;
    private JMenuItem selectAllBouton, copyBouton, pasteBouton, cutBouton;
    JFileChooser fc = new JFileChooser();
    File f;
    private JTextArea jta;
    private JScrollPane jsp;

    public void newFile() {
        jta.setText("");
    }

    public void openFile() {
        int choix = fc.showOpenDialog(this);
        if (choix == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            try (BufferedReader bfreader = new BufferedReader(new FileReader(f))) {
                jta.setText("");
                String s = bfreader.readLine();
                while (s != null) {
                    jta.append(s + "\n");
                    s = bfreader.readLine();
                }
                bfreader.close();
            } catch (IOException e) {
                System.err.println("Impossible d'ouvrir le fichier");
            }
        }
    }

    public void saveFile() {
        int choix = fc.showSaveDialog(this);
        if (choix == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            try (FileWriter myWriter = new FileWriter(f)) {
                myWriter.write(jta.getText());
                myWriter.close();
            } catch (IOException e) {
                System.err.println("Erreur de lecture");
            }
        }
    }

    public Frame(String titre) {
        super(titre);
        jta = new JTextArea(30, 60);
        jta.setEditable(true); // la zone est éditable
        jta.setLineWrap(true); // le texte passe à la ligne automatiquement
        jsp = new JScrollPane(jta);// association de la zone de texte avec un ascenseur
        jsp.setFocusable(true);
        add(jsp, BorderLayout.NORTH);
        JPanel Panels = new JPanel(new GridLayout(2, 8));
        JButton CompterBoutton = new JButton("Compter");
        JButton Remplacer = new JButton("Remplacer par");
        JTextField txt1 = new JTextField();
        JTextField txt2 = new JTextField();
        JLabel txt3 = new JLabel("Text");

        CompterBoutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String s = txt1.getText();
                txt3.setText(
                        s + " est apparu " + (jta.getText().split(s).length - 1) + "fois");
            }

        });
        Remplacer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String s1 = "";
                txt3.setText(
                        txt1.getText() + " est apparu " + (jta.getText().split(txt1.getText()).length - 1) + "fois");
                for (String s : jta.getText().split(txt1.getText())) {
                    s1 += s + txt2.getText();
                }
                jta.setText(s1);
            }

        });
        Panels.add(CompterBoutton);
        Panels.add(txt1);
        Panels.add(Remplacer);
        Panels.add(txt2);
        add(Panels, BorderLayout.CENTER);
        add(txt3, BorderLayout.SOUTH);
        // Creation du menu
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        // Bouton du file
        newBouton = new JMenuItem("New");
        openBouton = new JMenuItem("Open");
        saveBouton = new JMenuItem("Save");
        // Bouton du edit
        selectAllBouton = new JMenuItem("Select all");
        selectAllBouton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        copyBouton = new JMenuItem("Copy");
        copyBouton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        pasteBouton = new JMenuItem("Paste");
        pasteBouton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        cutBouton = new JMenuItem("Cut");
        cutBouton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        // Event des bouton
        openBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        newBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFile();
            }
        });
        saveBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        selectAllBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.selectAll();
            }
        });
        copyBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.copy();
            }
        });
        cutBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.cut();
            }
        });
        pasteBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.paste();

            }
        });
        // Mise en place des objet dans la fenetre
        fileMenu.add(newBouton);
        fileMenu.add(openBouton);
        fileMenu.add(saveBouton);
        editMenu.add(selectAllBouton);
        editMenu.add(copyBouton);
        editMenu.add(pasteBouton);
        editMenu.add(cutBouton);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);
    }

}