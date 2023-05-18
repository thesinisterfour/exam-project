package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Project;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.models.interfaces.IUserModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class WorkersViewController extends RootController {

    private UserSingleClass actualUser = UserSingleClass.getInstance();
    @FXML
    private VBox iconsVbox;
    @FXML
    private HBox mainHbox;
    @FXML
    private MFXTableView<Project> projectTable;
    @FXML
    private MFXTextField searchBar;
    @FXML
    private MFXScrollPane workerScrollPane;
    @FXML
    private HBox workers;
    private final IUserModel userModel = new UserModel();
    private User selectedUser = null;
    private HBoxController hboxController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ConcurrentMap<Integer, User> getAllUsersMap() throws SQLException {
        return userModel.getAllUsers();
    }

    private void addLabelAndScrollPane(String key, HBox hBox) {
        Label label = new Label(key);
        workers.getChildren().add(label);
        workerScrollPane.setContent(hBox);
        workerScrollPane.setFitToHeight(true);
    }

    private void initUsers() throws SQLException {
        ConcurrentMap<Integer, User> map = getAllUsersMap();
        Set<Integer> keys = map.keySet();
        try {
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/HboxCard.fxml")));
                HBox hBox = loader.load();
                hboxController = loader.getController();
                hboxController.setUserBoxes(map);
                addLabelAndScrollPane(key.toString(), hBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void handleCreate(){
        //TODO add button to FXML
        try { //Change View type to Create_worker when you make the FXML for it.
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.USERS_VIEW);
            this.getStage().setScene(new Scene(rootController.getView(), 560, 440));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void handleEdit(){ //TODO
        try { //Change View type to Create_worker when you make the FXML for it
            User selectedUser = hboxController.getSelectedUser();
            if (selectedUser == null) {
                AlertHelper.showDefaultAlert("Pleas select a user to edit", Alert.AlertType.ERROR);
            } else {
                CreateWorkerController createWorkerController = (CreateWorkerController) ControllerFactory.loadFxmlFile(ViewType.USERS_VIEW);
                createWorkerController.setUserData(selectedUser);
                this.getStage().setScene(new Scene(createWorkerController.getView(), 560, 440));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void handleDelete(){
        try {
            selectedUser = hboxController.getSelectedUser();
            if (selectedUser == null) {
                AlertHelper.showDefaultAlert("Pleas select a user to delete", Alert.AlertType.ERROR);
            }else {
                userModel.deleteUser(selectedUser.getUserID());
            }
        } catch (SQLException e) {
            AlertHelper.showDefaultAlert("User deleted successfully", Alert.AlertType.NONE);
        }
    }
    @FXML
    private void handleBack() {
        //TODO add button to FXML
        try { //Take User back to the main view.
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.USERS_VIEW);
            this.getStage().setScene(new Scene(rootController.getView(), 560, 440));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
