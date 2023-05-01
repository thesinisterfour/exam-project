package dk.easv.helpers;

import javafx.scene.control.Alert;
public class AlertHelper {
    public static Alert alert;
    public static void showDefaultAlert(String content, Alert.AlertType type){
        alert = new Alert(type);
        alert.setAlertType(type);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setResizable(false);
        alert.show();
    }
}
