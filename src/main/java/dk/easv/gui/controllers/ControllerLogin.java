package dk.easv.gui.controllers;


import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerLogin extends RootController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private void loginButtonAction(ActionEvent actionEvent) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/admin-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 760, 480);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
        Scene scene = new Scene(controller.getView(), 760, 480);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();

        System.out.println("Logged in");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
