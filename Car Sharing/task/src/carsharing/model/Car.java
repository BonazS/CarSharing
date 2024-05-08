package carsharing.model;

public class Car {

    private int id;

    private String name;

    private int companyId;

    public Car(final String name, final int companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final int companyId) {
        this.companyId = companyId;
    }

}
