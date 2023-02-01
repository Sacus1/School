import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws InterruptedException, SQLException {
    SQL sql = new SQL("jdbc:mysql://localhost:3306/JBDCTP4", "root", "");
    // insert csv data
    sql.extractFromCSV(
        "Vol.csv",
        "Vol",
        new String[] {"Numvol", "Heure_depart", "Heure_arrive", "Ville_depart", "Ville_arrivee"});
    Thread.sleep(1000);
    ResultSet rs = sql.select("* FROM Vol");
    ResultSetMetaData rsmd = rs.getMetaData();
    // print each column name
    int columnsNumber = rsmd.getColumnCount();
    for (int i = 1; i <= columnsNumber; i++) {
      if (i > 1) System.out.print(",  ");
      String columnName = rsmd.getColumnName(i);
      System.out.print(columnName);
    }
    System.out.println();
    // print each row
    while (rs.next()) {
      for (int i = 1; i <= columnsNumber; i++) {
        if (i > 1) System.out.print(",  ");
        String columnValue = rs.getString(i);
        System.out.print(columnValue);
      }
      System.out.println("");
    }
  }
}
