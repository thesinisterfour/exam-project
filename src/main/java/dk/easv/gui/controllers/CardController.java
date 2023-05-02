package dk.easv.gui.controllers;

import dk.easv.be.Card;
import dk.easv.be.Customer;
import dk.easv.be.User;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.rootContoller.RootController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
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

    private final UserModel userModel = new UserModel();

    private final CustomerModel customerModel = new CustomerModel();

    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    private ConcurrentMap<Integer, Customer> customers = new ConcurrentHashMap<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            test();
            System.out.println(customers.keySet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    public void test() throws SQLException {
        users = userModel.getAllUsers();
        customers = customerModel.getAllCustomers();
        test2();
    }

    public void test2(){
        if(customers != null){
            username.setText(customers.get(15).getCustomerName());
            userAddress.setText(customers.get(15).getCustomerAddress());
            userEmail.setText(customers.get(15).getCustomerEmail());
            String string = String.valueOf(customers.get(15).getZipCode());
            userZipcode.setText(string);
        }
        else{
            System.out.println("test");
        }
    }

    public void createCards(Card card){
        this.card = card;
        if(card.getUserZipCode() != null){
            username.setText(card.getUserName());
            userAddress.setText(card.getUserAddress());
            userZipcode.setText(card.getUserZipCode());
            //userEmail.setText(card.getUserEmail());
        }
        if(card.getUserRole() != null){
            username.setText(card.getUserName());
            userEmail.setText(card.getUserEmail());
            System.out.println(card.getUserRole());
        }
        else{
            throw new RuntimeException("Failed test");
        }
    }
}
