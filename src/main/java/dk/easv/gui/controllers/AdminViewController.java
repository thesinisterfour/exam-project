package dk.easv.gui.controllers;

import dk.easv.be.Document;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class AdminViewController extends RootController {

    @FXML
    private MFXTextField userName;
    @FXML
    private MFXComboBox comboBox;
    @FXML
    private MFXTableView<Document> tableView;
    @FXML
    private MFXButton logoutButton;

    private Stage stage;

    private ConcurrentMap<Integer, Document> documentMap;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    @FXML
    public void handleLogout() throws IOException {
        if (stage == null) {
            this.stage = this.getStage();
        }

        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView(), 760, 480));
        this.stage.setTitle("Login");
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
