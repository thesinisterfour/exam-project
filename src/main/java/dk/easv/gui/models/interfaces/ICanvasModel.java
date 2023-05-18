package dk.easv.gui.models.interfaces;

import javafx.scene.canvas.Canvas;

import java.io.File;

public interface ICanvasModel {
    void generateImage(File file, Canvas canvas);
}
