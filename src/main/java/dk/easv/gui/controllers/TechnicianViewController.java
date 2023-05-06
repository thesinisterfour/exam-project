package dk.easv.gui.controllers;

import dk.easv.be.Doc;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class TechnicianViewController extends RootController implements Initializable {


    @FXML
    private MFXButton createMFXButton;

    @FXML
    private GridPane gridBottom;

    @FXML
    private GridPane gridMain;

    @FXML
    private GridPane gridTop;

    @FXML
    private GridPane gridWelcome;

    @FXML
    private Label labelUserName;

    @FXML
    private Label labelWelcome;

    @FXML
    private MFXButton logoutMFXButton;

    @FXML
    private MFXButton statusMFXButton;

    @FXML
    private ImageView userImg;

    @FXML
    private Label userRole;

    @FXML
    private HBox welcomeHBox;

    @FXML
    private VBox welcomeVBox;

    @FXML
    private MFXPaginatedTableView documentTableView;

    private Stage stage;

    private DocumentModel documentModel;

    @FXML
    void StatusAction(ActionEvent event) {

    }

    @FXML
    void createDocumentAction() {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.CREATE_DOCUMENT);
            this.getStage().setScene(new Scene(rootController.getView(), 760, 480));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void logoutAction() throws IOException {
        if (stage == null) {
            this.stage = this.getStage();
        }

        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView(), 760, 480));
        this.stage.setTitle("Login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            documentModel = new DocumentModel();
            List<Doc> oldDocuments = documentModel.getOldDocuments();
            AlertHelper.showDefaultAlert(DocumentHelper.convertToString(oldDocuments), Alert.AlertType.CONFIRMATION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setUpPaginated();

    }



    @FXML
    private void statusAction(){
        System.out.println("I am lazy");
    }

    @FXML
    private void createDocAction(){
        System.out.println("I created the universe");
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

        documentTableView.getTableColumns().setAll(idColumn, nameColumn, dateCreatedColumn, dateLastOpenedColumn, descriptionColumn);

        try {
            documentTableView.setItems(documentModel.getObsAllDocuments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
