package dk.easv.gui.models.interfaces;

import dk.easv.be.User;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface IUserModel {
    ConcurrentMap<Integer, User> getAllUsers() throws SQLException;
}
