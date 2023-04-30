package dk.easv.bll;

import dk.easv.be.City;
import dk.easv.be.User;

import java.sql.SQLException;

public interface BLLFacade {
    int addUser(User user) throws SQLException;

    User checkForUser(String username, String password) throws SQLException;

    City getCity(int zipcode) throws SQLException;
}
