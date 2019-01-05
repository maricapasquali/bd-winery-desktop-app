package DataBaseConnections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import Model.Cask;
import Model.Grape;
import Model.Harvester;
import Model.PersonCompany;
import Utility.Utility;
import exception.InsertFailedException;
import exception.JustInsertException;


public class QueriesAdmin {

	private static final String insertWorker = "Insert into Persona_Azienda (Nome,Cognome, Ind_Via, Ind_Civico, Ind_Citta, Telefono, "
			+ "Data_di_Nascita, Tipo_Login, Stipendio_Mensile) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String selectIdWorker = "Select ID From Persona_Azienda Where Nome = ? And Cognome = ?";
	private static final String insertAssumption = "Insert into Assunzione (ID_Operaio, Data, ID_Proprietario) "
			+ "values(? , ?, ?)";
	private static final String insertGrape = "Insert into Uva(Nome_Uva, Tipologia, Prezzo_al_Litro, Prezzo_a_Bottiglia) values (?, ?, ?, ?)";
	private static final String insertCask = "Insert into Botte(ID_Botte, Capacita, Cantina) values (?, ?, ?)";
	private static final String insertHarvesterBorrow = "Insert into Vendemmiatrice(Marca, Modello, Tariffa_oraria) values (?, ?, ?)";
	private static final String insertHarvester = "Insert into Vendemmiatrice(Marca, Modello, Costo) values (?, ?, ?)";

	public static boolean assumptionWorker(final PersonCompany pers, final long idOwner) throws InsertFailedException, JustInsertException {
		boolean success = true;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(insertWorker);
			statement.setString(1, pers.getName());
			statement.setString(2, pers.getLastName());
			statement.setString(3, pers.getStreet());
			statement.setInt(4, pers.getStreetNumber());
			statement.setString(5, pers.getStreetCity());
			statement.setString(6, pers.getPhoneNumber());
			statement.setDate(7, pers.getDataBirth());
			statement.setString(8, pers.getType().toString());
			if (pers.isEmployee()) {
				statement.setDouble(9, pers.getMonthlySalary().get());
			} else {
				statement.setNull(9, Types.NULL);
			}
			statement.executeUpdate();
			Utility.log("Exexute Query ... ");
			statement.close();

			Long idWorker = null;
			statement = connection.prepareStatement(selectIdWorker);
			statement.setString(1, pers.getName());
			statement.setString(2, pers.getLastName());
			final ResultSet result = statement.executeQuery();
			Utility.log("Exexute Query ... ");
			while (result.next()) {
				idWorker = result.getLong("ID");
			}
			if (idWorker == null) {
				throw new InsertFailedException();
			}
			statement.close();

			statement = connection.prepareStatement(insertAssumption);
			statement.setLong(1, idWorker);
			statement.setDate(2, pers.getDateAssumption());
			statement.setLong(3, idOwner);
			statement.executeUpdate();
			Utility.log("Exexute Query ... ");
		} catch (SQLException e) {
			Utility.logError("Errore: " + e.getMessage());
			if(e.getErrorCode()==-104) {
				throw new JustInsertException("Operaio");
			}	
			new Exception(e.getMessage());
			success = false;
		} finally {
			try {
				if (statement != null)
					statement.close();

				if (connection != null) {
					connection.close();
					success = true;
				}
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
			}
		}
		return success;
	}

	public static boolean addGrapes(final Grape g) throws JustInsertException {
		boolean success = true;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(insertGrape);
			statement.setString(1, g.getName());
			statement.setString(2, g.getType().toString());
			statement.setDouble(3, g.getPriceLiter());
			statement.setDouble(4, g.getPriceBottle());
			statement.executeUpdate();
			Utility.log("Exexute Query ...");

		} catch (SQLException e) {
			Utility.logError("Errore: " + e.getMessage());
			if(e.getErrorCode()==-104) {
				throw new JustInsertException("Uva");
			}	
			new Exception(e.getMessage());
			success = false;
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
				success = false;
			}
		}
		return success;
	}

	public static boolean addCask(final Cask c) throws JustInsertException {
		boolean success = true;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(insertCask);
			statement.setInt(1, c.getIDBotte());
			statement.setDouble(2, c.getCapacity());
			statement.setInt(3, c.getWinery());

			statement.executeUpdate();
			Utility.log("Exexute Query ...");

		} catch (SQLException e) {
			Utility.logError("Errore: " + e.getMessage());
			if(e.getErrorCode()==-104) {
				throw new JustInsertException("Botte");
			}	
			new Exception(e.getMessage());
			success = false;
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
				success = false;
			}
		}
		return success;
	}

	public static boolean addHarvester(final Harvester harve) throws JustInsertException {
		boolean success = true;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;
		final boolean isBorrow = harve.isBorrowed();
		try {
			statement = connection.prepareStatement(isBorrow ? insertHarvesterBorrow : insertHarvester);
			statement.setString(1, harve.getBrand());
			statement.setString(2, harve.getModel());
			if (isBorrow) {
				statement.setDouble(3, harve.getHourlyRate());
			} else {
				statement.setDouble(3, harve.getCost());
			}

			statement.executeUpdate();
			Utility.log("Exexute Query ...");

		} catch (SQLException e) {
			Utility.logError("Errore: " + e.getMessage());
			if(e.getErrorCode()==-104) {
				throw new JustInsertException("Vendemmiatrice");
			}	
			new Exception(e.getMessage());
			success = false;
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
				success = false;
			}
		}
		return success;
	}

}
