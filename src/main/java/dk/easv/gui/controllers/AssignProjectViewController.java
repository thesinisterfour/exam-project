package dk.easv.gui.controllers;

import dk.easv.be.Project;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AssignProjectViewController extends RootController {
    @FXML
    private MFXFilterComboBox<Project> projectsComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            IProjectModel projectModel = ProjectModel.getInstance();
            projectModel.getAllProjects();
            projectsComboBox.setItems(projectModel.getProjectObservableList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void assignButton(ActionEvent actionEvent) {
    }

    @FXML
    private void cancel(ActionEvent actionEvent) {
        getStage().close();
    }
}
