package dk.easv.gui.models;

import dk.easv.be.Doc;
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
    private ObservableList<Doc> obsAllDocuments;

    public DocumentModel() throws SQLException {
        obsAllDocuments= FXCollections.observableArrayList();
    }
    public int addDocument(Doc document) throws SQLException {
        return bll.addDocument(document);
    }

    public int updateDocument(Doc document) throws SQLException {
        return bll.updateDocument(document);
    }
    public Doc getDocument(int id) throws SQLException {
        return bll.getDocument(id);
    }

    public ConcurrentMap<Integer, Doc> getAllDocuments() throws SQLException {
        return bll.getAllDocuments();
    }
    public int deleteDocument(int id) throws SQLException {
        return bll.deleteDocument(id);
    }

    public ObservableList<Doc> getObsAllDocuments() throws SQLException {
        setObsAllDocuments();
        return obsAllDocuments;
    }

    public void setObsAllDocuments() throws SQLException {
        ConcurrentMap<Integer, Doc> allDocuments = getAllDocuments();
        this.obsAllDocuments.setAll(allDocuments.values());
    }
    public List<Doc> getOldDocuments() throws SQLException {
        return new DocumentLogic().showOldDocuments();
    }
}
