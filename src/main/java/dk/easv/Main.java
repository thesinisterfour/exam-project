package dk.easv;

import dk.easv.dal.connectionManager.ConnectionManagerFactory;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.Config;
import dk.easv.helpers.ViewType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

public class Main extends Application {
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

    @Override
    public void start(Stage stage) throws IOException {
        if (Files.exists(Config.CONFIG_PATH.getUrl())) {
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
            Scene scene = new Scene(controller.getView());
            stage.setTitle("WUAV!!!");
            stage.setScene(scene);
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("icons/WUAV-Logo.png")).toExternalForm()));
            stage.show();
        } else {
            showConfigNotFoundAlert();
        }
    }

    @Override
    public void stop() {
        ConnectionManagerFactory.stopAllConnectionManagers();
    }
}