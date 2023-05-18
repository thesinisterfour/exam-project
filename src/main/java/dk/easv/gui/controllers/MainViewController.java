package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.ContentModel;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.IContentModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController extends RootController {

    @FXML
    public MFXButton addDocument,
            businessLayer,
            deleteDocument,
            HomeLayer,
            editDocument,
            workersLayer;

    @FXML
    public MFXTableView<Customer> customerTable;

    @FXML
    public MFXTableView<Doc> documentsTable;

    @FXML
    public MFXTableView<Project> projectTable;

    @FXML
    public MFXTextField searchBar;

    @FXML
    public HBox mainHbox;

    @FXML
    public VBox iconsVbox;

    @FXML
    public Label customerLabel, projectLabel;

    private Stage stage;

    private IDocumentModel documentModel;

    private ICustomerModel customerModel;
    private IProjectModel projectModel;

    private final UserSingleClass actualUser = UserSingleClass.getInstance();
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private VBox centerVBox;
    @FXML
    private MFXButton logoutButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            documentModel = new DocumentModel();
            customerModel = new CustomerModel();
            projectModel = new ProjectModel();
            setUpDocBoard();
            roleView();
            setUpCustomerBoard();
            setupProjectTable();

            List<Doc> oldDocuments = documentModel.getOldDocuments();

            if (!oldDocuments.isEmpty() && !AlertHelper.isAlertShown()) {
                AlertHelper.setDocumentModel(documentModel);
                AlertHelper.showDefaultAlert(DocumentHelper.convertToString(oldDocuments), Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void roleView() {
        if (actualUser != null) {
            if (actualUser.getRole() == Role.TECHNICIAN) {
                iconsVbox.getChildren().remove(businessLayer);
                iconsVbox.getChildren().remove(workersLayer);
                mainHbox.getChildren().remove(deleteDocument);
            }
            if (actualUser.getRole() == Role.SALESPERSON) {
                iconsVbox.getChildren().remove(workersLayer);
                mainHbox.getChildren().remove(deleteDocument);
                mainHbox.getChildren().remove(editDocument);
                mainHbox.getChildren().remove(addDocument);
            }
            if (actualUser.getRole() == Role.PROJECTMANAGER) {
                iconsVbox.getChildren().remove(businessLayer);

            }

        }
    }

    @FXML
    public void handleLogout(ActionEvent actionEvent) throws IOException {
        this.stage = (Stage) logoutButton.getScene().getWindow();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView()));
        this.stage.setTitle("WUAV!!!");
        AlertHelper.resetIsAlertShown();
    }

    @FXML
    private void displayBusiness() throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.BUSINESS_VIEW);
        mainBorderPane.setCenter(controller.getView());
    }

    @FXML
    private void displayWorkers(ActionEvent actionEvent) throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.WORKERS);
        mainBorderPane.setCenter(controller.getView());
    }

    @FXML
    private void handleCreateDocument() {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.CREATE_DOCUMENT);
            mainBorderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEditDocument() {
        try {
            IContentModel contentModel = ContentModel.getInstance();
            Doc document = documentsTable.getSelectionModel().getSelectedValues().get(0);
            contentModel.setDocumentId(document.getId());
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.DOCUMENT);
            mainBorderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            AlertHelper.showDefaultAlert("A file error occurred", Alert.AlertType.ERROR);
        } catch (IndexOutOfBoundsException e) {
            AlertHelper.showDefaultAlert("Please select a document to edit", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void handleDelete() throws SQLException {
        try {
            Doc selectedDocument = documentsTable.getSelectionModel().getSelectedValues().get(0);
            documentModel.deleteDocument(selectedDocument.getId());
            documentModel.setObsAllDocuments();
        } catch (IndexOutOfBoundsException e) {
            AlertHelper.showDefaultAlert("Please select a document to delete", Alert.AlertType.ERROR);
        }
    }

    private void setUpDocBoard() {
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
            if (date == null) {
                return new MFXTableRowCell<>(doc -> "Never");
            }
            return new MFXTableRowCell<>(Doc::getLastView);
        });
        descriptionColumn.setRowCellFactory(document -> {
            String description = document.getDescription();
            if (description == null) {
                return new MFXTableRowCell<>(doc -> "No description");
            }
            return new MFXTableRowCell<>(Doc::getDescription);
        });

        documentsTable.getTableColumns().setAll(idColumn, nameColumn, dateCreatedColumn, dateLastOpenedColumn, descriptionColumn);
        documentsTable.autosizeColumnsOnInitialization();


        projectTable.getSelectionModel().selectionProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    documentModel.setObsProjectDocuments(newValue.values().stream().findFirst().get().getProjectID());
                    documentsTable.setItems(documentModel.getObsDocuments());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }));

        documentsTable.setItems(documentModel.getObsDocuments());
    }

    private void setupProjectTable() {
        MFXTableColumn<Project> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(Project::getProjectID));
        MFXTableColumn<Project> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(Project::getProjectName));
        MFXTableColumn<Project> dateStartColumn = new MFXTableColumn<>("Start Date", true, Comparator.comparing(Project::getStartDate));
        MFXTableColumn<Project> dateEndColumn = new MFXTableColumn<>("End Date", true, Comparator.comparing(Project::getEndDate));
        MFXTableColumn<Project> addressColumn = new MFXTableColumn<>("Address", true, Comparator.comparing(Project::getProjectAddress));
        MFXTableColumn<Project> zipCodeColumn = new MFXTableColumn<>("Zip Code", true, Comparator.comparing(Project::getProjectZipcode));

        idColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getProjectID));
        nameColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getProjectName));
        dateStartColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getStartDate));
        dateEndColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getEndDate));
        addressColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getProjectAddress));
        zipCodeColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getProjectZipcode));

        projectTable.getTableColumns().setAll(idColumn, nameColumn, dateStartColumn, dateEndColumn, addressColumn, zipCodeColumn);
        projectTable.autosizeColumnsOnInitialization();


        customerTable.getSelectionModel().selectionProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    documentsTable.getItems().clear();
                    projectModel.getProjectsByCustomerId(newValue.values().stream().findFirst().get().getCustomerID());
                    projectTable.setItems(projectModel.getProjectObservableList());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }));

        projectTable.setItems(projectModel.getProjectObservableList());
    }

    private void setUpCustomerBoard() throws SQLException {
        MFXTableColumn<Customer> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(Customer::getCustomerID));
        MFXTableColumn<Customer> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(Customer::getCustomerName));
        MFXTableColumn<Customer> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(Customer::getCustomerEmail));
        MFXTableColumn<Customer> addressColumn = new MFXTableColumn<>("Address", true, Comparator.comparing(Customer::getCustomerAddress));
        MFXTableColumn<Customer> zipCodeColumn = new MFXTableColumn<>("Zip Code", true, Comparator.comparing(Customer::getZipCode));

        idColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerID));
        nameColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerName));
        emailColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerEmail));
        addressColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerAddress));
        zipCodeColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getZipCode));

        customerTable.getTableColumns().setAll(idColumn, nameColumn, emailColumn, addressColumn, zipCodeColumn);
        customerTable.autosizeColumnsOnInitialization();
        customerTable.setItems(customerModel.getObsAllCustomers());

    }


    @FXML
    private void displayHome(ActionEvent actionEvent) {
        try {
            if (mainBorderPane.getCenter() != centerVBox) {
                RootController rootController = ControllerFactory.loadFxmlFile(ViewType.MAIN);
                BorderPane borderPane = (BorderPane) rootController.getView();
                mainBorderPane.setCenter(borderPane.getCenter());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
