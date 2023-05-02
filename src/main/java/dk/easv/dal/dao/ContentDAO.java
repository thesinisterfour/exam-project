package dk.easv.dal.dao;

import dk.easv.dal.ConnectionManager;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ContentDAO {

    private final ConnectionManager cm = new ConnectionManager();

    public void addText(int documentId, int index, String content) throws SQLException {
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
    }

    public void addText(int documentId, int contentId, int index, String content) throws SQLException {
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
                } else {
                    throw new SQLException("bruh");
                }
            }
        }
    }

    public void addImage(int documentId, int index, Image image) throws SQLException {
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
            throw new RuntimeException(e);
        }
    }

    public void addImage(int documentId, int contentId, int index) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE document_contents SET content_index = ? WHERE document_id = ? AND content_id = ?");
            ps.setInt(1, index);
            ps.setInt(2, documentId);
            ps.setInt(3, contentId);
            ps.executeUpdate();
        }
    }

    public ConcurrentSkipListMap<Integer, Integer> loadAllContent(int documentId) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM document_contents INNER JOIN contents ON document_contents.content_id = contents.id WHERE document_id = ? ORDER BY content_index ASC");
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

    public Object getContent(Integer contentId) throws SQLException {
        try (Connection con = cm.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM contents WHERE id = ?");
            ps.setInt(1, contentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("text") != null) {
                    return rs.getString("text");
                } else {
                    return new Image(rs.getBinaryStream("image"));
                }
            } else {
                return null;
            }
        }
    }


}
