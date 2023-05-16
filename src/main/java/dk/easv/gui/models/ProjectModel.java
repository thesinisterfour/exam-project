package dk.easv.gui.models;

import dk.easv.be.Project;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.ICRUDLogic;
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
        return logic.getAllProjects();
    }

    @Override
    public ObservableList<Project> getProjectObservableList() throws SQLException{
        projectObservableList.setAll(getAllProjects().values());
        return projectObservableList;
    }

}
