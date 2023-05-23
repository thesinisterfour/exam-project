package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.controllers.tasks.LoadCustomerModelTask;
import dk.easv.gui.controllers.tasks.LoadDocumentModelTask;
import dk.easv.gui.controllers.tasks.LoadProjectModelTask;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewController extends RootController {
    private IDocumentModel documentModel;
    private ICustomerModel customerModel;
    private IProjectModel projectModel;
    @FXML
    private VBox rootVBox;
    @FXML
    private VBox customersVBox;
    @FXML
    private Label customerLabel;
    @FXML
    private MFXRectangleToggleNode projectsToggle;
    @FXML
    private MFXTableView<Customer> customerTable;
    @FXML
    private MFXTableView<Project> projectTable;
    private BorderPane mainBorderPane;
    @FXML
    private MFXButton showDetailsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        projectTable.setVisible(false);
        ExecutorService es = Executors.newFixedThreadPool(10);

        LoadDocumentModelTask loadDocumentModelTask = new LoadDocumentModelTask();
        loadDocumentModelTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            documentModel = newValue;
            try {
                List<Doc> oldDocuments = documentModel.getOldDocuments();
                if (!oldDocuments.isEmpty() && !DocumentHelper.isOldDocWarningShown()) {
                    AlertHelper alertHelper = new AlertHelper(DocumentHelper.convertToString(oldDocuments), Alert.AlertType.INFORMATION);
                    if (alertHelper.showAndWait()) {
                        for (Doc oldDocument : oldDocuments) {
                            documentModel.deleteDocument(oldDocument.getId());
                        }

                    }
                    documentModel.setObsAllDocuments();
                    DocumentHelper.setOldDocWarningShown(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        LoadCustomerModelTask loadCustomerModelTask = new LoadCustomerModelTask();
        loadCustomerModelTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            customerModel = newValue;
            try {
                setUpCustomerTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        LoadProjectModelTask loadProjectModelTask = new LoadProjectModelTask();
        loadProjectModelTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            projectModel = newValue;
            setupProjectTable();
        });

        es.submit(loadCustomerModelTask);
        es.submit(loadDocumentModelTask);
        es.submit(loadProjectModelTask);


        es.shutdown();

        showDetailsButton.setOnAction(this::showProjectsView);
    }

    private void setUpCustomerTable() throws SQLException {
        TableSetters.setUpCustomerTable(customerTable);
    }

    private void setupProjectTable() {
        try {
            TableSetters.setUpProjectTable(projectTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showProjectsView(ActionEvent event){
        try {
            mainBorderPane = (BorderPane) rootVBox.getParent();
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.PROJECTS_VIEW);
            mainBorderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showDocumentsView(ActionEvent event){
        try {
            mainBorderPane = (BorderPane) rootVBox.getParent();
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.DOCUMENTS_VIEW);
            mainBorderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void toggleAction(ActionEvent actionEvent) {
        if (projectsToggle.isSelected()) {
            customerLabel.setText("Projects");
            customerTable.setVisible(false);
            projectTable.setVisible(true);
            showDetailsButton.setText("Show Documents");
            showDetailsButton.setOnAction(this::showDocumentsView);
        } else {
            customerLabel.setText("Customers");
            projectTable.setVisible(false);
            customerTable.setVisible(true);
            showDetailsButton.setText("Show Projects");
            showDetailsButton.setOnAction(this::showProjectsView);
        }
    }
}
