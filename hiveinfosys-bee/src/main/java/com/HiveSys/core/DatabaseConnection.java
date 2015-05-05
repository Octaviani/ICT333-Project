package com.HiveSys.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private Connection mConnection = null;
	
	DatabaseConnection() {
		///* Does not seem to have the need for this
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*/
	}
	
	public void connect(String URL, String username, String password) throws SQLException {
		mConnection = DriverManager.getConnection(URL, username, password);
	}
	
	public Connection getConnection() {
		return this.mConnection;
	}
	
	public static DatabaseConnection getDefault() {
		return StaticDatabaseConnection.DEFAULT;
	}
}
