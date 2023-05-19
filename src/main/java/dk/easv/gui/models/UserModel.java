package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.ICRUDLogic;
import dk.easv.bll.CRUDLogic;
import dk.easv.gui.models.interfaces.IUserModel;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class UserModel implements IUserModel {

    private final ICRUDLogic crudLogic = new CRUDLogic();


    /**
     * @returns a ConcurrentMap of Users with Integer keys
     * @throws SQLException
     */
    @Override
    public ConcurrentMap<Integer, User> getAllUsers() throws SQLException {
        return crudLogic.getAllUsers();
    }
    @Override
    public int addUser(User user) throws SQLException {
        return crudLogic.addUser(user);
    }
    @Override
    public int updateUser(User object) throws SQLException {
        return crudLogic.updateUser(object);
    }
    @Override
    public int deleteUser(int id) throws SQLException {
        return crudLogic.deleteUser(id);
    }
}
