package carsharing.dao;

import carsharing.model.Car;

import java.util.Map;

public interface CarDao {

    void add(Car car);

    Map<Integer, Car> selectAllCars();
}
