package dk.easv.gui.models;

import dk.easv.be.Role;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.ICRUDLogic;
import dk.easv.gui.models.interfaces.IRoleModel;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class RoleModel implements IRoleModel {
    private final ICRUDLogic crudLogic = new CRUDLogic();

    @Override
    public ConcurrentMap<Integer, Role> getAllRoles() throws SQLException {
        return crudLogic.getAllRoles();
    }
}
