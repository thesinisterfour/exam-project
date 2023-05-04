package dk.easv.bll;

import dk.easv.be.Content;
import dk.easv.be.Document;
import dk.easv.dal.dao.ContentDAO;
import dk.easv.dal.dao.DocumentDAO;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentSkipListMap;

public class DocumentLogic {
    private ContentDAO contentDAO = new ContentDAO();

    public void addText(int documentId, int index, String content) throws SQLException {
        contentDAO.addText(documentId,  index, content);
    }

    public void addText(int documentId, int contentId, int index, String content) throws SQLException {
        contentDAO.addText(documentId, contentId, index, content);
    }

    public void addImage(int documentId, int index, Image image) throws SQLException {
        contentDAO.addImage(documentId,  index, image);
    }
    public void addImage(int documentId, int contentId, int index) throws SQLException {
        contentDAO.addImage(documentId, contentId, index);
    }

    public ConcurrentSkipListMap<Integer, Integer> loadAllContent(int documentId) throws SQLException {
        return contentDAO.loadAllContent(documentId);
    }

    public Object getContent(Integer contentId) throws SQLException{
        return contentDAO.getContent(contentId);
    }


    public void deleteContent(int documentId, int id) throws SQLException {
        contentDAO.deleteContent(documentId, id);
    }
    public List<Document> showOldDocuments() throws SQLException {
        LocalDateTime currentDate = LocalDateTime.now();
        DocumentDAO documentDAO = new DocumentDAO();
        ConcurrentMap<Integer, Document> documents = documentDAO.getAll();
        List<Document> oldDocuments = documents.values()
                .stream()
                .filter(doc -> ChronoUnit.MONTHS.between(
                        doc.getCreationDate().atStartOfDay(), currentDate) >= 48)
                .collect(Collectors.toList());

        return oldDocuments;
    }
}
