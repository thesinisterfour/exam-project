package dk.easv.gui.models;

import dk.easv.bll.DocumentLogic;
import javafx.scene.image.Image;

import java.sql.SQLException;

public class ContentModel {
    private DocumentLogic documentLogic = new DocumentLogic();
    public void addText(int documentId, int index, String content) throws SQLException {
        documentLogic.addText(documentId,  index, content);
    }

    public void addImage(int documentId, int index, Image image) throws SQLException {
        documentLogic.addImage(documentId,  index, image);
    }
}
