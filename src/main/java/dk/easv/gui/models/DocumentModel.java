package dk.easv.gui.models;

import dk.easv.be.Doc;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.DocumentLogic;
import dk.easv.bll.ICRUDLogic;
import dk.easv.gui.models.interfaces.IDocumentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class DocumentModel implements IDocumentModel {
    private static DocumentModel INSTANCE;
    private final ICRUDLogic bll = new CRUDLogic();
    private final ObservableList<Doc> obsAllDocuments;
    private final ObservableList<Doc> obsProjectDocuments;
    private final ObservableList<Doc> obsDocuments;

    /**
     *  This is a constructor that initializes an empty ObservableList named
     *  obsAllDocuments and calls a method named setObsAllDocuments()
     *  to populate the list with data from the database.
     * @throws SQLException
     */
    private DocumentModel() throws SQLException {
        obsAllDocuments = FXCollections.observableArrayList();
        obsProjectDocuments = FXCollections.observableArrayList();
        obsDocuments = FXCollections.observableArrayList();
        setObsAllDocuments();
        this.obsDocuments.setAll(obsAllDocuments);
    }

    public static DocumentModel getInstance() throws SQLException {
        if (INSTANCE == null){
            INSTANCE = new DocumentModel();
        }
        return INSTANCE;
    }

    @Override
    public int addDocument(Doc document) throws SQLException {
        return bll.addDocument(document);
    }

    @Override
    public int updateDocument(Doc document) throws SQLException {
        return bll.updateDocument(document);
    }

    @Override
    public Doc getDocument(int id) throws SQLException {
        return bll.getDocument(id);
    }

    @Override
    public ConcurrentMap<Integer, Doc> getAllDocuments() throws SQLException {
        return bll.getAllDocuments();
    }

    @Override
    public int deleteDocument(int id) throws SQLException {
        setObsAllDocuments();
        return bll.deleteDocument(id);
    }

    @Override
    public ObservableList<Doc> getObsAllDocuments() throws SQLException {
        setObsAllDocuments();
        return obsAllDocuments;
    }

    @Override
    public void setObsAllDocuments() throws SQLException {
        ConcurrentMap<Integer, Doc> allDocuments = getAllDocuments();
        this.obsAllDocuments.setAll(allDocuments.values());
    }

    @Override
    public List<Doc> getOldDocuments() throws SQLException {
        return new DocumentLogic().showOldDocuments();
    }

    @Override
    public ObservableList<Doc> getObsProjectDocuments() {
        return obsProjectDocuments;
    }

    @Override
    public void setObsProjectDocuments(int id) throws SQLException {
        ConcurrentMap<Integer, Doc> projectDocuments = new DocumentLogic().getProjectDocuments(id);
        this.obsDocuments.setAll(projectDocuments.values());
    }

    @Override
    public ObservableList<Doc> getObsDocuments() {
        return obsDocuments;
    }
}
