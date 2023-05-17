package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.DocumentMapperModel;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IDocumentMapperModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateDocumentController extends RootController {
    @FXML
    private MFXTextField nameTextField;
    @FXML
    private MFXTextField descriptionTextField;
    @FXML
    private MFXFilterComboBox<Customer> customerComboBox;
    @FXML
    private MFXFilterComboBox<Project> projectComboBox;

    @FXML
    private void createOnAction(ActionEvent actionEvent) {
        try {
            final IDocumentModel model = new DocumentModel();
            final IDocumentMapperModel mapperModel = new DocumentMapperModel();
            boolean emptyField = false;
            Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
            Project selectedProject = projectComboBox.getSelectionModel().getSelectedItem();
            String name = nameTextField.getText();
            if (name.isEmpty()) {
                nameTextField.setPromptText("Required");
                emptyField = true;
            }
            if (selectedCustomer == null) {
                customerComboBox.setPromptText("Required");
                emptyField = true;
            }
            if (selectedProject == null) {
                projectComboBox.setPromptText("Required");
                emptyField = true;
            }
            if (emptyField) {
                return;
            }


            int docId;
            String description = descriptionTextField.getText();

            if (description.isEmpty()) {
                docId = model.addDocument(new Doc(name));

            } else {
                docId = model.addDocument(new Doc(name, description));
            }
            mapperModel.addDocumentToProject(selectedProject.getProjectID(), docId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
            this.getStage().setScene(new Scene(rootController.getView(), 760, 480));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ICustomerModel customerModel = new CustomerModel();
        IProjectModel projectModel = new ProjectModel();
        try {
            customerComboBox.setItems(customerModel.getObsCustomers());
            customerComboBox.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    projectModel.getProjectsByCustomerId(newValue.getCustomerID());
                    projectComboBox.setItems(projectModel.getProjectObservableList());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
