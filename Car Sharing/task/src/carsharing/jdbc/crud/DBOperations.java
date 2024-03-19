package carsharing.jdbc.crud;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperations {

    private final Connection con;

    public DBOperations(Connection con) {
        this.con = con;
    }

    public void createCompany() {
        System.out.println("Creating table in given database...");
        try (Statement stmt = con.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SHOW TABLES");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            String query = "DROP TABLE IF EXISTS COMPANY";
            stmt.executeUpdate(query);
            query = "CREATE TABLE COMPANY " +
                    "(ID INTEGER NOT NULL," +
                    " NAME VARCHAR(255)," +
                    " PRIMARY KEY (ID))";
            stmt.executeUpdate(query);
            System.out.println("Created table COMPANY in given database.");
        } catch (SQLException e) {
            System.out.println("Statement not created.");
            throw new RuntimeException(e);
        }
    }


}
