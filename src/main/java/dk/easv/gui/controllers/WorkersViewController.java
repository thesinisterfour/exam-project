package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Project;
import dk.easv.be.User;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.models.interfaces.IUserModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.UserSingleClass;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private VBox iconsVbox;
    @FXML
    private HBox mainHbox;

    @FXML
    private MFXTableView<Project> projectTable;

    @FXML
    private MFXTextField searchBar;

    @FXML
    private MFXScrollPane workerScrollPane;

    private Stage stage;

    @FXML
    private HBox workers;

    private IUserModel userModel = new UserModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initUsers();
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

}
