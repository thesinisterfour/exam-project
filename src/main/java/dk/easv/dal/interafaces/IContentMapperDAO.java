package dk.easv.dal.interafaces;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentNavigableMap;

public interface IContentMapperDAO {

    /**
     * This method is used to delete a content mapping based on the document ID and the mapping ID.
     * It takes two integer parameters: documentId and id of the mapping.
     * @throws SQLException if there's an error during the database operation.
     */
    void deleteMapping(int documentId, int id) throws SQLException;


    /**
     * This method is used to load all content mappings for a specific document ID.
     * It takes an integer parameter documentId representing the ID of the document.
     * @returns a ConcurrentNavigableMap where the keys represent the mapping IDs and the values represent the corresponding content.
     * @throws SQLException if there's an error during the database operation.
     */
    ConcurrentNavigableMap<Integer, Integer> loadAllContent(int documentId) throws SQLException;
}
