package dk.easv.gui.controllerFactory;

import dk.easv.Main;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ControllerFactory {
    public static RootController loadFxmlFile(ViewType fxmlFile) throws IOException, NullPointerException {
        final URL fxmlFileUrl = Objects.requireNonNull(Main.class.getResource(fxmlFile.getFXMLView()), fxmlFile.getFXMLView() + " does not exist");
        final FXMLLoader loader = new FXMLLoader(fxmlFileUrl);
        final Parent view = loader.load();
        final RootController controller = loader.getController();
        controller.setView(view);

        return controller;

    }
}
