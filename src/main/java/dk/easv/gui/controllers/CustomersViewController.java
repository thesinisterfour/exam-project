package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.AlertHelper;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.CurrentUser;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.ADD_CUSTOMER);
            BorderPane borderPane = (BorderPane) rootVBox.getParent();
            borderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        }
    }

    @FXML
    private void editCustomer(ActionEvent actionEvent) {
        try {
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.ADD_CUSTOMER);
            AddCustomerViewController addCustomerViewController = (AddCustomerViewController) controller;
            addCustomerViewController.setCustomerData(customersTable.getSelectionModel().getSelectedValues().get(0));
            BorderPane borderPane = (BorderPane) rootVBox.getParent();
            borderPane.setCenter(controller.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
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
            AlertHelper alertHelper = new AlertHelper("An error occurred while deleting the customer", e);
            alertHelper.showAndWait();
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
            ProjectModel.getInstance().loadAllProjects();
            BorderPane mainBorderPane = (BorderPane) rootVBox.getParent();
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.PROJECTS_VIEW);
            mainBorderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading a new view", e);
            alertHelper.showAndWait();
        } catch (NullPointerException ex) {
            AlertHelper alertHelper = new AlertHelper("The view you selected does not exist", ex);
            alertHelper.showAndWait();
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading the projects", e);
            alertHelper.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CurrentUser actualUser = CurrentUser.getInstance();
        if (actualUser.getRole() == Role.SALESPERSON) {
            crudHBox.setVisible(false);
        }
        try {
            customerModel = CustomerModel.getInstance();
            TableSetters.setUpCustomerTable(customersTable);
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while loading the customers", e);
            alertHelper.showAndWait();
        }
    }


}
