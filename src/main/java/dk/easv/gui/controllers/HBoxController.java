package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.User;
import dk.easv.gui.rootContoller.RootController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HBoxController extends RootController {

    @FXML
    private HBox mainHboxCard;
    @FXML
    private Label testing;
    private HBox hBox;
    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void populateHBox() {
        try {
            ObservableList<Node> children = mainHboxCard.getChildren();
            for (int i = 0; i < 12; i++){
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/HboxCard.fxml")));
                Parent parent = loader.load();
                WorkerCardController workerCardController = loader.getController();

                workerCardController.setWorkerView(new HBox(users.get(i).getUserID()));
                testing.setText(String.valueOf(i));
                children.addAll(parent);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setUserList(ConcurrentMap<Integer, User> userList){
        this.users = userList;
        populateHBox();
    }

}

