package dk.easv.gui.models.interfaces;

import dk.easv.be.Project;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface IProjectModel {
    ConcurrentMap<Integer, Project> getAllProjects() throws SQLException;

    ObservableList<Project> getProjectObservableList();

    ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException;

    void addProject(Project project) throws SQLException;
}
