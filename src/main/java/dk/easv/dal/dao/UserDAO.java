package dk.easv.dal.dao;

import dk.easv.be.Role;
import dk.easv.be.User;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserDAO implements ICRUDDao<User> {

    private final ConnectionManager cm = ConnectionManager.getINSTANCE();
    @Override
    public int add(User object) throws SQLException {
        if (object == null) {
            throw new SQLException("Object cannot be null");
        }

        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("" +
                    "INSERT INTO dbo.[users] (first_name, last_name, role_id, username, password)\n" +
                    "VALUES (?, ?, (SELECT role_id FROM users_role WHERE role_name=?), ?, ?);");

            ps.setString(1, object.getFirstName());
            ps.setString(2, object.getLastName());
            ps.setString(3, object.getRole().toString());
            ps.setString(4, object.getUsername());
            ps.setString(5, object.getPassword());

            ps.executeQuery();
        }
        return 0;
    }

    @Override
    public int update(User object) throws SQLException {
        if (object == null) {
            throw new SQLException("Object cannot be null");
        }

        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("" + "UPDATE dbo.[users] SET first_name=?, last_name=?, " +
                    "(SELECT role_id FROM users_role WHERE role_name=?), username=?, password=? WHERE user_id=?;");

            ps.setString(1, object.getFirstName());
            ps.setString(2, object.getLastName());
            ps.setString(3, object.getRole().toString());
            ps.setString(4, object.getUsername());
            ps.setString(5, object.getPassword());
            ps.setInt(6, object.getUserID());

            ps.executeQuery();
        }
        return 0;
    }

    @Override
    public User get(int id) throws SQLException {
        if (id <= 0) {
            throw new SQLException("Id must be greater than 0");
        }

        try(Connection connection = cm.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM dbo.[users] WHERE user_id=? INNER JOIN dbo.users_role ON dbo.users_role.role_id = dbo.[users].role_id;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"),
                        Role.valueOf(rs.getString("role_name")), rs.getString("username"), rs.getString("password"));
            }
        }
            return null;
    }

    @Override
    public ConcurrentMap<Integer, User> getAll() throws SQLException {
        ConcurrentMap<Integer, User> userMap = new ConcurrentHashMap<>();
        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT dbo.[users].user_id, dbo.[users].first_name, dbo.[users].last_name, dbo.[users].username, dbo.[users].[password], dbo.users_role.role_name\n" +
                    "FROM  dbo.[users]\n" +
                    "INNER JOIN dbo.users_role ON dbo.users_role.role_id = dbo.[users].role_id;");

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int dbId = rs.getInt("user_id");
                String dbFirstName = rs.getString("first_name");
                String dbLastName = rs.getString("last_name");
                String dbRole = rs.getString("role_name");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                Role userRole = Role.valueOf(dbRole);

                userMap.put(dbId, new User(dbId, dbFirstName, dbLastName, userRole, dbUsername, dbPassword));
            }
            return userMap;
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        if (id <= 0) {
            throw new SQLException("Id must be greater than 0");
        }

        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("" + "DELETE FROM dbo.[users] WHERE user_id=?;");
            ps.setInt(1, id);

            ps.executeQuery();
        }

        return 0;
    }
}
