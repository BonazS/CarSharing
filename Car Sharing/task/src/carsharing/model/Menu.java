package carsharing.model;

import carsharing.dao.CustomerDao;
import carsharing.dao.DbCustomerDao;
import carsharing.jdbc.connection.JDBConnection;

import java.util.Scanner;

public class Menu {

    private final Scanner input;

    private final JDBConnection dbConnection;

    public Menu(final JDBConnection dbConnection) {
        input = new Scanner(System.in);
        this.dbConnection = dbConnection;
    }

    public void mainMenu() {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            if (input.hasNextInt()) {
                int optionMenu = input.nextInt();
                if (optionMenu == 1) {
                    System.out.println();
                    LoginManager loginManager = new LoginManager(dbConnection, this);
                    loginManager.loginMenu(input);
                } else if (optionMenu == 3) {
                    CustomerDao customerDao = new DbCustomerDao(dbConnection);
                    // It's necessary because of the character of new line remained in the scanner after nextInt call.
                    input.nextLine();
                    System.out.println("Enter the customer name:");
                    if (input.hasNextLine()) {
                        customerDao.add(new Customer(input.nextLine()));
                    }
                } else if (optionMenu == 0) {
                    dbConnection.closeConnection();
                    System.exit(0);
                }
            }
        }
    }
}
