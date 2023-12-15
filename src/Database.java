import java.sql.*;

class Database {
	public static Connection connection;
	
	public static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/shadowbank?serverTimezone=EST", "root", "BP18singh$02");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}