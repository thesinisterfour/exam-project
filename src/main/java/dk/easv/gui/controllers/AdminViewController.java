package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController extends RootController {

    @FXML
    private MFXTextField userName;
    @FXML
    private MFXComboBox comboBox;
    @FXML
    private MFXTableView tableView;
    @FXML
    private MFXButton logoutButton;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    @FXML
    public void handleLogout() throws IOException {
        if (stage == null) {
            System.err.println("Stage is null. Unable to perform logout.");
            return;
        }
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/path/to/login-view.fxml"));
        Parent root = fxmlLoader.load();
        ControllerLogin loginController = fxmlLoader.getController();

        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(root, 760, 480));
        loginStage.setTitle("Login");
        loginController.setStage(loginStage);

        loginStage.show();
    }
    @FXML
    private void handleDelete() {

    }
    @FXML
    private void handleCreateDocument() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/create-document.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 990, 625);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
    }
    @FXML
    private void handleEditDocument() {

    }
    @FXML
    private void handleUsers() {

    }
    @FXML
    private void handleCustomers() {

    }

    public void displayFxml() throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
        Scene scene = new Scene(controller.getView(), 760, 480);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }
}
