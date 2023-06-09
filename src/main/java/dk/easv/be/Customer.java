package dk.easv.be;

public class Customer {

    private int customerID;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private int zipCode;

    public Customer(int customerID, String customerName, String customerEmail, String customerAddress) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
    }

    public Customer(int customerID, String customerName, String customerEmail, String customerAddress, City city) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.zipCode = city.getZipcode();
    }

    public Customer(String customerName, String customerEmail, String customerAddress, int zipCode) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.zipCode = zipCode;
    }

    public Customer(int id, String customerName, String customerEmail, String customerAddress, int zipCode) {
        this.customerID = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.zipCode = zipCode;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "ID = " + customerID + " Name = " + customerName;
    }
}
