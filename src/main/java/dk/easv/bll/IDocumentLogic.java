package dk.easv.bll;

import dk.easv.be.Content;
import dk.easv.be.Doc;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

public interface IDocumentLogic {
    void addText(int documentId, int index, String text) throws SQLException;

    void addText(int documentId, int contentId, int index, String content) throws SQLException;

    void addImage(int documentId, int index, Image image) throws SQLException;

    void addImage(int documentId, int contentId, int index) throws SQLException;

    ConcurrentMap<Integer, Content> getAllContent() throws SQLException;

    Content getContent(int contentId) throws SQLException;

    void deleteContent(int id) throws SQLException;

    void deleteMapping(int documentId, int id) throws SQLException;

    ConcurrentNavigableMap<Integer, Integer> loadAllContent(int documentId) throws SQLException;

    List<Doc> showOldDocuments() throws SQLException;

    void generatePDF(Doc doc, String dest) throws IOException, SQLException;
}
