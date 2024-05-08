package carsharing.dao;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.jdbc.crud.DBOperations;
import carsharing.model.Customer;

public class DbCustomerDao implements CustomerDao {

    private static final String CREATE_CUSTOMER = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(255) NOT NULL UNIQUE," +
            "RENTED_CAR_ID INT, " +
            "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";

    private static final String INSERT_CUSTOMER = "INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) " +
            "VALUES(?, ?)";

    private final DBOperations dbOperations;

    public DbCustomerDao(final JDBConnection con) {
        dbOperations = new DBOperations(con);
        dbOperations.create(CREATE_CUSTOMER);
    }

    @Override
    public void add(final Customer customer) {
        System.out.println(
                dbOperations.insert(INSERT_CUSTOMER, customer) == 1 ? "The customer was added!\n" : "Customer not added.\n"
        );
    }
}
