package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.AlertHelper;
import dk.easv.gui.controllers.helpers.InputValidators;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerViewController extends RootController {
    private final ICustomerModel customerModel;
    @FXML
    private MFXTextField nameTextField, emailTextField, addressTextField, zipCodeTextField;
    @FXML
    private VBox rootVBox;
    @FXML
    private MFXButton submitButton;

    public AddCustomerViewController() throws SQLException {
        customerModel = CustomerModel.getInstance();
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        goBack();
    }

    @FXML
    void removeCustomerData(ActionEvent event) {
        nameTextField.setText("");
        emailTextField.setText("");
        addressTextField.setText("");
        zipCodeTextField.setText("");
    }

    @FXML
    private void submitButtonAction(ActionEvent event) {

        if (!InputValidators.isEmptyField(rootVBox.getChildren())) {
            if (!emailTextField.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
                AlertHelper alertHelper = new AlertHelper("Email is not valid", Alert.AlertType.ERROR);
                alertHelper.showAndWait();
                return;
            }
            int zipCode = InputValidators.checkZipCode(zipCodeTextField.getText());
            if (zipCode == 0) return;
            try {
                customerModel.add(new Customer(nameTextField.getText(), emailTextField.getText(), addressTextField.getText(), zipCode));
            } catch (SQLException e) {
                // catch if exception in add
                AlertHelper alertHelper = new AlertHelper("Error adding customer", Alert.AlertType.ERROR);
                alertHelper.showAndWait();
                return;
            }
            goBack();
        }
    }

    private void goBack() {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.CUSTOMERS_VIEW);
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


    public void setCustomerData(Customer customer) {
        nameTextField.setText(customer.getCustomerName());
        emailTextField.setText(customer.getCustomerEmail());
        addressTextField.setText(customer.getCustomerAddress());
        zipCodeTextField.setText(String.valueOf(customer.getZipCode()));

        submitButton.setText("Edit Customer");
        submitButton.setOnAction(event -> {
            try {
                if (!InputValidators.isEmptyField(rootVBox.getChildren())) {
                    int zipcode = InputValidators.checkZipCode(zipCodeTextField.getText());
                    if (zipcode == 0) return;
                    customerModel.updateCustomer(new Customer(customer.getCustomerID(), nameTextField.getText(), emailTextField.getText(), addressTextField.getText(), zipcode));
                    goBack();
                }
            } catch (SQLException e) {
                AlertHelper alertHelper = new AlertHelper("Error updating customer", Alert.AlertType.ERROR);
                alertHelper.showAndWait();
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}") || newValue.isEmpty()) {
                emailTextField.setFloatingText("Email");
            } else {
                emailTextField.setFloatingText("Invalid email");
            }
        });
    }
}
