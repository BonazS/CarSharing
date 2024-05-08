package carsharing.dao;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.jdbc.crud.DBOperations;
import carsharing.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class DbCustomerDao implements CustomerDao {

    private static final String CREATE_CUSTOMER = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(255) NOT NULL UNIQUE," +
            "RENTED_CAR_ID INT, " +
            "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";

    private static final String INSERT_CUSTOMER = "INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) " +
            "VALUES(?, ?)";

    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER";

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

    @Override
    public Map<Integer, Customer> selectAllCustomers() {
        Map<Integer, Customer> customers = new TreeMap<>();
        try (ResultSet customersDBData = dbOperations.select(SELECT_ALL_CUSTOMERS)) {
            if (customersDBData != null) {
                while (customersDBData.next()) {
                    Customer customer = new Customer(customersDBData.getString("NAME"));
                    customer.setId(customersDBData.getInt("ID"));
                    // these next two calls are necessary
                    // because the method wasNull only works on the last get call on database ResultSet
                    final int rentedCarId = customersDBData.getInt("RENTED_CAR_ID");
                    if (customersDBData.wasNull()) {
                        // Set the field to null if it was NULL in the database
                        customer.setRentedCarId(null);
                    } else {
                        customer.setRentedCarId(rentedCarId);
                    }
                    customers.put(customer.getId(), customer);
                }
                return customers;
            }
        } catch (SQLException e) {
            System.out.println("Customers not obtained.");
            throw new RuntimeException(e);
        }
        return customers;
    }
}
