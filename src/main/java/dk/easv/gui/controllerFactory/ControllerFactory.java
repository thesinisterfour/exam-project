package dk.easv.gui.controllerFactory;

import dk.easv.Main;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ControllerFactory  {
    public static RootController loadFxmlFile(ViewType fxmlFile) throws IOException {
        Objects.requireNonNull(fxmlFile, "fxmlFile must not be null.");

        final URL fxmlFileUrl = Main.class.getResource(fxmlFile.getFXMLView());

        final FXMLLoader loader = new FXMLLoader(fxmlFileUrl);

        final Parent view = loader.load();
        final RootController controller = loader.getController();
        controller.setView(view);

        return controller;
    }
}
