package carsharing.model;

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
            System.out.println("0. Exit");
            if (input.hasNextInt()) {
                int optionMenu = input.nextInt();
                if (optionMenu == 1) {
                    System.out.println();
                    LoginManager loginManager = new LoginManager(dbConnection);
                    loginManager.loginMenu(input);
                } else if (optionMenu == 0) {
                    dbConnection.closeConnection();
                    System.exit(0);
                }
            }
        }
    }
}
