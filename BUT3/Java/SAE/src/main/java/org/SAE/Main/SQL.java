package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
/**
 * This class provides methods to interact with a SQL database.
 */
public class SQL {
	public Connection con;

	/**
	 * Constructor for the SQL class.
	 * It initializes the connection to the database.
	 *
	 * @param url      the URL of the database
	 * @param user     the username for the database
	 * @param password the password for the database
	 */
	public SQL(String url, String user, String password) {
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
			System.err.println("Main.SQL Exception : " + e.getMessage());
		}
	}

	/**
	 * This method updates a table in the database using a prepared statement.
	 *
	 * @param table     the name of the table to update
	 * @param colName   an array of column names to update
	 * @param attr      an array of new values for the columns
	 * @param whereCond an array of conditions for the WHERE clause
	 * @return true if the update was successful, false otherwise
	 */
	public boolean updatePreparedStatement(String table, String[] colName, Object[] attr, String[] whereCond) {
		StringBuilder query = new StringBuilder("UPDATE " + table + " SET ");
		for (int i = 0; i < colName.length; i++) {
			query.append(colName[i]).append(" = ?");
			if (i != colName.length - 1) query.append(", ");
		}
		query.append(" WHERE ");
		for (int i = 0; i < whereCond.length; i++) {
			query.append(whereCond[i]).append(" = ?");
			if (i != whereCond.length - 1) query.append(" AND ");
		}
		query.append(";");
		try {
			PreparedStatement stmt = con.prepareStatement(query.toString());
			for (int i = 0; i < attr.length; i++)
				if (attr[i] instanceof String) stmt.setString(i + 1, (String) attr[i]);
				else if (attr[i] instanceof Integer) stmt.setInt(i + 1, (Integer) attr[i]);
				else if (attr[i] instanceof Double) stmt.setDouble(i + 1, (Double) attr[i]);
				else if (attr[i] instanceof File file) {
					InputStream inputStream = new FileInputStream(file);
					stmt.setBinaryStream(i + 1, inputStream, (int) file.length());
					if (file.delete()) System.out.println("File deleted successfully");
					else System.out.println("Failed to delete the file");
				}
			return stmt.executeUpdate() != 0;
		} catch (SQLException e) {
			System.err.println("Main.SQL Exception : " + e.getMessage() + "\n" + query.toString() + "\n" + Arrays.toString(attr));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	/**
	 * This method inserts a new row into a table in the database using a prepared statement.
	 *
	 * @param table   the name of the table to insert into
	 * @param colName an array of column names for the new row
	 * @param attr    an array of values for the new row
	 * @return true if the insertion was successful, false otherwise
	 */
	public boolean createPrepareStatement(String table, String[] colName, Object[] attr) {
		StringBuilder query = buildPartialQuery(table, colName, "INSERT INTO ", " (", ", ", ") VALUES (");
		for (int i = 0; i < colName.length; i++) {
			query.append("?");
			if (i != colName.length - 1) query.append(", ");
		}
		query.append(");");
		try {
			PreparedStatement stmt = con.prepareStatement(query.toString());
			for (int i = 0; i < attr.length; i++)
				if (attr[i] instanceof String) stmt.setString(i + 1, (String) attr[i]);
				else if (attr[i] instanceof Integer) stmt.setInt(i + 1, (Integer) attr[i]);
				else if (attr[i] instanceof Double) stmt.setDouble(i + 1, (Double) attr[i]);
				else if (attr[i] instanceof File file) if (file.exists()) {
					InputStream inputStream = new FileInputStream(file);
					stmt.setBinaryStream(i + 1, inputStream, (int) file.length());
				} else stmt.setNull(i + 1, Types.BLOB);
				else if (attr[i] == null) stmt.setNull(i + 1, Types.BLOB);
			return stmt.executeUpdate() != 0;
		} catch (SQLException e) {
			System.err.println("Main.SQL Exception : " + e.getMessage() + "\n" + query.toString() + "\n" + Arrays.toString(attr));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	private StringBuilder buildPartialQuery(String tableName, String[] columnNames, String initialQuery,
	                                        String preColumns, String betweenColumns, String postColumns){
		StringBuilder query = new StringBuilder(initialQuery + tableName + preColumns);
		for (int i = 0; i < columnNames.length; i++) {
			query.append(columnNames[i]);
			if (i != columnNames.length - 1) query.append(betweenColumns);
		}
		query.append(postColumns);
		return query;
	}

	/**
	 * This method deletes rows from a table in the database using a prepared statement.
	 *
	 * @param table     the name of the table to delete from
	 * @param whereCond an array of conditions for the WHERE clause
	 * @return true if the deletion was successful, false otherwise
	 */
	public boolean deletePrepareStatement(String table, String[] whereCond) {
		StringBuilder query = buildPartialQuery(table, whereCond, "DELETE FROM ", " WHERE ", " AND ", ";");
		try {
			PreparedStatement stmt = con.prepareStatement(query.toString());
			return stmt.executeUpdate() != 0;
		} catch (SQLException e) {
			System.err.println("Main.SQL Exception : " + e.getMessage() + "\n" + query);
		}
		return false;
	}

	/**
	 * This method selects rows from a table in the database.
	 *
	 * @param table     the name of the table to select from
	 * @param whereCond an array of conditions for the WHERE clause
	 * @return a ResultSet object containing the selected rows
	 */
	public ResultSet select(String table, String[] whereCond) {
		StringBuilder query = buildPartialQuery(table, whereCond, "SELECT * FROM ", " WHERE ", " AND ", ";");
		try {
			return con.prepareStatement(query.toString()).executeQuery();
		} catch (SQLException e) {
			System.err.println("Main.SQL Exception : " + e.getMessage() + "\n" + query.toString());
		}
		return null;
	}

	/**
	 * This method executes a raw SQL query.
	 *
	 * @param rawQuery the raw SQL query to execute
	 * @return a ResultSet object containing the results of the query
	 */
	public ResultSet selectRaw(String rawQuery) {
		try {
			return con.prepareStatement(rawQuery).executeQuery();
		} catch (SQLException e) {
			System.err.println("Main.SQL Exception : " + e.getMessage() + "\n" + rawQuery);
		}
		return null;
	}

	/**
	 * This method selects all rows from a table in the database.
	 *
	 * @param table the name of the table to select from
	 * @return a ResultSet object containing the selected rows
	 */
	public ResultSet select(String table) {
		return select(table, new String[]{"1 = 1"});
	}
	/**
	 * This method closes the connection to the database.
	 */
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("Main.SQL Exception : " + e.getMessage());
		}
	}
}
