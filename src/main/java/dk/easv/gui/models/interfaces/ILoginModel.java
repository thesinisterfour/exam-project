package dk.easv.gui.models.interfaces;

import dk.easv.be.User;

import java.sql.SQLException;

public interface ILoginModel {
    User checkForUser(String username, String password) throws SQLException;
    boolean isLoginReady();
    void setLoginReady(boolean loginReady);
}
