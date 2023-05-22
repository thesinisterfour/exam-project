package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.ICRUDLogic;
import dk.easv.gui.models.interfaces.IUserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class UserModel implements IUserModel {

    private static UserModel instance;
    private final ICRUDLogic crudLogic = new CRUDLogic();

    private final ObservableList<User> obsAllUsers;
    private ConcurrentMap<Integer, User> allUsers;

    private UserModel() {
        obsAllUsers = FXCollections.observableArrayList();
        loadAllUsers();
    }


    public static UserModel getInstance() {
        if (instance == null) {
            instance = new UserModel();
        }
        return instance;
    }

    /**
     * @throws SQLException
     * @returns a ConcurrentMap of Users with Integer keys
     */
    @Override
    public ConcurrentMap<Integer, User> getAllUsers() throws SQLException {
        return allUsers;
    }

    @Override
    public int addUser(User user) throws SQLException {
        int id = crudLogic.addUser(user);
        loadAllUsers();
        return id;
    }

    @Override
    public int updateUser(User object) throws SQLException {
        int affectedRows = crudLogic.updateUser(object);
        loadAllUsers();
        return affectedRows;
    }

    @Override
    public int deleteUser(int id) throws SQLException {
        int affectedRows = crudLogic.deleteUser(id);
        loadAllUsers();
        return affectedRows;
    }

    @Override
    public void loadAllUsers() {
        try {
            allUsers = crudLogic.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<User> getObsAllUsers() throws SQLException {
        obsAllUsers.setAll(allUsers.values());
        return obsAllUsers;
    }
}
