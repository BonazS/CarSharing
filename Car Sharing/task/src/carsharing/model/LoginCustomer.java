package carsharing.model;

import carsharing.dao.CustomerDao;
import carsharing.dao.DbCustomerDao;
import carsharing.jdbc.connection.JDBConnection;

import java.util.Map;
import java.util.Scanner;

public class LoginCustomer {

    private final CustomerDao customerDao;

    private final Menu startingMenu;

    public LoginCustomer(final JDBConnection con, final Menu startingMenu) {
        customerDao = new DbCustomerDao(con);
        this.startingMenu = startingMenu;
    }

    public void loginMenu(final Scanner scanner) {
        final Map<Integer, Customer> customers = customerDao.selectAllCustomers();
        if (!customers.isEmpty()) {
            while (true) {
                System.out.println("Customer list:");
                customers.forEach(
                        (id, company) -> System.out.printf("%d. %s%n", id, company.getName())
                );
                System.out.println("0. Back");
                if (scanner.hasNextInt()) {
                    int optionMenu = scanner.nextInt();
                    System.out.println();
                    if (optionMenu == 0) return;
                    /*
                    else if (customers.containsKey(optionMenu)){
                        companyMenu(scanner, companies.get(optionMenu));
                    }
                     */
                }
            }
        } else {
            System.out.println("The customer list is empty!\n");
        }
    }
}
