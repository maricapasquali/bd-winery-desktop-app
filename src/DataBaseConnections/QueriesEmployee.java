package DataBaseConnections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import Model.Buying;
import Model.Client;
import Model.Product;
import Model.builder.ClientBuilderImpl;
import Model.builder.GrapeBuilderImpl;
import Model.builder.PhaseProductionBuilderImpl;
import Model.builder.ProductBuilderImpl;
import Model.enumeration.TypeProduct;
import Utility.Utility;
import exception.InsertFailedException;
import exception.JustInsertException;

public class QueriesEmployee {

	private static PreparedStatement statement = null;
	private static Long idBuy = null;
	private static PreparedStatement statementUpdateQuantity = null;
	
	private static final String insertClient = "Insert into Cliente(Nome, Cognome, Ind_Via, Ind_Civico, Ind_Citta, Telefono) values (?, ?, ?, ?, ?, ?)";
	private static final String insertBuy = "Insert into Acquisto (Data, ID_Cliente, Prezzo_Totale, ID_Aziendale) values (?,?,?,?)";
	private static final String selectClients = "Select ID_Cliente, Nome, Cognome From Cliente";
	private static final String insertDetailBuy = "Insert into Dettagli_Acquisto (ID_Acquisto, ID_Vino, Numero_Bottiglie,"
			+ " Prezzo_Totale_Bottiglia, Numero_Litri, Prezzo_Totale_Damigiana) values (?, ?, ?, ?, ?, ?)";
	private static final String selectIdBuy = "Select ID_Acquisto From Acquisto Where Data = ? And ID_Cliente = ?";
	private static final String selectAllWine = "Select FU.ID_Fase, FU.Uva, FU.Tipologia, FU.Prezzo_al_Litro, FU.Prezzo_a_Bottiglia, FU.Data, "
			+ "V.Quantita_Attuale, V.Botte From Vino V inner join ( Select * From Sfecciatura SF inner join Uva U on (U.Nome_Uva = SF.Uva) ) "
			+ "as FU on (V.ID_Sfecciatura = FU.ID_Fase) Where V.Quantita_Attuale <> 0";
	private static final String updateQuantityWines = "Update Vino set Quantita_Attuale = ? Where ID_Sfecciatura = ? ";
	
	public static boolean insertClient(final Client client) throws JustInsertException {
		boolean success = true;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(insertClient);
			statement.setString(1, client.getName());
			statement.setString(2, client.getLastName());
			statement.setString(3, client.getStreet());
			statement.setInt(4, client.getStreetNumber());
			statement.setString(5, client.getStreetCity());
			statement.setString(6, client.getPhoneNumber());
			statement.executeUpdate();
			Utility.log("Exexute Query ...");

		} catch (SQLException e) {	
			Utility.logError("Errore: " + e.getMessage());
			if(e.getErrorCode()==-104) {
				throw new JustInsertException("Cliente");
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
				Utility.logError("Errore: " + e.getMessage());
				success = false;
			}
		}
		return success;
	}

	public static void insertBuy(final Buying buy) throws InsertFailedException {
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		try {
			statement = connection.prepareStatement(insertBuy);
			statement.setDate(1, buy.getDateBuying());
			statement.setLong(2, buy.getIdClient());
			statement.setDouble(3, buy.getPriceTot());
			statement.setLong(4, buy.getIdCompany());
			statement.executeUpdate();
			Utility.log("Exexute Query ...");
			statement.close();
			
			statement = connection.prepareStatement(selectIdBuy);
			statement.setDate(1, buy.getDateBuying());
			statement.setLong(2, buy.getIdClient());
			final ResultSet result = statement.executeQuery();
			while (result.next()) {
				idBuy = result.getLong("ID_Acquisto");
			}
			Utility.log("Exexute Query ... ");
			if (idBuy == null) {
				throw new InsertFailedException();
			}
			statement.close();
			
			
			statement = connection.prepareStatement(insertDetailBuy);
			buy.getCart().entrySet().forEach(e -> {
				try {
					statement.setLong(1, idBuy);
					statement.setLong(2, e.getKey().getPhaseProduction().getId());
					if(e.getValue().left.isPresent()) {
						statement.setInt(3, e.getValue().left.get().left);
						statement.setDouble(4, e.getValue().left.get().right);
					} else {
						statement.setNull(3, Types.NULL);
						statement.setNull(4, Types.NULL);
					}
					if(e.getValue().right.isPresent()) {
						statement.setDouble(5, e.getValue().right.get().left);
						statement.setDouble(6, e.getValue().right.get().right);
					} else {
						statement.setNull(5, Types.NULL);
						statement.setNull(6, Types.NULL);
					}
					statement.executeUpdate();
					Utility.log("Exexute Query ...");
				} catch (SQLException e1) {
					new Exception(e1.getMessage());
					Utility.logError("Errore: " + e1.getMessage());
				}						
			});
			
		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
				idBuy = null;
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
				
			}
		}		
	}
	
	public static void updateQuantityWine(final Buying buy) {
	
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		try {
			statementUpdateQuantity = connection.prepareStatement(updateQuantityWines);
			buy.getCart().keySet().forEach(w -> {
				try {
					statementUpdateQuantity.setDouble(1, w.getQuantityActual());
					statementUpdateQuantity.setDouble(2, w.getPhaseProduction().getId());
					
					statementUpdateQuantity.executeUpdate();
					Utility.log("Exexute Query ...");
				} catch (SQLException e) {
					new Exception(e.getMessage());
					Utility.logError("Errore: " + e.getMessage());
				}	
			});
		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());

		} finally {
			try {
				if (statementUpdateQuantity != null)
					statementUpdateQuantity.close();
				if (connection != null)
					connection.close();

			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
			}
		}
	}
	
	public static List<Client> listOfClients() {
		List<Client> clients = null;
		Client c = null;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;

		try {
			statement = connection.createStatement();
			final ResultSet result = statement.executeQuery(selectClients);
			Utility.log("Exexute Query ...");
			clients = new ArrayList<>();
			while (result.next()) {
				c = new ClientBuilderImpl().setID(result.getLong("ID_Cliente")).setName(result.getString("Nome"))
						.setLastName(result.getString("Cognome")).build();
				clients.add(c);
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
		return clients;
	}

	public static List<Product> listOfWines() {

		List<Product> wines = null;
		Product w = null;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;
		try {
			statement = connection.createStatement();
			final ResultSet result = statement.executeQuery(selectAllWine);
			if (result != null) {
				wines = new ArrayList<>();
			}

			while (result.next()) {
				w = new ProductBuilderImpl().setName(TypeProduct.VINO)
						.setPhaseProd(new PhaseProductionBuilderImpl().setId(result.getLong("ID_Fase"))
								.setGrape(new GrapeBuilderImpl().setName(result.getString("Uva"))
										.setType(result.getString("Tipologia"))
										.setPriceLiter(result.getDouble("Prezzo_al_Litro"))
										.setPriceBottle(result.getDouble("Prezzo_a_Bottiglia")).build())
								.setDate(result.getDate("Data")).build())
						.setQuantityActual(result.getDouble("Quantita_Attuale")).setCask(result.getInt("Botte"))
						.build();
				wines.add(w);
			}
			Utility.log("Exexute Query ... \nwines=" + wines);
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
		return wines;
	}
}
