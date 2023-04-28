package dk.easv.be;

public class User {

    private int userID;
    private String firstName;
    private String lastName;
    private Role role;
    private String password;
    private String username;

    public User (int userID, String firstName, String lastName, Role role, String username, String password){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
        this.password = password;
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

    public String getUsername(){return username;}

    public String getPassword(){return password;}

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
