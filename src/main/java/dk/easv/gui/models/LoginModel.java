package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.BLLFacade;
import dk.easv.bll.CRUDLogic;

import java.sql.SQLException;

public class LoginModel {

    private BLLFacade bll;

    /**
     *  This a constructor that creates an instance of CRUDLogic and assigns it to an instance variable
     */
    public LoginModel(){
        bll = new CRUDLogic();
    }

    /**
     * @param this method checks if a User object with the specified username and password exists in the database
     * @return If user is found, it returns the User object
     * @throws SQLException
     */
    public User checkForUser(String username, String password) throws SQLException {
        return bll.checkForUser(username, password);
    }
}
