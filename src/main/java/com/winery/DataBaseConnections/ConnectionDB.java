package com.winery.DataBaseConnections;

import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

import com.winery.Utility.Utility;

public class ConnectionDB {

	public static Connection getConnection(String dbType) {

		String _dbType = Objects.requireNonNull(dbType);
		Properties props = new Properties();

		try (InputStream input = ConnectionDB.class.getResourceAsStream("/config.properties")) {
			props.load(input);
		} catch (Exception e) {
			throw new RuntimeException("Errore nel caricamento config.properties", e);
		}

		return getConnection(_dbType, props);
	}

	public static Connection getConnection() {

		Properties props = new Properties();
		String dbType;
		try (InputStream input = ConnectionDB.class.getResourceAsStream("/config.properties")) {
			props.load(input);
			dbType = props.getProperty("db.type");
		} catch (Exception e) {
			throw new RuntimeException("Errore nel caricamento config.properties", e);
		}

		return getConnection(dbType, props);
	}

	private static Connection getConnection(String dbType, Properties props) {
		String prefix = dbType + ".";

		String driver = props.getProperty(prefix + "driver");
		String connURL = props.getProperty(prefix + "url");
		String user = props.getProperty(prefix + "user");
		String password = props.getProperty(prefix + "password");

		Connection connection = null;

		try {
			Utility.log("DataSource.getConnection() driver = " + driver);
			Class.forName(driver);

			Utility.log("DataSource.getConnection() connURL = " + connURL);
			connection = DriverManager.getConnection(connURL, user, password);

		}
		catch (ClassNotFoundException e) {
			new Exception(e.getMessage());
			Utility.log("ErroreCNF " + e.getMessage());
		}
		catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.log("ErroreSQL " + e.getMessage());
		}
		return connection;
	}

}
