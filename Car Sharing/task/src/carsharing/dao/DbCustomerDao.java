package carsharing.dao;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.jdbc.crud.DBOperations;
import carsharing.model.Car;
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

    private static final String SELECT_CUSTOMERS_WITH_A_CAR_RENTED = "SELECT * FROM CUSTOMER " +
            "WHERE RENTED_CAR_ID IS NOT NULL";

    private static final String UPDATE_CUSTOMER_RENTED_CAR = "UPDATE CUSTOMER SET RENTED_CAR_ID = ?" +
            "WHERE ID = ?";

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
        final Map<Integer, Customer> customers = new TreeMap<>();
        int key = 0;
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
                    // customers.put(customer.getId(), customer);
                    customers.put(++key, customer);
                }
                return customers;
            }
        } catch (SQLException e) {
            System.out.println("Customers not obtained.");
            throw new RuntimeException(e);
        }
        return customers;
    }

    @Override
    public Map<Integer, Customer> selectCustomersWithACarRented() {
        final Map<Integer, Customer> customersWithACarRented = new TreeMap<>();
        int key = 0;
        try (ResultSet customersDBData = dbOperations.select(SELECT_CUSTOMERS_WITH_A_CAR_RENTED)) {
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
                    customersWithACarRented.put(++key, customer);
                }
                return customersWithACarRented;
            }
        } catch (SQLException e) {
            System.out.println("Customers with a car rented not obtained.");
            throw new RuntimeException(e);
        }
        return customersWithACarRented;
    }

    @Override
    public void rentCar(Customer customer, Car car) {
        final int numberDBRowsUpdated = dbOperations.update(UPDATE_CUSTOMER_RENTED_CAR, customer, car);
        System.out.printf(
                numberDBRowsUpdated == 1 ? "You rented '%s'\n" : "Rented car not updated.\n", car.getName()
        );
        if (numberDBRowsUpdated == 1) {
            customer.setRentedCarId(car.getId());
        }
    }

    @Override
    public void returnRentedCar(Customer customer) {
        final int numberDBRowsUpdated = dbOperations.update(UPDATE_CUSTOMER_RENTED_CAR, customer, null);
        System.out.println(
                numberDBRowsUpdated == 1 ? "You've returned a rented car!\n" : "Rented car not returned.\n"
        );
        if (numberDBRowsUpdated == 1) {
            customer.setRentedCarId(null);
        }
    }


}
