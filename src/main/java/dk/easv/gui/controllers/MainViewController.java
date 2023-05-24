package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.tasks.LoadCustomerModelTask;
import dk.easv.gui.controllers.tasks.LoadDocumentModelTask;
import dk.easv.gui.controllers.tasks.LoadProjectModelTask;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.rootContoller.IRootController;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

    private final UserSingleClass actualUser = UserSingleClass.getInstance();
    @FXML
    public MFXButton addDocument,
            businessLayer,
            deleteDocument,
            HomeLayer,
            editDocument,
            workersLayer;
    @FXML
    public MFXTableView<Customer> customerTable;
    @FXML
    public MFXTableView<Doc> documentsTable;
    @FXML
    public MFXTableView<Project> projectTable;
    @FXML
    public MFXTextField searchBar;
    @FXML
    public HBox mainHbox;
    @FXML
    public VBox iconsVbox;
    @FXML
    public Label customerLabel, projectLabel;
    private Stage stage;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private VBox centerVBox;
    @FXML
    private MFXButton logoutButton;
    @FXML
    private MFXRectangleToggleNode projectsToggle;
    @FXML
    private VBox customersVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                throw new RuntimeException(e);
            }
        });

        es.submit(new LoadCustomerModelTask());
        es.submit(loadDocumentModelTask);
        es.submit( new LoadProjectModelTask());


        es.shutdown();



        try {
            IRootController controller = ControllerFactory.loadFxmlFile(ViewType.BUSINESS_VIEW);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        roleView();

    }

    private void roleView() {
        if (actualUser != null) {
            if (actualUser.getRole() == Role.TECHNICIAN) {
                iconsVbox.getChildren().remove(businessLayer);
                iconsVbox.getChildren().remove(workersLayer);
            }
            if (actualUser.getRole() == Role.SALESPERSON) {
                iconsVbox.getChildren().remove(workersLayer);
            }
            if (actualUser.getRole() == Role.PROJECTMANAGER) {
                iconsVbox.getChildren().remove(businessLayer);

            }
        }
    }

    @FXML
    public void handleLogout(ActionEvent actionEvent) throws IOException {
        this.stage = (Stage) logoutButton.getScene().getWindow();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView()));
        this.stage.setTitle("WUAV!!!");
        this.stage.centerOnScreen();
        DocumentHelper.setOldDocWarningShown(false);
    }

    @FXML
    private void displayBusiness() throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.BUSINESS_VIEW);
        mainBorderPane.setCenter(controller.getView());
    }

    @FXML
    private void displayWorkers(ActionEvent actionEvent) throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.WORKERS);
        mainBorderPane.setCenter(controller.getView());
    }

    @FXML
    private void displayProjects(ActionEvent actionEvent) {
        try {
            CustomerModel.getInstance().setSelectedCustomerId(0);
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.PROJECTS_VIEW);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void displayDocuments(ActionEvent actionEvent) {
        try {
            ProjectModel.getInstance().setSelectedProjectId(0);
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.DOCUMENTS_VIEW);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
