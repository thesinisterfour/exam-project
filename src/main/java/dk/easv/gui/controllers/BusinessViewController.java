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
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class BusinessViewController extends RootController{

    @FXML
    private MFXButton businessLayer,
            createCustomer,
            delete,
            HomeLayer,
            workersLayer,
            edit,
            logoutButton;

    @FXML
    private HBox Customers, mainHbox;


    @FXML
    private MFXScrollPane customersScrollPane;


    @FXML
    private MFXTableView<?> documentsTable;


    @FXML
    private VBox iconsVbox;
    @FXML
    private MFXTextField searchBar;

    private Stage stage;

    private ICustomerModel customerModel = new CustomerModel();

    public BusinessViewController() throws SQLException {
    }

    @FXML
    private void displayWorkers(ActionEvent actionEvent) throws IOException {
        this.stage = this.getStage();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.WORKERS);
        this.stage.setScene(new Scene(controller.getView()));
    }

    @FXML
    private void displayHome(ActionEvent actionEvent) throws IOException {
        this.stage = this.getStage();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.MAIN);
        this.stage.setScene(new Scene(controller.getView()));
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

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        this.stage = this.getStage();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView()));
        this.stage.setTitle("WUAV!!!");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            businessLayer.setDisable(true);
            initCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
