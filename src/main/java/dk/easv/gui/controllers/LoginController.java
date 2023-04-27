package dk.easv.gui.controllers;


import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController extends RootController {
    @FXML
    private MFXTextField username;
    @FXML
    private MFXTextField password;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private void loginButtonAction(ActionEvent actionEvent) throws IOException {
        if (stage == null){
            this.stage = this.getStage();
        }
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
        stage.setScene(new Scene(controller.getView(), 760, 480));
        stage.setTitle("Admin");

//        System.out.println("Logged in");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
