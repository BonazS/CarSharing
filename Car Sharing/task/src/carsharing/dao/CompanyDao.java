package carsharing.dao;

import carsharing.model.Company;

import java.util.Map;

public interface CompanyDao {

    void add(Company company);

    Map<Integer, Company> selectAllCompanies();

}
