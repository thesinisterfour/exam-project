package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Project;
import dk.easv.gui.models.CityModel;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.ICityModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
        ICityModel cityModel = new CityModel();
        int zipCode = Integer.parseInt(zipcodeTextField.getText());
        try {
            cityModel.get(zipCode);
        } catch (SQLException e) {
            // catch if city does not exist
            System.out.println("City does not exist");
            return;
        }
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
}
