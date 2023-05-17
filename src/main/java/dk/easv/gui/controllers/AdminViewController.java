package dk.easv.gui.controllers;

import dk.easv.be.Doc;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.ContentModel;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.models.interfaces.IContentModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AdminViewController extends RootController {


    private IDocumentModel documentModel;

    @FXML
    private MFXTextField userName;
    @FXML
    private MFXComboBox comboBox;
    @FXML
    private MFXPaginatedTableView<Doc> tableView;
    @FXML
    private MFXButton logoutButton;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            documentModel = new DocumentModel();
            List<Doc> oldDocuments = documentModel.getOldDocuments();
            if (!oldDocuments.isEmpty() && !AlertHelper.isAlertShown()) {
                AlertHelper.setDocumentModel(documentModel);
                AlertHelper.showDefaultAlert(DocumentHelper.convertToString(oldDocuments), Alert.AlertType.INFORMATION);
            }
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
    private void handleDelete() throws SQLException {
        try {
            Doc selectedDocument = tableView.getSelectionModel().getSelectedValues().get(0);
            documentModel.deleteDocument(selectedDocument.getId());
            documentModel.setObsAllDocuments();
        } catch (IndexOutOfBoundsException e) {
            AlertHelper.showDefaultAlert("Pleas select a document to delete", Alert.AlertType.ERROR);
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
        IContentModel contentModel = ContentModel.getInstance();
        Doc document = tableView.getSelectionModel().getSelectedValues().get(0);
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

    private void setUpPaginated() {
        MFXTableColumn<Doc> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(Doc::getId));
        MFXTableColumn<Doc> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(Doc::getName));
        MFXTableColumn<Doc> dateCreatedColumn = new MFXTableColumn<>("Date Created", true, Comparator.comparing(Doc::getCreationDate));
        MFXTableColumn<Doc> dateLastOpenedColumn = new MFXTableColumn<>("Date Last Opened", true, Comparator.comparing(Doc::getLastView));
        MFXTableColumn<Doc> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(Doc::getDescription));

        idColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getId));
        nameColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getName));
        dateCreatedColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getCreationDate));
        dateLastOpenedColumn.setRowCellFactory(document -> {
            LocalDate date = document.getLastView();
            if (date == null){
                return new MFXTableRowCell<>(doc -> "Never");
            }
            return new MFXTableRowCell<>(Doc::getLastView);
        });
        descriptionColumn.setRowCellFactory(document ->{
            String description = document.getDescription();
            if (description == null){
                return new MFXTableRowCell<>(doc -> "No description");
            }
            return new MFXTableRowCell<>(Doc::getDescription);
        });

        tableView.getTableColumns().setAll(idColumn, nameColumn, dateCreatedColumn, dateLastOpenedColumn, descriptionColumn);
        tableView.autosizeColumnsOnInitialization();

        try {
            tableView.setItems(documentModel.getObsAllDocuments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
