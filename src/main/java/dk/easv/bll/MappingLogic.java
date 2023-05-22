package dk.easv.bll;

import dk.easv.be.Project;
import dk.easv.dal.dao.DocumentMapperDAO;
import dk.easv.dal.dao.ProjectDAO;
import dk.easv.dal.interafaces.IDocumentMapperDAO;
import dk.easv.dal.interafaces.IProjectMapper;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class MappingLogic implements IMappingLogic {
    private final IDocumentMapperDAO documentMapperDAO = new DocumentMapperDAO();

    @Override
    public int addDocumentToProject(int projectID, int documentID) throws SQLException {
        return documentMapperDAO.addDocumentToProject(projectID, documentID);
    }

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException {
        IProjectMapper projectMapper = new ProjectDAO();
        return projectMapper.getProjectsByCustomerId(id);
    }

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByWorkerId(int id) throws SQLException {
        IProjectMapper projectMapper = new ProjectDAO();
        return projectMapper.getProjectsByWorkerId(id);
    }

    @Override
    public int addUserToProject(int projectId, int userId) throws SQLException {
        IProjectMapper projectMapper = new ProjectDAO();
        return projectMapper.addUserToProject(projectId, userId);
    }

    @Override
    public int deassignproject(int projectId, int userId) throws SQLException {
        IProjectMapper projectMapper = new ProjectDAO();
        return projectMapper.deassignProject(projectId, userId);
    }
}
