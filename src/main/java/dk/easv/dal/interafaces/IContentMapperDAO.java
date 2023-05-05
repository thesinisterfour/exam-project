package dk.easv.dal.interafaces;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentNavigableMap;

public interface IContentMapperDAO {
    void deleteMapping(int documentId, int id) throws SQLException;

    ConcurrentNavigableMap<Integer, Integer> loadAllContent(int documentId) throws SQLException;
}
