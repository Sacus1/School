import java.sql.*;

public class SQL {
	Connection con;

	public SQL(String url,String user, String password) {
		try {
			// chargement de la classe par son nom lié au driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// levée d'exception si le driver n'est pas trouvé
			System.err.println("Class not found : " + e.getMessage());
		}
		try {
			// on se connecte à la base de données
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.err.println("SQL Exception : " + e.getMessage());
		}
	}
	public boolean executeUpdate(String query) {
		Statement stmt;
		try {
			// on crée un objet Statement
			stmt = con.createStatement();
			// on exécute la requête
			boolean state = stmt.executeUpdate(query) == 1;
			// on ferme la connexion
			stmt.close();
			return state;
		} catch (SQLException e) {
			System.err.println("SQL Exception : " + e.getMessage());
		}
		return false;
	}
	public ResultSet select(String querie) {
		Statement stmt;
		try {
			// on crée un objet Statement
			stmt = con.createStatement();
			// on exécute la requête
			return stmt.executeQuery(querie);
		} catch (SQLException e) {
			System.err.println("SQL Exception : " + e.getMessage());
		}
		return null;
	}
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("SQL Exception : " + e.getMessage());
		}
	}
}
