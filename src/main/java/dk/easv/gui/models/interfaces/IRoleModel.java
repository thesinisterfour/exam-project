package dk.easv.gui.models.interfaces;

import dk.easv.be.Role;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface IRoleModel {
    ConcurrentMap<Integer, Role> getAllRoles() throws SQLException;
}
