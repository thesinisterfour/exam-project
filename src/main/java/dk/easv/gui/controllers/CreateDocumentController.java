package dk.easv.gui.controllers;

import dk.easv.be.Doc;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateDocumentController extends RootController {
    private IDocumentModel model = new DocumentModel();
    @FXML
    private MFXTextField nameTextField;
    @FXML
    private MFXTextField descriptionTextField;

    public CreateDocumentController() throws SQLException {
    }

    @FXML
    private void createOnAction(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        if (name.isEmpty()) {
            nameTextField.setPromptText("Required");
            return;
        }
        String description = descriptionTextField.getText();
        try {
            if (description.isEmpty()) {
                model.addDocument(new Doc(name));
            } else {
                model.addDocument(new Doc(name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.MAIN);
            this.getStage().setScene(new Scene(rootController.getView()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
