package dk.easv.gui.models;

import dk.easv.be.Document;
import dk.easv.bll.DocumentLogic;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ContentModel {
    private DocumentLogic documentLogic = new DocumentLogic();

    private int documentId;

    private static ContentModel INSTANCE;

    private ConcurrentSkipListMap<Integer, Integer> contentMap;

    private BufferedImage image;

    private Document document;
    private ContentModel() {

    }

    public static ContentModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContentModel();
        }
        return INSTANCE;
    }
    public void addText(int index, String content) throws SQLException {
        documentLogic.addText(documentId, index, content);
    }

    public void addText(int contentId, int index, String content) throws SQLException {
        documentLogic.addText(documentId, contentId, index, content);
    }

    public void addImage(int index, Image image) throws SQLException {
        documentLogic.addImage(documentId,  index, image);
    }

    public void addImage(int contentId, int index) throws SQLException {
        documentLogic.addImage(documentId, contentId, index);
    }

    public void saveAsPDF(File file, Parent root) {
       documentLogic.generatePDFFromImage(file, root);
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


    public void deleteContent(int id) throws SQLException{
        documentLogic.deleteContent(documentId, id);
    }
}
