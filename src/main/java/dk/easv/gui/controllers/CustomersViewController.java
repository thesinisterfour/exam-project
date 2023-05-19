package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class CustomersViewController extends RootController{

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
            TableSetters.setUpCustomerTable(customersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
