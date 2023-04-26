package dk.easv.gui.rootContoller;

import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Abstract class that cannot be instantiated it is used for setting up view when its loaded
 * serves just purpose of setting and getting view when it is needed with little help of abstraction
 */
public abstract class RootController implements IRootController {
    protected Parent root;

    @Override
    public Parent getView() {
        return root;
    }

    @Override
    public void setView(Parent node) {
        this.root = Objects.requireNonNull(node, "view must not be null.");
    }

    @Override
    public Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

}
