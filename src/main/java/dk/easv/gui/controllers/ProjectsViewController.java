package dk.easv.gui.controllers;

import dk.easv.be.Project;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectsViewController extends RootController {
    @FXML
    private MFXTableView<Project> projectsTable;

    @FXML
    private void newWorker(ActionEvent actionEvent) {
    }

    @FXML
    private void handleEdit(ActionEvent actionEvent) {
    }

    @FXML
    private void handleDelete(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            TableSetters.setUpProjectTable(projectsTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
