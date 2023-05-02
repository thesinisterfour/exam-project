package dk.easv.gui.models;

import dk.easv.be.Customer;
import dk.easv.be.User;
import dk.easv.bll.CRUDLogic;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class UserModel {

    private CRUDLogic crudLogic = new CRUDLogic();

    public ConcurrentMap<Integer, User> getAllUsers() throws SQLException {
        return crudLogic.getAllUsers();
    }
}
