package dk.easv.dal.dao;

import dk.easv.dal.connectionManager.ConnectionManagerFactory;
import dk.easv.dal.connectionManager.IConnectionManager;
import dk.easv.dal.interafaces.IDocumentMapperDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DocumentMapperDAO implements IDocumentMapperDAO {
    private final IConnectionManager cm = ConnectionManagerFactory.createConnectionManager();
    @Override
    public int addDocumentToProject(int projectID, int documentID) throws SQLException {
        try (Connection connection = cm.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO dbo.project_documents (project_id, document_id) VALUES (?, ?);");
            ps.setInt(1, projectID);
            ps.setInt(2, documentID);

            return ps.executeUpdate();
        }
    }
}
