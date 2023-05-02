package dk.easv.gui.controllers;

import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.ContentModel;
import dk.easv.gui.models.tasks.RetrieveContentTask;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DocumentViewController extends RootController {

    private ContentModel model = ContentModel.getInstance();

    @FXML
    private VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (model.getDocumentId() != 0) {
            populateContent();
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

    private void populateContent() {
        Platform.runLater(() -> {
            ConcurrentSkipListMap<Integer, Integer> contentMap = model.getContentMap();
            ObservableList<Node> children = vbox.getChildren();
            contentMap.forEach((k, v) -> children.add(new Text("")));
            for (Integer key : contentMap.keySet()) {
                RetrieveContentTask task = new RetrieveContentTask(contentMap.get(key));
                task.valueProperty().addListener((observable, oldValue, newValue) -> {
                    Platform.runLater(() -> {
                        if (newValue instanceof Image image) {
                            ImageView imageView = new ImageView();
                            imageView.setImage(image);
                            imageView.preserveRatioProperty().set(true);
                            imageView.setFitWidth(vbox.getWidth() - 20);
                            vbox.widthProperty().addListener((o, oldV, newV) -> {
                                imageView.setFitWidth((double) newV - 20);
                            });
                            children.set(key, imageView);
                        } else {
                            MFXTextField mfxTextField = new MFXTextField();
                            mfxTextField.setPrefWidth(vbox.getWidth() - 20);
                            mfxTextField.setFloatMode(FloatMode.BORDER);
                            mfxTextField.setText(newValue.toString());
                            children.set(key, mfxTextField);
                        }
                    });
                });

                ExecutorService es = Executors.newSingleThreadExecutor();
                es.submit(task);
                es.shutdown();

            }
        });
    }
}
