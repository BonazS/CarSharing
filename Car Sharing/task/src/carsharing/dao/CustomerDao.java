package carsharing.dao;

import carsharing.model.Car;
import carsharing.model.Customer;

import java.util.Map;

public interface CustomerDao {

    void add(final Customer customer);

    Map<Integer, Customer> selectAllCustomers();

    Map<Integer, Customer> selectCustomersWithACarRented();

    void rentCar(final Customer customer, final Car car);

    void returnRentedCar(final Customer customer);
}
