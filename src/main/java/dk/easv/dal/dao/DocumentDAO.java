package dk.easv.dal.dao;

import dk.easv.be.Document;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DocumentDAO implements ICRUDDao<Document> {
    private final ConnectionManager cm;

    public DocumentDAO(){
        cm = new ConnectionManager();
    }

    @Override
    public int add(Document document) throws SQLException {
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("Insert Into documents (document_name, date_created, date_last_opened, description) OUTPUT inserted.document_id VALUES (?, ?, ?, ?)");
            ps.setString(1, document.getName());
            ps.setDate(2, Date.valueOf(document.getCreationDate()));
            ps.setDate(3, Date.valueOf(document.getLastView()));
            ps.setString(4, document.getDescription());

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
    }

    @Override
    public int update(Document document) throws SQLException {
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE documents SET document_name = ?, date_created = ?, date_last_opened = ?, description = ? WHERE document_id = ?");
            ps.setString(1, document.getName());
            ps.setDate(2, Date.valueOf(document.getCreationDate()));
            ps.setDate(3, Date.valueOf(document.getLastView()));
            ps.setString(4, document.getDescription());
            ps.setInt(5, document.getId());

            return ps.executeUpdate();
        }
    }

    @Override
    public Document get(int id) throws SQLException {
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT document_id, document_name, date_created, date_last_opened, description FROM documents WHERE document_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int documentID = rs.getInt("document_id");
                String documentName = rs.getString("document_name");
                LocalDate dateCreated = rs.getDate("date_created").toLocalDate();
                LocalDate dateLastOpened = rs.getDate("date_last_opened").toLocalDate();
                String description = rs.getString("description");

                return new Document(documentID, documentName, dateCreated, dateLastOpened, description);
            } else {
                return null;
            }
        }
    }

    @Override
    public ConcurrentMap<Integer, Document> getAll() throws SQLException {
        ConcurrentMap<Integer, Document> map = new ConcurrentHashMap<>();
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT document_id, document_name, date_created, date_last_opened, description FROM documents");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int documentID = rs.getInt("document_id");
                String documentName = rs.getString("document_name");
                LocalDate dateCreated = rs.getDate("date_created").toLocalDate();
                Date dateLastOpened = rs.getDate("date_last_opened");
                LocalDate dateLastOpenedLocalDate = dateLastOpened == null ? null : dateLastOpened.toLocalDate();
                String description = rs.getString("description");
                Document doc = new Document(documentID, documentName, dateCreated, dateLastOpenedLocalDate, description);
                map.put(documentID, doc);
            }
            return map;
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM documents WHERE document_id = ?");
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
}
