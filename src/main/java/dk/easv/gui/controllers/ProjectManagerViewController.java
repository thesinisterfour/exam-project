package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Doc;
import dk.easv.be.User;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.ProjectModel;
import dk.easv.be.Project;

import dk.easv.gui.models.DocumentModel;

import dk.easv.gui.models.UserModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class ProjectManagerViewController extends RootController {
    private DocumentModel documentModel;
    @FXML
    private VBox boxVert;

    @FXML
    private MFXTableView<Doc> tableViewDoc;

    @FXML
    private GridPane gridPaneMain;

    @FXML
    private HBox horiBox;

    @FXML
    private MFXButton logoutButton;

    @FXML
    private HBox navBarHBox, workers, projects;

    @FXML
    private VBox navBarVBox;

    @FXML
    private MFXScrollPane projectScrollPane;

    @FXML
    private MFXScrollPane workerScrollPane;

    @FXML
    private Label usernameLabel;

    @FXML
    private VBox vertBox;

    private Stage stage;

    private UserModel userModel = new UserModel();

    private ProjectModel projectModel = new ProjectModel();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            documentModel = new DocumentModel();
            List<Doc> oldDocuments = documentModel.getOldDocuments();
            AlertHelper.showDefaultAlert(DocumentHelper.convertToString(oldDocuments),Alert.AlertType.INFORMATION);
            initUsers();
            initProjects();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            documentModel = new DocumentModel();
            List<Doc> oldDocuments = documentModel.getOldDocuments();
            AlertHelper.showDefaultAlert(DocumentHelper.convertToString(oldDocuments),Alert.AlertType.CONFIRMATION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setUpPaginated();
    }


    private void addLabelAndScrollPane(String key, HBox hBox) {
        Label label = new Label(key);
        workers.getChildren().add(label);
        workerScrollPane.setContent(hBox);
        workerScrollPane.setFitToHeight(true);
    }

    private void addProjectsAndScrollPane(String key, HBox hBox){
        Label label = new Label(key);
        projects.getChildren().add(label);
        projectScrollPane.setContent(hBox);
        projectScrollPane.setFitToHeight(true);
    }

    private void initUsers() throws SQLException {
        ConcurrentMap<Integer, User> map = getAllUsersMap();
        Set<Integer> keys = map.keySet();
        try {
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/HboxCard.fxml")));
                HBox hBox = loader.load();
                HBoxController hboxController = loader.getController();
                hboxController.setUserBoxes(map);
                addLabelAndScrollPane(key.toString(), hBox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initProjects() throws SQLException {
        ConcurrentMap<Integer, Project> map = getAllProjectsMap();
        Set<Integer> keys = map.keySet();
        try {
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/HboxCard.fxml")));
                HBox hBox = loader.load();
                HBoxController hBoxController = loader.getController();
                hBoxController.setProjectBoxes(map);
                addProjectsAndScrollPane(key.toString(),hBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private ConcurrentMap<Integer, Project> getAllProjectsMap() throws SQLException{
        return projectModel.getCustomerPoject();
    }

    private ConcurrentMap<Integer, User> getAllUsersMap() throws SQLException {

        return userModel.getAllUsers();
    }
    @FXML
    public void logoutAction() throws IOException {
        if (stage == null) {
            this.stage = this.getStage();
        }

        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView(), 760, 480));
        this.stage.setTitle("Login");
    }
    @FXML
    public void handleDelete() throws SQLException{
        if (tableViewDoc == null){ // this condition alert can be removed after adding Documents to the tableview
            AlertHelper.showDefaultAlert("There is no documents to delete", Alert.AlertType.ERROR);
        } else {
            try {
                Doc selectedDocument = tableViewDoc.getSelectionModel().getSelectedValues().get(0);
                documentModel.deleteDocument(selectedDocument.getId());
                documentModel.setObsAllDocuments();
            } catch (IndexOutOfBoundsException e) {
                AlertHelper.showDefaultAlert("Pleas select a document to delete", Alert.AlertType.ERROR);
            }
        }
    }

    private void setUpPaginated(){
        MFXTableColumn<Doc> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(Doc::getId));
        MFXTableColumn<Doc> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(Doc::getName));
        MFXTableColumn<Doc> dateCreatedColumn = new MFXTableColumn<>("Date Created", true, Comparator.comparing(Doc::getCreationDate));
        MFXTableColumn<Doc> dateLastOpenedColumn = new MFXTableColumn<>("Date Last Opened", true, Comparator.comparing(Doc::getLastView));
        MFXTableColumn<Doc> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(Doc::getDescription));

        idColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getId));
        nameColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getName));
        dateCreatedColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getCreationDate));
        dateLastOpenedColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getLastView));
        descriptionColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getDescription));

        tableViewDoc.getTableColumns().setAll(idColumn, nameColumn, dateCreatedColumn, dateLastOpenedColumn, descriptionColumn);

        try {
            tableViewDoc.setItems(documentModel.getObsAllDocuments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
