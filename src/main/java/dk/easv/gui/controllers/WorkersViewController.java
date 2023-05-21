package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.models.interfaces.IUserModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    private MFXTextField searchBar;
    @FXML
    private MFXScrollPane workerScrollPane;
    @FXML
    private HBox workers;
    private final IUserModel userModel = UserModel.getInstance();
    private User selectedUser = null;
    private HBoxController hboxController;
    @FXML
    private MFXTableView<User> workersTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
//            initUsers();
            TableSetters.setupUsersTable(workersTable);
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
    private void handleEdit(){
        try {
            User selectedUser = hboxController.getSelectedUser();
            if (selectedUser == null) {
                AlertHelper alertHelper = new AlertHelper("Pleas select a user to edit", Alert.AlertType.ERROR);
                alertHelper.showAndWait();
            } else {
                Stage stage = new Stage();
                CreateWorkerController controller = (CreateWorkerController) ControllerFactory.loadFxmlFile(ViewType.CREATE_WORKER);
                controller.setUserData(selectedUser);
                Scene scene = new Scene(controller.getView());
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
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
                AlertHelper alertHelper = new AlertHelper("Pleas select a user to delete", Alert.AlertType.ERROR);
                alertHelper.showAndWait();
            }else {
                userModel.deleteUser(selectedUser.getUserID());
            }
        } catch (SQLException e) {
                AlertHelper alertHelper = new AlertHelper("User deleted successfully", Alert.AlertType.NONE);
                alertHelper.showAndWait();
        }
    }

    public void newWorker(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.CREATE_WORKER);
            Scene scene = new Scene(controller.getView());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
