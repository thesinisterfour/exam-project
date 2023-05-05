package dk.easv.bll;

import dk.easv.be.Content;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.be.Content;
import dk.easv.be.Document;
import dk.easv.dal.dao.ContentDAO;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.dal.interafaces.IContentMapperDAO;
import dk.easv.helpers.DAOType;
import dk.easv.dal.dao.DocumentDAO;
import dk.easv.helpers.AlertHelper;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class DocumentLogic {
    private final ICRUDDao<Content> contentDAO = CRUDDAOFactory.getDao(DAOType.CONTENT_DAO);
    private final IContentMapperDAO contentMapper = new ContentDAO();

    public void addText(int documentId, int index, String text) throws SQLException {
        contentDAO.add(new Content(documentId, index, text));
    }

    public void addText(int documentId, int contentId, int index, String content) throws SQLException {
        contentDAO.add(new Content(documentId, contentId, index, content));
    }

    public void addImage(int documentId, int index, Image image) throws SQLException {
        contentDAO.add(new Content(documentId,  index, image));
    }
    public void addImage(int documentId, int contentId, int index) throws SQLException {
        contentDAO.add(new Content(documentId, contentId, index));
    }

    public ConcurrentMap<Integer, Content> getAllContent() throws SQLException {
        return contentDAO.getAll();
    }

    public Content getContent(int contentId) throws SQLException{
        return contentDAO.get(contentId);
    }


    public void deleteContent(int id) throws SQLException {
        contentDAO.delete(id);
    }

    public void deleteMapping(int documentId, int id) throws SQLException {
        contentMapper.deleteMapping(documentId, id);
    }

    public ConcurrentNavigableMap<Integer, Integer> loadAllContent(int documentId) throws SQLException {
        return contentMapper.loadAllContent(documentId);
    }
    public List<Document> showOldDocuments() throws SQLException {
        LocalDateTime currentDate = LocalDateTime.now();
        DocumentDAO documentDAO = new DocumentDAO();
        ConcurrentMap<Integer, Document> documents = documentDAO.getAll();
        List<Document> oldDocuments = documents.values()
                .stream()
                .filter(doc -> ChronoUnit.MONTHS.between(
                        doc.getCreationDate().atStartOfDay(), currentDate) >= 48)
                .collect(Collectors.toList());

        return oldDocuments;
    }



    public void generatePDFFromImage(File file, Parent root){
        if (file != null) {
            try {
                WritableImage snapshot = root.snapshot(new SnapshotParameters(), null);
                PDImageXObject pdImage = LosslessFactory.createFromImage(
                        new PDDocument(), SwingFXUtils.fromFXImage(snapshot, null));
                PDDocument document = new PDDocument();
                PDRectangle pageSize = new PDRectangle(500, 700); // width=500, height=700
                PDPage page = new PDPage(pageSize);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
                contentStream.close();
                document.addPage(page);
                document.save(file);
                document.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
