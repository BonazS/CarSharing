package carsharing.dao;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.jdbc.crud.DBOperations;
import carsharing.model.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class DbCarDao implements CarDao{

    private static final String CREATE_CAR = "CREATE TABLE IF NOT EXISTS CAR (" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(255) NOT NULL UNIQUE," +
            "COMPANY_ID INT NOT NULL, " +
            "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";

    private static final String INSERT_CAR = "INSERT INTO CAR (NAME, COMPANY_ID) " +
            "VALUES(?, ?)";

    private static final String SELECT_ALL_COMPANY_CARS = "SELECT * FROM CAR WHERE COMPANY_ID = %d";

    private static final String SELECT_CAR_BY_ID = "SELECT * FROM CAR WHERE ID = %d";

    private final DBOperations dbOperations;

    public DbCarDao(final JDBConnection con) {
        dbOperations = new DBOperations(con);
        dbOperations.create(CREATE_CAR);
    }

    @Override
    public void add(final Car car) {
        System.out.println(
                dbOperations.insert(INSERT_CAR, car) == 1 ? "The car was added!\n" : "Company not added.\n"
        );
    }

    @Override
    public Car selectCarById(final int id) {
        Car car = null;
        try (ResultSet carDBData = dbOperations.select(SELECT_CAR_BY_ID.formatted(id))) {
            if (carDBData != null) {
                while (carDBData.next()) {
                    car = new Car(carDBData.getString("NAME"), carDBData.getInt("COMPANY_ID"));
                    car.setId(carDBData.getInt("ID"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Car not obtained.");
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public Map<Integer, Car> selectCarsByCompanyId(final int companyId) {
        final Map<Integer, Car> companyCars = new TreeMap<>();
        int key = 0;
        try (ResultSet companyCarsDBData = dbOperations.select(SELECT_ALL_COMPANY_CARS.formatted(companyId))) {
            if (companyCarsDBData != null) {
                while (companyCarsDBData.next()) {
                    Car car = new Car(
                            companyCarsDBData.getString("NAME"),
                            companyCarsDBData.getInt("COMPANY_ID")
                    );
                    car.setId(companyCarsDBData.getInt("ID"));
                    companyCars.put(++key, car);
                }
                return companyCars;
            }
        } catch (SQLException e) {
            System.out.println("Cars not obtained.");
            throw new RuntimeException(e);
        }
        return companyCars;
    }
}
