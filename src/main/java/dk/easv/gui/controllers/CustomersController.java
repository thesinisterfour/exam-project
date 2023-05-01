package dk.easv.gui.controllers;
import dk.easv.Main;
import dk.easv.be.Customer;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomersController extends RootController {

    private Stage stage;

    private final CustomerModel customerModel = new CustomerModel();

    private ConcurrentMap<Integer, Customer> customers = new ConcurrentHashMap<>();

    @FXML
    private MFXButton edit, delete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            customers = customerModel.getAllCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        test();
    }
    public void test(){
        delete.setOnAction(e -> {
            System.out.println("Delete");
        });
        edit.setOnAction(e -> {
            System.out.println("Edit");
        });
    }

    @FXML
    private void createCustomer(ActionEvent actionEvent) throws RuntimeException, IOException {
        RootController controller;
        Stage stage = new Stage();
        controller = ControllerFactory.loadFxmlFile(ViewType.CREATE_CUSTOMERS);
        Scene scene = new Scene(controller.getView());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void goBack(ActionEvent actionEvent) throws RuntimeException, IOException {
        RootController controller;
        Stage stage = new Stage();
        controller = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
        Scene scene = new Scene(controller.getView());
        stage.setTitle("Admin");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        getStage().close();
    }
}
