package dk.easv.gui.controllers;

import animatefx.animation.FadeIn;
import dk.easv.Main;
import dk.easv.be.Content;
import dk.easv.gui.controllerFactory.ControllerFactory;
import dk.easv.gui.models.ContentModel;
import dk.easv.gui.models.DrawnImageModel;
import dk.easv.gui.models.interfaces.IContentModel;
import dk.easv.gui.models.tasks.RetrieveContentTask;
import dk.easv.gui.rootContoller.RootController;
import dk.easv.helpers.ViewType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * The DocumentViewController class is a JavaFX controller that handles adding and saving text and
 * image content to a VBox container, as well as populating the container with content retrieved from a
 * database.
 */
public class DocumentViewController extends RootController {

    private final ScheduledExecutorService scheduledSaveService = Executors.newSingleThreadScheduledExecutor();
    private final int scaleOffset = 50;
    private final Label emptyLabel = new Label("No content to display");
    private final IContentModel model = ContentModel.getInstance();
    @FXML
    private VBox vbox;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private HBox centeringHBox;
    private Pane scaleReferencePane;

    /**
     * This function initializes the URL and ResourceBundle and populates the content if the document
     * ID is not equal to zero.
     *
     * @param location  The location of the FXML file that contains the controller class.
     * @param resources A ResourceBundle object that contains the resources for the current locale. It
     *                  is used to retrieve localized strings, images, and other resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (model.getDocumentId() != 0) {
            populateContent();
        }

        scrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            centeringHBox.setMinWidth(newValue.doubleValue() - 14);
        });
        vbox.setMaxWidth(1000);

        scaleReferencePane = vbox;

//        progressiveSave();
    }

    /**
     * This function adds an image to a JavaFX VBox container and resizes it to fit the container's
     * width.
     *
     * @param actionEvent An event that represents a user action, such as clicking a button.
     */
    @FXML
    private void addImageOnAction(ActionEvent actionEvent) {
        ObservableList<Node> children = vbox.getChildren();

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.getAbsolutePath());
            HBox hBox = addImage(image);
            children.add(hBox);
            new FadeIn(hBox).play();

        }

//        emptyCheck();
    }

    private VBox createSideButtonsVBox() {
        MFXButton buttonUp = new MFXButton("↑");
        buttonUp.setOnAction(this::moveUp);
        MFXButton buttonDelete = new MFXButton("❌");
        buttonDelete.setOnAction(this::deleteNode);
        MFXButton buttonDown = new MFXButton("↓");
        buttonDown.setOnAction(this::moveDown);
        VBox vBox = new VBox(8, buttonUp, buttonDelete, buttonDown);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void moveUp(ActionEvent event) {
        ObservableList<Node> children = vbox.getChildren();
        MFXButton button = (MFXButton) event.getSource();
        HBox hBox = (HBox) button.getParent().getParent();
        int i = children.indexOf(hBox);
        if (i != 0) {
            Node swapping = children.remove(i);
            Node swapFor = children.remove(i - 1);
            children.add(i - 1, swapping);
            children.add(i, swapFor);
        }
    }

    private void moveDown(ActionEvent event) {
        ObservableList<Node> children = vbox.getChildren();
        MFXButton button = (MFXButton) event.getSource();
        HBox hBox = (HBox) button.getParent().getParent();
        int i = children.indexOf(hBox);
        if (i != children.size() - 1) {
            Node swapFor = children.remove(i + 1);
            Node swapping = children.remove(i);
            children.add(i, swapFor);
            children.add(i + 1, swapping);
        }
    }

    private void deleteNode(ActionEvent event) {
        ObservableList<Node> children = vbox.getChildren();
        MFXButton button = (MFXButton) event.getSource();
        HBox hBox = (HBox) button.getParent().getParent();
        if (hBox.getId() != null) {
            int id = Integer.parseInt(hBox.getId());
            try {
                model.deleteMap(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        children.remove(hBox);
//        emptyCheck();
    }

    /**
     * This function adds a new MFXTextField to a VBox container when an action event occurs.
     *
     * @param actionEvent An event that represents a user action, such as clicking a button.
     */
    @FXML
    private void addTextOnAction(ActionEvent actionEvent) {
        ObservableList<Node> children = vbox.getChildren();
        children.add(addText(""));
//        emptyCheck();;

    }

    /**
     * This function saves the text and image data from a JavaFX VBox container into a database using a
     * model class.
     *
     * @param actionEvent An event that represents a user action, such as clicking a button.
     */
    @FXML
    private void saveOnAction(ActionEvent actionEvent) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::saveContent);
        executorService.shutdown();
    }

    private void saveContent() {
        ObservableList<Node> children = vbox.getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (!(children.get(i) instanceof HBox hBox)) {
                return;
            }
            if (hBox.getChildren().get(0) instanceof MFXTextField mfxTextField) {
                try {
                    String id = hBox.getId();
                    if (id == null) {
                        model.addText(i, mfxTextField.getText());
                    } else {
                        model.addText(Integer.parseInt(id), i, mfxTextField.getText());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (hBox.getChildren().get(0) instanceof ImageView imageView) {
                try {
                    String id = hBox.getId();
                    if (id == null) {
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
     *                    interaction with a GUI element such as a button.
     */
    @FXML
    private void cancelOnAction(ActionEvent actionEvent) {
        model.setDocumentId(0);
        try {
            RootController rootController = ControllerFactory.loadFxmlFile(ViewType.MAIN);
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
        //TODO: refactoring on this
        ObservableList<Node> children = vbox.getChildren();
        children.add(new ImageView(Objects.requireNonNull(Main.class.getResource("icons/loading.gif")).toString()));

        Platform.runLater(() -> {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Task<ConcurrentNavigableMap<Integer, Integer>> mapTask = new Task<>() {
                @Override
                protected ConcurrentNavigableMap<Integer, Integer> call() throws Exception {
                    return model.getContentMap();
                }
            };
            mapTask.valueProperty().addListener(((obs, o, n) -> {
                ConcurrentNavigableMap<Integer, Integer> contentMap = n;
                ExecutorService es = Executors.newFixedThreadPool(50);
                contentMap.forEach((k, v) -> children.add(new ImageView(Objects.requireNonNull(Main.class.getResource("icons/loading.gif")).toString())));
                for (Integer key : contentMap.keySet()) {
                    RetrieveContentTask task = new RetrieveContentTask(contentMap.get(key));
                    // This code is checking if the content is an image or text and updates the VBox on change of the value property.
                    task.valueProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
                        Content content = (Content) newValue;
                        if (content.getImage() != null) {
                            HBox hBox = addImage(content.getImage());
                            hBox.setId(contentMap.get(key) + "");
                            children.set(key, hBox);
                            new FadeIn(hBox).play();
                        } else {
                            HBox hBox = addText(content.getText());
                            hBox.setId(contentMap.get(key) + "");
                            children.set(key, hBox);
                            new FadeIn(hBox).play();
                        }
                    }));
                    es.submit(task);

                }
                es.shutdown();

                if (n.isEmpty()) {
//                    vbox.getChildren().add(0, new Label("No content to display"));
                }

            }));
            executorService.submit(mapTask);
            executorService.shutdown();

            try {
                if (executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            children.remove(children.size() - 1);
        });


    }

    private HBox addText(String newValue) {
        MFXTextField mfxTextField = new MFXTextField();
        mfxTextField.setFloatMode(FloatMode.BORDER);
        mfxTextField.setText(newValue);
        mfxTextField.setMinWidth(scaleReferencePane.getWidth() - scaleOffset);
        mfxTextField.setMaxWidth(scaleReferencePane.getWidth() - scaleOffset);
        scaleReferencePane.widthProperty().addListener((o, oldV, newV) -> {
            mfxTextField.setMinWidth(newV.doubleValue() - scaleOffset);
            mfxTextField.setMaxWidth(newV.doubleValue() - scaleOffset);
        });
        return getHBoxWithNavButtons(mfxTextField);
    }

    private HBox addImage(Image image) {
        ImageView imageView = new ImageView();

        imageView.setImage(image);
        imageView.preserveRatioProperty().set(true);
        imageView.setFitWidth(scaleReferencePane.getWidth() - scaleOffset);
        scaleReferencePane.widthProperty().addListener((o, oldV, newV) -> imageView.setFitWidth((double) newV - scaleOffset));
        return getHBoxWithNavButtons(imageView);
    }

    @FXML
    private void saveAsPdfOnAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                model.saveAsPDF(file.getAbsolutePath());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private HBox getHBoxWithNavButtons(Node node) {
        HBox hBox = new HBox(8, node, createSideButtonsVBox());
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }

    // to be implemented
    private void progressiveSave() {
        scheduledSaveService.scheduleAtFixedRate(() -> saveOnAction(null), 0, 5, TimeUnit.SECONDS);
    }

    private void stopProgressiveSave() {
        scheduledSaveService.shutdown();
    }

    private void emptyCheck() {
        if (vbox.getChildren().size() == 0) {
            vbox.getChildren().add(0, emptyLabel);
        } else if (vbox.getChildren().get(0) == emptyLabel) {
            vbox.getChildren().remove(0);
        }
    }

    @FXML
    private void vboxOnDragDropped(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        List<File> droppedFiles = null;
        if (db.hasFiles()) {
            droppedFiles = db.getFiles();
            success = true;
        }

        List<String> imageExtensions = new ArrayList<>();
        imageExtensions.add("jpg");
        imageExtensions.add("png");

        ObservableList<Node> children = vbox.getChildren();
        if (droppedFiles != null) {
            for (File file : droppedFiles) {
                if (imageExtensions.contains(FilenameUtils.getExtension(file.getAbsolutePath()))) {
                    children.add(addImage(new Image(file.getAbsolutePath())));
                } else {
                    System.out.println("Filetype not compatible");
                }
            }
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        dragEvent.setDropCompleted(success);

        children.remove(dropImage);

        dragEvent.consume();
    }

    private VBox createDropImageVBox(){
        VBox dropImage = new VBox(new Label("Release mouse to add image"));
        dropImage.setAlignment(Pos.CENTER);
        dropImage.setPadding(new Insets(50, 10, 50, 10));

        dropImage.setStyle("-fx-border-style: dashed inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");

        return dropImage;
    }


    private final VBox dropImage = createDropImageVBox();
    @FXML
    private void vboxOnDragOver(DragEvent dragEvent) {
        scrollPane.setVvalue(1.0);
        if (dragEvent.getGestureSource() != dragEvent && dragEvent.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);

            ObservableList<Node> children = vbox.getChildren();
            if (!children.contains(dropImage)){
                children.add(dropImage);
            }
        }
        dragEvent.consume();
    }

    @FXML
    private void openCanvasOnAction() throws IOException {
        DrawnImageModel.setDocumentViewController(this);
        Stage stage = new Stage();
        stage.setTitle("Sketch");
        RootController controller = ControllerFactory.loadFxmlFile(ViewType.CANVAS);
        Scene scene = new Scene(controller.getView());
        stage.setScene(scene);
        stage.show();
    }

  /*

  Unfinished feature for auto adding drawn image

        public void setDrawnImage(Image image){
        HBox hBox = addImage(image);
        vbox.getChildren().add(hBox);
        new FadeIn(hBox).play();
    }*/
}
