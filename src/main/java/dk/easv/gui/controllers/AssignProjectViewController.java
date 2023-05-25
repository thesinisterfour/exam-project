package dk.easv.gui.controllers;

import dk.easv.be.Project;
import dk.easv.be.User;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentMap;

public class AssignProjectViewController extends RootController {
    IProjectModel projectModel;
    @FXML
    private MFXFilterComboBox<Project> projectsComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            projectModel = ProjectModel.getInstance();
            ConcurrentMap<Integer, Project> projects = projectModel.getAllProjects();
            projectsComboBox.setItems(FXCollections.observableArrayList(projects.values()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void assignButton(ActionEvent actionEvent) {
        Project project = projectsComboBox.getSelectionModel().getSelectedItem();
        if (project != null) {
            User user = (User) getStage().getUserData();
            try {
                projectModel.addUserToProject(project.getProjectID(), user.getUserID());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getStage().close();
        }
    }

    @FXML
    private void cancel(ActionEvent actionEvent) {
        getStage().close();
    }
}
