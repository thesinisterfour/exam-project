package dk.easv.gui.models;

import dk.easv.be.User;
import dk.easv.bll.ICRUDLogic;
import dk.easv.bll.CRUDLogic;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class UserModel {

    private final ICRUDLogic crudLogic = new CRUDLogic();

    public ConcurrentMap<Integer, User> getAllUsers() throws SQLException {
        return crudLogic.getAllUsers();
    }
}
