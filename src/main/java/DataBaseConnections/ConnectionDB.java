package DataBaseConnections;

import java.sql.*;

import Utility.Utility;
import View.Personalmenu.MenuLogin;

public class ConnectionDB {

	public static Connection getUcanaccessConnection() {

		final String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
		final String connURL = "jdbc:ucanaccess://" + MenuLogin.getDataDase().getAbsolutePath();
		
		Connection connection = null;
		
		try {
			Utility.log("DataSource.getConnection() driver = " + driver);
			Class.forName(driver);

			Utility.log("DataSource.getConnection() connURL = " + connURL);
			connection = DriverManager.getConnection(connURL);

		} catch (ClassNotFoundException e) {
			new Exception(e.getMessage());
			Utility.log("ErroreCNF " + e.getMessage());
		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.log("ErroreSQL " + e.getMessage());
		}
		return connection;
	}

}
