package dk.easv.gui.models.interfaces;

import dk.easv.be.Project;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface IProjectModel {
    ConcurrentMap<Integer, Project> getAllProjects() throws SQLException;

    ObservableList<Project> getProjectObservableList() throws SQLException;

    ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException;

    ConcurrentMap<Integer, Project> getProjectsByWorkerId(int id) throws SQLException;

    int addUserToProject(int projectId, int userId) throws SQLException;

    void addProject(Project project) throws SQLException;

    int deassignProject(int userID, int projectID) throws SQLException;

    int getSelectedProjectId();

    void setSelectedProjectId(int selectedProjectId);

    void updateProject(Project project) throws SQLException;
}
