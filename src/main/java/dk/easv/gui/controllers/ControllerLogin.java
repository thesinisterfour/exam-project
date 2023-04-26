package dk.easv.gui.controllers;


import dk.easv.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class ControllerLogin {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    public void loginButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/admin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 760, 480);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
