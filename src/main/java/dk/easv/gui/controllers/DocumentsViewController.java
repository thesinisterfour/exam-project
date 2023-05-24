package dk.easv.gui.controllers;

import dk.easv.be.Doc;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.helpers.TableSetters;
import dk.easv.gui.models.ContentModel;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.models.interfaces.IContentModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DocumentsViewController extends RootController {
    private IDocumentModel model;
    @FXML
    private MFXTableView<Doc> documentsTable;
    @FXML
    private VBox rootVbox;
    @FXML
    private MFXButton addDocumentButton;
    @FXML
    private MFXButton editDocumentButton;
    @FXML
    private MFXButton deleteDocumentButton;

    @FXML
    private void newDocument(ActionEvent actionEvent) {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.CREATE_DOCUMENT);
            BorderPane borderPane = (BorderPane) rootVbox.getParent();
            borderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEdit(ActionEvent actionEvent) {
        try {
            IContentModel contentModel = ContentModel.getInstance();
            Doc document = documentsTable.getSelectionModel().getSelectedValues().get(0);
            contentModel.setDocumentId(document.getId());
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.DOCUMENT);
            BorderPane borderPane = (BorderPane) rootVbox.getParent();
            borderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("A file error occurred", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        } catch (IndexOutOfBoundsException e) {
            AlertHelper alertHelper = new AlertHelper("Please select a document to edit", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        }
    }

    @FXML
    private void handleDelete(ActionEvent actionEvent) {
        try {
            Doc selectedDocument = documentsTable.getSelectionModel().getSelectedValues().get(0);
            model.deleteDocument(selectedDocument.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserSingleClass actualUser = UserSingleClass.getInstance();
        if (actualUser.getRole() == Role.SALESPERSON){
            addDocumentButton.setDisable(true);
            editDocumentButton.setText("View");
            deleteDocumentButton.setDisable(true);
        }
        try {
            model = DocumentModel.getInstance();
            model.setObsAllDocuments();
            TableSetters.setUpDocumentTable(documentsTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
