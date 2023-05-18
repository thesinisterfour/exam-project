package dk.easv.gui.models.interfaces;

import dk.easv.be.Doc;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public interface IDocumentModel {
    int addDocument(Doc document) throws SQLException;

    int updateDocument(Doc document) throws SQLException;

    Doc getDocument(int id) throws SQLException;

    ConcurrentMap<Integer, Doc> getAllDocuments() throws SQLException;

    int deleteDocument(int id) throws SQLException;

    ObservableList<Doc> getObsAllDocuments() throws SQLException;

    void setObsAllDocuments() throws SQLException;

    List<Doc> getOldDocuments() throws SQLException;

    void setObsProjectDocuments(int id) throws SQLException;

    ObservableList<Doc> getObsProjectDocuments();

    ObservableList<Doc> getObsDocuments();
}
