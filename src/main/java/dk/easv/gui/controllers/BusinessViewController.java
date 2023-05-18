package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class BusinessViewController extends RootController{

    private UserSingleClass actualUser = UserSingleClass.getInstance();
    @FXML
    private HBox customers, mainHbox;


    @FXML
    private MFXScrollPane customersScrollPane;



    @FXML
    private VBox iconsVbox;
    @FXML
    private MFXTextField searchBar;

    private Stage stage;

    private ICustomerModel customerModel;

    @FXML
    private MFXTableView<Customer> customersTable;


    private void initCustomers() throws SQLException {
        ConcurrentMap<Integer, Customer> map = customerModel.getAllCustomers();
        Set<Integer> keys = map.keySet();
        try {
            for (Integer key : keys) {
                HBoxController hboxController = (HBoxController) ControllerFactory.loadFxmlFile(ViewType.HBOX_CARD);
                HBox hBox = (HBox) hboxController.getView();
                hboxController.setCustomerBoxes(map);
                addLabelAndScrollPane(key.toString(), hBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addLabelAndScrollPane(String key, HBox hBox) {
        Label label = new Label(key);
        customers.getChildren().add(label);
        customersScrollPane.setContent(hBox);
        customersScrollPane.setFitToHeight(true);
    }



    @FXML
    private void createCustomer(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.CREATE_CUSTOMERS);
            Scene scene = new Scene(controller.getView());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            customerModel = CustomerModel.getInstance();
            initCustomers();
            setUpCustomerBoard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private void setUpCustomerBoard() {
        MFXTableColumn<Customer> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(Customer::getCustomerID));
        MFXTableColumn<Customer> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(Customer::getCustomerName));
        MFXTableColumn<Customer> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(Customer::getCustomerEmail));
        MFXTableColumn<Customer> addressColumn = new MFXTableColumn<>("Address", true, Comparator.comparing(Customer::getCustomerAddress));
        MFXTableColumn<Customer> zipCodeColumn = new MFXTableColumn<>("Zip Code", true, Comparator.comparing(Customer::getZipCode));

        idColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerID));
        nameColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerName));
        emailColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerEmail));
        addressColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerAddress));
        zipCodeColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getZipCode));

        customersTable.getTableColumns().setAll(idColumn, nameColumn, emailColumn, addressColumn, zipCodeColumn);
        customersTable.autosizeColumnsOnInitialization();
        try {
            customersTable.setItems(customerModel.getObsAllCustomers());
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("Data retrieval failed", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        }
    }


}
