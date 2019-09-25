package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static final String USERNAME = "kitchenManager_admin";
	private static final String PASSWORD = "12345678";
	private static final String URL =  "jdbc:mysql://127.0.0.1:3306/test_km";
	
	public static Connection getConnection() throws SQLException {	
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
}
