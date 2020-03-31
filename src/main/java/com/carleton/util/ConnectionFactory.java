package com.carleton.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	// Static reference to itself
	private static ConnectionFactory instance = new ConnectionFactory();

	public static final String DRIVER_CLASS = "org.sqlite.JDBC";
	public static final String URL = "jdbc:sqlite:src/main/resources/bug.db";
	
	// private constructor
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: Unable to Connect to Database.");
		}
		return connection;
	}

	public static Connection getConnection() {
		return instance.createConnection();
	}
}