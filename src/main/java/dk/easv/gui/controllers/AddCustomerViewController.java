package dk.easv.gui.controllers;

import dk.easv.be.City;
import dk.easv.be.Customer;
import dk.easv.dal.dao.CityDAO;
import dk.easv.gui.models.CityModel;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    void submitButtonAction(ActionEvent event) throws SQLException {
        if(nameTextField != null && emailTextField != null && addressTextField != null && cityTextField != null && ZipCodeTextField != null){
            int zipCode = Integer.parseInt(ZipCodeTextField.getText());
            cityModel.add(new City(zipCode ,cityTextField.getText()));
            customerModel.add(new Customer(nameTextField.getText(),emailTextField.getText(),addressTextField.getText(),zipCode));
        }
        else{
            System.out.println("Shazam");
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
