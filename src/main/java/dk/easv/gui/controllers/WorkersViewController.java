package dk.easv.gui.controllers;

import dk.easv.be.Project;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.models.interfaces.IUserModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentMap;

public class WorkersViewController extends RootController {

    private final IUserModel userModel = UserModel.getInstance();
    @FXML
    private MFXTableView<User> workersTable;
    @FXML
    private MFXTableView<Project> projectsTable;
    @FXML
    private VBox rootVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            TableSetters.setupUsersTable(workersTable);
            TableSetters.setUpProjectTable(projectsTable);
            IProjectModel projectModel = ProjectModel.getInstance();
            projectModel.setSelectedUserId(0);
            workersTable.getSelectionModel().selectionProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    try {
                        ConcurrentMap<Integer, Project> map = projectModel.getProjectsByWorkerId(newValue.values().stream().findFirst().get().getUserID());
                        projectsTable.setItems(FXCollections.observableArrayList(map.values()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEdit() {
        try {
            User selectedUser = workersTable.getSelectionModel().getSelectedValues().get(0);
            if (selectedUser == null) {
                AlertHelper alertHelper = new AlertHelper("Please select a user to edit", Alert.AlertType.ERROR);
                alertHelper.showAndWait();
            } else {
                CreateWorkerController controller = (CreateWorkerController) ControllerFactory.loadFxmlFile(ViewType.CREATE_WORKER);
                controller.setUserData(selectedUser);
                BorderPane borderPane = (BorderPane) rootVBox.getParent();
                borderPane.setCenter(controller.getView());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleDelete() {
        try {
            User selectedUser = workersTable.getSelectionModel().getSelectedValues().get(0);
            if (selectedUser == null) {
                AlertHelper alertHelper = new AlertHelper("Pleas select a user to delete", Alert.AlertType.ERROR);
                alertHelper.showAndWait();
            } else {
                userModel.deleteUser(selectedUser.getUserID());
            }
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("User deleted successfully", Alert.AlertType.NONE);
            alertHelper.showAndWait();
        } catch (IndexOutOfBoundsException o) {
            AlertHelper alertHelper = new AlertHelper("Please select a user to delete", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        }
    }

    @FXML
    private void newWorker(ActionEvent actionEvent) {
        try {
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.CREATE_WORKER);
            BorderPane borderPane = (BorderPane) rootVBox.getParent();
            borderPane.setCenter(controller.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void assignProject(ActionEvent actionEvent) {
        try {
            User selectedUser = workersTable.getSelectionModel().getSelectedValues().get(0);
            Stage stage = new Stage();
            stage.setUserData(selectedUser);
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.ASSIGN_PROJECT);
            Scene scene = new Scene(controller.getView());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException o) {
            AlertHelper alertHelper = new AlertHelper("Please select a user to assign a project to", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        }
    }


    @FXML
    private void deassignProject(ActionEvent actionEvent) {
        try {
            User selectedUser = workersTable.getSelectionModel().getSelectedValues().get(0);
            Project selectedProject = projectsTable.getSelectionModel().getSelectedValues().get(0);
            IProjectModel projectModel = ProjectModel.getInstance();
            projectModel.deassignProject(selectedProject.getProjectID(), selectedUser.getUserID());
        } catch (IndexOutOfBoundsException o) {
            AlertHelper alertHelper = new AlertHelper("Please select a user and a project to deassign", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
