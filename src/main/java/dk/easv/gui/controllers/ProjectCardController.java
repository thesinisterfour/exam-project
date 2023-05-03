package dk.easv.gui.controllers;

import dk.easv.gui.rootContoller.RootController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectCardController extends RootController {

    @FXML
    private Label addressAndCity;

    @FXML
    private GridPane gridPaneProject;

    @FXML
    private GridPane lowerGrid;

    @FXML
    private ImageView presentationImage;

    @FXML
    private Label projectNameLabel;

    @FXML
    private Label projectStatusLabel;

    @FXML
    private GridPane topGrid;

    @FXML
    private GridPane topRightGrid;

    @FXML
    private Label zipcodeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}


