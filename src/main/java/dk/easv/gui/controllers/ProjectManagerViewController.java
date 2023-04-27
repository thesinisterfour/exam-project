package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectManagerViewController extends RootController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void displayFxml(Stage stage) throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.PROJECT_MANAGER);
        Scene scene = new Scene(controller.getView(), 760, 480);
        stage.setTitle("Project Manager");
        stage.setScene(scene);
        stage.show();
    }
}
