package dk.easv.gui.controllers;

import dk.easv.be.Project;
import dk.easv.gui.rootContoller.RootController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

    private ConcurrentMap<Integer, Project> projects = new ConcurrentHashMap<>();

    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void receiveProjectData(ConcurrentMap<Integer, Project> projects) {
        this.projects = projects;
    }

    public void createProCards(Project project) {
        this.project = project;
        if (project.getProjectName() != null) {
            projectNameLabel.setText(project.getProjectName());
            addressAndCity.setText(project.getProjectAddress());
            zipcodeLabel.setText(String.valueOf(project.getProjectZipcode()));
            gridPaneProject.setStyle(gridPaneProject.getStyle());

        }


    }

}


