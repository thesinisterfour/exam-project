package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.controllers.tasks.LoadCustomerModelTask;
import dk.easv.gui.controllers.tasks.LoadDocumentModelTask;
import dk.easv.gui.controllers.tasks.LoadProjectModelTask;
import dk.easv.gui.models.ContentModel;
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
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewController extends RootController {

    private final UserSingleClass actualUser = UserSingleClass.getInstance();
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
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private VBox centerVBox;
    @FXML
    private MFXButton logoutButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExecutorService es = Executors.newFixedThreadPool(10);

        LoadDocumentModelTask loadDocumentModelTask = new LoadDocumentModelTask();
        loadDocumentModelTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            documentModel = newValue;
            setUpDocBoard();

            try {
                List<Doc> oldDocuments = documentModel.getOldDocuments();
                if (!oldDocuments.isEmpty() && !DocumentHelper.isOldDocWarningShown()) {
                    AlertHelper alertHelper = new AlertHelper(DocumentHelper.convertToString(oldDocuments), Alert.AlertType.INFORMATION);
                    if (alertHelper.showAndWait()){
                        for (Doc oldDocument : oldDocuments) {
                            documentModel.deleteDocument(oldDocument.getId());
                        }
                        documentModel.setObsAllDocuments();
                    }
                    DocumentHelper.setOldDocWarningShown(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        LoadCustomerModelTask loadCustomerModelTask = new LoadCustomerModelTask();
        loadCustomerModelTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            customerModel = newValue;
            try {
                setUpCustomerBoard();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        LoadProjectModelTask loadProjectModelTask = new LoadProjectModelTask();
        loadProjectModelTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            projectModel = newValue;
            setupProjectTable();
        });

        es.submit(loadCustomerModelTask);
        es.submit(loadDocumentModelTask);
        es.submit(loadProjectModelTask);

        roleView();
        es.shutdown();

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
        DocumentHelper.setOldDocWarningShown(false);
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
            AlertHelper alertHelper = new AlertHelper("A file error occurred", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        } catch (IndexOutOfBoundsException e) {
            AlertHelper alertHelper = new AlertHelper("Please select a document to edit", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
        }

    }

    @FXML
    private void handleDelete() throws SQLException {
        try {
            Doc selectedDocument = documentsTable.getSelectionModel().getSelectedValues().get(0);
            documentModel.deleteDocument(selectedDocument.getId());
            documentModel.setObsAllDocuments();
        } catch (IndexOutOfBoundsException e) {
            AlertHelper alertHelper = new AlertHelper("Please select a document to delete", Alert.AlertType.ERROR);
            alertHelper.showAndWait();
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
        documentsTable.getSelectionModel().setAllowsMultipleSelection(false);

        documentsTable.getFilters().addAll(
                new StringFilter<>("Name", Doc::getName),
                new StringFilter<>("Description", Doc::getDescription)
        );

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

        projectTable.getFilters().addAll(new StringFilter<>("Name", Project::getProjectName),
                new StringFilter<>("Address", Project::getProjectAddress),
                new IntegerFilter<>("Zip Code", Project::getProjectZipcode));


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

        customerTable.getFilters().addAll(new StringFilter<>("Name", Customer::getCustomerName),
                new StringFilter<>("Email", Customer::getCustomerEmail),
                new StringFilter<>("Address", Customer::getCustomerAddress),
                new IntegerFilter<>("Zip Code", Customer::getZipCode));
        customerTable.getTableColumns().setAll(nameColumn, emailColumn, addressColumn, zipCodeColumn);
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
