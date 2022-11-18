import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLTest {
	private static SQL sql;
	@BeforeAll
	public static void init(){
		sql = new SQL("jdbc:mysql://localhost:3306/myband", "root", "");
	}
	@Test
	public void select() {
		ResultSet rs = sql.select("SELECT * FROM `admin`");
		try {
			rs.next();
			assertEquals("Sacus", rs.getString("login"), "Login should be Sacus");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void insert(){
		// check if test is in admin table
		ResultSet rs = sql.select("SELECT * FROM `admin` WHERE `login` = 'test'");
		// if test is in admin table remove it
		try {
			if (rs.next()) {
				sql.executeUpdate("DELETE FROM `admin` WHERE `login` = 'test'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// add test in admin table
		sql.executeUpdate("INSERT INTO `admin` (`login`, `password`,`email`,`contact`) VALUES ('test', 'test','test'," +
				"false)");
// check if test is in admin table
		rs = sql.select("SELECT * FROM `admin` WHERE `login` = 'test'");
		try {
			rs.next();
			assertEquals("test", rs.getString("login"), "Test should be in admin table");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// remove test in admin table
		sql.executeUpdate("DELETE FROM `admin` WHERE `login` = 'test'");
	}
	@AfterAll
	public static void close(){
		sql.close();
	}
}
