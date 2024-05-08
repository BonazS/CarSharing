package carsharing.model;

public class Customer {

    private int id;

    private String name;

    private Integer rentedCarId;

    public Customer(final String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(final Integer rentedCarId) {
        this.rentedCarId = rentedCarId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
