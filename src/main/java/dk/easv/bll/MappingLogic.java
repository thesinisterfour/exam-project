package dk.easv.bll;

import dk.easv.be.Project;
import dk.easv.dal.dao.DocumentMapperDAO;
import dk.easv.dal.dao.ProjectDAO;
import dk.easv.dal.interafaces.IDocumentMapperDAO;
import dk.easv.dal.interafaces.IProjectMapper;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

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

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException {
        IProjectMapper projectMapper = new ProjectDAO();
        return projectMapper.getProjectsByCustomerId(id);
    }
}
