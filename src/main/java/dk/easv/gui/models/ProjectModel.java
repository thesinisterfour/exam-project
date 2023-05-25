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
    private static IProjectModel INSTANCE;
    private final ObservableList<Project> projectObservableList;

    private final ICRUDLogic logic = new CRUDLogic();
    private int selectedUserId = 0;
    private int selectedProjectId = 0;

    private ProjectModel(){
        projectObservableList = FXCollections.observableArrayList();
    }

    public static IProjectModel getInstance() throws SQLException {
        if (INSTANCE == null) {
            INSTANCE = new ProjectModel();
        }
        return INSTANCE;
    }

    @Override
    public ConcurrentMap<Integer, Project> getAllProjects() throws SQLException {
        return logic.getAllProjects();
    }

    @Override
    public void loadAllProjects() throws SQLException {
        int selectedCustomerId = CustomerModel.getInstance().getSelectedCustomerId();
        if (selectedCustomerId != 0) {
            projectObservableList.setAll(getProjectsByCustomerId(selectedCustomerId).values());
        } else if (selectedUserId != 0) {
            projectObservableList.setAll(getProjectsByWorkerId(selectedUserId).values());
        } else {
            ConcurrentMap<Integer, Project> allProjects = getAllProjects();
            projectObservableList.setAll(allProjects.values());
        }
    }

    @Override
    public ObservableList<Project> getProjectObservableList() {
        return projectObservableList;
    }

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException {
        IMappingLogic projectMapper = new MappingLogic();
        return projectMapper.getProjectsByCustomerId(id);
    }

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByWorkerId(int id) throws SQLException {
        IMappingLogic projectMapper = new MappingLogic();
        return projectMapper.getProjectsByWorkerId(id);
    }

    @Override
    public int addUserToProject(int projectId, int userId) throws SQLException {
        IMappingLogic mappingLogic = new MappingLogic();
        return mappingLogic.addUserToProject(projectId, userId);
    }

    @Override
    public void addProject(Project project) throws SQLException {
        logic.addProject(project);
        loadAllProjects();
    }

    @Override
    public void updateProject(Project project) throws SQLException {
        logic.updateProject(project);
        loadAllProjects();
    }

    @Override
    public void deleteProject(Project project) throws SQLException {
        logic.deleteProject(project);
        loadAllProjects();
    }

    @Override
    public int deassignProject(int projectId, int userId) throws SQLException {
        IMappingLogic mappingLogic = new MappingLogic();
        return mappingLogic.deassignproject(projectId, userId);
    }

    @Override
    public int getSelectedProjectId() {
        return selectedProjectId;
    }

    @Override
    public void setSelectedProjectId(int selectedProjectId) {
        this.selectedProjectId = selectedProjectId;
    }

    @Override
    public int getSelectedUserId() {
        return selectedUserId;
    }

    @Override
    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;
    }
}
