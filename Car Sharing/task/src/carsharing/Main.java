package carsharing;

import carsharing.dao.DbCarDao;
import carsharing.dao.DbCompanyDao;
import carsharing.dao.DbCustomerDao;
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

        // Create DB's structure of application
        initializeDatabase(H2DBConnection);

        Menu menu = new Menu(H2DBConnection);
        menu.mainMenu();
    }

    private static void initializeDatabase(JDBConnection dbConnection) {
        new DbCompanyDao(dbConnection);
        new DbCarDao(dbConnection);
        new DbCustomerDao(dbConnection);
    }
}