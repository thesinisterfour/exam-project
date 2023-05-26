package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.AlertHelper;
import dk.easv.gui.controllers.helpers.InputValidators;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddDocumentViewController extends RootController {
    @FXML
    private MFXTextField nameTextField;
    @FXML
    private MFXTextField descriptionTextField;
    @FXML
    private MFXFilterComboBox<Customer> customerComboBox;
    @FXML
    private MFXFilterComboBox<Project> projectComboBox;
    @FXML
    private VBox rootVBox;

    @FXML
    private void createOnAction(ActionEvent actionEvent) {
        try {
            final IDocumentModel model = DocumentModel.getInstance();
            final IDocumentMapperModel mapperModel = new DocumentMapperModel();
            if (!InputValidators.isEmptyField(rootVBox.getChildren())) {
                Project selectedProject = projectComboBox.getSelectionModel().getSelectedItem();
                int docId = model.addDocument(new Doc(nameTextField.getText(), descriptionTextField.getText()));
                mapperModel.addDocumentToProject(selectedProject.getProjectID(), docId);
                goBack();
            }
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while creating a document", e);
            alertHelper.showAndWait();
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        goBack();
    }

    private void goBack() {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.DOCUMENTS_VIEW);
            BorderPane borderPane = (BorderPane) rootVBox.getParent();
            borderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex){
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ICustomerModel customerModel = CustomerModel.getInstance();
            IProjectModel projectModel = ProjectModel.getInstance();
            customerComboBox.setItems(customerModel.getObsCustomers());
            customerComboBox.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    projectModel.getProjectsByCustomerId(newValue.getCustomerID());
                    projectComboBox.setItems(projectModel.getProjectObservableList());
                } catch (SQLException e) {
                    AlertHelper alertHelper = new AlertHelper("An error occurred while loading the projects", e);
                    alertHelper.showAndWait();
                }
            });
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading the properties", e);
            alertHelper.showAndWait();
        }
    }
}
