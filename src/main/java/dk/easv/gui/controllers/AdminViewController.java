package dk.easv.gui.controllers;

import dk.easv.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private ComboBox comboBox;
    @FXML
    private TableView tableView;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    @FXML
    private void handleLogout() {

    }
    @FXML
    private void handleDelete() {

    }
    @FXML
    private void handleCreateDocument() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/create-document.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 990, 625);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
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


}
