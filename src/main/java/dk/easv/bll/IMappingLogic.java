package dk.easv.bll;

import java.sql.SQLException;

public interface IMappingLogic {
    int addDocumentToProject(int projectID, int documentID) throws SQLException;
    int addProjectToCustomer(int customerId, int projectId) throws SQLException;
}
