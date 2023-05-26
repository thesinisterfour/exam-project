package dk.easv.gui.models.interfaces;

import dk.easv.helpers.CustomerType;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentNavigableMap;

public interface IContentModel {

    /**
     * Adds a text to the database
     *
     * @param index
     * @param content
     * @throws SQLException
     */
    void addText(int index, String content) throws SQLException;

    /**
     * Updates the index of the text and the text in the database
     *
     * @param contentId
     * @param index
     * @param content
     * @throws SQLException
     */
    void addText(int contentId, int index, String content) throws SQLException;

    /**
     * Adds an image to the database
     *
     * @param index
     * @param image
     * @throws SQLException
     */
    void addImage(int index, Image image) throws SQLException;

    /**
     * Updates the index of the image in the database
     *
     * @param contentId
     * @param index
     * @throws SQLException
     */
    void addImage(int contentId, int index) throws SQLException;

    /**
     * Saves the document as a PDF
     *
     * @param type Public or Private customer
     * @param dest The destination of the PDF
     * @throws SQLException
     * @throws IOException
     */
    void saveAsPDF(CustomerType type, String dest) throws SQLException, IOException;

    /**
     * @return The id of the document
     */
    int getDocumentId();

    /**
     * @param documentId The id of the document
     */
    void setDocumentId(int documentId);

    /**
     * Loads all the content from the database
     *
     * @param documentId The id of the document
     * @throws SQLException
     */
    void loadAllContent(int documentId) throws SQLException;

    ConcurrentNavigableMap<Integer, Integer> getContentMap() throws SQLException;

    void setContentMap(ConcurrentNavigableMap<Integer, Integer> contentMap);

    void deleteContent(int id) throws SQLException;

    /**
     * Deletes the mapping from the database
     *
     * @param id The id of the content
     * @throws SQLException
     */
    void deleteMap(int id) throws SQLException;
}
