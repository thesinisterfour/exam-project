package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.ILoginLogic;
import dk.easv.bll.LoginLogic;

import java.sql.SQLException;

public class LoginModel {

    private final ILoginLogic bll = new LoginLogic();

    public User checkForUser(String username, String password) throws SQLException {
        return bll.checkForUser(username, password);
    }
}
