package dk.easv.gui.controllers;
import dk.easv.Main;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomersController extends RootController {

    private Stage stage;

    @FXML
    private MFXButton back_button,delete_button, edit_button, new_customer_button ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        test();
    }
    public void test(){
        back_button.setOnAction(e -> {
            RootController controller = null;
            Stage stage = new Stage();
            try {
                controller = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
                Scene scene = new Scene(controller.getView());
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                getStage().close();
        });
        delete_button.setOnAction(e -> {
            System.out.println("Delete");
        });
        edit_button.setOnAction(e -> {
            System.out.println("Edit");
        });
    }

    @FXML
    private void createCustomer(ActionEvent actionEvent) {
        RootController controller = null;
        Stage stage = new Stage();
        try {
            controller = ControllerFactory.loadFxmlFile(ViewType.CREATE_CUSTOMERS);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Scene scene = new Scene(controller.getView());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
