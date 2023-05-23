package dk.easv.gui.controllers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.rootContoller.IRootController;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.DocumentHelper;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML
    private MFXRectangleToggleNode projectsToggle;
    @FXML
    private VBox customersVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainBorderPane.centerProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });

        try {
            IRootController controller = ControllerFactory.loadFxmlFile(ViewType.HOME);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        roleView();

    }

    private void roleView() {
        if (actualUser != null) {
            if (actualUser.getRole() == Role.TECHNICIAN) {
                iconsVbox.getChildren().remove(businessLayer);
                iconsVbox.getChildren().remove(workersLayer);
            }
            if (actualUser.getRole() == Role.SALESPERSON) {
                iconsVbox.getChildren().remove(workersLayer);
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
        this.stage.centerOnScreen();
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
    private void displayHome(ActionEvent actionEvent) {
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.HOME);
            mainBorderPane.setCenter(rootController.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void displayProjects(ActionEvent actionEvent) {
        try {
            projectModel.setSelectedProjectId(0);
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.PROJECTS_VIEW);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void displayDocuments(ActionEvent actionEvent) {
        try {
            RootController controller = ControllerFactory.loadFxmlFile(ViewType.DOCUMENTS_VIEW);
            mainBorderPane.setCenter(controller.getView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
