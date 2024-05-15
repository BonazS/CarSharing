package carsharing.dao;

import carsharing.jdbc.connection.JDBConnection;
import carsharing.jdbc.crud.DBOperations;
import carsharing.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class DbCompanyDao implements CompanyDao {

    private static final String CREATE_COMPANY = "CREATE TABLE IF NOT EXISTS COMPANY (" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(255) NOT NULL," +
            "CONSTRAINT UNIQUE_NAME UNIQUE (NAME))";

    private static final String INSERT_COMPANY = "INSERT INTO COMPANY (NAME) " +
            "VALUES(?)";

    private static final String SELECT_ALL_COMPANIES = "SELECT * FROM COMPANY";

    private static final String SELECT_COMPANY_BY_ID = "SELECT * FROM COMPANY WHERE ID = %d";

    private final DBOperations dbOperations;

    public DbCompanyDao(final JDBConnection con) {
        dbOperations = new DBOperations(con);
        dbOperations.create(CREATE_COMPANY);
    }

    @Override
    public void add(final Company company) {
        System.out.println(
                dbOperations.insert(INSERT_COMPANY, company) == 1
                ? "The company was created!\n" : "Company not inserted.\n"
        );
    }

    @Override
    public Map<Integer, Company> selectAllCompanies() {
        final Map<Integer, Company> companies = new TreeMap<>();
        int key = 0;
        try (ResultSet companiesDBData = dbOperations.select(SELECT_ALL_COMPANIES)) {
            if (companiesDBData != null) {
                while (companiesDBData.next()) {
                    int companyId = companiesDBData.getInt("ID");
                    Company company = new Company(companiesDBData.getString("NAME"));
                    company.setId(companyId);
                    companies.put(++key, company);
                }
                return companies;
            }
        } catch (SQLException e) {
            System.out.println("Companies not obtained.");
            throw new RuntimeException(e);
        }
        return companies;
    }

    @Override
    public Company selectCompanyById(final int id) {
        Company company = null;
        try (ResultSet companyDBData = dbOperations.select(SELECT_COMPANY_BY_ID.formatted(id))) {
            if (companyDBData != null) {
                while (companyDBData.next()) {
                    company = new Company(companyDBData.getString("NAME"));
                    company.setId(companyDBData.getInt("ID"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Company not obtained.");
            throw new RuntimeException(e);
        }
        return company;
    }
}
