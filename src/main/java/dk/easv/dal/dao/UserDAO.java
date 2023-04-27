package dk.easv.dal.dao;

import dk.easv.be.User;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class UserDAO implements ICRUDDao<User> {
    @Override
    public int add(User object) throws SQLException {
        if (object == null) {
            throw new SQLException("Object cannot be null");
        }
        return 0;
    }

    @Override
    public int update(User object) throws SQLException {
        if (object == null) {
            throw new SQLException("Object cannot be null");
        }
        return 0;
    }

    @Override
    public User get(int id) throws SQLException {
        if (id <= 0) {
            throw new SQLException("Id must be greater than 0");
        }
        return null;
    }

    @Override
    public ConcurrentMap<Integer, User> getAll() throws SQLException {
        return null;
    }

    @Override
    public int delete(int id) throws SQLException {
        if (id <= 0) {
            throw new SQLException("Id must be greater than 0");
        }
        return 0;
    }
}
