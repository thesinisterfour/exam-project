package dk.easv.gui.models;

import dk.easv.be.Document;
import dk.easv.bll.BLLFacade;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.DocumentLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class DocumentModel {
    private final BLLFacade bll = new CRUDLogic();
    private ObservableList<Document> obsAllDocuments;

    public DocumentModel() throws SQLException {
        obsAllDocuments= FXCollections.observableArrayList();
    }
    public int addDocument(Document document) throws SQLException {
        return bll.addDocument(document);
    }

    public int updateDocument(Document document) throws SQLException {
        return bll.updateDocument(document);
    }
    public Document getDocument(int id) throws SQLException {
        return bll.getDocument(id);
    }

    public ConcurrentMap<Integer, Document> getAllDocuments() throws SQLException {
        return bll.getAllDocuments();
    }
    public int deleteDocument(int id) throws SQLException {
        return bll.deleteDocument(id);
    }

    public ObservableList<Document> getObsAllDocuments() throws SQLException {
        setObsAllDocuments();
        return obsAllDocuments;
    }

    public void setObsAllDocuments() throws SQLException {
        ConcurrentMap<Integer, Document> allDocuments = getAllDocuments();
        this.obsAllDocuments.setAll(allDocuments.values());
    }
    public List<Document> getOldDocuments() throws SQLException {
        return new DocumentLogic().showOldDocuments();
    }
}
