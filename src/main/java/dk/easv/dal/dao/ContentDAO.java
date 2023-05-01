package dk.easv.dal.dao;

import dk.easv.dal.ConnectionManager;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContentDAO {

    private final ConnectionManager cm = new ConnectionManager();
    public void addText(int documentId, int index, String content) throws SQLException {
        try (Connection con = cm.getConnection()){
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

    public void addImage(int documentId, int index, Image image) throws SQLException{
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("Insert Into contents (image) OUTPUT inserted.id VALUES (?)");
            System.out.println(image.getUrl());
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
}
