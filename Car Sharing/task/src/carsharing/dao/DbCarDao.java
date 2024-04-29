package carsharing.dao;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.jdbc.crud.DBOperations;
import carsharing.model.Car;

import java.util.Map;

public class DbCarDao implements CarDao{

    private final DBOperations dbOperations;

    public DbCarDao(final JDBConnection con) {
        dbOperations = new DBOperations(con);
    }

    @Override
    public void add(final Car car) {
        System.out.println("The car was added!\n");
    }

    @Override
    public Map<Integer, Car> selectAllCars() {
        return Map.of();
    }
}
