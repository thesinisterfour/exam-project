package dk.easv.gui.models.interfaces;

import javafx.scene.canvas.Canvas;

import java.io.File;
import java.io.IOException;

public interface ICanvasModel {
    void generateImage(File file, Canvas canvas) throws IOException;
}
