package dk.easv.bll;

import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CanvasLogic implements ICanvasLogic {
    @Override
    public void generateImage(File file, Canvas canvas) throws IOException {
        if (file != null) {
            double canvasWidth = canvas.getWidth();
            double canvasHeight = canvas.getHeight();

            WritableImage writableImage = new WritableImage((int) canvasWidth, (int) canvasHeight);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.WHITE); // Set white background
            canvas.snapshot(params, writableImage);

            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
            BufferedImage imageWithWhiteBackground = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = imageWithWhiteBackground.createGraphics();
            graphics.setColor(java.awt.Color.WHITE);
            graphics.fillRect(0, 0, imageWithWhiteBackground.getWidth(), imageWithWhiteBackground.getHeight());
            graphics.drawImage(bufferedImage, 0, 0, null);
            graphics.dispose();

            ImageIO.write(imageWithWhiteBackground, "png", file);
        }
    }
}
