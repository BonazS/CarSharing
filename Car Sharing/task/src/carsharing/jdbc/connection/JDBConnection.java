package carsharing.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class JDBConnection {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_PATH = "./src/carsharing/db/";
    private static final String DB_URL = "jdbc:h2:%s".formatted(DB_PATH);
    private static final String DEFAULT_DB_FILE_NAME = "carsharing";
    private final String dbFileName;
    private Connection con;

    public JDBConnection(Optional<String> dbFileName) {
        this.dbFileName = dbFileName.orElse(DEFAULT_DB_FILE_NAME);
    }

    public Connection getConnection() {
        return con;
    }

    public void createConnection() throws Exception {
        // STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);

        // STEP 2: Open a connection
        try {
            con = DriverManager.getConnection(DB_URL + dbFileName);
            if (con.isValid(5)) {
                System.out.println("Connection established.");
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            System.out.println("Connection not established.");
        }
    }

    public void closeConnection() {
        try {
            con.close();
            System.out.println("Connection to database closed.");
        } catch (SQLException e) {
            System.out.println("Error closing connection.");
            throw new RuntimeException(e);
        }
    }
}
