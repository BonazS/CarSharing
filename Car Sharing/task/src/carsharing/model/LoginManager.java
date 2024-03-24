package carsharing.model;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class LoginManager {

    private final Map<Integer, Company> companies;
    private final Menu menu;

    public LoginManager(final Menu menu) {
        this.menu = menu;
        companies = new TreeMap<>();
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
                    if (!companies.isEmpty()) {
                        System.out.println("Company list:");
                        companies.forEach(
                                (id, company) -> System.out.printf("%d. %s%n", id, company.getName())
                        );
                    } else {
                        System.out.println("The company list is empty!\n");
                    }
                } else if (optionMenu == 2) {
                    // It's necessary because of the character of new line remained in the scanner after nextInt call.
                    scanner.nextLine();
                    System.out.println("Enter the company name:");
                    if (scanner.hasNextLine()) {
                        scanner.nextLine();
                        System.out.println("The company was created!\n");
                    }
                }
            }
        }
    }
}
