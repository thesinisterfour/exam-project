package dk.easv.bll;

import dk.easv.be.Project;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface IMappingLogic {
    int addDocumentToProject(int projectID, int documentID) throws SQLException;
    int addProjectToCustomer(int customerId, int projectId) throws SQLException;

    ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException;
}
