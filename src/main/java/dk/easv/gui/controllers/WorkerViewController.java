package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.bll.CRUDLogic;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WorkerViewController extends RootController{

    private CustomerModel customerModel = new CustomerModel();

    private ConcurrentMap<Integer, Customer> customers = new ConcurrentHashMap<>();

    @FXML
    private MFXButton newUser, edit, delete, back;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonsTest();
        try {
            fetchingCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Test controller initial commit
     */
    public void buttonsTest (){
        newUser.setOnAction(e -> {
            System.out.println(customers);
        });
        edit.setOnAction(e -> System.out.println("edit"));
        delete.setOnAction(e -> System.out.println("delete"));
        back.setOnAction(e -> System.out.println("go back"));
    }


    public void fetchingCustomers() throws SQLException {
        customers = customerModel.getAllCustomers();
    }
}
