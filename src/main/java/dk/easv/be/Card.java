package dk.easv.be;

public class Card {

    private String userName;

    private String userEmail;

    private String userRole;

    private String userAddress;

    private String userZipCode;

    private String customerName;



    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserZipCode() {
        return userZipCode;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setUserZipCode(String userZipCode) {
        this.userZipCode = userZipCode;
    }
    public Card(String userName, String userRole) {
        this.userName = userName;
        //this.userEmail = userEmail;
        this.userRole = userRole;
    }
    public Card(String customerName, String userAddress, String userZipCode, String userEmail) {
        this.customerName = customerName;
        this.userAddress = userAddress;
        this.userZipCode = userZipCode;
        this.userEmail = userEmail;
    }


}
