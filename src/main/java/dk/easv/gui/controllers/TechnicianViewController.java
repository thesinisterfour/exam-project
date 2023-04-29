package dk.easv.gui.controllers;

import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TechnicianViewController extends RootController implements Initializable {

    @FXML
    private TableColumn<?, ?> columnDocID;

    @FXML
    private TableColumn<?, ?> columnDocument;

    @FXML
    private Label companyLogo;

    @FXML
    private MFXButton createMFXButton;

    @FXML
    private TableView<?> docTable;

    @FXML
    private GridPane gridBottom;

    @FXML
    private GridPane gridMain;

    @FXML
    private GridPane gridTop;

    @FXML
    private GridPane gridWelcome;

    @FXML
    private Label labelUserName;

    @FXML
    private Label labelWelcome;

    @FXML
    private MFXButton logoutMFXButton;

    @FXML
    private MFXButton statusMFXButton;

    @FXML
    private ImageView userImg;

    @FXML
    private Label userRole;

    @FXML
    private HBox welcomeHBox;

    @FXML
    private VBox welcomeVBox;

    @FXML
    void StatusAction(ActionEvent event) {

    }

    @FXML
    void createAction(ActionEvent event) {

    }

    @FXML
    void logoutAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void logoutUserAction(){
        System.out.println("I logged out");
    }

    @FXML
    private void statusAction(){
        System.out.println("I am lazy");
    }

    @FXML
    private void createDocAction(){
        System.out.println("I created the universe");
    }


}
