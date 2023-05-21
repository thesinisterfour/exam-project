package dk.easv.gui.models.interfaces;

import dk.easv.be.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface IUserModel {
    ConcurrentMap<Integer, User> getAllUsers() throws SQLException;

    int addUser(User user) throws SQLException;

    int updateUser(User object) throws SQLException;

    int deleteUser(int id) throws SQLException;

    void loadAllUsers();

    ObservableList<User> getObsAllUsers() throws SQLException;
}
