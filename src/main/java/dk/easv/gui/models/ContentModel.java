package dk.easv.gui.models;

import dk.easv.be.Content;
import dk.easv.bll.DocumentLogic;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.util.List;

public class ContentModel {
    private DocumentLogic documentLogic = new DocumentLogic();

    private int documentId;

    private static ContentModel INSTANCE;

    private List<Content> contentList;

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

    public void loadContent(int documentId) throws SQLException {
        contentList = documentLogic.loadContent(documentId);
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }
}
