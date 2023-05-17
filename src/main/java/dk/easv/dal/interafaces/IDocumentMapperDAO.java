package dk.easv.dal.interafaces;

import java.sql.SQLException;

public interface IDocumentMapperDAO {
    int addDocumentToProject(int projectID, int documentID) throws SQLException;
    int addProjectToCustomer(int customerId, int projectId) throws SQLException;

}
