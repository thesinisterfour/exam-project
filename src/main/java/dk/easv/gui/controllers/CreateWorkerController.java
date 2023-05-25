package dk.easv.gui.controllers;

import dk.easv.be.Role;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.RoleModel;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.models.interfaces.IRoleModel;
import dk.easv.gui.models.interfaces.IUserModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentMap;

public class CreateWorkerController extends RootController {
    private final IRoleModel roleModel = new RoleModel();
    private final IUserModel userModel = UserModel.getInstance();
    @FXML
    private MFXTextField nameTextField,
            lastNameTextField,
            usernameTextField,
            passwordTextField;
    @FXML
    private MFXComboBox<String> roleComboBox;
    private boolean editMode = false;
    private User selectedUser;
    @FXML
    private GridPane rootGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ConcurrentMap<Integer, Role> rolesMap = roleModel.getAllRoles();
            for (Role role : rolesMap.values()) {
                if (!role.toString().equals("ADMIN")) {
                    roleComboBox.getItems().add(role.toString());
                }
            }
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("There was an error retrieving roles from database", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        }
    }

    public void setUserData(User user) {
        this.selectedUser = user;
        if (selectedUser != null) {
            nameTextField.setText(user.getFirstName());
            lastNameTextField.setText(user.getLastName());
            usernameTextField.setText(user.getUsername());
            passwordTextField.setText(user.getPassword());
            roleComboBox.setValue(user.getRole().toString());
        }
        editMode = true;
    }

    @FXML
    private void submitButtonAction(ActionEvent actionEvent) {
        if (editMode) {
            preformEdit();
        } else {
            preformCreate();
        }
    }

    private void goBack() {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.WORKERS);
            BorderPane borderPane = (BorderPane) rootGrid.getParent();
            borderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void preformEdit() {
        String firstName = nameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String selectedRole = roleComboBox.getValue();
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || selectedRole.isEmpty()) {
            AlertHelper alertHelper = new AlertHelper("Please fill all fields and make sure to choose the user role!", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
            return;
        }
        selectedUser.setFirstName(firstName);
        selectedUser.setLastName(lastName);
        selectedUser.setUsername(username);
        selectedUser.setPassword(password);
        selectedUser.setRole(Role.valueOf(selectedRole));
        try {
            userModel.updateUser(selectedUser);
            goBack();
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("Error editing user, please try again", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        }
    }

    private void preformCreate() {
        String firstName = nameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String selectedRole = roleComboBox.getValue();
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || selectedRole.isEmpty()) {
            AlertHelper alertHelper = new AlertHelper("Pleas fill all fields and make sure to choose the user role!!", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
            return;
        }
        User user = new User(firstName, lastName, Role.valueOf(selectedRole));
        user.setUsername(username);
        user.setPassword(password);
        try {
            int userId = userModel.addUser(user);
            user.setUserID(userId);
            goBack();
        } catch (SQLException e) {
            AlertHelper alertHelper = new AlertHelper("Error creating user, pleas try again", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        }
    }

    public void removeCustomerData(ActionEvent actionEvent) {
        nameTextField.setText("");
        lastNameTextField.setText("");
        usernameTextField.setText("");
        passwordTextField.setText("");
    }

    @FXML
    private void cancelButtonAction(ActionEvent actionEvent) {
        goBack();
    }
}
