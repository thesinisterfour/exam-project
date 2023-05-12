package dk.easv;

import dk.easv.dal.connectionManager.ConnectionManagerFactory;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.LOGIN);
        Scene scene = new Scene(controller.getView(), 760, 480);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() {
        ConnectionManagerFactory.stopAllConnectionManagers();
    }
}