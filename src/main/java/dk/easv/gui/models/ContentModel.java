package dk.easv.gui.models;

import dk.easv.bll.DocumentLogic;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ContentModel {
    private DocumentLogic documentLogic = new DocumentLogic();

    private int documentId;

    private static ContentModel INSTANCE;

    private ConcurrentSkipListMap<Integer, Integer> contentMap;

    private ContentModel() {

    }

    public static ContentModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContentModel();
        }
        return INSTANCE;
    }
    public void addText(int documentId, int index, String content) throws SQLException {
        documentLogic.addText(documentId,  index, content);
    }

    public void addImage(int documentId, int index, Image image) throws SQLException {
        documentLogic.addImage(documentId,  index, image);
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public void loadAllContent(int documentId) throws SQLException {
        contentMap = documentLogic.loadAllContent(documentId);
    }

    public ConcurrentSkipListMap<Integer, Integer> getContentMap() {
        try {
            loadAllContent(documentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentMap;
    }

    public void setContentMap(ConcurrentSkipListMap<Integer, Integer> contentMap) {
        this.contentMap = contentMap;
    }


}
