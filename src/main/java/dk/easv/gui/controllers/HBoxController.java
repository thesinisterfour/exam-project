package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Card;
import dk.easv.be.User;
import dk.easv.gui.models.UserModel;
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
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HBoxController extends RootController {

    @FXML
    private HBox mainHboxCard;
    private HBox hBox;
    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    private final UserModel userModel = new UserModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setUserList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        populateHBox();
    }

    public void setBoxes(User user){
        users.put(user.getUserID(), user);
        populateHBox();
    }

    private void populateHBox() {
        try {
                ObservableList<Node> children = mainHboxCard.getChildren();
                for(int i = 1; users.size() > i; i++){
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/Card.fxml")));
                    Parent parent = loader.load();
                    CardController cardController = loader.getController();
                    cardController.createCards(new Card(users.get(i).getFirstName(), users.get(i).getRole().toString()));
                    children.addAll(parent);
                }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setUserList() throws SQLException {
        users = userModel.getAllUsers();
        System.out.println(users.keySet());
    }

}

