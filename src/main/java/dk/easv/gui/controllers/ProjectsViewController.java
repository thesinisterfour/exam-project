package dk.easv.gui.controllers;

import dk.easv.be.Project;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.CurrentUser;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProjectsViewController extends RootController {
    @FXML
    private MFXTableView<Project> projectsTable;
    @FXML
    private VBox rootVBox;
    @FXML
    private HBox crudHBox;

    @FXML
    private void newProject(ActionEvent actionEvent) {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.ADD_PROJECT);
            BorderPane borderPane = (BorderPane) rootVBox.getParent();
            borderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEdit(ActionEvent actionEvent) {
        try {
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.ADD_PROJECT);
            AddProjectViewController addProjectViewController = (AddProjectViewController) controller;
            addProjectViewController.setProjectData(projectsTable.getSelectionModel().getSelectedValues().get(0));
            BorderPane borderPane = (BorderPane) rootVBox.getParent();
            borderPane.setCenter(controller.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleDelete(ActionEvent actionEvent) {
        try {
            ProjectModel.getInstance().deleteProject(projectsTable.getSelectionModel().getSelectedValues().get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            AlertHelper alertHelper = new AlertHelper( "No project selected", Alert.AlertType.WARNING);
            alertHelper.showAndWait();
        }
    }



    @FXML
    private void viewDocumentsOnAction(ActionEvent actionEvent) {
        try {
            ProjectModel.getInstance().setSelectedProjectId(projectsTable.getSelectionModel().getSelectedValues().get(0).getProjectID());
            BorderPane rootBorderPane = (BorderPane) rootVBox.getParent();
            rootBorderPane.setCenter(ControllerFactory.loadFxmlFile(ViewType.DOCUMENTS_VIEW).getView());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            AlertHelper alertHelper = new AlertHelper( "No project selected", Alert.AlertType.WARNING);
            alertHelper.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CurrentUser actualUser = CurrentUser.getInstance();
        if (actualUser.getRole() == Role.SALESPERSON){
            crudHBox.setVisible(false);
        }
        try {
            TableSetters.setUpProjectTable(projectsTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
