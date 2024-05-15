package carsharing.dao;

import carsharing.model.Car;

import java.util.Map;

public interface CarDao {

    void add(final Car car);

    Car selectCarById(final int id);

    Map<Integer, Car> selectCarsByCompanyId(final int companyId);
}
