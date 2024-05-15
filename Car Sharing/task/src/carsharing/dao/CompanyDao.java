package carsharing.dao;

import carsharing.model.Company;

import java.util.Map;

public interface CompanyDao {

    void add(final Company company);

    Map<Integer, Company> selectAllCompanies();

    Company selectCompanyById(final int id);

}
