package carsharing.model;

import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.dao.DbCarDao;
import carsharing.dao.DbCompanyDao;
import carsharing.jdbc.connection.JDBConnection;

import java.util.Map;
import java.util.Scanner;

public class LoginManager {

    private final CompanyDao companyDao;

    private final CarDao carDao;

    private final Menu startingMenu;

    public LoginManager(final JDBConnection con, final Menu startingMenu) {
        companyDao = new DbCompanyDao(con);
        carDao = new DbCarDao(con);
        this.startingMenu = startingMenu;
    }

    public void loginMenu(final Scanner scanner) {
        while (true) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            if (scanner.hasNextInt()) {
                int optionMenu = scanner.nextInt();
                System.out.println();
                if (optionMenu == 0) {
                    startingMenu.mainMenu();
                    System.exit(0);
                } else if (optionMenu == 1) {
                    final Map<Integer, Company> companies = companyDao.selectAllCompanies();
                    if (!companies.isEmpty()) {
                        companyListMenu(scanner, companies);
                        System.out.println();
                    } else {
                        System.out.println("The company list is empty!\n");
                    }
                } else if (optionMenu == 2) {
                    // It's necessary because of the character of new line remained in the scanner after nextInt call.
                    scanner.nextLine();
                    System.out.println("Enter the company name:");
                    if (scanner.hasNextLine()) {
                        companyDao.add(new Company(scanner.nextLine()));
                    }
                }
            }
        }
    }

    private void companyListMenu(final Scanner scanner, Map<Integer, Company> companies) {
        while (true) {
            System.out.println("Choose the company:");
            companies.forEach(
                    (id, company) -> System.out.printf("%d. %s%n", id, company.getName())
            );
            System.out.println("0. Back");
            if (scanner.hasNextInt()) {
                int optionMenu = scanner.nextInt();
                System.out.println();
                if (optionMenu == 0) return;
                else if (companies.containsKey(optionMenu)){
                    companyMenu(scanner, companies.get(optionMenu));
                }
            }
        }

    }

    private void companyMenu(final Scanner scanner, final Company company) {
        while (true) {
            System.out.printf("%s company:\n", company.getName());
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            if (scanner.hasNextInt()) {
                int optionMenu = scanner.nextInt();
                System.out.println();
                if (optionMenu == 0) {
                    loginMenu(scanner);
                    return;
                } else if (optionMenu == 1) {
                    final Map<Integer, Car> companyCars = carDao.selectCarsByCompanyId(company.getId());
                    if (!companyCars.isEmpty()) {
                        int indexList = 0;
                        System.out.println("Car list:");
                        for (Map.Entry<Integer, Car> car : companyCars.entrySet()) {
                            System.out.printf("%d. %s%n", ++indexList, car.getValue().getName());
                        }
                        System.out.println();
                    } else {
                        System.out.println("The car list is empty!\n");
                    }
                } else if (optionMenu == 2) {
                    // It's necessary because of the character of new line remained in the scanner after nextInt call.
                    scanner.nextLine();
                    System.out.println("Enter the car name:");
                    if (scanner.hasNextLine()) {
                        carDao.add(new Car(scanner.nextLine(), company.getId()));
                    }
                }
            }
        }
    }
}
