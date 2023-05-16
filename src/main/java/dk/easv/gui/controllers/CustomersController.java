package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Customer;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomersController extends RootController {

    private Stage stage;

    private ICustomerModel customerModel = new CustomerModel();


    private ConcurrentMap<Integer, Customer> customers = new ConcurrentHashMap<>();

    @FXML
    private MFXButton edit, delete;

    @FXML
    private HBox Customers;

    @FXML
    private MFXScrollPane customersScrollPane;

    public CustomersController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        test();
    }

    private ConcurrentMap<Integer, Customer> getAllCustomersMap() throws SQLException {

        return customerModel.getAllCustomers();
    }

    private void initCustomers() throws SQLException {
        ConcurrentMap<Integer, Customer> map = getAllCustomersMap();
        Set<Integer> keys = map.keySet();
        try {
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/HboxCard.fxml")));
                HBox hBox = loader.load();
                HBoxController hboxController = loader.getController();
                hboxController.setCustomerBoxes(map);
                addLabelAndScrollPane(key.toString(), hBox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addLabelAndScrollPane(String key, HBox hBox) {
        Label label = new Label(key);
        Customers.getChildren().add(label);
        customersScrollPane.setContent(hBox);
        customersScrollPane.setFitToHeight(true);
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
