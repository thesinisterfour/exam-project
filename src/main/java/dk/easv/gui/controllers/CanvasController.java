package dk.easv.gui.controllers;

import dk.easv.gui.rootContoller.RootController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CanvasController extends RootController {
    @FXML
    private ChoiceBox<String> colorChooser;

    @FXML
    private ChoiceBox<String> sizeChooser;

    @FXML
    private Button resetButton;

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

        colorChooser.setItems(FXCollections.observableArrayList("Black", "Blue", "Red", "Green", "Brown", "Orange"));
        colorChooser.getSelectionModel().selectFirst();
        colorChooser.getSelectionModel().selectedIndexProperty().addListener((ov, old, newval) -> {
            Number idx = (Number) newval;
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
            Number idx = (Number) newval;
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
}
