package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.ContentModel;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.models.interfaces.IContentModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.AlertHelper;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.fxml.FXML;
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
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainViewController extends RootController {

        @FXML
        public MFXButton addDocument,
                businessLayer,
                deleteDocument,
                documentLayer,
                editDocument,
                workersLayer;

        @FXML
        public MFXTableView<Customer> customerTable;

        @FXML
        public MFXTableView<Doc> documentsTable;

        @FXML
        public MFXTableView<?> projectTable;

        @FXML
        public MFXTextField searchBar;

        @FXML
        public HBox mainHbox;

        @FXML
        public VBox iconsVbox;

        @FXML
        public GridPane tableCustomer, tableProject;

        @FXML
        public Label customerLabel, projectLabel;

        private Stage stage;

        private IDocumentModel documentModel;

        private ICustomerModel customerModel;

        private UserSingleClass actualUser = UserSingleClass.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            documentModel = new DocumentModel();
            customerModel= new CustomerModel();
            setUpDocBoard();
            roleView();
            setUpCustomerBoard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void roleView(){
            if(actualUser != null){
                    if(actualUser.getRole() == Role.TECHNICIAN){
                        iconsVbox.getChildren().remove(businessLayer);
                        iconsVbox.getChildren().remove(workersLayer);
                        mainHbox.getChildren().remove(deleteDocument);
                        tableCustomer.getChildren().remove(customerTable);
                        tableCustomer.getChildren().remove(customerLabel);
                    }
                    if(actualUser.getRole() == Role.SALESPERSON){
                         iconsVbox.getChildren().remove(documentLayer);
                         iconsVbox.getChildren().remove(workersLayer);
                         mainHbox.getChildren().remove(deleteDocument);
                         mainHbox.getChildren().remove(editDocument);
                         mainHbox.getChildren().remove(addDocument);
                        tableProject.getChildren().remove(projectTable);
                        tableProject.getChildren().remove(projectLabel);

                    }
                    if(actualUser.getRole() == Role.PROJECTMANAGER){
                        iconsVbox.getChildren().remove(businessLayer);

                    }

            }
    }

    @FXML
    public void handleLogout() throws IOException {
        if (stage == null) {
            this.stage = this.getStage();
        }

        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView()));
        this.stage.setTitle("Login");
    }

    @FXML
    private void handleCreateDocument() {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.CREATE_DOCUMENT);
            this.getStage().setScene(new Scene(rootController.getView()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEditDocument() {
        IContentModel contentModel = ContentModel.getInstance();
        Doc document = documentsTable.getSelectionModel().getSelectedValues().get(0);
        contentModel.setDocumentId(document.getId());
        try {
            this.stage = this.getStage();
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.DOCUMENT);
            this.getStage().setScene(new Scene(rootController.getView(), stage.getWidth(), stage.getWidth()));
        } catch (IOException e) {
            AlertHelper.showDefaultAlert("Pleas select a document to Edit", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void handleDelete() throws SQLException {
        try {
            Doc selectedDocument = documentsTable.getSelectionModel().getSelectedValues().get(0);
            documentModel.deleteDocument(selectedDocument.getId());
            documentModel.setObsAllDocuments();
        } catch (IndexOutOfBoundsException e) {
            AlertHelper.showDefaultAlert("Pleas select a document to delete", Alert.AlertType.ERROR);
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

        documentsTable.getTableColumns().setAll(idColumn, nameColumn, dateCreatedColumn, dateLastOpenedColumn, descriptionColumn);
        documentsTable.autosizeColumnsOnInitialization();
        try {
            documentsTable.setItems(documentModel.getObsAllDocuments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
