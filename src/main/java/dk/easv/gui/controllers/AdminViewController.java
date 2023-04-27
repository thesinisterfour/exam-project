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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController extends RootController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView backgroundImage;

    @FXML
    private VBox buttonVBox;

    @FXML
    private MFXButton createButton;

    @FXML
    private MFXButton customerButton;

    @FXML
    private MFXButton deleteButton;

    @FXML
    private TableColumn<?, ?> documentID;

    @FXML
    private TableColumn<?, ?> documentName;

    @FXML
    private TableView<?> documentView;

    @FXML
    private MFXButton editButton;

    @FXML
    private Label logoLabel;

    @FXML
    private MFXButton logoutButton;

    @FXML
    private MFXButton statusButton;

    @FXML
    private HBox tableHBox;

    @FXML
    private MFXButton userButton;

    @FXML
    private ImageView userImage;

    @FXML
    private Text userName;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Pane welcomePane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void selectStatusAction(ActionEvent actionEvent) {
        System.out.println("Busy");
    }

    @FXML
    private void logOutAction(ActionEvent actionEvent) {
        System.out.println("You're out");
    }
}

