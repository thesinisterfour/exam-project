package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.ILoginLogic;
import dk.easv.bll.LoginLogic;
import dk.easv.gui.controllers.LoginViewController;
import dk.easv.gui.models.interfaces.ILoginModel;

import java.sql.SQLException;

public class LoginModel implements ILoginModel {

    private final ILoginLogic bll = new LoginLogic(this);

    private boolean loginReady = false;
    private final LoginViewController observer;

    public LoginModel(LoginViewController loginViewController) {
        observer = loginViewController;
    }


    /**
     * @param username,password this method checks if a User object with the specified username and password exists in the database
     * @return If user is found, it returns the User object
     * @throws SQLException
     */
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
