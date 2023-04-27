package dk.easv;

import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.Config;
import dk.easv.helpers.ViewType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        if (Files.exists(Config.CONFIG_PATH.getUrl())){
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.ADMINVIEW);
            Scene scene = new Scene(controller.getView(), 600, 417);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } else {
            showConfigNotFoundAlert();
        }
    }


    private static void showConfigNotFoundAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Config file not found");
        alert.setContentText("""
                Config file not found
                Please create a config file in the following path:
                src/main/resources/dk/easv/config.cfg
                """);
        Optional<ButtonType> resultButton = alert.showAndWait();
        if (resultButton.isPresent()) {
            if (resultButton.get() == ButtonType.OK) {
                System.exit(0);
            }
        } else {
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}