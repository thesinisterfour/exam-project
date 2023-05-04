package dk.easv.helpers;

import javafx.scene.control.Alert;
import java.sql.SQLException;
import java.util.List;

public class AlertHelper {
    public static Alert alert;
    public static void showDefaultAlert(String content, Alert.AlertType type, List<String> documentNames) throws SQLException {
            alert = new Alert(type);
            alert.setAlertType(type);
            alert.setHeaderText(null);
            alert.setContentText(content + documentNames);
            alert.setResizable(false);
            alert.show();
    }
}
