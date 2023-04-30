package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.gui.models.CityModel;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerViewController extends RootController {

    @FXML
    private MFXTextField nameTextField, emailTextField, addressTextField, cityTextField, ZipCodeTextField;

    private final CityModel cityModel = new CityModel();

    private final CustomerModel customerModel = new CustomerModel();

    private Stage stage;
    @FXML
    void cancelButtonAction(ActionEvent event) {
        getStage().close();

    }

    @FXML
    void removeCustomerData(ActionEvent event) {
        nameTextField.setText("");
        emailTextField.setText("");
        addressTextField.setText("");
        cityTextField.setText("");
        ZipCodeTextField.setText("");
    }

    @FXML
    void submitButtonAction(ActionEvent event) {
        ObservableList<Node> nodes = this.getView().getChildrenUnmodifiable();
        boolean emptyField = false;
        for (Node node : nodes) {
            if (node instanceof MFXTextField textField){
                textField.setText(textField.getText().strip());
                if (textField.getText().isEmpty()) {
                    textField.setPromptText("Fill this please");
                    textField.setStyle(textField.getStyle() + "-fx-border-color : red");
                    emptyField = true;
                }
            }
        }
        if (!emptyField){
            int zipCode = Integer.parseInt(ZipCodeTextField.getText());
            try {
                System.out.println(cityModel.get(zipCode));
            } catch (SQLException e) {
                // catch if city does not exist
                System.out.println("City does not exist");
                return;
            }
            try {
                customerModel.add(new Customer(nameTextField.getText(),emailTextField.getText(),addressTextField.getText(),zipCode));
            } catch (SQLException e) {
                // catch if exception in add
                throw new RuntimeException(e);
            }
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
