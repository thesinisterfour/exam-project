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

public class DocumentLogic {
    private ContentDAO contentDAO = new ContentDAO();

    public void addText(int documentId, int index, String content) throws SQLException {
        contentDAO.addText(documentId,  index, content);
    }

    public void addImage(int documentId, int index, Image image) throws SQLException {
        contentDAO.addImage(documentId,  index, image);
    }

    public List<Content> loadContent(int documentId) throws SQLException {
        return contentDAO.loadContent(documentId);
    }
    public List<String> showDocumentName() throws SQLException {
        LocalDateTime currentDate = LocalDateTime.now();
        DocumentDAO documentDAO = new DocumentDAO();
        ConcurrentMap<Integer, Document> documents = documentDAO.getAll();
        List<String> documentNames = documents.values()
                .stream()
                .filter(doc -> ChronoUnit.MONTHS.between(
                        doc.getCreationDate().atStartOfDay(), currentDate) >= 48)
                .map(Document::getName)
                .collect(Collectors.toList());

        return documentNames;
    }
}
