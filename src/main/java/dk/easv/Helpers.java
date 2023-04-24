package dk.easv;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Helpers {
    // checks if config exists
    public static boolean checkConfig() {
    Path path = Path.of("src/main/resources/dk/easv/config.cfg");
        if (!Files.exists(path)) {
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
            }
            return false;
        }
        return true;
    }
}
