package dk.easv.gui.models;

import dk.easv.be.Doc;
import dk.easv.bll.DocumentLogic;
import dk.easv.bll.IDocumentLogic;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentNavigableMap;

public class ContentModel {
    private final IDocumentLogic documentLogic = new DocumentLogic();

    private int documentId;

    private static ContentModel INSTANCE;

    private ConcurrentNavigableMap<Integer, Integer> contentMap;

    private BufferedImage image;

    private Doc document;
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

    public void saveAsPDF(String dest) throws SQLException, IOException {
        ICRUDDao<Doc> docDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
       documentLogic.generatePDF(docDao.get(documentId), dest);
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

    public ConcurrentNavigableMap<Integer, Integer> getContentMap() {
        try {
            loadAllContent(documentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentMap;
    }

    public void setContentMap(ConcurrentNavigableMap<Integer, Integer> contentMap) {
        this.contentMap = contentMap;
    }


    public void deleteContent(int id) throws SQLException{
        documentLogic.deleteContent(id);
    }

    public void deleteMap(int id) throws SQLException{
        documentLogic.deleteMapping(documentId, id);
    }
}
