package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Project;
import dk.easv.gui.controllers.helpers.ZipCodeChecker;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddProjectController extends RootController {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ICustomerModel customerModel = CustomerModel.getInstance();
            projectModel = ProjectModel.getInstance();
            customerComboBox.setItems(customerModel.getObsAllCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void createOnAction(ActionEvent actionEvent) {
        if (checkEmptyFields()) return;
        int zipCode = ZipCodeChecker.checkZipCode(zipcodeTextField.getText());
        if (zipCode == 0) return;
        try {
            projectModel.addProject(new Project(nameTextField.getText(), startDatePicker.getValue(), endDatePicker.getValue(), customerComboBox.getSelectionModel().getSelectedItem().getCustomerID(), addressTextField.getText(), zipCode));
        } catch (SQLException e) {
            // catch if exception in add
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        getStage().close();
    }

    public void setProjectData(Project project) {
        nameTextField.setText(project.getProjectName());
        startDatePicker.setValue(project.getStartDate());
        endDatePicker.setValue(project.getEndDate());
        addressTextField.setText(project.getProjectAddress());
        zipcodeTextField.setText(String.valueOf(project.getProjectZipcode()));

        submitButton.setText("Update");
        submitButton.setOnAction(event -> {
            try {
                if (checkEmptyFields()) return;
                int zip = ZipCodeChecker.checkZipCode(zipcodeTextField.getText());
                if (zip == 0) return;
                projectModel.updateProject(new Project(project.getProjectID(), nameTextField.getText(), startDatePicker.getValue(), endDatePicker.getValue(), customerComboBox.getSelectionModel().getSelectedItem().getCustomerID(), addressTextField.getText(), Integer.parseInt(zipcodeTextField.getText())));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getStage().close();
        });
    }

    //a method to check if all fields are filled out
    private boolean checkEmptyFields(){
        if (nameTextField.getText().isEmpty() || startDatePicker.getValue() == null || endDatePicker.getValue() == null || addressTextField.getText().isEmpty() || zipcodeTextField.getText().isEmpty() || customerComboBox.getSelectionModel().getSelectedItem() == null){
            AlertHelper alertHelper = new AlertHelper("Please fill out all fields", Alert.AlertType.WARNING);
            alertHelper.showAndWait();
            return true;
        } else{
            return false;
        }
    }
}
