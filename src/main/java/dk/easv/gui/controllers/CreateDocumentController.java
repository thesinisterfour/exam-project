package dk.easv.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CreateDocumentController {
    @FXML
    private MFXTextField txfDocumentName;
    @FXML
    private MFXTextField txfLocation;
    @FXML
    private MFXTextField txfDescription;
    @FXML
    private MFXComboBox comboBox;
    @FXML
    private MFXDatePicker dpCreateDate;


    @FXML
    private void handleSave() {

    }
    @FXML
    private void handleCancel() {

    }
}
