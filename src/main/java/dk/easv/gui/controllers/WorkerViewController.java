package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.User;
import dk.easv.bll.CRUDLogic;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WorkerViewController extends RootController{

    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    @FXML
    private MFXButton newUser, edit, delete;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonsActions();
    }

    /***
     * Test controller initial commit
     */
    public void buttonsActions (){
        newUser.setOnAction(e -> {
            System.out.println("New User");
        });
        edit.setOnAction(e -> System.out.println("edit"));
        delete.setOnAction(e -> System.out.println("delete"));
    }


    @FXML
    private void goBack(ActionEvent actionEvent) throws RuntimeException, IOException {
        RootController controller;
        Stage stage = new Stage();
        controller = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
        Scene scene = new Scene(controller.getView());
        stage.setTitle("Admin");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        getStage().close();
    }
}
