package dk.easv.gui.controllers;

import dk.easv.gui.models.ContentModel;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DocumentViewController extends RootController {

    private ContentModel model = new ContentModel();

    @FXML
    private VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void addImageOnAction(ActionEvent actionEvent) {
        ObservableList<Node> children = vbox.getChildren();
        ImageView imageView = new ImageView();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        Image image = new Image(selectedFile.getAbsolutePath());
        imageView.setImage(image);
        imageView.preserveRatioProperty().set(true);
        imageView.setFitWidth(vbox.getWidth()-20);

        vbox.widthProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setFitWidth((double) newValue - 20);
        });
        children.add(imageView);
        try {
            model.addImage(1, 1, image);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void addTextOnAction(ActionEvent actionEvent) {
        ObservableList<Node> children = vbox.getChildren();
        MFXTextField mfxTextField = new MFXTextField();
        mfxTextField.setPrefWidth(vbox.getWidth()-20);
        mfxTextField.setFloatMode(FloatMode.BORDER);
        children.add(mfxTextField);
    }
}
