package dk.easv.gui.controllers;


import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class LoginController extends RootController {
    @FXML
    private MFXTextField username;
    @FXML
    private MFXTextField password;
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
