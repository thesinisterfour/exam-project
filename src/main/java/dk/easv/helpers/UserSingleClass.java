package dk.easv.helpers;

import dk.easv.be.Role;

public class UserSingleClass {
    private static UserSingleClass instance;
    private int id;
    private String name;
    private Role role;

    private UserSingleClass() {
    }

    public static UserSingleClass getInstance() {
        if (instance == null) {
            instance = new UserSingleClass();
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

