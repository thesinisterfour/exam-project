package dk.easv.gui.controllers;


import dk.easv.be.Role;
import dk.easv.be.User;
import dk.easv.bll.CRUDLogic;
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
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController extends RootController {

    /*No models yet so routing straight to bll, will be changed when models are implemented*/
    CRUDLogic bll = new CRUDLogic();
    @FXML
    private MFXTextField username;
    @FXML
    private MFXTextField password;

    Stage stage;

    @FXML
    private void loginButtonAction(ActionEvent actionEvent) throws SQLException, IOException {

        User selectedUser = bll.checkForUser(username.getText(), password.getText());
        if (selectedUser != null){
            if (selectedUser.getRole() == Role.ADMIN){
                displayAdmin();
            } else if (selectedUser.getRole() == Role.PROJECTMANAGER) {
                displayProjectManager();
            } else if (selectedUser.getRole() == Role.SALESPERSON) {
                displaySalesPerson();
            } else if (selectedUser.getRole() == Role.TECHNICIAN) {
                displayTechnician();
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
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.SALES_PERSON);
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
        stage = this.stage;
    }
}
