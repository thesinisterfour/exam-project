package dk.easv.bll;

import dk.easv.dal.dao.ContentDAO;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentSkipListMap;

public class DocumentLogic {
    private ContentDAO contentDAO = new ContentDAO();

    public void addText(int documentId, int index, String content) throws SQLException {
        contentDAO.addText(documentId,  index, content);
    }

    public void addImage(int documentId, int index, Image image) throws SQLException {
        contentDAO.addImage(documentId,  index, image);
    }

    public ConcurrentSkipListMap<Integer, Integer> loadAllContent(int documentId) throws SQLException {
        return contentDAO.loadAllContent(documentId);
    }

    public Object getContent(Integer contentId) throws SQLException{
        return contentDAO.getContent(contentId);
    }
}
