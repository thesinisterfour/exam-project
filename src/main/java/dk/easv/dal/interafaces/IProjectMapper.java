package dk.easv.dal.interafaces;

import dk.easv.be.Doc;
import dk.easv.be.Project;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface IProjectMapper {
    ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException;
    ConcurrentMap<Integer, Doc> getDocumentsByProjectId(int id) throws SQLException;

    ConcurrentMap<Integer, Project> getProjectsByWorkerId(int id) throws SQLException;

    int addUserToProject(int projectId, int userId) throws SQLException;
}
