package dk.easv.dal.interafaces;

import dk.easv.be.Project;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface IProjectMapper {
    ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException;
}
