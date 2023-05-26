package dk.easv.dal.dao;

import dk.easv.be.Content;
import dk.easv.dal.connectionManager.ConnectionManagerFactory;
import dk.easv.dal.connectionManager.IConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.dal.interafaces.IContentMapperDAO;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ContentDAO implements ICRUDDao<Content>, IContentMapperDAO {

    private final IConnectionManager cm = ConnectionManagerFactory.createConnectionManager();

    @Override
    public int add(Content content) throws SQLException {
        if (content.getImage() != null) {
            if (content.getId() != 0) {
                // if the image is already in the database update the index
                return addImage(content.getDocumentId(), content.getId(), content.getIndex());
            } else {
                // if the image is not in the database add it to contents table and its index into document_contents table
                return addImage(content.getDocumentId(), content.getIndex(), content.getImage());
            }
        } else {
            if (content.getId() != 0) {
                // same procedure as above except with also updating the content
                return addText(content.getDocumentId(), content.getId(), content.getIndex(), content.getText());
            } else {
                return addText(content.getDocumentId(), content.getIndex(), content.getText());
            }
        }
    }

    @Override
    public int update(Content object) throws SQLException {
        return 0;
    }

    @Override
    public Content get(int id) throws SQLException {
        return getContent(id);
    }

    @Override
    public ConcurrentMap<Integer, Content> getAll() throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM contents");
            ResultSet rs = ps.executeQuery();
            ConcurrentMap<Integer, Content> map = new ConcurrentHashMap<>();
            while (rs.next()) {
                int contentId = rs.getInt("id");
                if (rs.getString("text") != null) {
                    map.put(contentId, new Content(contentId, rs.getString("text")));
                } else {
                    map.put(contentId, new Content(contentId, new Image(rs.getBinaryStream("image"))));
                }
            }
            return map;
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM contents WHERE id = ?");
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }


    private int addText(int documentId, int index, String content) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("Insert Into contents (text) OUTPUT inserted.id VALUES (?)");
            ps.setString(1, content);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                ps = con.prepareStatement("Insert Into document_contents (document_id, content_id, content_index) VALUES (?, ?, ?)");
                ps.setInt(1, documentId);
                ps.setInt(2, id);
                ps.setInt(3, index);
                ps.executeUpdate();
            }
        }
        return documentId;
    }

    private int addText(int documentId, int contentId, int index, String content) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE contents SET text = ? OUTPUT inserted.id WHERE id = ?");
            ps.setString(1, content);
            ps.setInt(2, contentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                if (id == contentId) {
                    ps = con.prepareStatement("UPDATE document_contents SET content_index = ? WHERE document_id = ? AND content_id = ?");
                    ps.setInt(1, index);
                    ps.setInt(2, documentId);
                    ps.setInt(3, contentId);
                    ps.executeUpdate();
                }
            }
        }
        return documentId;
    }

    private int addImage(int documentId, int index, Image image) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("Insert Into contents (image) OUTPUT inserted.id VALUES (?)");
            FileInputStream inputStream = new FileInputStream(image.getUrl());
            ps.setBinaryStream(1, inputStream);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                ps = con.prepareStatement("Insert Into document_contents (document_id, content_id, content_index) VALUES (?, ?, ?)");
                ps.setInt(1, documentId);
                ps.setInt(2, id);
                ps.setInt(3, index);
                ps.executeUpdate();
            }
        } catch (FileNotFoundException e) {
            throw new SQLException("File not found");
        }
        return documentId;
    }

    private int addImage(int documentId, int contentId, int index) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE document_contents SET content_index = ? WHERE document_id = ? AND content_id = ?");
            ps.setInt(1, index);
            ps.setInt(2, documentId);
            ps.setInt(3, contentId);
            ps.executeUpdate();
        }
        return documentId;
    }

    @Override
    public ConcurrentNavigableMap<Integer, Integer> loadAllContent(int documentId) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE documents SET date_last_opened = CURRENT_TIMESTAMP WHERE document_id = ?");
            ps.setInt(1, documentId);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT * FROM document_contents INNER JOIN contents ON document_contents.content_id = contents.id WHERE document_id = ? ORDER BY content_index ASC");
            ps.setInt(1, documentId);
            ResultSet rs = ps.executeQuery();
            ConcurrentSkipListMap<Integer, Integer> cslm = new ConcurrentSkipListMap<>();
            while (rs.next()) {
                int index = rs.getInt("content_index");
                int id = rs.getInt("id");
                cslm.put(index, id);
            }
            return cslm;
        }
    }

    private Content getContent(Integer contentId) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM contents WHERE id = ?");
            ps.setInt(1, contentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("text") != null) {
                    return new Content(contentId, rs.getString("text"));
                } else {
                    return new Content(contentId, new Image(rs.getBinaryStream("image")));
                }
            } else {
                return null;
            }
        }
    }


    @Override
    public void deleteMapping(int documentId, int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM document_contents WHERE content_id = ? AND document_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, documentId);
            ps.executeUpdate();
        }
    }
}
