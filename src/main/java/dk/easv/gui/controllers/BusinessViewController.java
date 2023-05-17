package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class BusinessViewController extends RootController{

    private UserSingleClass actualUser = UserSingleClass.getInstance();

    @FXML
    private MFXButton businessLayer,
            createCustomer,
            delete,
            HomeLayer,
            workersLayer,
            edit,
            logoutButton;

    private IDocumentModel documentModel;
    @FXML
    private HBox Customers, mainHbox;


    @FXML
    private MFXScrollPane customersScrollPane;


    @FXML
    private MFXTableView<Doc> documentsTable;


    @FXML
    private VBox iconsVbox;
    @FXML
    private MFXTextField searchBar;

    private Stage stage;

    private ICustomerModel customerModel = new CustomerModel();

    public BusinessViewController() throws SQLException {
    }

    @FXML
    private void displayWorkers(ActionEvent actionEvent) throws IOException {
        this.stage = this.getStage();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.WORKERS);
        this.stage.setScene(new Scene(controller.getView()));
    }

    @FXML
    private void displayHome(ActionEvent actionEvent) throws IOException {
        this.stage = this.getStage();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.MAIN);
        this.stage.setScene(new Scene(controller.getView()));
    }


    private ConcurrentMap<Integer, Customer> getAllCustomersMap() throws SQLException {

        return customerModel.getAllCustomers();
    }
    private void initCustomers() throws SQLException {
        ConcurrentMap<Integer, Customer> map = getAllCustomersMap();
        Set<Integer> keys = map.keySet();
        try {
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/HboxCard.fxml")));
                HBox hBox = loader.load();
                HBoxController hboxController = loader.getController();
                hboxController.setCustomerBoxes(map);
                addLabelAndScrollPane(key.toString(), hBox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addLabelAndScrollPane(String key, HBox hBox) {
        Label label = new Label(key);
        Customers.getChildren().add(label);
        customersScrollPane.setContent(hBox);
        customersScrollPane.setFitToHeight(true);
    }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        this.stage = this.getStage();
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        this.stage.setScene(new Scene(controller.getView()));
        this.stage.setTitle("WUAV!!!");
    }

    private void roleView(){
            if(actualUser.getRole() == Role.TECHNICIAN){
                iconsVbox.getChildren().remove(businessLayer);
                iconsVbox.getChildren().remove(workersLayer);
            }
            if(actualUser.getRole() == Role.SALESPERSON){
                iconsVbox.getChildren().remove(workersLayer);

            }
            if(actualUser.getRole() == Role.PROJECTMANAGER){
                iconsVbox.getChildren().remove(businessLayer);

            }


    }



    @FXML
    private void createCustomer(ActionEvent actionEvent) throws RuntimeException, IOException {
        RootController controller;
        Stage stage = new Stage();
        controller = ControllerFactory.loadFxmlFile(ViewType.CREATE_CUSTOMERS);
        Scene scene = new Scene(controller.getView());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            documentModel = new DocumentModel();
            roleView();
            businessLayer.setDisable(true);
            initCustomers();
            setUpDocBoard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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


}
