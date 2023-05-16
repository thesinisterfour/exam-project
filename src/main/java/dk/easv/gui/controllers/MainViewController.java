package dk.easv.gui.controllers;

import dk.easv.be.Role;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.UserSingleClass;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
        public MFXTableView<?> customerTable;

        @FXML
        public MFXTableView<?> documentsTable;

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

        private UserSingleClass actualUser = UserSingleClass.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            System.out.println(actualUser.getName());
            roleView();

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
}
