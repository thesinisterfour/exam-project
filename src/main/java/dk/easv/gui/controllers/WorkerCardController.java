package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Customer;
import dk.easv.be.User;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.rootContoller.RootController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WorkerCardController extends RootController {

    @FXML
    private HBox mainHBoxCard;

    private final UserModel userModel = new UserModel();

    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    private HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            exampleMika();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /*public void showWorker() throws IOException {
        populateHBox();
    }

     */

    public void exampleMika() throws SQLException {
        users = userModel.getAllUsers();
        System.out.println(users.get(users.size()).getFirstName());
    }

    public void setWorkerView(HBox hBox) {
        this.hBox = hBox;

    }


}