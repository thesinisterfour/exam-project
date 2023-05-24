package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.gui.controllers.helpers.ZipCodeChecker;
import dk.easv.gui.models.CityModel;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.interfaces.ICityModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerViewController extends RootController {

    private final ICityModel cityModel = new CityModel();
    private final ICustomerModel customerModel = CustomerModel.getInstance();
    @FXML
    private MFXTextField nameTextField, emailTextField, addressTextField, zipCodeTextField;
    @FXML
    private VBox rootVBox;
    @FXML
    private MFXButton submitButton;

    public AddCustomerViewController() throws SQLException {
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        getStage().close();

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

        if (!isEmptyField()) {
            int zipCode = ZipCodeChecker.checkZipCode(zipCodeTextField.getText());
            if (zipCode == 0) return;
            try {
                customerModel.add(new Customer(nameTextField.getText(), emailTextField.getText(), addressTextField.getText(), zipCode));
            } catch (SQLException e) {
                // catch if exception in add
                throw new RuntimeException(e);
            }
            getStage().close();
        }


    }



    private boolean isEmptyField() {
        ObservableList<Node> nodes = this.getView().getChildrenUnmodifiable();
        boolean emptyField = false;
        for (Node node : nodes) {
            if (node instanceof MFXTextField textField) {
                textField.setText(textField.getText().strip());
                if (textField.getText().isEmpty()) {
                    textField.setPromptText("Fill this please");
                    textField.setStyle(textField.getStyle() + "-fx-border-color : red");
                    emptyField = true;
                }
            }
        }
        return emptyField;
    }


    public void setCustomerData(Customer customer) {


        nameTextField.setText(customer.getCustomerName());
        emailTextField.setText(customer.getCustomerEmail());
        addressTextField.setText(customer.getCustomerAddress());
        zipCodeTextField.setText(String.valueOf(customer.getZipCode()));

        submitButton.setText("Edit Customer");
        submitButton.setOnAction(event -> {
            try {
                if (isEmptyField()) {
                    return;
                }
                int zipcode = ZipCodeChecker.checkZipCode(zipCodeTextField.getText());
                if (zipcode == 0) return;
                customerModel.updateCustomer(new Customer(customer.getCustomerID(), nameTextField.getText(), emailTextField.getText(), addressTextField.getText(), zipcode));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            getStage().close();
        });

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
