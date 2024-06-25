package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbUtil {
	
	private static Connection con;
	
	private dbUtil() {
		
	}
	
	// Method to establish a database connection
	public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sims", "root", "");
			return con;
		
	}

	public static void setCon(Connection con) {
		dbUtil.con = con;
	}
	
	public static Connection getCon() {
		return con;
	}

}
