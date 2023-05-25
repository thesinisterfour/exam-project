package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.gui.controllerFactory.ControllerFactory;
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
    private VBox rootVBox;

    @FXML
    private void createOnAction(ActionEvent actionEvent) {
        try {
            final IDocumentModel model = DocumentModel.getInstance();
            final IDocumentMapperModel mapperModel = new DocumentMapperModel();
            if (!InputValidators.isEmptyField(rootVBox.getChildren())) {
                Project selectedProject = projectComboBox.getSelectionModel().getSelectedItem();
                int docId = model.addDocument(new Doc(nameTextField.getText(),descriptionTextField.getText()));
                mapperModel.addDocumentToProject(selectedProject.getProjectID(), docId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.DOCUMENTS_VIEW);
            BorderPane borderPane = (BorderPane) rootVBox.getParent();
            borderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
