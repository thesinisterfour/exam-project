package dk.easv.gui.models.interfaces;

import dk.easv.be.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IUserModel {

    int addUser(User user) throws SQLException;

    int updateUser(User object) throws SQLException;

    int deleteUser(int id) throws SQLException;

    void loadAllUsers() throws SQLException;

    ObservableList<User> getObsAllUsers();
}
