package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Project;
import dk.easv.be.Role;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.models.interfaces.IUserModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private MFXButton HomeLayer, businessLayer, logoutButton, workersLayer;

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

    @FXML
    void displayBusiness(ActionEvent event) throws IOException {
        this.stage = this.getStage();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.BUSINESS_VIEW);
        Scene scene = new Scene(controller.getView());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        this.stage = this.getStage();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView()));
        this.stage.setTitle("WUAV!!!");
    }

    private void roleView(){
            if(actualUser.getRole() == Role.TECHNICIAN){
                iconsVbox.getChildren().remove(businessLayer);
                iconsVbox.getChildren().remove(workersLayer);
            }
            if(actualUser.getRole() == Role.SALESPERSON){
                iconsVbox.getChildren().remove(workersLayer);

            }
            if(actualUser.getRole() == Role.PROJECTMANAGER){
                iconsVbox.getChildren().remove(businessLayer);

            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            roleView();
            workersLayer.setDisable(true);
            initUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void displayHome(ActionEvent actionEvent) throws IOException {
            this.stage = this.getStage();
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.MAIN);
            Scene scene = new Scene(controller.getView());
            stage.setScene(scene);
            stage.show();
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
