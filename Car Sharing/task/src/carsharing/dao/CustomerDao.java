package carsharing.dao;

import carsharing.model.Customer;

import java.util.Map;

public interface CustomerDao {

    void add(final Customer customer);

    Map<Integer, Customer> selectAllCustomers();
}
