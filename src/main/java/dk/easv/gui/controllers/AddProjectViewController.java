package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Project;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.AlertHelper;
import dk.easv.gui.controllers.helpers.InputValidators;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
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

public class AddProjectViewController extends RootController {
    @FXML
    private MFXTextField nameTextField;
    @FXML
    private MFXDatePicker startDatePicker;
    @FXML
    private MFXDatePicker endDatePicker;
    @FXML
    private MFXTextField addressTextField;
    @FXML
    private MFXTextField zipcodeTextField;

    private IProjectModel projectModel;
    @FXML
    private MFXFilterComboBox<Customer> customerComboBox;
    @FXML
    private MFXButton submitButton;
    @FXML
    private VBox rootVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ICustomerModel customerModel = CustomerModel.getInstance();
            projectModel = ProjectModel.getInstance();
            customerComboBox.setItems(customerModel.getObsCustomers());
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading data", e);
            alertHelper.showAndWait();
        }

    }

    @FXML
    private void createOnAction(ActionEvent actionEvent) {
        if (InputValidators.isEmptyField(rootVBox.getChildren())) return;
        int zipCode = InputValidators.checkZipCode(zipcodeTextField.getText());
        if (zipCode == 0) return;
        try {
            projectModel.addProject(new Project(nameTextField.getText(), startDatePicker.getValue(), endDatePicker.getValue(), customerComboBox.getSelectionModel().getSelectedItem().getCustomerID(), addressTextField.getText(), zipCode));
        } catch (SQLException e) {
            // catch if exception in add
            AlertHelper alertHelper = new AlertHelper("An error occurred while creating a project", e);
            alertHelper.showAndWait();
        }
        goBack();
    }


    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        goBack();
    }

    private void goBack() {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.PROJECTS_VIEW);
            BorderPane borderPane = (BorderPane) rootVBox.getParent();
            borderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        }
    }

    public void setProjectData(Project project) {
        nameTextField.setText(project.getProjectName());
        startDatePicker.setValue(project.getStartDate());
        endDatePicker.setValue(project.getEndDate());
        addressTextField.setText(project.getProjectAddress());
        zipcodeTextField.setText(String.valueOf(project.getProjectZipcode()));
        customerComboBox.getSelectionModel().selectItem(getCustomerById(project.getCustomerID()));

        submitButton.setText("Update");
        submitButton.setOnAction(event -> {
            try {
                if (InputValidators.isEmptyField(rootVBox.getChildren())) return;
                int zip = InputValidators.checkZipCode(zipcodeTextField.getText());
                if (zip != 0) {
                    projectModel.updateProject(new Project(project.getProjectID(), nameTextField.getText(), startDatePicker.getValue(), endDatePicker.getValue(), customerComboBox.getSelectionModel().getSelectedItem().getCustomerID(), addressTextField.getText(), Integer.parseInt(zipcodeTextField.getText())));
                }
                goBack();
            } catch (SQLException e) {
                AlertHelper alertHelper = new AlertHelper("An error occurred while updating a project", e);
                alertHelper.showAndWait();
            }
            getStage().close();
        });
    }

    private Customer getCustomerById(int customerID) {
        for (Customer customer : customerComboBox.getItems()) {
            if (customer.getCustomerID() == customerID) {
                return customer;
            }
        }
        return null;
    }

}
