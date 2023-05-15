package dk.easv.gui.controllers;

import dk.easv.be.Role;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.UserSingleClass;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends RootController {

        @FXML
        public MFXButton addDocument;

        @FXML
        public MFXButton businessLayer;

        @FXML
        public MFXTableView<?> customerTable;

        @FXML
        public MFXButton deleteDocument;

        @FXML
        public MFXButton documentLayer;

        @FXML
        public MFXTableView<?> documentsTable;

        @FXML
        public MFXButton editDocument;

        @FXML
        public MFXTableView<?> projectTable;

        @FXML
        public MFXTextField searchBar;

        @FXML
        public MFXButton workersLayer;

        @FXML
        public HBox mainHbox;

        private UserSingleClass actualUser = UserSingleClass.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            System.out.println(actualUser.getName());
            if(actualUser.getRole() == Role.ADMIN){
                    mainHbox.getChildren().remove(deleteDocument);
            }

    }
}
