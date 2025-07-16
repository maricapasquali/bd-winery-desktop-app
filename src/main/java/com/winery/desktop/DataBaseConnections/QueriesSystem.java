package com.winery.desktop.DataBaseConnections;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.winery.desktop.Model.Login;
import com.winery.desktop.Model.PersonCompany;
import com.winery.desktop.Model.builder.LoginBuilderImpl;
import com.winery.desktop.Model.builder.PersonCompanyBuilderImpl;
import com.winery.desktop.Utility.Utility;
import com.winery.desktop.Exception.NeverLoggedIn;
import com.winery.desktop.Exception.NotInSystemException;

public class QueriesSystem {

	private static String inTheSystem = "Select * From Persona_Azienda Where LOWER(Nome)=LOWER(?) And LOWER(Cognome)=LOWER(?) ";
	private static String insertLogin = "Insert into Login (ID_Aziendale, Password ) values(?, ?)";
	private static String loggedIn = "Select * From Login Where ID_Aziendale = ?";
	private static String updateDateLogin = "Update Login set Ultimo_Accesso = ? Where ID_Aziendale = ?";
	
	private static String selectDateAssumption = "Select Data From Assunzione Where ID_Operaio=?";
	private static String selectAllHoursWorker = "Select Sum(OP.Ore_Lavoro) as Ore_Totali_Lavoro From ("
			+ "SELECT Sum(GR.Ore_Lavoro) as Ore_Lavoro FROM Persona_Azienda AS PA inner join Gruppo_Raccolta "
			+ "AS GR ON (GR.ID_Operaio = PA.ID) WHERE PA.ID=? UNION "
			+ "SELECT Sum(GP.Ore_Lavoro) FROM Persona_Azienda AS PA inner join Gruppo_Pigiatura AS GP "
			+ "ON (GP.ID_Operaio = PA.ID) WHERE PA.ID=? UNION "
			+ "SELECT Sum(GSV.Ore_Lavoro) FROM Persona_Azienda AS PA inner join Gruppo_Svinatura AS GSV "
			+ "ON (GSV.ID_Operaio = PA.ID) WHERE PA.ID=? UNION "
			+ "SELECT Sum(GSF.Ore_Lavoro) FROM Persona_Azienda AS PA inner join Gruppo_Sfecciatura AS GSF "
			+ "ON (GSF.ID_Operaio = PA.ID) WHERE PA.ID=?) as OP";

	public static PersonCompany IsInSystem(final String name, final String lastName) throws NotInSystemException {
		PersonCompany p = null;
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;
		ResultSet result ;
		try {
			statement = connection.prepareStatement(inTheSystem);
			statement.setString(1, name);
			statement.setString(2, lastName);

			result = statement.executeQuery();
			Utility.log("Exexute Query ... ");

			while (result.next()) {
				p = new PersonCompanyBuilderImpl().setID(result.getLong("ID"))
												  .setName(result.getString("Nome"))
												  .setLastName(result.getString("Cognome"))
												  .setStreet(result.getString("Ind_Via"))
												  .setStreetNumber(result.getShort("Ind_Civico"))
												  .setStreetCity(result.getString("Ind_Citta"))
												  .setPhoneNumber(result.getString("Telefono"))
												  .setDataBirth(result.getDate("Data_di_Nascita"))
												  .setType(result.getString("Tipo_Login"))
												  .build();
				if (p.isEmployee()) {
					p.setMonthlySalary(result.getDouble("Stipendio_Mensile"));
				}
				Utility.log("Set values in local ...");
			}
		
			statement.close();
			if(p==null) {
				throw new NotInSystemException();
			}
			if (!p.isAdmin()) {
				try {
					statement = connection.prepareStatement(selectDateAssumption);
					statement.setLong(1, p.getID());

					result = statement.executeQuery();
					Utility.log("Exexute Query ... ");

					while (result.next()) {
						p.setDateAssumption(result.getDate("Data"));
						Utility.log("Set values in local ...");
					}
				} catch (SQLException e) {
					new Exception(e.getMessage());
					Utility.logError("Errore: " + e.getMessage());
				} finally {
					try {
						if (statement != null)
							statement.close();
					} catch (SQLException e) {
						new Exception(e.getMessage());
						Utility.logError("Errore: " + e.getMessage());
					}
				}
			}
			Utility.log(p);
		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());
		} finally {
			
			try {
				if (statement != null)
					statement.close();
				
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
			}
		}
		return p;
	}

	public static Login isLoddedIn(final long id) throws NeverLoggedIn {
	
		Login l = null;
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(loggedIn);
			statement.setLong(1, id);

			final ResultSet result = statement.executeQuery();
			Utility.log("Exexute Query ... ");

			while (result.next()) {
				l = new LoginBuilderImpl().setIdAziendale(result.getShort("ID_Aziendale"))
						.setPassword(result.getString("Password")).setLastlogin(result.getDate("Ultimo_Accesso"))
						.build();

				Utility.log("Insert values ...");
			}
			if (l == null) {
				throw new NeverLoggedIn();
			}
			Utility.log(l);
		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
			}
		}
		
		return l;
	}

	public static void insertLogin(final Login log) {

		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(insertLogin);
			statement.setLong(1, log.getIdAziendale());
			statement.setString(2, log.getPassword());

			statement.executeUpdate();
			Utility.log("Exexute Query ... ");

		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
			}
		}
	}

	public static void updateDateLogin(final Date date, final long id) {

		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(updateDateLogin);
			statement.setDate(1, date);
			statement.setLong(2, id);

			statement.executeUpdate();
			Utility.log("Exexute Query ... ");

		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
			}
		}

	}

	public static Integer selectAllHoursWork(final Long id) {
		Integer hoursTot = null;
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(selectAllHoursWorker);
			for(int i=1; i<5; i++)
				statement.setLong(i, id);
			
			final ResultSet result = statement.executeQuery();
			Utility.log("Exexute Query ... ");

			while (result.next()) {
				hoursTot = result.getInt("Ore_Totali_Lavoro");		
				Utility.log("Insert values ...");
			}	
			
		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
			}
		}
		return hoursTot;
	}
}