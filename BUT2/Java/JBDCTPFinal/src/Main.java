import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    JFrame frame = new JFrame("Hello World");
    frame.setLayout(new GridLayout(4, 2));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    JPanel numCardsPanel = new JPanel();
    JLabel numCardsLabel = new JLabel("Numero de carte");
    JTextField numCards = new JTextField(20);
    numCardsPanel.add(numCardsLabel);
    numCardsPanel.add(numCards);
    frame.getContentPane().add(numCardsPanel);
    JPanel nomPanel = new JPanel();
    JLabel nomLabel = new JLabel("Nom");
    JTextField nom = new JTextField(20);
    nomPanel.add(nomLabel);
    nomPanel.add(nom);
    frame.getContentPane().add(nomPanel);
    JPanel montantPanel = new JPanel();
    JLabel montantLabel = new JLabel("Montant");
    JTextField montant = new JTextField(20);
    montantPanel.add(montantLabel);
    montantPanel.add(montant);
    frame.getContentPane().add(montantPanel);
    JButton button = new JButton("Valider");
    frame.getContentPane().add(button);
    SQL sql = new SQL("jdbc:mysql://localhost:3306/restaurant", "root", "");
    button.addActionListener(
        e -> {
          ResultSet rsNom =
              sql.select("SELECT * FROM usager WHERE numCarte = '" + numCards.getText() + "'");
          ResultSet rsDepot =
              sql.select(
                  "SELECT sum(montant) as montant FROM depot WHERE numCarte = '"
                      + numCards.getText()
                      + "'");
          try {
            if (rsNom.next() && rsDepot.next()) {
              String nomClient = rsNom.getString("nom");
              if (!nomClient.equals(nom.getText())) {
                JOptionPane.showMessageDialog(frame, "Le nom ne correspond pas au numero de carte");
                return;
              }
              int depot = rsDepot.getInt("montant");
              System.out.println(depot);
              int montantInt = Integer.parseInt(montant.getText());
              if (montantInt >= 100) {
                JOptionPane.showMessageDialog(frame, "Le montant doit etre inferieur a 100");
                return;
              }
              if (montantInt > depot) {
                JOptionPane.showMessageDialog(frame, "Le montant est superieur au depot");
                deposer(sql, numCards.getText());
                return;
              }
              // recuperer le dernier numTicket
              ResultSet rsTicket =
                  sql.select("SELECT * FROM ticket ORDER BY dateAchat DESC LIMIT 1");
              int numTicket = 0;
              if (rsTicket.next()) {
                numTicket = Integer.parseInt(rsTicket.getString("numTicket").substring(2));
              }
              numTicket++;
              // get current date
              String date =
                  new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
              if (!sql.executeUpdate(
                  "INSERT INTO ticket (numTicket, numCarte, dateAchat) VALUES ('TI"
                      + numTicket
                      + "', "
                      + "'"
                      + numCards.getText()
                      + "', '"
                      + date
                      + "')")) {
                JOptionPane.showMessageDialog(frame, "Erreur lors de l'achat du ticket");
              }
              JOptionPane.showMessageDialog(frame, "Ticket achete");
            } else {
              JOptionPane.showMessageDialog(frame, "Carte inconnue");
            }
          } catch (Exception exception) {
            exception.printStackTrace();
          }
        });
    frame.setVisible(true);
  }

  private static void deposer(SQL sql, String numCarte) {
    JFrame frame = new JFrame("Depot");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    frame.setVisible(true);
  }
}
