package dk.easv.gui.controllers;


import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.LoginModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController extends RootController {

    private final LoginModel model = new LoginModel();
    @FXML
    private MFXTextField username;
    @FXML
    private MFXTextField password;
    private Stage stage;

    @FXML
    private void loginButtonAction(ActionEvent actionEvent) throws SQLException, IOException {
        this.stage = this.getStage();
        User selectedUser = model.checkForUser(username.getText(), password.getText());
        if (selectedUser != null){
            switch (selectedUser.getRole()) {
                case ADMIN -> displayAdmin();
                case PROJECTMANAGER -> displayProjectManager();
                case SALESPERSON -> displaySalesPerson();
                case TECHNICIAN -> displayTechnician();
            }
        }
    }

    private void displayAdmin() throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
        Scene scene = new Scene(controller.getView(), 760, 480);
        stage.setTitle("Admin");
        stage.setScene(scene);
        stage.show();
    }

    private void displayProjectManager() throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.PROJECT_MANAGER);
        Scene scene = new Scene(controller.getView(), 760, 480);
        stage.setTitle("Project Manager");
        stage.setScene(scene);
        stage.show();
    }

    private void displaySalesPerson() throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.SALESPERSON);
        Scene scene = new Scene(controller.getView(), 760, 480);
        stage.setTitle("Sales Person");
        stage.setScene(scene);
        stage.show();
    }

    private void displayTechnician() throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.TECHNICIAN);
        Scene scene = new Scene(controller.getView(), 760, 480);
        stage.setTitle("Technician");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
