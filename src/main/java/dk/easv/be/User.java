package dk.easv.be;

public class User {

    private int userID;
    private String firstName;
    private String lastName;
    private Role role;

    public User (int userID, String firstName, String lastName, Role role){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User (String firstName, String lastName, Role role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public int getUserID(){
        return userID;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public Role getRole(){
        return role;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
