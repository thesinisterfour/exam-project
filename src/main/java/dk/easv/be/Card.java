package dk.easv.be;

public class Card {

    private String userName;

    private String customerEmail;

    private String userRole;

    private String customerAddress;

    private String customerZipCode;

    private String customerName;


    public Card(String userName, String userEmail, String userRole) {
        this.userName = userName;
        this.customerEmail = userEmail;
        this.userRole = userRole;
    }

    public Card(String customerName, String userAddress, int userZipCode, String userEmail) {
        this.customerName = customerName;
        this.customerAddress = userAddress;
        this.customerZipCode = String.valueOf(userZipCode);
        this.customerEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerZipCode() {
        return customerZipCode;
    }

    public void setCustomerZipCode(String customerZipCode) {
        this.customerZipCode = customerZipCode;
    }


}
