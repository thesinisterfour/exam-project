package dk.easv.gui.models.interfaces;

import java.sql.SQLException;

public interface IDocumentMapperModel {
    int addDocumentToProject(int projectID, int documentID) throws SQLException;
}
