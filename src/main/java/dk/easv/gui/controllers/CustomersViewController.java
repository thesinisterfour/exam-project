package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomersViewController extends RootController {
    private ICustomerModel customerModel;

    @FXML
    private MFXTableView<Customer> customersTable;
    @FXML
    private VBox rootVBox;
    @FXML
    private HBox crudHBox;


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

    @FXML
    private void editCustomer(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.CREATE_CUSTOMERS);
            AddCustomerViewController addCustomerViewController = (AddCustomerViewController) controller;
            addCustomerViewController.setCustomerData(customersTable.getSelectionModel().getSelectedValues().get(0));
            Scene scene = new Scene(controller.getView());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            AlertHelper alertHelper = new AlertHelper("Please select a Customer", Alert.AlertType.WARNING);
            alertHelper.showAndWait();
        }
    }

    @FXML
    private void deleteCustomer(ActionEvent actionEvent) {
        try {
            customerModel.deleteCustomer(customersTable.getSelectionModel().getSelectedValues().get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            AlertHelper alertHelper = new AlertHelper("Please select a Customer", Alert.AlertType.WARNING);
            alertHelper.showAndWait();
        }
    }

    @FXML
    private void viewProjectsAction(ActionEvent actionEvent) {
        try {
            if (customersTable.getSelectionModel().getSelectedValues().isEmpty()) {
                AlertHelper alertHelper = new AlertHelper("Please select a Customer", Alert.AlertType.WARNING);
                alertHelper.showAndWait();
                return;
            }

            customerModel.setSelectedCustomerId(customersTable.getSelectionModel().getSelectedValues().get(0).getCustomerID());
            ProjectModel.getInstance().getAllProjects();
            BorderPane mainBorderPane = (BorderPane) rootVBox.getParent();
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.PROJECTS_VIEW);
            mainBorderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserSingleClass actualUser = UserSingleClass.getInstance();
        if (actualUser.getRole() == Role.SALESPERSON){
            crudHBox.setVisible(false);
        }
        try {
            customerModel = CustomerModel.getInstance();
            TableSetters.setUpCustomerTable(customersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
