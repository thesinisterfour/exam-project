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
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentMap;

public class CreateWorkerController extends RootController {
    private final IRoleModel roleModel = new RoleModel();
    private final IUserModel userModel = new UserModel();

    //TODO  Add id for TextFields in FXML
    @FXML
    private MFXTextField firstNameTXF;
    @FXML
    private MFXTextField lastNameTXF;
    @FXML
    private MFXComboBox<String> roleComboBox;
    @FXML
    private MFXTextField userNameTXF;
    @FXML
    private MFXTextField passwordTXF;
    @FXML
    private ImageView image;
    private boolean editMode = false;
    private User selectedUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ConcurrentMap<Integer, Role> rolesMap = roleModel.getAllRoles();
            for(Role role : rolesMap.values()){
                roleComboBox.getItems().add(role.toString());
            }
            roleComboBox.getSelectionModel().selectFirst();
        } catch (SQLException e){
            AlertHelper.showDefaultAlert("There was an error retrieving roles from database", Alert.AlertType.ERROR);
        }
    }
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    public void setUserData(User user) {
        this.selectedUser = user;
        if (selectedUser != null){
            firstNameTXF.setText(user.getFirstName());
            lastNameTXF.setText(user.getLastName());
            userNameTXF.setText(user.getUsername());
            passwordTXF.setText(user.getPassword());
            roleComboBox.setValue(user.getRole().toString());
        }
        editMode = true;
    }

    @FXML
    private void handleCreate(){
        if (editMode) {
            preformEdit();
        } else {
            preformCreate();
        }
    }
    private void preformEdit(){
        String firstName = firstNameTXF.getText();
        String lastName = lastNameTXF.getText();
        String username = userNameTXF.getText();
        String password = passwordTXF.getText();
        String selectedRoleName = roleComboBox.getValue();
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || selectedRoleName == null){
            AlertHelper.showDefaultAlert("Please fill all fields and make sure to choose the user role!", Alert.AlertType.ERROR);
            return;
        }

        Role selectedRole = Role.valueOf(selectedRoleName.toUpperCase());
        User user = new User(firstName, lastName, selectedRole);
        user.setUsername(username);
        user.setPassword(password);
        try {
            userModel.updateUser(selectedUser);
            AlertHelper.showDefaultAlert("User successfully edited", Alert.AlertType.INFORMATION);
        } catch (SQLException e){
            AlertHelper.showDefaultAlert("Error editing user, please try again", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }
    private void preformCreate(){
        String firstName = firstNameTXF.getText();
        String lastName = lastNameTXF.getText();
        String username = userNameTXF.getText();
        String password = passwordTXF.getText();
        String selectedRoleName = roleComboBox.getValue();
        if(firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || selectedRoleName == null){
            AlertHelper.showDefaultAlert("Pleas fill all fields and make sure to choose the user role!!", Alert.AlertType.ERROR);
            return;
        }
        Role selectedRole = Role.valueOf(selectedRoleName.toUpperCase());
        User user = new User(firstName, lastName, selectedRole);
        user.setUsername(username);
        user.setPassword(password);
        try {
            int userId = userModel.addUser(user);
            user.setUserID(userId);
            AlertHelper.showDefaultAlert("User successfully created", Alert.AlertType.INFORMATION);
        } catch (SQLException e){
            AlertHelper.showDefaultAlert("Error creating user, pleas try again", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleBack(){
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.USERS_VIEW);
            this.getStage().setScene(new Scene(rootController.getView(), 760, 480));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
