package dk.easv.gui.controllers;

import dk.easv.gui.controllers.helpers.AlertHelper;
import dk.easv.gui.models.CanvasModel;
import dk.easv.gui.models.interfaces.ICanvasModel;
import dk.easv.gui.rootContoller.RootController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class CanvasViewController extends RootController {

    private final ICanvasModel model = CanvasModel.getInstance();
    @FXML
    private ChoiceBox<String> colorChooser;

    @FXML
    private ChoiceBox<Integer> sizeChooser;

    @FXML
    private MFXButton resetButton;

    @FXML
    private Canvas canvas;

    private GraphicsContext graphicsContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();

        resetButton.setOnAction(actionEvent -> {
            graphicsContext.clearRect(1, 1, graphicsContext.getCanvas().getWidth() - 2,
                    graphicsContext.getCanvas().getHeight() - 2);
        });

        initializePenSettings();
        drawOnCanvas();
    }

    private void initializePenSettings() {
        colorChooser.setItems(FXCollections.observableArrayList("Black", "Blue", "Red", "Green", "Brown", "Orange"));
        colorChooser.getSelectionModel().selectFirst();
        colorChooser.valueProperty().addListener((ov, old, newVal) -> {
            graphicsContext.setStroke(Color.web(newVal));
        });

        sizeChooser.setItems(FXCollections.observableList(IntStream.rangeClosed(1, 5).boxed().toList()));
        sizeChooser.getSelectionModel().selectFirst();
        sizeChooser.valueProperty().addListener((ov, old, newVal) -> {
            graphicsContext.setLineWidth(newVal);
        });
    }

    private void drawOnCanvas() {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            graphicsContext.beginPath();
            graphicsContext.moveTo(event.getX(), event.getY());
            graphicsContext.stroke();
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
            graphicsContext.lineTo(event.getX(), event.getY());
            graphicsContext.stroke();
        });
    }

    @FXML
    private void handleCancelOnAction() {
        this.getStage().close();
    }

    @FXML
    private void handleSaveOnAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Canvas Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image", "*.png")
        );
        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        try {
            model.generateImage(file, canvas);
        } catch (IOException e) {
            AlertHelper alertHelper = new AlertHelper("An error occurred while saving image", e);
            alertHelper.showAndWait();
        }
    }
}
