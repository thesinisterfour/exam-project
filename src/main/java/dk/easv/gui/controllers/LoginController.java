package dk.easv.gui.controllers;


import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.Shake;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.LoginModel;
import dk.easv.gui.models.interfaces.ILoginModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.CurrentUser;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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

    private final ILoginModel model = new LoginModel(this);
    @FXML
    private MFXTextField username;
    @FXML
    private MFXTextField password;
    private Stage stage;

    private final CurrentUser newUser = CurrentUser.getInstance();
    @FXML
    private MFXButton loginButton;
    @FXML
    private VBox textfieldsVbox;

    @FXML
    private void loginButtonAction(ActionEvent actionEvent) throws SQLException {
        this.stage = this.getStage();
        User selectedUser = model.checkForUser(username.getText(), password.getText());
        if (selectedUser != null) {
            newUser.setId(selectedUser.getUserID());
            newUser.setRole(selectedUser.getRole());
            newUser.setName(selectedUser.getFirstName());
            displayMain();
        } else {
            // Display wrong input message on top of the textfields
            ObservableList<Node> children = textfieldsVbox.getChildren();
            if (children.size() == 2) {
                Label wrongInput = new Label("Wrong username or password");
                wrongInput.setPadding(new Insets(10, 10, 10, 10));
                wrongInput.setStyle("-fx-background-color: rgba(255,141,141,0.6); -fx-background-radius: 5");
                wrongInput.setPrefWidth(username.getPrefWidth());
                children.add(0, wrongInput);
            }

            // Clear textfields, add styling and shake them
            username.setText("");
            password.setText("");
            username.getStyleClass().add("wrong-input");
            password.getStyleClass().add("wrong-input");
            new Shake(username).setSpeed(2).play();
            new Shake(password).setSpeed(2).play();

        }
    }

    private void displayMain() {
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
            try {
                Parent root = future.get();
                root.setOpacity(0);
                stage.setTitle("WUAV!!! " + newUser.getRole().toString());

                stage.setScene(new Scene(root));
                new FadeIn(root).setSpeed(1).play();

            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        });

        fo.setSpeed(1).play();

        es.shutdown();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setText("Loading users...");
        loginButton.setDisable(true);

    }

    public void setLoginReady() {
        loginButton.setText("Login");
        loginButton.setDisable(false);
    }
}
