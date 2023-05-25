package dk.easv.gui.models;

import dk.easv.be.Doc;
import dk.easv.bll.DocumentLogic;
import dk.easv.bll.IDocumentLogic;
import dk.easv.gui.models.interfaces.IContentModel;
import dk.easv.helpers.CustomerType;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentNavigableMap;

public class ContentModel implements IContentModel {
    private static ContentModel INSTANCE;
    private final IDocumentLogic documentLogic = new DocumentLogic();
    private int documentId;
    private ConcurrentNavigableMap<Integer, Integer> contentMap;

    private BufferedImage image;

    private Doc document;

    private ContentModel() {

    }

    public static ContentModel getInstance() {
        if (ContentModel.INSTANCE == null) {
            ContentModel.INSTANCE = new ContentModel();
        }
        return ContentModel.INSTANCE;
    }

    @Override
    public void addText(int index, String content) throws SQLException {
        documentLogic.addText(documentId, index, content);
    }

    @Override
    public void addText(int contentId, int index, String content) throws SQLException {
        documentLogic.addText(documentId, contentId, index, content);
    }

    @Override
    public void addImage(int index, Image image) throws SQLException {
        documentLogic.addImage(documentId, index, image);
    }

    @Override
    public void addImage(int contentId, int index) throws SQLException {
        documentLogic.addImage(documentId, contentId, index);
    }

    @Override
    public void saveAsPDF(CustomerType type, String dest) throws SQLException, IOException {
        documentLogic.generatePDF(type, documentId, dest);
    }

    @Override
    public int getDocumentId() {
        return documentId;
    }

    @Override
    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    @Override
    public void loadAllContent(int documentId) throws SQLException {
        contentMap = documentLogic.loadAllContent(documentId);
    }

    @Override
    public ConcurrentNavigableMap<Integer, Integer> getContentMap() {
        try {
            loadAllContent(documentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentMap;
    }

    @Override
    public void setContentMap(ConcurrentNavigableMap<Integer, Integer> contentMap) {
        this.contentMap = contentMap;
    }


    @Override
    public void deleteContent(int id) throws SQLException {
        documentLogic.deleteContent(id);
    }

    @Override
    public void deleteMap(int id) throws SQLException {
        documentLogic.deleteMapping(documentId, id);
    }
}
