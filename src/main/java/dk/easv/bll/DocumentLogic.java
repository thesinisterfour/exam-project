package dk.easv.bll;

import dk.easv.be.Content;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.dao.ContentDAO;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.dal.interafaces.IContentMapperDAO;
import dk.easv.helpers.DAOType;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class DocumentLogic {
    private final ICRUDDao<Content> contentDAO = CRUDDAOFactory.getDao(DAOType.CONTENT_DAO);
    private final IContentMapperDAO contentMapper = new ContentDAO();

    public void addText(int documentId, int index, String text) throws SQLException {
        contentDAO.add(new Content(documentId, index, text));
    }

    public void addText(int documentId, int contentId, int index, String content) throws SQLException {
        contentDAO.add(new Content(documentId, contentId, index, content));
    }

    public void addImage(int documentId, int index, Image image) throws SQLException {
        contentDAO.add(new Content(documentId,  index, image));
    }
    public void addImage(int documentId, int contentId, int index) throws SQLException {
        contentDAO.add(new Content(documentId, contentId, index));
    }

    public ConcurrentMap<Integer, Content> getAllContent() throws SQLException {
        return contentDAO.getAll();
    }

    public Content getContent(int contentId) throws SQLException{
        return contentDAO.get(contentId);
    }


    public void deleteContent(int id) throws SQLException {
        contentDAO.delete(id);
    }

    public void deleteMapping(int documentId, int id) throws SQLException {
        contentMapper.deleteMapping(documentId, id);
    }

    public ConcurrentNavigableMap<Integer, Integer> loadAllContent(int documentId) throws SQLException {
        return contentMapper.loadAllContent(documentId);
    }
}
