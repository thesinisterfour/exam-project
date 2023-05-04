package dk.easv.gui.controllers;

import dk.easv.be.Card;
import dk.easv.be.Customer;
import dk.easv.be.Project;
import dk.easv.be.User;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.rootContoller.RootController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CardController extends RootController {

    @FXML
    private Label userAddress;

    @FXML
    private Label userEmail;

    @FXML
    private Label userZipcode;

    @FXML
    private Label username;

    private Card card;

    @FXML
    private GridPane gridLoyal;

    private final UserModel userModel = new UserModel();

    private final CustomerModel customerModel = new CustomerModel();

    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    private ConcurrentMap<Integer, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void receiveUserData(ConcurrentMap<Integer, User> users) {
        this.users = users;
    }

    public void receiveCustomerData(ConcurrentMap<Integer, Customer> customers) {
        this.customers = customers;
    }


    public void createCards(Card card){
        this.card = card;
        if(card.getCustomerZipCode() != null){
            username.setText(card.getCustomerName());
            userAddress.setText(card.getCustomerAddress());
            userZipcode.setText(card.getCustomerZipCode());
            userEmail.setText(card.getCustomerEmail());
            gridLoyal.setStyle(gridLoyal.getStyle() + "-fx-background-color : red;");

        }
        if(card.getUserRole() != null){
            username.setText(card.getUserName());
            userEmail.setText(card.getUserName());
            userAddress.setText("");
            userZipcode.setText("");
            gridLoyal.setStyle(gridLoyal.getStyle() + "-fx-background-color : red;");
            System.out.println(card.getUserRole());
        }
    }
}
