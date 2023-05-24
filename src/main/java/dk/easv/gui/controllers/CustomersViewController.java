package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.controllers.tasks.LoadCustomerModelTask;
import dk.easv.gui.controllers.tasks.LoadDocumentModelTask;
import dk.easv.gui.controllers.tasks.LoadProjectModelTask;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTableView;
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

public class CustomersViewController extends RootController {
    private ICustomerModel customerModel;

    @FXML
    private MFXTableView<Customer> customersTable;
    @FXML
    private VBox rootVBox;


    @FXML
    private void createCustomer(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.CREATE_CUSTOMERS);
            Scene scene = new Scene(controller.getView());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void editCustomer(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.CREATE_CUSTOMERS);
            stage.setUserData(customersTable.getSelectionModel().getSelectedValues().get(0));
            Scene scene = new Scene(controller.getView());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            AlertHelper alertHelper = new AlertHelper("Please select a Customer", Alert.AlertType.WARNING);
            alertHelper.showAndWait();
        }
    }

    @FXML
    private void deleteCustomer(ActionEvent actionEvent) {
        try {
            customerModel.deleteCustomer(customersTable.getSelectionModel().getSelectedValues().get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            AlertHelper alertHelper = new AlertHelper("Please select a Customer", Alert.AlertType.WARNING);
            alertHelper.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            TableSetters.setUpCustomerTable(customersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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

        LoadCustomerModelTask loadCustomerModelTask = new LoadCustomerModelTask();
        loadCustomerModelTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            customerModel = newValue;
        });

        LoadProjectModelTask loadProjectModelTask = new LoadProjectModelTask();

        es.submit(loadCustomerModelTask);
        es.submit(loadDocumentModelTask);
        es.submit(loadProjectModelTask);


        es.shutdown();
    }


    @FXML
    private void viewProjectsAction(ActionEvent actionEvent) {
        try {
            if (customersTable.getSelectionModel().getSelectedValues().isEmpty()) {
                AlertHelper alertHelper = new AlertHelper("Please select a Customer", Alert.AlertType.WARNING);
                alertHelper.showAndWait();
                return;
            }

            customerModel.setSelectedCustomerId(customersTable.getSelectionModel().getSelectedValues().get(0).getCustomerID());
            BorderPane mainBorderPane = (BorderPane) rootVBox.getParent();
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.PROJECTS_VIEW);
            mainBorderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
