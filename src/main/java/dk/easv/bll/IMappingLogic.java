package dk.easv.bll;

import dk.easv.be.Project;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface IMappingLogic {
    int addDocumentToProject(int projectID, int documentID) throws SQLException;

    ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException;

    ConcurrentMap<Integer, Project> getProjectsByWorkerId(int id) throws SQLException;

    int addUserToProject(int projectId, int userId) throws SQLException;

    int deassignproject(int projectId, int userId) throws SQLException;
}
