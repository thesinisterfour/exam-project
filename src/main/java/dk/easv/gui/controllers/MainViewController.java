package dk.easv.gui.controllers;

import dk.easv.be.Doc;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.AlertHelper;
import dk.easv.gui.controllers.tasks.LoadCustomerModelTask;
import dk.easv.gui.controllers.tasks.LoadDocumentModelTask;
import dk.easv.gui.controllers.tasks.LoadProjectModelTask;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.CurrentUser;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewController extends RootController {

    private final CurrentUser actualUser = CurrentUser.getInstance();
    @FXML
    public MFXButton businessLayer,
            workersLayer,
            projectsButton,
            documentsButton,
            logoutButton;
    @FXML
    public VBox iconsVbox;
    private IProjectModel projectModel;
    @FXML
    private BorderPane mainBorderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadModels();
        setViewByRole();
    }

    private void loadModels() {
        ExecutorService es = Executors.newFixedThreadPool(10);

        LoadDocumentModelTask loadDocumentModelTask = new LoadDocumentModelTask();
        loadDocumentModelTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                List<Doc> oldDocuments = newValue.getOldDocuments();
                if (!oldDocuments.isEmpty() && !DocumentHelper.isOldDocWarningShown()) {
                    AlertHelper alertHelper = new AlertHelper(DocumentHelper.convertToString(oldDocuments), Alert.AlertType.INFORMATION);
                    if (alertHelper.showAndWait()) {
                        for (Doc oldDocument : oldDocuments) {
                            newValue.deleteDocument(oldDocument.getId());
                        }

                    }
                    newValue.setObsAllDocuments();
                    DocumentHelper.setOldDocWarningShown(true);
                }
            } catch (SQLException e) {
                AlertHelper alertHelper = new AlertHelper("Old documents could not be loaded", Alert.AlertType.WARNING);
                alertHelper.showAndWait();
            }
        });

        LoadProjectModelTask loadProjectModelTask = new LoadProjectModelTask();
        loadProjectModelTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            projectModel = newValue;
            if (actualUser.getRole() == Role.TECHNICIAN) {
                projectModel.setSelectedUserId(actualUser.getId());
            }
            try {
                projectModel.loadAllProjects();
            } catch (SQLException e) {
                AlertHelper alertHelper = new AlertHelper("An error occurred during loading of projects", e);
                alertHelper.showAndWait();
            }
        });

        es.submit(new LoadCustomerModelTask());
        es.submit(loadDocumentModelTask);
        es.submit(loadProjectModelTask);
        es.shutdown();
    }

    private void setViewByRole() {
        if (actualUser != null) {
            if (actualUser.getRole() == Role.TECHNICIAN) {
                setupTechnician();
                return;
            } else if (actualUser.getRole() == Role.SALESPERSON) {
                iconsVbox.getChildren().remove(workersLayer);
            } else if (actualUser.getRole() == Role.PROJECTMANAGER) {
                iconsVbox.getChildren().remove(businessLayer);

            }
            try {
                RootController controller = ControllerFactory.loadFxmlFile(ViewType.CUSTOMERS_VIEW);
                mainBorderPane.setCenter(controller.getView());
            } catch (IOException e) {
                AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
                alertHelper.showAndWait();
            } catch (NullPointerException ex) {
                AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
                alertHelper.showAndWait();
            }

        }
    }

    private void setupTechnician() {
        iconsVbox.getChildren().remove(businessLayer);
        iconsVbox.getChildren().remove(workersLayer);
        iconsVbox.getChildren().remove(documentsButton);

        projectsButton.setOnAction(event -> {
            setTechnicianMainView();
        });

        setTechnicianMainView();


    }

    private void setTechnicianMainView() {
        try {
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.PROJECTS_VIEW);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        }
    }

    @FXML
    public void handleLogout(ActionEvent actionEvent) {
        try {
            Stage stage = getStage();
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
            stage.setScene(new Scene(controller.getView()));
            stage.setTitle("WUAV!!!");
            stage.centerOnScreen();
            DocumentHelper.setOldDocWarningShown(false);
            projectModel.setSelectedUserId(0);
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        }
    }

    @FXML
    private void displayBusiness() {
        try {
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.CUSTOMERS_VIEW);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        }
    }

    @FXML
    private void displayWorkers(ActionEvent actionEvent) {
        try {
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.WORKERS);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        }

    }

    @FXML
    private void displayProjects(ActionEvent actionEvent) {
        try {
            CustomerModel.getInstance().setSelectedCustomerId(0);
            ProjectModel.getInstance().loadAllProjects();
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.PROJECTS_VIEW);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading projects", e);
            alertHelper.showAndWait();
        }

    }

    @FXML
    private void displayDocuments(ActionEvent actionEvent) {
        try {
            ProjectModel.getInstance().setSelectedProjectId(0);
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.DOCUMENTS_VIEW);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading projects", e);
            alertHelper.showAndWait();
        }
    }
}
