package carsharing.model;

import java.util.Scanner;

public class Menu {

    private final Scanner input;

    public Menu() {
        input = new Scanner(System.in);
    }

    public void mainMenu() {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");
            if (input.hasNextInt()) {
                int optionMenu = input.nextInt();
                if (optionMenu == 1) {
                    System.out.println();
                    LoginManager loginManager = new LoginManager(this);
                    loginManager.loginMenu(input);
                } else if (optionMenu == 0) {
                    System.exit(0);
                }
            }
        }
    }
}
