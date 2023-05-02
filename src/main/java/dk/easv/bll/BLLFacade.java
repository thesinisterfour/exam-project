package dk.easv.bll;

import dk.easv.be.City;
import dk.easv.be.Document;
import dk.easv.be.User;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface BLLFacade {
    int addUser(User user) throws SQLException;

    User checkForUser(String username, String password) throws SQLException;

    int addDocument(Document document) throws SQLException;
    int updateDocument(Document document) throws SQLException;
    Document getDocument(int id) throws SQLException;
    ConcurrentMap<Integer, Document> getAllDocuments() throws SQLException;
    int deleteDocument(int id) throws SQLException;

    City getCity(int zipcode) throws SQLException;
}
