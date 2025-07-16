package com.winery.DataBaseConnections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.winery.Model.Cask;
import com.winery.Model.Grape;
import com.winery.Model.Group;
import com.winery.Model.Harvester;
import com.winery.Model.PersonCompany;
import com.winery.Model.PhaseProduction;
import com.winery.Model.Product;
import com.winery.Model.builder.GrapeBuilderImpl;
import com.winery.Model.builder.PersonCompanyBuilderImpl;
import com.winery.Model.builder.PhaseProductionBuilderImpl;
import com.winery.Model.enumeration.PhaseProductionWine;
import com.winery.Model.enumeration.TypeProduct;
import com.winery.Utility.Utility;
import com.winery.Exception.JustInsertException;
import com.winery.Exception.NotPhaseException;

public class QueriesPartTime {

	private static final String insertPhaseProduction = "Insert into ";
	private static final String selectGrapes = "Select * From Uva";
	private static String insertProduct = "Insert into ";
	private static final String selectCasks = "Select ID_Botte, Capacita From Botte Where ID_Botte Not In ( "
			+ "Select Botte From Mosto Union Select Botte From VNF Union "
			+ "Select distinct Botte From Feccia Union Select Botte From Vino )";
	private static final String selectCaskForFeccia = "Select TB.Tipologia, TB.Botte, B.Capacita From Botte B inner join "
			+ "(Select TS.Tipologia, F.Botte From Feccia as F inner join "
			+ "(Select SF.ID_Fase, U.Tipologia From Uva as U inner join Sfecciatura as SF on (U.Nome_Uva = SF.Uva)) "
			+ "as TS on (F.ID_Sfecciatura = TS.ID_Fase)) as TB on (TB.Botte = B.ID_Botte)";
	private static String insertGroup = "Insert into Gruppo_";
	private static final String selectAllWorkers = "Select ID, Nome, Cognome, Tipo_Login From Persona_Azienda Where Tipo_Login <> 'Admin'";
	private static final String selectAllHarvesters = "Select ID_Vend, Marca, Modello From Vendemmiatrice";

	public static boolean insertPhaseProduction(final PhaseProduction pp) {
		boolean success = true;
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(insertPhaseProduction + (pp.getPpw().toString() + " (Data, Uva"
					+ (pp.isCollection() ? ", Quantita) values (?, ?, ?) " : ") values (?, ?) ")));
			statement.setDate(1, pp.getDate());
			statement.setString(2, pp.getGrape().getName());
			if (pp.isCollection()) {
				statement.setDouble(3, pp.getQuantity().get());
			}
			statement.executeUpdate();
			Utility.log("Exexute Query ... ");

		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());
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

	public static List<Grape> listOfGrapes() {

		List<Grape> grapes = null;
		Grape g = null;
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;

		try {
			statement = connection.createStatement();
			final ResultSet result = statement.executeQuery(selectGrapes);
			Utility.log("Exexute Query ...");
			grapes = new ArrayList<>();
			while (result.next()) {
				g = new GrapeBuilderImpl().setName(result.getString("Nome_Uva")).setType(result.getString("Tipologia"))
						.setPriceLiter(result.getDouble("Prezzo_al_Litro"))
						.setPriceBottle(result.getDouble("Prezzo_a_Bottiglia")).build();
				grapes.add(g);
			}
			Utility.log(grapes);
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
		return grapes;
	}

	public static boolean insertProduct(final Product p) {
		boolean success = true;
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;
		try {
			productQuery(p.getName());
			statement = connection.prepareStatement(insertProduct);
			
			statement.setLong(1, p.getPhaseProduction().getId());
			statement.setDouble(2, p.getQuantity());
			switch (p.getName()) {
			case MOSTO:
				statement.setInt(3, p.getCask().get());
				break;
			case VNF:
				statement.setInt(3, p.getCask().get());
				break;
			case FECCIA:
				statement.setInt(3, p.getCask().get());
				break;
			case VINO:
				statement.setDouble(3, p.getQuantityActual());
				statement.setInt(4, p.getCask().get());
				break;
			default:
				break;
			}

			statement.executeUpdate();
			Utility.log("Exexute Query ... ");
		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());
			success = false;
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
				resetStringProcuct();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
				success = false;
			}
		}
		return success;
	}

	private static void productQuery(final TypeProduct p) {
		insertProduct += p ;
		switch (p) {
		case RASPI:
			insertProduct += " (ID_Pigiatura, Quantita) values (?,?)";
			break;
		case MOSTO:
			insertProduct += " (ID_Pigiatura, Quantita, Botte) values (?, ?, ?)";
			break;
		case VINACCIA:
			insertProduct += " (ID_Svinatura, Quantita) values (?, ?)";
			break;
		case VNF:
			insertProduct += " (ID_Svinatura, Quantita, Botte) values (?, ?, ?)";
			break;	
		case FECCIA:
			insertProduct += " (ID_Sfecciatura, Quantita, Botte) values (?, ?, ?)";
			break;
		case VINO:
			insertProduct += " (ID_Sfecciatura, Quantita, Quantita_Attuale , Botte) values (?, ?, ?, ?)";
			break;
		default:
			break;
		}
	}
	
	public static List<PhaseProduction> selectPhase(final PhaseProduction pp, final String product) throws NotPhaseException, JustInsertException {

		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;
		List<PhaseProduction> phases = null;
		PhaseProduction phase = null;
		List<PhaseProduction> toSet = null; 
		try {
			final String ppw = pp.getPpw().toString() ;
			statement = connection.createStatement();
				
			final ResultSet result = statement.executeQuery("Select ID_Fase, Data, Uva, Quantita From " + ppw +  " left join " +  product 
					+ " on (" + ppw+".ID_Fase = "+ product+".ID_"+ppw + ")");
			
			if (result != null) {
				phases = new ArrayList<>();
			}
			while (result.next()) {
				phase = new PhaseProductionBuilderImpl().setId(result.getLong("ID_Fase")).setPpw(pp.getPpw())
						.setDate(result.getDate("Data")).setGrape(new GrapeBuilderImpl().setName(result.getString("Uva")).build())
						.setQuantity(result.getDouble("Quantita")).build();
				phases.add(phase);
				Utility.log("Exexute Query ... ");
			}
			
			List<PhaseProduction> grapeInterest  = phases.stream().filter(p -> p.getGrape().getName().equals(pp.getGrape().getName()))
					.collect(Collectors.toList());
			if(grapeInterest.isEmpty()) {
				throw new NotPhaseException(pp.getGrape().getName());
			}
			
			toSet = grapeInterest.stream().filter(p -> p.getQuantity().get()==0).collect(Collectors.toList());
			if(toSet.size()==0) {
				throw new JustInsertException("Prodotto");
			}		
			Utility.log(toSet);
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
		return toSet;
	}
	
	public static List<Cask> selectAvailableCasks() {
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;
		List<Cask> casks = null;
		Cask cask = null;
		try {
			statement = connection.createStatement();

			final ResultSet result = statement.executeQuery(selectCasks);
			Utility.log("Exexute Query ... ");
			if (result != null) {
				casks = new ArrayList<>();
			}
			while (result.next()) {
				cask = new Cask();
				cask.setIDBotte(result.getShort("ID_Botte"));
				cask.setCapacity(result.getDouble("Capacita"));
				casks.add(cask);
			}
			Utility.log(casks);
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
		return casks;
	}

	public static Map<String, Cask> selectCasksForFeccia() {
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;
		Map<String, Cask> caskForFeccia = null;
		Cask cask = null;
		try {
			statement = connection.createStatement();

			final ResultSet result = statement.executeQuery(selectCaskForFeccia);
			Utility.log("Exexute Query ... ");
			if (result != null) {
				caskForFeccia = new HashMap<>();
			}
			while (result.next()) {
				cask = new Cask();
				cask.setIDBotte(result.getShort("Botte"));
				cask.setCapacity(result.getDouble("Capacita"));
				caskForFeccia.put(result.getString("Tipologia"), cask);
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
		return caskForFeccia;
	}

	private static void resetStringProcuct() {
		insertProduct = "Insert into ";
	}
	
	public static boolean insertGroup(final Group group) throws NullPointerException {
		boolean success = true;
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		PreparedStatement statement = null;
		try {
			if(group==null) {
				throw new NullPointerException();
			}
			groupQuery(group);
			statement = connection.prepareStatement(insertGroup);

			for (int i = 0; i < group.getWorkerAndHoursWork().size(); i++) {
				statement.setLong(1, new ArrayList<>(group.getWorkerAndHoursWork().keySet()).get(i).getID());
				statement.setLong(2, group.getPhaseProduction().getId());
				final Optional<Integer> hours = new ArrayList<>(group.getWorkerAndHoursWork().values()).get(i);
				if (hours.isPresent()) {
					statement.setInt(3, hours.get());
				} else {
					statement.setNull(3, Types.NULL);
				}
				if (group.getPhaseProduction().isCollection()) {
					if (group.getIdHarvester().isPresent()) {
						statement.setLong(4, group.getIdHarvester().get());
					} else {
						statement.setNull(4, Types.NULL);
					}
				}
				statement.executeUpdate();
				Utility.log("Exexute Query ... ");
			}

		} catch (SQLException e) {
			new Exception(e.getMessage());
			Utility.logError("Errore: " + e.getMessage());
			success = false;
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();

				resetStringGroup();
			} catch (SQLException e) {
				new Exception(e.getMessage());
				Utility.logError("Errore: " + e.getMessage());
				success = false;
			}
		}

		return success;
	}

	private static void groupQuery(final Group g) {
		final String phase = g.getPhaseProduction().getPpw().toString();
		insertGroup += (phase + " (ID_Operaio, ID_" + phase + ", Ore_Lavoro");
		switch (g.getPhaseProduction().getPpw()) {
		case RACCOLTA:
			insertGroup += (", ID_Vend) values(?,?,?,?)");
			break;
		default:
			insertGroup += (") values(?,?,?)");
			break;
		}
	}

	public static List<PhaseProduction> selectPhaseGroup(final String ppw) throws JustInsertException {

		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;
		List<PhaseProduction> phases = null;
		PhaseProduction phase = null;		
		try {
			final String group = "Gruppo_" + ppw;
			statement = connection.createStatement();
						
			final ResultSet result = statement.executeQuery("Select ID_Fase, Data, Uva, ID_Operaio From " + ppw +  " left join " + group 
					+ " on (" + ppw+".ID_Fase = "+ group +".ID_"+ppw + ") Where " + group + ".ID_Operaio is null");
			Utility.log("Exexute Query ... ");
			if (result != null) {
				phases = new ArrayList<>();
			}
			while (result.next()) {
				phase = new PhaseProductionBuilderImpl().setId(result.getLong("ID_Fase")).setPpw(PhaseProductionWine.conversion(ppw))
						.setDate(result.getDate("Data")).setGrape(new GrapeBuilderImpl().setName(result.getString("Uva")).build())
						.build();
				phases.add(phase);
			
			}
			
			if(phases.size()==0) {
				throw new JustInsertException("Gruppo");
			}		
			Utility.log(phases);
		} catch (SQLException  e) {
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
		return phases;
	}
	
	public static List<PersonCompany> selectAllWorker() {
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;
		List<PersonCompany> workers = null;
		PersonCompany worker = null;
		try {
			statement = connection.createStatement();
			final ResultSet result = statement.executeQuery(selectAllWorkers);
			Utility.log("Exexute Query ...");
			if (result != null) {
				workers = new ArrayList<>();
			}
			while (result.next()) {
				worker = new PersonCompanyBuilderImpl().setID(result.getLong("ID")).setName(result.getString("Nome"))
						.setLastName(result.getString("Cognome")).setType(result.getString("Tipo_Login")).build();
				workers.add(worker);
			}
			Utility.log(workers);
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
		return workers;
	}

	public static List<Harvester> selectAllHarvesters() {
		final Connection connection = ConnectionDB.getConnection();
		Utility.log("Exexute Connection ...");
		Statement statement = null;
		List<Harvester> harvesters = null;
		Harvester harvester = null;
		try {
			statement = connection.createStatement();
			final ResultSet result = statement.executeQuery(selectAllHarvesters);
			Utility.log("Exexute Query ... ");
			if (result != null) {
				harvesters = new ArrayList<>();
			}
			while (result.next()) {
				harvester = new Harvester(result.getLong("ID_Vend"), result.getString("Marca"),
						result.getString("Modello"));
				harvesters.add(harvester);
			}
			Utility.log(harvesters);	
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
		return harvesters;
	}
	
	private static void resetStringGroup() {
		insertGroup = "Insert into Gruppo_";
	}

}
