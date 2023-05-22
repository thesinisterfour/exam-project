package dk.easv.dal.dao;

import dk.easv.be.Role;
import dk.easv.dal.connectionManager.ConnectionManagerFactory;
import dk.easv.dal.connectionManager.IConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RoleDAO implements ICRUDDao<Role> {

    private final IConnectionManager cm = ConnectionManagerFactory.createConnectionManager();

    @Override
    public int add(Role object) throws SQLException {
        return 0;
    }

    @Override
    public int update(Role object) {
        return 0;
    }

    @Override
    public Role get(int id) throws SQLException {
        return null;
    }

    @Override
    public ConcurrentMap<Integer, Role> getAll() throws SQLException {
        ConcurrentMap<Integer, Role> rolesMap = new ConcurrentHashMap<>();
        try (Connection connection = cm.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT  * FROM users_role");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                Role role = Role.valueOf(roleName);
                rolesMap.put(roleId, role);
            }
        }

        return rolesMap;
    }

    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
