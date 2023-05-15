package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.ILoginLogic;
import dk.easv.bll.LoginLogic;
import dk.easv.gui.models.interfaces.ILoginModel;

import java.sql.SQLException;

public class LoginModel implements ILoginModel {

    private final ILoginLogic bll = new LoginLogic();

    @Override
    public User checkForUser(String username, String password) throws SQLException {
        return bll.checkForUser(username, password);
    }
}
