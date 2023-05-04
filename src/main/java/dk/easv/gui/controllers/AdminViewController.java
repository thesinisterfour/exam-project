package dk.easv.gui.controllers;

import dk.easv.be.Document;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.ContentModel;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
public class AdminViewController extends RootController {

    private DocumentModel documentModel;

    @FXML
    private MFXTextField userName;
    @FXML
    private MFXComboBox comboBox;
    @FXML
    private MFXPaginatedTableView<Document> tableView;
    @FXML
    private MFXButton logoutButton;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            documentModel = new DocumentModel();
            List<Document> oldDocuments = documentModel.getOldDocuments();
            AlertHelper.showDefaultAlert(DocumentHelper.convertToString(oldDocuments),Alert.AlertType.CONFIRMATION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setUpPaginated();
    }
    @FXML
    public void handleLogout() throws IOException {
        if (stage == null) {
            this.stage = this.getStage();
        }

        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView(), 760, 480));
        this.stage.setTitle("Login");
    }
    @FXML
    private void handleDelete(){
        Document selectedDocument =  tableView.getSelectionModel().getSelectedValues().get(0);
        if (selectedDocument != null) {
            try {
                documentModel.deleteDocument(selectedDocument.getId());
                tableView.getItems().remove(selectedDocument);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    private void handleCreateDocument() {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.CREATE_DOCUMENT);
            this.getStage().setScene(new Scene(rootController.getView(), 760, 480));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void handleEditDocument() {
        ContentModel contentModel = ContentModel.getInstance();
        Document document = tableView.getSelectionModel().getSelectedValues().get(0);
        contentModel.setDocumentId(document.getId());

        try {
            this.stage = this.getStage();
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.DOCUMENT);
            this.getStage().setScene(new Scene(rootController.getView(), stage.getWidth(), stage.getWidth()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void handleUsers() throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.USERS_VIEW);
        Scene scene = new Scene(controller.getView());
        this.stage = this.getStage();
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    private void handleCustomers() throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.CUSTOMERS);
        Scene scene = new Scene(controller.getView());
        this.stage = this.getStage();
        stage.setScene(scene);
        stage.setTitle("Admin");

    }

    private void setUpPaginated(){
        MFXTableColumn<Document> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(Document::getId));
        MFXTableColumn<Document> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(Document::getName));
        MFXTableColumn<Document> dateCreatedColumn = new MFXTableColumn<>("Date Created", true, Comparator.comparing(Document::getCreationDate));
        MFXTableColumn<Document> dateLastOpenedColumn = new MFXTableColumn<>("Date Last Opened", true, Comparator.comparing(Document::getLastView));
        MFXTableColumn<Document> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(Document::getDescription));

        idColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Document::getId));
        nameColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Document::getName));
        dateCreatedColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Document::getCreationDate));
        dateLastOpenedColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Document::getLastView));
        descriptionColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Document::getDescription));

        tableView.getTableColumns().setAll(idColumn, nameColumn, dateCreatedColumn, dateLastOpenedColumn, descriptionColumn);

        try {
            tableView.setItems(documentModel.getObsAllDocuments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
