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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

