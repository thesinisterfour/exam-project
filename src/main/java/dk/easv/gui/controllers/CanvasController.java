package dk.easv.gui.controllers;

import com.itextpdf.io.source.ByteArrayOutputStream;
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
import java.net.URL;
import java.util.ResourceBundle;
import dk.easv.gui.models.ContentModel;
import dk.easv.gui.models.DrawnImageModel;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class CanvasController extends RootController {

    private final ICanvasModel model = CanvasModel.getInstance();

    String filePath = null;
    @FXML
    private ChoiceBox<String> colorChooser;

    @FXML
    private ChoiceBox<String> sizeChooser;

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
        colorChooser.getSelectionModel().selectedIndexProperty().addListener((ov, old, newval) -> {
            Number idx = newval;
            Color newColor;
            switch (idx.intValue()) {
                case 0:
                    newColor = Color.BLACK;
                    break;
                case 1:
                    newColor = Color.BLUE;
                    break;
                case 2:
                    newColor = Color.RED;
                    break;
                case 3:
                    newColor = Color.GREEN;
                    break;
                case 4:
                    newColor = Color.BROWN;
                    break;
                case 5:
                    newColor = Color.ORANGE;
                    break;
                default:
                    newColor = Color.BLACK;
                    break;
            }
            graphicsContext.setStroke(newColor);
        });

        sizeChooser.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
        sizeChooser.getSelectionModel().selectFirst();
        sizeChooser.getSelectionModel().selectedIndexProperty().addListener((ov, old, newval) -> {
            Number idx = newval;
            switch (idx.intValue()) {
                case 0:
                    graphicsContext.setLineWidth(1);
                    break;
                case 1:
                    graphicsContext.setLineWidth(2);
                    break;
                case 2:
                    graphicsContext.setLineWidth(3);
                    break;
                case 3:
                    graphicsContext.setLineWidth(4);
                    break;
                case 4:
                    graphicsContext.setLineWidth(5);
                    break;
                default:
                    graphicsContext.setLineWidth(1);
                    break;
            }
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
        model.generateImage(file, canvas);

        if (file != null) {
            filePath = file.getAbsolutePath();
            Image image = new Image("file:" + filePath);
            DrawnImageModel.setDrawnImage(image);
        }
    }
}
