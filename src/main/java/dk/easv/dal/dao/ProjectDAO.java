package dk.easv.dal.dao;

import dk.easv.be.Project;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class ProjectDAO implements ICRUDDao<Project> {

    private final ConnectionManager cm = new ConnectionManager();
    @Override
    public int add(Project object) throws SQLException {
        if (object == null) {
            throw new SQLException("Object cannot be null");
        }
        return 0;
    }

    @Override
    public int update(Project object) throws SQLException {
        return 0;
    }

    @Override
    public Project get(int id) throws SQLException {
        return null;
    }

    @Override
    public ConcurrentMap<Integer, Project> getAll() throws SQLException {
        return null;
    }

    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
