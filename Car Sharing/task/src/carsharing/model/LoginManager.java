package carsharing.model;

import carsharing.dao.CompanyDao;
import carsharing.dao.DbCompanyDao;
import carsharing.jdbc.connection.JDBConnection;

import java.util.Map;
import java.util.Scanner;

public class LoginManager {

    private final CompanyDao companyDao;

    public LoginManager(final JDBConnection con) {
        companyDao = new DbCompanyDao(con);
    }

    public void loginMenu(final Scanner scanner) {
        while (true) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            if (scanner.hasNextInt()) {
                int optionMenu = scanner.nextInt();
                System.out.println();
                if (optionMenu == 0) return;
                else if (optionMenu == 1) {
                    final Map<Integer, Company> companies = companyDao.selectAllCompanies();
                    if (!companies.isEmpty()) {
                        /* old run configuration
                        System.out.println("Company list:");
                        companies.forEach(
                                (id, company) -> System.out.printf("%d. %s%n", id, company.getName())
                        );
                         */
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
            System.out.println("Choose a company:");
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
                if (optionMenu == 0) return;
                else if (optionMenu == 1) {

                } else if (optionMenu == 2) {

                }
            }
        }
    }
}
