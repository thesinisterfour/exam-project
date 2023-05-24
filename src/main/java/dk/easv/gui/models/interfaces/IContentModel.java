package dk.easv.gui.models.interfaces;

import dk.easv.helpers.CustomerType;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentNavigableMap;

public interface IContentModel {

    void addText(int index, String content) throws SQLException;

    void addText(int contentId, int index, String content) throws SQLException;

    void addImage(int index, Image image) throws SQLException;

    void addImage(int contentId, int index) throws SQLException;

    void saveAsPDF(CustomerType aPrivate, String dest) throws SQLException, IOException;

    int getDocumentId();

    void setDocumentId(int documentId);

    void loadAllContent(int documentId) throws SQLException;

    ConcurrentNavigableMap<Integer, Integer> getContentMap();

    void setContentMap(ConcurrentNavigableMap<Integer, Integer> contentMap);

    void deleteContent(int id) throws SQLException;

    void deleteMap(int id) throws SQLException;
}
