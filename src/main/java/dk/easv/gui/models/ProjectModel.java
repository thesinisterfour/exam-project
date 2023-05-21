package dk.easv.gui.models;

import dk.easv.be.Project;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.ICRUDLogic;
import dk.easv.bll.IMappingLogic;
import dk.easv.bll.MappingLogic;
import dk.easv.gui.models.interfaces.IProjectModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class ProjectModel implements IProjectModel {
    private static ProjectModel INSTANCE;
    private final ObservableList<Project> projectObservableList;
    private ICRUDLogic logic = new CRUDLogic();

    private ProjectModel() throws SQLException {
        projectObservableList = FXCollections.observableArrayList();
        getAllProjects();
    }

    public static ProjectModel getInstance() throws SQLException {
        if (INSTANCE == null) {
            INSTANCE = new ProjectModel();
        }
        return INSTANCE;
    }

    @Override
    public ConcurrentMap<Integer, Project> getAllProjects() throws SQLException {
        ConcurrentMap<Integer, Project> allProjects = logic.getAllProjects();
        projectObservableList.setAll(allProjects.values());
        return allProjects;
    }

    @Override
    public ObservableList<Project> getProjectObservableList() {
        return projectObservableList;
    }

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException {
        IMappingLogic projectMapper = new MappingLogic();
        ConcurrentMap<Integer, Project> projectsByCustomerId = projectMapper.getProjectsByCustomerId(id);
        if (projectsByCustomerId.isEmpty()) {
            projectObservableList.clear();
        } else {
            projectObservableList.setAll(projectsByCustomerId.values());
        }
        return projectsByCustomerId;
    }

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByWorkerId(int id) throws SQLException {
        IMappingLogic projectMapper = new MappingLogic();
        ConcurrentMap<Integer, Project> projectsByWorkerId = projectMapper.getProjectsByWorkerId(id);
        if (projectsByWorkerId.isEmpty()) {
            projectObservableList.clear();
        } else {
            projectObservableList.setAll(projectsByWorkerId.values());
        }
        return projectsByWorkerId;
    }

    @Override
    public int addUserToProject(int projectId, int userId) throws SQLException {
        IMappingLogic mappingLogic = new MappingLogic();
        return mappingLogic.addUserToProject(projectId, userId);
    }

    @Override
    public void addProject(Project project) throws SQLException {
        logic.addProject(project);
        getAllProjects();
    }

    @Override
    public int deassignProject(int projectId, int userId) throws SQLException {
        IMappingLogic mappingLogic = new MappingLogic();
        return mappingLogic.deassignproject(projectId, userId);
    }
}
