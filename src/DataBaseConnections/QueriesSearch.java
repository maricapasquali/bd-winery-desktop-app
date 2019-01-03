package DataBaseConnections;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Pair;

import Model.Buying;
import Model.PersonCompany;
import Model.Product;
import Model.builder.BuyingBuilderImpl;
import Model.builder.GrapeBuilderImpl;
import Model.builder.PersonCompanyBuilderImpl;
import Model.builder.PhaseProductionBuilderImpl;
import Model.builder.ProductBuilderImpl;
import Model.enumeration.PhaseProductionWine;
import Model.enumeration.TypeProduct;
import Utility.Utility;

public class QueriesSearch {

	private static Map<Long, Pair<Product, Product>> products;
	
	private static String searchInfoWine = "Select S.Data, V.Quantita, V.Quantita_Attuale, V.Botte From Vino V inner join Sfecciatura S"
			+ " on (V.ID_Sfecciatura = S.ID_Fase) Where S.Uva=?";
	private static String searchBuyClient = "Select A.Data, A.Prezzo_Totale, A.ID_Aziendale From Acquisto A Where A.ID_Cliente = ?";

	private static String selectAllPartTime = "Select PA.ID, PA.Nome, PA.Cognome From Persona_Azienda PA Where PA.Tipo_Login = 'Part-time'";

	private static String searchProduct;

	public static List<Product> searchWineInfo(final String nameGrape) {
		List<Product> wines = null;
		Product p = null;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(searchInfoWine);
			statement.setString(1, nameGrape);
			final ResultSet result = statement.executeQuery();
			Utility.log("Exexute Query ... ");
			if (result != null) {
				wines = new ArrayList<>();
			}
			while (result.next()) {
				p = new ProductBuilderImpl()
						.setPhaseProd(new PhaseProductionBuilderImpl().setDate(result.getDate("Data")).build())
						.setQuantity(result.getDouble("Quantita"))
						.setQuantityActual(result.getDouble("Quantita_Attuale")).setCask(result.getInt("Botte"))
						.build();
				Utility.log("Insert values ...");
				wines.add(p);
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
		return wines;
	}

	public static List<Buying> searchBuyingClient(final Long idClient) {
		List<Buying> buy = null;
		Buying b = null;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(searchBuyClient);
			statement.setLong(1, idClient);
			final ResultSet result = statement.executeQuery();
			Utility.log("Exexute Query ... ");
			if (result != null) {
				buy = new ArrayList<>();
			}
			while (result.next()) {
				b = new BuyingBuilderImpl().setIdClient(idClient).setDateBuying(result.getDate("Data"))
						.setPriceTot(result.getDouble("Prezzo_Totale")).setIdCompany(result.getLong("ID_Aziendale"))
						.build();
				Utility.log("Insert values ...");
				buy.add(b);
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
		return buy;
	}

	public static Map<Pair<String, Date>, Integer> searchHoursPartTime(final Long idWorker) {
		final Map<Pair<String, Date>, Integer> hours = new HashMap<>();
		PreparedStatement statement = null;
		for (PhaseProductionWine w : PhaseProductionWine.values()) {
			final Connection connection = ConnectionDB.getUcanaccessConnection();
			Utility.log("Exexute Connection ...");
			final String searchHoursPartTime = "Select F.Data, G.Ore_Lavoro FROM GRUPPO_" + w + " G Inner Join " + w
					+ " F on (G.ID_" + w + " = F.ID_Fase)" + "	WHERE ID_Operaio = ?";
			try {

				statement = connection.prepareStatement(searchHoursPartTime);
				statement.setLong(1, idWorker);
				final ResultSet result = statement.executeQuery();
				Utility.log("Exexute Query ... ");

				while (result.next()) {
					hours.put(Pair.of(w.toString(), result.getDate("Data")), result.getInt("Ore_Lavoro"));
					Utility.log("Insert values ...\n hours=" + hours);
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
		}

		return hours.entrySet().stream()
				.sorted(Map.Entry.comparingByKey((d1, d2) -> Long.compare(d1.right.getTime(), d2.right.getTime())))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	public static List<PersonCompany> selectAllPartTime() {
		List<PersonCompany> partTimes = null;
		PersonCompany p;
		final Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;
		try {
			statement = connection.createStatement();

			final ResultSet result = statement.executeQuery(selectAllPartTime);
			Utility.log("Exexute Query ... ");
			if (result != null) {
				partTimes = new ArrayList<>();
			}
			while (result.next()) {
				p = new PersonCompanyBuilderImpl().setID(result.getLong("ID")).setName(result.getString("Nome"))
						.setLastName(result.getString("Cognome")).build();
				partTimes.add(p);
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
		return partTimes;
	}

	public static Map<Long, Pair<Product, Product>> searchProductsOfPhase(final PhaseProductionWine phase, final int year) {
		Statement statement = null;
		products = new HashMap<>();
		if (PhaseProductionWine.withProducts().contains(phase)) {
			products = new HashMap<>();
			for (TypeProduct p : TypeProduct.productOf(phase)) {
				searchProduct = "Select F.ID_Fase, F.Uva, P.Quantita "
						+ (TypeProduct.withCask().contains(p) ? ", P.Botte " : "") + "From " + phase + " F Inner Join "
						+ p + " P on (P.ID_" + phase + " = F.ID_Fase) Where DatePart ('yyyy', [Data]) = "+year;
				Utility.log(searchProduct);
				connectionProduct(statement, p, phase);
			}
		} else {
			searchProduct = "Select ID_Fase, Uva, Quantita From Raccolta Where DatePart ('yyyy', [Data]) = "+year;
			connectionProduct(statement, null, phase);
		}
		return products;
	}

	private static void connectionProduct(Statement statement, final TypeProduct p, final PhaseProductionWine phase) {
		Connection connection = ConnectionDB.getUcanaccessConnection();
		Utility.log("Exexute Connection ...");
		try {
			statement = connection.createStatement();
			final ResultSet result = statement.executeQuery(searchProduct);

			Utility.log("Exexute Query ... ");
			while (result.next()) {
				final Long idPhase = result.getLong("ID_Fase");

				final Product prod = new ProductBuilderImpl().setName(p)
						.setPhaseProd(new PhaseProductionBuilderImpl().setId(idPhase).setPpw(phase)
								.setGrape(new GrapeBuilderImpl().setName(result.getString("Uva")).build()).build())
						.setQuantity(result.getDouble("Quantita")).build();
				if (TypeProduct.withCask().contains(p)) {
					prod.setCask(result.getInt("Botte"));
				}

				if (products.containsKey(idPhase)) {
					products.replace(idPhase, Pair.of(products.get(idPhase).left, prod));
				} else {
					if (p == null) {
						products.put(idPhase, Pair.of(prod, null));
					} else {
						products.put(idPhase, TypeProduct.firstProductForEachPhase().contains(p) ? Pair.of(prod, null)
								: Pair.of(null, prod));
					}
				}
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
	}
}
