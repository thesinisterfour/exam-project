package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.ICRUDLogic;
import dk.easv.bll.CRUDLogic;
import dk.easv.gui.models.interfaces.IUserModel;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class UserModel implements IUserModel {

    private final ICRUDLogic crudLogic = new CRUDLogic();

    @Override
    public ConcurrentMap<Integer, User> getAllUsers() throws SQLException {
        return crudLogic.getAllUsers();
    }
}
