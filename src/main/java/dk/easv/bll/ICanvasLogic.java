package dk.easv.bll;

import javafx.scene.canvas.Canvas;

import java.io.File;

public interface ICanvasLogic {
    void generateImage(File file, Canvas canvas);
}
