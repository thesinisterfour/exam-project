package dk.easv.gui.models;

import dk.easv.bll.IMappingLogic;
import dk.easv.bll.MappingLogic;
import dk.easv.gui.models.interfaces.IDocumentMapperModel;

import java.sql.SQLException;

public class DocumentMapperModel implements IDocumentMapperModel {
    private final IMappingLogic mappingLogic = new MappingLogic();
    @Override
    public int addDocumentToProject(int projectID, int documentID) throws SQLException {
        return mappingLogic.addDocumentToProject(projectID, documentID);
    }
}
