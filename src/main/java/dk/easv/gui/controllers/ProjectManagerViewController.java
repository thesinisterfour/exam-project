package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentMap;

public class ProjectManagerViewController extends RootController {

    @FXML
    private VBox boxVert;

    @FXML
    private MFXTableView<?> documentTable;

    @FXML
    private GridPane gridPaneMain;

    @FXML
    private HBox horiBox;

    @FXML
    private MFXButton logoutButton;

    @FXML
    private HBox navBarHBox;

    @FXML
    private VBox navBarVBox;

    @FXML
    private MFXScrollPane projectScrollPane;

    @FXML
    private MFXScrollPane workerScrollPane;

    @FXML
    private Label usernameLabel;

    @FXML
    private VBox vertBox;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUsers();
    }

    private void initUsers(){
        ConcurrentMap<Integer, User> map = getAllUsersMap();
    }

    private ConcurrentMap<Integer, User> getAllUsersMap() {

        return null;
    }


    @FXML
    public void logoutAction() throws IOException {
        if (stage == null) {
            this.stage = this.getStage();
        }

        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView(), 760, 480));
        this.stage.setTitle("Login");
    }


}
