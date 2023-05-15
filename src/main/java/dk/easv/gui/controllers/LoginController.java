package dk.easv.gui.controllers;


import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.interfaces.ILoginModel;
import dk.easv.gui.models.LoginModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


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
        System.out.println(newUser);
        if (newUser != null){
            displayMain();
            }
    }

    private void displayMain() throws IOException{
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.MAIN);
        Scene scene = new Scene(controller.getView());
        stage.setTitle(newUser.getRole().toString());
        stage.setScene(scene);
        stage.show();
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
