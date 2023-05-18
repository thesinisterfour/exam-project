package dk.easv.gui.models;

import dk.easv.bll.CanvasLogic;
import dk.easv.gui.models.interfaces.ICanvasModel;
import javafx.scene.canvas.Canvas;

import java.io.File;

public class CanvasModel implements ICanvasModel {

    private static CanvasModel INSTANCE;

    private CanvasLogic canvasLogic = new CanvasLogic();

    @Override
    public void generateImage(File file, Canvas canvas) {
        canvasLogic.generateImage(file, canvas);
    }

    public static CanvasModel getInstance() {
        if (CanvasModel.INSTANCE == null) {
            CanvasModel.INSTANCE = new CanvasModel();
        }
        return CanvasModel.INSTANCE;
    }
}
