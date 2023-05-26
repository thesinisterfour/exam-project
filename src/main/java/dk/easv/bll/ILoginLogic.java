package dk.easv.bll;

import dk.easv.be.User;

import java.sql.SQLException;

public interface ILoginLogic {
    User checkForUser(String username, String password);
}
