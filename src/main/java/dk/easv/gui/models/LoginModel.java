package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.BLLFacade;
import dk.easv.bll.CRUDLogic;

import java.sql.SQLException;

public class LoginModel {

    private BLLFacade bll;

    public LoginModel(){
        bll = new CRUDLogic();
    }
    public User checkForUser(String username, String password) throws SQLException {
        return bll.checkForUser(username, password);
    }
}
