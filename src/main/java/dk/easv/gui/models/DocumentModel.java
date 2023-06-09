package dk.easv.gui.models;

import dk.easv.be.Doc;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.DocumentLogic;
import dk.easv.bll.ICRUDLogic;
import dk.easv.bll.IDocumentLogic;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class DocumentModel implements IDocumentModel {
    private static IDocumentModel INSTANCE;
    private final ICRUDLogic bll = new CRUDLogic();
    private final ObservableList<Doc> obsDocuments;

    /**
     * This is a constructor that initializes an empty ObservableList named
     * obsAllDocuments and calls a method named setObsAllDocuments()
     * to populate the list with data from the database.
     *
     * @throws SQLException
     */
    private DocumentModel() throws SQLException {
        obsDocuments = FXCollections.observableArrayList();
        setObsAllDocuments();
    }

    public static IDocumentModel getInstance() throws SQLException {
        if (INSTANCE == null) {
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
        int i = bll.deleteDocument(id);
        setObsAllDocuments();
        return i;
    }

    @Override
    public void setObsAllDocuments() throws SQLException {
        IProjectModel model = ProjectModel.getInstance();
        if (model.getSelectedProjectId() == 0) {
            ConcurrentMap<Integer, Doc> allDocuments = getAllDocuments();
            this.obsDocuments.setAll(allDocuments.values());
        } else {
            setObsProjectDocuments(model.getSelectedProjectId());
        }

    }


    @Override
    public List<Doc> getOldDocuments() throws SQLException {
        IDocumentLogic logic = new DocumentLogic();
        return logic.showOldDocuments();
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
