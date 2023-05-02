package dk.easv.gui.controllers;

import dk.easv.be.Content;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.ContentModel;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
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
import java.util.List;
import java.util.ResourceBundle;

public class DocumentViewController extends RootController {

    private ContentModel model = ContentModel.getInstance();

    @FXML
    private VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (model.getDocumentId() != 0) {
            try {
                model.loadContent(model.getDocumentId());
                populateContent();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
        imageView.setFitWidth(vbox.getWidth() - 20);

        vbox.widthProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setFitWidth((double) newValue - 20);
        });
        children.add(imageView);

    }

    @FXML
    private void addTextOnAction(ActionEvent actionEvent) {
        ObservableList<Node> children = vbox.getChildren();
        MFXTextField mfxTextField = new MFXTextField();
        mfxTextField.setPrefWidth(vbox.getWidth() - 20);
        mfxTextField.setFloatMode(FloatMode.BORDER);
        children.add(mfxTextField);
    }

    @FXML
    private void saveOnAction(ActionEvent actionEvent) {
        ObservableList<Node> children = vbox.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            if (child instanceof MFXTextField mfxTextField) {
                try {
                    model.addText(1, i, mfxTextField.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (child instanceof ImageView imageView) {
                try {
                    model.addImage(1, i, imageView.getImage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        model.setDocumentId(0);
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
            this.getStage().setScene(rootController.getView().getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void populateContent(){
        List<Content> contentList = model.getContentList();
        for (Content content : contentList) {
             if (content.getText() == null){
                    ImageView imageView = new ImageView();
                    imageView.setImage(content.getImage());
                    imageView.preserveRatioProperty().set(true);
                    imageView.setFitWidth(vbox.getWidth() - 20);
                    vbox.widthProperty().addListener((observable, oldValue, newValue) -> {
                        imageView.setFitWidth((double) newValue - 20);
                    });
                    vbox.getChildren().add(imageView);
                } else {
                    MFXTextField mfxTextField = new MFXTextField();
                    mfxTextField.setPrefWidth(vbox.getWidth() - 20);
                    mfxTextField.setFloatMode(FloatMode.BORDER);
                    mfxTextField.setText(content.getText());
                    vbox.getChildren().add(mfxTextField);
             }

        }
    }
}
