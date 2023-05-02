package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Card;
import dk.easv.be.Customer;
import dk.easv.be.User;
import dk.easv.gui.rootContoller.RootController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HBoxController extends RootController {

    @FXML
    private HBox mainHboxCard;
    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    private ConcurrentMap<Integer, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setBoxes(ConcurrentMap<Integer, User> user){
        this.users = user;
        populateHBox();
    }

    private void populateHBox() {
        try {
                ObservableList<Node> children = mainHboxCard.getChildren();
                for(int i = 1; users.size() > i; i++){
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/Card.fxml")));
                    Parent parent = loader.load();
                    CardController cardController = loader.getController();
                    cardController.receiveData(users);
                    cardController.createCards(new Card(users.get(i).getFirstName(),users.get(i).getFirstName(), users.get(i).getRole().toString()));
                    children.addAll(parent);
                }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}

