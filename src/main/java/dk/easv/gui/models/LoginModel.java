package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.ILoginLogic;
import dk.easv.bll.LoginLogic;
import dk.easv.gui.controllers.LoginController;
import dk.easv.gui.models.interfaces.ILoginModel;

import java.sql.SQLException;

public class LoginModel implements ILoginModel {

    private final ILoginLogic bll = new LoginLogic(this);

    private boolean loginReady = false;
    private LoginController observer;

    public LoginModel(LoginController loginController) {
        observer = loginController;
    }

    @Override
    public User checkForUser(String username, String password) throws SQLException {
        return bll.checkForUser(username, password);
    }

    @Override
    public boolean isLoginReady() {
        return loginReady;
    }

    @Override
    public void setLoginReady(boolean loginReady) {
        this.loginReady = loginReady;
        notifyObserver();
    }

    private void notifyObserver() {
        observer.setLoginReady();
    }
}
