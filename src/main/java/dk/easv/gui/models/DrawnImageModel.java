package dk.easv.gui.models;

import dk.easv.gui.controllers.CanvasController;
import dk.easv.gui.controllers.DocumentViewController;
import javafx.scene.image.Image;

public class DrawnImageModel {
    private static Image image;
    private static DocumentViewController documentViewController;

    public static void setDrawnImage(Image drawnImage) {
        image = drawnImage;
        documentViewController.setDrawnImage(image);
    }

    public static void setDocumentViewController(DocumentViewController documentViewController) {
        DrawnImageModel.documentViewController = documentViewController;
    }
}
