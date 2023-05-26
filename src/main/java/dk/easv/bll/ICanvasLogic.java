package dk.easv.bll;

import javafx.scene.canvas.Canvas;

import java.io.File;
import java.io.IOException;

public interface ICanvasLogic {
    void generateImage(File file, Canvas canvas) throws IOException;
}
