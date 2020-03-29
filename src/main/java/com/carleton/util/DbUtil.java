package com.carleton.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				/* log or print or ignore */
			}
		}
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
				statement = null;
			} catch (SQLException e) {
				/* log or print or ignore */
			}
		}
	}

	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
				resultSet = null;
			} catch (SQLException e) {
				/* log or print or ignore */
			}
		}
	}
}