package dk.easv.gui.models;

import dk.easv.be.Document;
import dk.easv.bll.BLLFacade;
import dk.easv.bll.CRUDLogic;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class DocumentModel {
    private final BLLFacade bll = new CRUDLogic();

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

}
