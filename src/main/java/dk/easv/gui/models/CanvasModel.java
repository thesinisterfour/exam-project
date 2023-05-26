package dk.easv.gui.models;

import dk.easv.bll.CanvasLogic;
import dk.easv.bll.ICanvasLogic;
import dk.easv.gui.models.interfaces.ICanvasModel;
import javafx.scene.canvas.Canvas;

import java.io.File;
import java.io.IOException;

public class CanvasModel implements ICanvasModel {

    private static CanvasModel INSTANCE;

    private final ICanvasLogic canvasLogic = new CanvasLogic();

    public static CanvasModel getInstance() {
        if (CanvasModel.INSTANCE == null) {
            CanvasModel.INSTANCE = new CanvasModel();
        }
        return CanvasModel.INSTANCE;
    }

    @Override
    public void generateImage(File file, Canvas canvas) throws IOException {
        canvasLogic.generateImage(file, canvas);
    }
}
