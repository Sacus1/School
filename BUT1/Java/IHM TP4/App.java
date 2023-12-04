import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import javax.swing.*;

public class App {

  public static void main(String[] args) {

    EventQueue.invokeLater(
        () -> {
          Frame f = new Frame("TP4");
          f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          f.pack();
          f.setVisible(true);
        });
  }
}

class Frame extends JFrame {
  JFileChooser fc = new JFileChooser();
  File f;
  private final JTextArea jta;

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
    JScrollPane jsp = new JScrollPane(jta); // association de la zone de texte avec un ascenseur
    jsp.setFocusable(true);
    add(jsp, BorderLayout.NORTH);
    JPanel Panels = new JPanel(new GridLayout(2, 8));
    JButton CompterBoutton = new JButton("Compter");
    JButton Remplacer = new JButton("Remplacer par");
    JTextField txt1 = new JTextField();
    JTextField txt2 = new JTextField();
    JLabel txt3 = new JLabel("Text");

    CompterBoutton.addActionListener(
        arg0 -> {
          String s = txt1.getText();
          txt3.setText(s + " est apparu " + (jta.getText().split(s).length - 1) + "fois");
        });
    Remplacer.addActionListener(
        arg0 -> {
          StringBuilder s1 = new StringBuilder();
          txt3.setText(
              txt1.getText()
                  + " est apparu "
                  + (jta.getText().split(txt1.getText()).length - 1)
                  + "fois");
          for (String s : jta.getText().split(txt1.getText())) {
            s1.append(s).append(txt2.getText());
          }
          jta.setText(s1.toString());
        });
    Panels.add(CompterBoutton);
    Panels.add(txt1);
    Panels.add(Remplacer);
    Panels.add(txt2);
    add(Panels, BorderLayout.CENTER);
    add(txt3, BorderLayout.SOUTH);
    // Creation du menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    // Bouton du file
    JMenuItem newBouton = new JMenuItem("New");
    JMenuItem openBouton = new JMenuItem("Open");
    JMenuItem saveBouton = new JMenuItem("Save");
    // Bouton du edit
    JMenuItem selectAllBouton = new JMenuItem("Select all");
    selectAllBouton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
    JMenuItem copyBouton = new JMenuItem("Copy");
    copyBouton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    JMenuItem pasteBouton = new JMenuItem("Paste");
    pasteBouton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    JMenuItem cutBouton = new JMenuItem("Cut");
    cutBouton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

    // Event des bouton
    openBouton.addActionListener(e -> openFile());
    newBouton.addActionListener(e -> newFile());
    saveBouton.addActionListener(e -> saveFile());
    selectAllBouton.addActionListener(e -> jta.selectAll());
    copyBouton.addActionListener(e -> jta.copy());
    cutBouton.addActionListener(e -> jta.cut());
    pasteBouton.addActionListener(e -> jta.paste());
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
