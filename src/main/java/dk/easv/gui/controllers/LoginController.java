package dk.easv.gui.controllers;


import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.LoginModel;
import dk.easv.gui.models.interfaces.ILoginModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class LoginController extends RootController {

    private final ILoginModel model = new LoginModel();
    @FXML
    private MFXTextField username;
    @FXML
    private MFXTextField password;
    private Stage stage;

    private UserSingleClass newUser = UserSingleClass.getInstance();

    @FXML
    private void loginButtonAction(ActionEvent actionEvent) throws SQLException, IOException {
        this.stage = this.getStage();
        User selectedUser = model.checkForUser(username.getText(), password.getText());
        newUser.setId(selectedUser.getUserID());
        newUser.setRole(selectedUser.getRole());
        newUser.setName(selectedUser.getFirstName());
        if (newUser != null) {
            displayMain();
        }
    }

    private void displayMain() throws IOException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Parent> future = es.submit(() -> {
            try {
                RootController controller = ControllerFactory.loadFxmlFile(ViewType.MAIN);
                return controller.getView();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        FadeOut fo = new FadeOut(root);
        fo.setOnFinished(e -> {
            Platform.runLater(() -> {
                try {
                    Parent root = future.get();
                    root.setOpacity(0);
                    stage.setTitle("WUAV!!! " + newUser.getRole().toString());
                    stage.setScene(new Scene(root));
                    new FadeIn(root).setSpeed(2).play();

                } catch (InterruptedException | ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });

        fo.setSpeed(2).play();

        es.shutdown();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
