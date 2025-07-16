package script;

import com.winery.desktop.DataBaseConnections.ConnectionDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DataMigratorAccessToPostgres is a utility class for migrating database schema
 * and data
 * from a Microsoft Access database to a PostgreSQL database.
 * <p>
 * The migration process includes:
 * <ul>
 * <li>Creating tables in the PostgreSQL database based on a provided schema DDL
 * file.</li>
 * <li>Copying data from all tables in the Access database to the corresponding
 * tables in PostgreSQL,
 * using upsert logic (INSERT ... ON CONFLICT DO UPDATE) to handle existing
 * rows.</li>
 * <li>Adding constraints (such as foreign keys) to the PostgreSQL tables after
 * data migration,
 * based on the same schema DDL file.</li>
 * </ul>
 * <p>
 * The class expects the schema DDL file to be available in the classpath and
 * uses helper methods to parse and execute SQL statements, remove comments,
 * and handle errors.
 * <p>
 *
 * <p>
 * Note: This class assumes that table and column names are compatible between
 * Access and PostgreSQL, and that the schema DDL file is properly formatted
 * for PostgreSQL.
 * </p>
 */
public class DataMigratorAccessToPostgres {

    public static void main(String[] args) {

        final String fromDatabase = "access";
        final String toDatabase = "postgres";

        final String schemaToMigrate = "db/schema/db_" + toDatabase + ".ddl";
        try (Connection pgConnection = ConnectionDB.getConnection(toDatabase)) {

            // create tables
            createTablesFromSchema(pgConnection, schemaToMigrate);

            // import data
            try (Connection accessConnection = ConnectionDB.getConnection(fromDatabase)) {
                DatabaseMetaData meta = accessConnection.getMetaData();
                ResultSet tables = meta.getTables(null, null, null, new String[] { "TABLE" });
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME").toLowerCase();
                    System.out.println("Transferring data from the table " + tableName);
                    copyTableData(accessConnection, pgConnection, tableName);
                }
                System.out.println("All data migrated successfully!");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

            // add constraints to tables
            addConstraintsFromSchema(pgConnection, schemaToMigrate);

            System.out.println("Migration completed successfully.");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void createTablesFromSchema(Connection pgConn, String schemaPath) {
        executeStatementsFromSchema(pgConn, schemaPath, "CREATE");
    }

    private static void addConstraintsFromSchema(Connection pgConn, String schemaPath) {
        executeStatementsFromSchema(pgConn, schemaPath, "ALTER");
    }

    private static void copyTableData(Connection fromConnection, Connection toConnection, String tableName) {
        try {
            DatabaseMetaData accessMeta = fromConnection.getMetaData();

            // COLUMNS
            List<String> columns = new ArrayList<>();
            ResultSet cols = accessMeta.getColumns(null, null, tableName, null);
            while (cols.next()) {
                columns.add(cols.getString("COLUMN_NAME"));
            }
            if (columns.isEmpty())
                return;

            // PRIMARY KEYS
            ResultSet pk = accessMeta.getPrimaryKeys(null, null, tableName);
            List<String> pkCols = new ArrayList<>();
            while (pk.next()) {
                pkCols.add(pk.getString("COLUMN_NAME"));
            }
            if (pkCols.isEmpty())
                return;

            List<String> values = Stream.generate(() -> "?").limit(columns.size()).collect(Collectors.toList());

            // SET clause per UPDATE
            StringBuilder updateSet = new StringBuilder();
            StringBuilder whereDiff = new StringBuilder();
            for (String col : columns) {
                if (!pkCols.contains(col)) {
                    updateSet.append(col).append(" = EXCLUDED.").append(col).append(", ");
                    whereDiff.append(tableName).append(".").append(col).append(" IS DISTINCT FROM EXCLUDED.")
                            .append(col).append(" OR ");
                }
            }
            if (updateSet.length() == 0)
                return;
            if (whereDiff.length() == 0)
                return;

            updateSet.setLength(updateSet.length() - 2); // remove last comma
            whereDiff.setLength(whereDiff.length() - 4); // remove last OR

            String insertSql = String.format(
                    "INSERT INTO %s (%s)\n VALUES (%s)\n ON CONFLICT (%s) DO UPDATE SET %s\n WHERE %s;",
                    tableName,
                    String.join(", ", columns),
                    String.join(", ", values),
                    String.join(", ", pkCols),
                    updateSet,
                    whereDiff);

            PreparedStatement pgStmt = toConnection.prepareStatement(insertSql);

            Statement accessStmt = fromConnection.createStatement();
            ResultSet rs = accessStmt.executeQuery(String.format("SELECT * FROM %s;", tableName));

            int count = 0;
            while (rs.next()) {
                for (int i = 0; i < columns.size(); i++) {
                    Object value = rs.getObject(columns.get(i));
                    pgStmt.setObject(i + 1, value);
                }

                int affected = pgStmt.executeUpdate(); // return 1 if the row has been modified/inserted
                count += affected;
            }

            System.out.printf("Table '%s': %d rows migrated.%n", tableName, count);
        } catch (SQLException e) {
            System.err.printf("Error in table '%s': %s%n", tableName, e.getMessage());
        }
    }

    private static void executeStatementsFromSchema(Connection connection, String schemaPath, String action) {
        try (InputStream is = DataMigratorAccessToPostgres.class.getClassLoader().getResourceAsStream(schemaPath)) {
            if (is == null)
                throw new NullPointerException();

            // Read all content in one string
            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Split queries using semicolons
            String[] statements = sql.split("(?<!;)\\s*;\\s*(?!;)");

            for (String stmt : statements) {
                String trimmedStmt = removeSqlComments(stmt.trim());
                // Run only if it starts with "action" (case-insensitive)
                if (trimmedStmt.toUpperCase().startsWith(action)) {
                    try (Statement s = connection.createStatement()) {
                        s.execute(trimmedStmt);
                        System.out.printf("Execute: %s%n", trimmedStmt);
                    } catch (SQLException ex) {
                        System.err.printf("Error in '%s': %s%n", trimmedStmt,  ex.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String removeSqlComments(String sql) {
        // Remove single line comments --
        sql = sql.replaceAll("(?m)--.*?$", "");
        // Remove multiline comments /* ... */
        sql = sql.replaceAll("/\\*.*?\\*/", "");
        // Remove blank lines or lines with spaces only
        sql = sql.replaceAll("(?m)^\\s*$[\r\n]*", "");
        return sql;
    }

}
