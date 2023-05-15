package dk.easv.bll;

import dk.easv.dal.dao.DocumentMapperDAO;
import dk.easv.dal.interafaces.IDocumentMapperDAO;

import java.sql.SQLException;

public class MappingLogic implements IMappingLogic{
    private final IDocumentMapperDAO documentMapperDAO = new DocumentMapperDAO();

    @Override
    public int addDocumentToProject(int projectID, int documentID) throws SQLException {
        return documentMapperDAO.addDocumentToProject(projectID, documentID);
    }

    @Override
    public int addProjectToCustomer(int customerId, int projectId) throws SQLException {
        return documentMapperDAO.addProjectToCustomer(customerId, projectId);
    }
}
