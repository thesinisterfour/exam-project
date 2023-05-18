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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
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

    public void setUserBoxes(ConcurrentMap<Integer, User> user){
        this.users = user;
        populateUserHBox();
    }

    public void setCustomerBoxes(ConcurrentMap<Integer, Customer> customers){
        this.customers = customers;
        populateCustomersHBox();
    }

    private void populateUserHBox() {
        try {
                ObservableList<Node> children = mainHboxCard.getChildren();
                Set<Integer> keys = users.keySet();
                for (Integer key : keys) {
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/Card.fxml")));
                    Parent parent = loader.load();
                    CardController cardController = loader.getController();
                    cardController.receiveUserData(users);
                    cardController.createCards(new Card(users.get(key).getFirstName(), users.get(key).getLastName(), users.get(key).getRole().toString()));
                    children.addAll(parent);
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void populateCustomersHBox() {
        try {
            ObservableList<Node> children = mainHboxCard.getChildren();
            Set<Integer> keys = customers.keySet();
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/Card.fxml")));
                Parent parent = loader.load();
                CardController cardController = loader.getController();
                cardController.receiveCustomerData(customers);
                cardController.createCards(new Card(customers.get(key).getCustomerName(), customers.get(key).getCustomerAddress(), customers.get(key).getZipCode(), customers.get(key).getCustomerEmail()));
                children.addAll(parent);
            }
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}

