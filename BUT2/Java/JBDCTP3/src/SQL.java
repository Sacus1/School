import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class SQL {
  Connection con;
  PreparedStatement ps;

  public SQL(String url, String user, String password) {
    try {
      // chargement de la classe par son nom lié au driver
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      // levée d’exception si le driver n’est pas trouvé
      System.err.println("Class not found : " + e.getMessage());
    }
    try {
      // on se connecte à la base de données
      con = DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      System.err.println("SQL Exception : " + e.getMessage());
    }
  }

  /**
   * Take a query to execute, if their not exactly one result it will be considered as failed.
   *
   * @param query the query to execute
   * @return true if the query was executed successfully and false if it failed.
   */
  public boolean executeUpdate(String query) {
    Statement stmt;
    boolean state = false;
    try {
      // on crée un objet Statement
      stmt = con.createStatement();
      // on exécute la requête
      state = stmt.executeUpdate(query) == 1;
      // on ferme la connexion
      stmt.close();
    } catch (SQLException e) {
      System.err.println("SQL Exception : " + e.getMessage());
    }
    return state;
  }

  public ResultSet executePreparedQuery() {
    try {
      return ps.executeQuery();
    } catch (SQLException e) {
      System.err.println("SQL Exception : " + e.getMessage());
    }
    return null;
  }

  public ResultSet select(String query) {
    Statement stmt;
    try {
      // on crée un objet Statement
      stmt = con.createStatement();
      // on exécute la requête
      return stmt.executeQuery("SELECT " + query);
    } catch (SQLException e) {
      System.err.println("SQL Exception : " + e.getMessage());
    }
    return null;
  }

  public void startPrepared(String[] attribute, String table) {
    StringBuilder query = new StringBuilder("INSERT INTO " + table + " (");
    for (String s : attribute) {
      query.append(s).append(",");
    }
    query = new StringBuilder(query.substring(0, query.length() - 1));
    query.append(") VALUES (");
    query.append("?,".repeat(attribute.length));
    query.deleteCharAt(query.length() - 1); // remove last comma
    query.append(")");
    try {
      ps = con.prepareStatement(query.toString());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void executePrepared() {
    try {
      ps.executeUpdate();
    } catch (SQLException e) {
      System.err.println("SQL Exception : " + e.getMessage());
    }
  }

  /** close the connection to the database */
  public void close() {
    try {
      // check si il y a une requête non commit
      if (ps != null) {
        // annulation de la requête
        ps.cancel();
        // fermeture de la requête
        ps.close();
      }
      con.close();
    } catch (SQLException e) {
      System.err.println("SQL Exception : " + e.getMessage());
    }
  }

  public void extractFromCSV(String path, String table, String[] attribute) {
    File file = new File(path);
    try (Scanner sc = new Scanner(file)) {
      startPrepared(attribute, table);
      while (sc.hasNextLine()) {
        String[] line = sc.nextLine().split(";");
        for (int i = 0; i < line.length; i++) {
          ps.setString(i + 1, line[i]);
        }
        executePrepared();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
