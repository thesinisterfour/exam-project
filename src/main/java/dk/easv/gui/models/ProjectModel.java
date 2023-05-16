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
    private ICRUDLogic logic = new CRUDLogic();

    private final ObservableList<Project> projectObservableList;

    public ProjectModel(){
        projectObservableList = FXCollections.observableArrayList();
    }

    @Override
    public ConcurrentMap<Integer, Project> getAllProjects() throws SQLException {
        ConcurrentMap<Integer, Project> allProjects = logic.getAllProjects();
        projectObservableList.setAll(allProjects.values());
        return allProjects;
    }

    @Override
    public ObservableList<Project> getProjectObservableList() throws SQLException{
        return projectObservableList;
    }

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException {
        IMappingLogic projectMapper = new MappingLogic();
        ConcurrentMap<Integer, Project> projectsByCustomerId = projectMapper.getProjectsByCustomerId(id);
        projectObservableList.setAll(projectsByCustomerId.values());
        return projectsByCustomerId;
    }
}
