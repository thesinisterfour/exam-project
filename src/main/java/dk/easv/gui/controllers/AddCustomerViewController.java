package dk.easv.gui.controllers;

import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerViewController extends RootController {

    @FXML
    private MFXTextField emailTextField;

    @FXML
    private MFXTextField nameTextField;

    @FXML
    private MFXButton removeCustomer;

    @FXML
    void cancelButtonAction(ActionEvent event) {
        System.out.println("Cancel");

    }

    @FXML
    void removeCustomerData(ActionEvent event) {
        System.out.println("Remove");

    }

    @FXML
    void submitButtonAction(ActionEvent event) {
        System.out.println("Submit");

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
