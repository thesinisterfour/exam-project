package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Customer;
import dk.easv.be.Document;
import dk.easv.be.Project;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class ProjectManagerViewController extends RootController {

    @FXML
    private VBox boxVert;

    @FXML
    private MFXTableView<Document> documentTable;

    @FXML
    private GridPane gridPaneMain;

    @FXML
    private HBox horiBox;

    @FXML
    private MFXButton logoutButton;

    @FXML
    private HBox navBarHBox, workers;

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

    private UserModel userModel = new UserModel();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initUsers();
            //initProjects();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                HBoxController hboxController = loader.getController();
                hboxController.setUserBoxes(map);
                addLabelAndScrollPane(key.toString(), hBox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*private void initProjects() throws SQLException {
        ConcurrentMap<Integer, Project> map = getAllProjectsMap();
        Set<Integer> keys = map.keySet();
        try {
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("view/HboxCard.fxml")))
                HBox hBox = loader.load();
                HBoxController hBoxController = loader
            }
        }
    }

     */

    private ConcurrentMap<Integer, User> getAllUsersMap() throws SQLException {

        return userModel.getAllUsers();
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
