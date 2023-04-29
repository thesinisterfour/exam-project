package dk.easv.gui.controllers;

import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerViewController extends RootController {

    @FXML
    private MFXTextField emailTextField;

    @FXML
    private MFXTextField nameTextField;

    private Stage stage;
    @FXML
    void cancelButtonAction(ActionEvent event) {
        getStage().close();

    }

    @FXML
    void removeCustomerData(ActionEvent event) {
        nameTextField.setText("");
        emailTextField.setText("");

    }

    @FXML
    void submitButtonAction(ActionEvent event) {
        System.out.println("Submit");

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
