package dk.easv.gui.controllers;


import dk.easv.gui.rootContoller.RootController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ControllerLogin extends RootController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private void loginButtonAction(ActionEvent actionEvent) {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/admin-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 760, 480);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();

        System.out.println("Logged in");
    }
}
