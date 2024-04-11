package carsharing;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.model.Menu;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        final List<String> argsList = Arrays.asList(args);
        JDBConnection H2DBConnection;
        if (argsList.contains("-databaseFileName")) {
            try {
                H2DBConnection = new JDBConnection(Optional.of(
                        argsList.get(argsList.indexOf("-databaseFileName") + 1))
                );
            } catch (IndexOutOfBoundsException e) {
                H2DBConnection = new JDBConnection(Optional.empty());
            }
        } else {
            H2DBConnection = new JDBConnection(Optional.empty());
        }

        Menu menu = new Menu(H2DBConnection);
        menu.mainMenu();
    }
}