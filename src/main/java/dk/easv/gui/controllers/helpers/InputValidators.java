package dk.easv.gui.controllers.helpers;

import dk.easv.gui.models.CityModel;
import dk.easv.gui.models.interfaces.ICityModel;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class InputValidators {
    public static int checkZipCode(String zip) {
        ICityModel cityModel = new CityModel();
        try {
            int zipCode = Integer.parseInt(zip);
            cityModel.get(zipCode);
            return zipCode;
        } catch (SQLException e) {
            // catch if city does not exist
            AlertHelper alertHelper = new AlertHelper("City does not exist", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
            return 0;
        } catch (Exception e) {
            // catch if zip is not a number
            AlertHelper alertHelper = new AlertHelper("Zip code must be a number", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
            return 0;
        }
    }

    public static boolean isEmptyField(ObservableList<Node> nodes) {
        boolean emptyField = false;
        for (Node node : nodes) {
            if (node instanceof MFXTextField textField) {
                textField.setText(textField.getText().strip());
                if (textField.getText().isEmpty()) {
                    textField.setPromptText("This field cannot be empty");
                    textField.getStyleClass().add("wrong-input");
                    emptyField = true;
                }
            } else if (node instanceof MFXDatePicker datePicker) {
                if (datePicker.getValue() == null) {
                    datePicker.setPromptText("This field cannot be empty");
                    datePicker.getStyleClass().add("wrong-input");
                    emptyField = true;
                }
            } else if (node instanceof MFXComboBox<?> comboBox) {
                if (comboBox.getSelectionModel().getSelectedItem() == null) {
                    comboBox.setPromptText("This field cannot be empty");
                    comboBox.getStyleClass().add("wrong-input");
                    emptyField = true;
                }
            }
        }
        if (emptyField) {
            AlertHelper alertHelper = new AlertHelper("One or more fields are empty", Alert.AlertType.WARNING);
            alertHelper.showAndWait();
        }
        return emptyField;
    }
}