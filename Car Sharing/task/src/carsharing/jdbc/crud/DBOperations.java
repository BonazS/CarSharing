package carsharing.jdbc.crud;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;

import java.sql.*;

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

    public int insert(final String query, final Car car) {
        try (PreparedStatement stmt = con.getConnection().prepareStatement(query)) {
            stmt.setString(1, car.getName());
            stmt.setInt(2, car.getCompanyId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert Statement not created.");
            throw new RuntimeException(e);
        }
    }

    public int insert(final String query, final Customer customer) {
        try (PreparedStatement stmt = con.getConnection().prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setNull(2, Types.INTEGER);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert Statement not created.");
            throw new RuntimeException(e);
        }
    }

    public int update(final String query, final Customer customer, final Car car) {
        try (PreparedStatement stmt = con.getConnection().prepareStatement(query)) {
            if (customer.getRentedCarId() == null) {
                stmt.setInt(1, car.getId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setInt(2, customer.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update Statement not created.");
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
