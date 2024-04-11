package carsharing.jdbc.crud;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.model.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperations {

    private final JDBConnection con;

    public DBOperations(final JDBConnection con) {
        this.con = con;
    }

    public void create(final String query) {
        System.out.println("Creating table in given database...");
        try (Statement stmt = con.getConnection().createStatement()) {
            boolean resultQueryExec = stmt.execute(query);
            if (!resultQueryExec) {
                System.out.println("Car Sharing database's tables:");
                ResultSet resultSet = stmt.executeQuery("SHOW TABLES");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
            } else System.out.println("Table not created.");
        } catch (SQLException e) {
            System.out.println("Create Statement not created.");
            throw new RuntimeException(e);
        }
    }

    public int insert(final String query, final Company company) {
        try (PreparedStatement stmt = con.getConnection().prepareStatement(query)) {
            stmt.setString(1, company.getName());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert Statement not created.");
            throw new RuntimeException(e);
        }
    }

    public ResultSet select(final String query) {
        final ResultSet h2DBResultSet;
        try {
            Statement stmt = con.getConnection().createStatement();
            h2DBResultSet = stmt.executeQuery(query);
            if (!stmt.isClosed()) {
                return h2DBResultSet;
            }
        } catch (SQLException e) {
            System.out.println("Select Statement not created.");
            throw new RuntimeException(e);
        }
        return h2DBResultSet;
    }

}
