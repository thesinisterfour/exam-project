package dk.easv.gui.controllers;


import dk.easv.gui.rootContoller.RootController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerLogin extends RootController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private void loginButtonAction(ActionEvent actionEvent) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/admin-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 760, 480);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
        System.out.println("Logged in");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
