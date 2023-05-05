package dk.easv.bll;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import dk.easv.be.Content;
import dk.easv.be.Doc;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.dao.ContentDAO;
import dk.easv.dal.dao.DocumentDAO;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.dal.interafaces.IContentMapperDAO;
import dk.easv.helpers.DAOType;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.stream.Collectors;

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
    public List<Doc> showOldDocuments() throws SQLException {
        LocalDateTime currentDate = LocalDateTime.now();
        DocumentDAO documentDAO = new DocumentDAO();
        ConcurrentMap<Integer, Doc> documents = documentDAO.getAll();
        List<Doc> oldDocuments = documents.values()
                .stream()
                .filter(doc -> ChronoUnit.MONTHS.between(
                        doc.getCreationDate().atStartOfDay(), currentDate) >= 48)
                .collect(Collectors.toList());

        return oldDocuments;
    }

    public void generatePDF(Doc doc, String dest) throws IOException, SQLException {
        ConcurrentNavigableMap<Integer, Integer> contentMap = loadAllContent(doc.getId());
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        document.setFont(font);
        document.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(new Paragraph(doc.getName()).setFont(bold).setFontSize(32));
        document.add(new Paragraph(doc.getDescription()));
        document.add(new Paragraph("Document created on: " + doc.getCreationDate().toString()));
        document.add(new Paragraph("PDF generated on: " + LocalDateTime.now()));

        document.add(new AreaBreak());
        document.setHorizontalAlignment(HorizontalAlignment.LEFT);
        for (Integer contentId : contentMap.values()) {
            Content content = getContent(contentId);
            if (content.getImage() != null) {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(content.getImage(), null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                document.add(new com.itextpdf.layout.element.Image(ImageDataFactory.create(baos.toByteArray())));
            } else {
                document.add(new Paragraph(content.getText()));
            }
        }
        document.close();
    }
}
