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
import javafx.scene.Scene;
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

/**
 * The DocumentViewController class is a JavaFX controller that handles adding and saving text and
 * image content to a VBox container, as well as populating the container with content retrieved from a
 * database.
 */
public class DocumentViewController extends RootController {

    private ContentModel model = ContentModel.getInstance();

    @FXML
    private VBox vbox;

    /**
     * This function initializes the URL and ResourceBundle and populates the content if the document
     * ID is not equal to zero.
     * 
     * @param location The location of the FXML file that contains the controller class.
     * @param resources A ResourceBundle object that contains the resources for the current locale. It
     * is used to retrieve localized strings, images, and other resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (model.getDocumentId() != 0) {
            populateContent();
        }
    }

    /**
     * This function adds an image to a JavaFX VBox container and resizes it to fit the container's
     * width.
     * 
     * @param actionEvent An event that is triggered when the "addImage" button is clicked or activated
     * in some way.
     */
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

    /**
     * This function adds a new MFXTextField to a VBox container when an action event occurs.
     * 
     * @param actionEvent An event that is triggered when the "addTextOnAction" method is called,
     * usually by a user action such as clicking a button or pressing a key.
     */
    @FXML
    private void addTextOnAction(ActionEvent actionEvent) {
        ObservableList<Node> children = vbox.getChildren();
        MFXTextField mfxTextField = new MFXTextField();
        mfxTextField.setPrefWidth(vbox.getWidth() - 20);
        mfxTextField.setFloatMode(FloatMode.BORDER);
        children.add(mfxTextField);
    }

    /**
     * This function saves the text and image data from a JavaFX VBox container into a database using a
     * model class.
     * 
     * @param actionEvent An event that represents a user action, such as clicking a button or pressing
     * a key. It is passed as a parameter to the method saveOnAction().
     */
    @FXML
    private void saveOnAction(ActionEvent actionEvent) {
        ObservableList<Node> children = vbox.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            if (child instanceof MFXTextField mfxTextField) {
                try {
                    // This code is checking if the MFXTextField has an ID assigned to it. If it does
                    // not have an ID, it adds the text content of the MFXTextField to the model at the
                    // current index `i`. If it does have an ID, it parses the ID to an integer and
                    // adds the text content of the MFXTextField to the model at the specified index
                    // `i` and ID.
                    String id = mfxTextField.getId();
                    if (id == null){
                        model.addText(i, mfxTextField.getText());
                    } else {
                        model.addText(Integer.parseInt(id), i, mfxTextField.getText());
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (child instanceof ImageView imageView) {
                try {
                    String id = imageView.getId();
                    if (id == null){
                        model.addImage(i, imageView.getImage());
                    } else {
                        model.addImage(Integer.parseInt(id), i);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * This function sets the document ID to 0 and loads the admin view when the cancel button is
     * clicked.
     * 
     * @param actionEvent The action event that triggered the method, which is usually a user
     * interaction with a GUI element such as a button.
     */
    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        model.setDocumentId(0);
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.ADMIN);
            this.getStage().setScene(new Scene(rootController.getView()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This function populates a JavaFX VBox with content retrieved from a ConcurrentSkipListMap using
     * RetrieveContentTask and updates the VBox with either an ImageView or MFXTextField depending on
     * the type of content retrieved.
     */
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
                            imageView.setId(contentMap.get(key) + "");
                            children.set(key, imageView);
                        } else {
                            MFXTextField mfxTextField = new MFXTextField();
                            mfxTextField.setPrefWidth(vbox.getWidth() - 20);
                            mfxTextField.setFloatMode(FloatMode.BORDER);
                            mfxTextField.setText((String) newValue);
                            mfxTextField.setId(contentMap.get(key) + "");
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
