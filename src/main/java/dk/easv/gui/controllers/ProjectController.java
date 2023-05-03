package dk.easv.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProjectController {

    @FXML
    private MFXButton backButton;

    @FXML
    private Label customerAddress;

    @FXML
    private Label customerName;

    @FXML
    private HBox gridHBox;

    @FXML
    private VBox gridVBox;

    @FXML
    private GridPane imageGridPane;

    @FXML
    private GridPane infoGridPane;

    @FXML
    private ImageView logoImage;

    @FXML
    private GridPane projectGridPane;

    @FXML
    private Label projectName;

    @FXML
    private MFXButton viewDocuments;

    @FXML
    private void viewDocumentsAction(ActionEvent actionEvent) {

    }

    @FXML
    private void backToAction(ActionEvent actionEvent) {
    }
}
