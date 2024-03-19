package carsharing;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.jdbc.crud.DBOperations;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        JDBConnection H2DBConnection = new JDBConnection(Optional.ofNullable(null));
        try {
            H2DBConnection.createConnection();
            DBOperations operation = new DBOperations(H2DBConnection.getConnection());
            operation.createCompany();
            H2DBConnection.closeConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}