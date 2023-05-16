package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.interfaces.IUserModel;
import dk.easv.gui.models.UserModel;
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

public class WorkerViewController extends RootController{

    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    private IUserModel userModel = new UserModel();

    @FXML
    private HBox workers;

    @FXML
    private MFXScrollPane workerScrollPane;

    @FXML
    private MFXButton edit, delete;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonsActions();
        try {
            initUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Test controller initial commit
     */
    public void buttonsActions (){
        edit.setOnAction(e -> System.out.println("edit"));
        delete.setOnAction(e -> System.out.println("delete"));
    }

    private ConcurrentMap<Integer, User> getAllUsersMap() throws SQLException {

        return userModel.getAllUsers();
    }


    private void initUsers() throws SQLException {
        ConcurrentMap<Integer, User> map = getAllUsersMap();
        Set<Integer> keys = map.keySet();
        try {
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/HboxCard.fxml")));
                HBox hBox = loader.load();
                HBoxController hboxController = loader.getController();
                hboxController.setUserBoxes(map);
                addLabelAndScrollPane(key.toString(), hBox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void addLabelAndScrollPane(String key, HBox hBox) {
        Label label = new Label(key);
        workers.getChildren().add(label);
        workerScrollPane.setContent(hBox);
        workerScrollPane.setFitToHeight(true);
    }


    @FXML
    private void goBack(ActionEvent actionEvent) throws RuntimeException, IOException {
        RootController controller;
        Stage stage = new Stage();
        controller = ControllerFactory.loadFxmlFile(ViewType.MAIN);
        Scene scene = new Scene(controller.getView());
        stage.setTitle("Admin");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        getStage().close();
    }
}
