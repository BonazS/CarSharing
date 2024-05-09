package carsharing.model;

import carsharing.dao.*;
import carsharing.jdbc.connection.JDBConnection;

import java.util.Map;
import java.util.Scanner;

public class LoginCustomer {

    private final CustomerDao customerDao;

    private final CompanyDao companyDao;

    private final CarDao carDao;

    private final Menu startingMenu;

    public LoginCustomer(final JDBConnection con, final Menu startingMenu) {
        customerDao = new DbCustomerDao(con);
        companyDao = new DbCompanyDao(con);
        carDao = new DbCarDao(con);
        this.startingMenu = startingMenu;
    }

    public void loginMenu(final Scanner scanner) {
        final Map<Integer, Customer> customers = customerDao.selectAllCustomers();
        if (!customers.isEmpty()) {
            while (true) {
                System.out.println("Customer list:");
                customers.forEach((key, company) -> System.out.printf("%d. %s%n", key, company.getName()));
                System.out.println("0. Back");
                if (scanner.hasNextInt()) {
                    int optionMenu = scanner.nextInt();
                    System.out.println();
                    if (optionMenu == 0) return;
                    else if (customers.containsKey(optionMenu)){
                        customerMenu(scanner, customers.get(optionMenu));
                    }
                }
            }
        } else {
            System.out.println("The customer list is empty!\n");
        }
    }

    private void customerMenu(final Scanner scanner, final Customer customer) {
        while (true) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            if (scanner.hasNextInt()) {
                int optionMenu = scanner.nextInt();
                System.out.println();
                if (optionMenu == 0) {
                    startingMenu.mainMenu();
                    System.exit(0);
                } else if (optionMenu == 1) {
                    /*
                    if (customer.getRentedCarId() != null) {
                        System.out.println();
                        System.out.println("You've already rented a car!");
                        System.out.println();
                    } else {
                        final Map<Integer, Company> companies = companyDao.selectAllCompanies();
                        if (!companies.isEmpty()) {
                            companyListMenu(scanner, companies);
                            System.out.println();
                        } else {
                            System.out.println("The company list is empty!\n");
                        }
                    }
                     */
                } else if (optionMenu == 2) {
                    // It's necessary because of the character of new line remained in the scanner after nextInt call.
                    // scanner.nextLine();
                    System.out.println();
                    if (customer.getRentedCarId() != null) {
                        //carDao.add(new Car(scanner.nextLine(), company.getId()));
                    } else {
                        System.out.println("You didn't rent a car!\n");
                    }
                } else if (optionMenu == 3) {
                    // It's necessary because of the character of new line remained in the scanner after nextInt call.
                    // scanner.nextLine();
                    System.out.println();
                    if (customer.getRentedCarId() != null) {
                        //carDao.add(new Car(scanner.nextLine(), company.getId()));
                    } else {
                        System.out.println("You didn't rent a car!\n");
                    }
                }
            }
        }
    }

    private void companyListMenu(final Scanner scanner, Map<Integer, Company> companies) {
        while (true) {
            int indexList = 0;
            System.out.println("Choose the company:");
            for (Map.Entry<Integer, Company> company : companies.entrySet()) {
                System.out.printf("%d. %s%n", ++indexList, company.getValue().getName());
            }
            System.out.println("0. Back");
            if (scanner.hasNextInt()) {
                int optionMenu = scanner.nextInt();
                System.out.println();
                if (optionMenu == 0) return;
                else if (companies.containsKey(optionMenu)){
                    // companyMenu(scanner, companies.get(optionMenu));
                }
            }
        }
    }

    private void carListMenu(final Scanner scanner, final Company company) {
        final Map<Integer, Car> companyCars = carDao.selectCarsByCompanyId(company.getId());
        if (!companyCars.isEmpty()) {
            int indexList = 0;
            System.out.println("Car list:");
            for (Map.Entry<Integer, Car> car : companyCars.entrySet()) {
                System.out.printf("%d. %s%n", ++indexList, car.getValue().getName());
            }
            System.out.println();
        } else {
            System.out.printf("No available cars in the %s company\n", company.getName());
        }
    }
}
