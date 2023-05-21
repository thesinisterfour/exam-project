package dk.easv.bll;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import dk.easv.Main;
import dk.easv.be.Content;
import dk.easv.be.Doc;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.dao.ContentDAO;
import dk.easv.dal.dao.DocumentDAO;
import dk.easv.dal.dao.ProjectDAO;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.dal.interafaces.IContentMapperDAO;
import dk.easv.dal.interafaces.IProjectMapper;
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
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.stream.Collectors;

public class DocumentLogic implements IDocumentLogic {
    private final ICRUDDao<Content> contentDAO = CRUDDAOFactory.getDao(DAOType.CONTENT_DAO);
    private final IContentMapperDAO contentMapper = new ContentDAO();

    @Override
    public void addText(int documentId, int index, String text) throws SQLException {
        contentDAO.add(new Content(documentId, index, text));
    }

    @Override
    public void addText(int documentId, int contentId, int index, String content) throws SQLException {
        contentDAO.add(new Content(documentId, contentId, index, content));
    }

    @Override
    public void addImage(int documentId, int index, Image image) throws SQLException {
        contentDAO.add(new Content(documentId,  index, image));
    }
    @Override
    public void addImage(int documentId, int contentId, int index) throws SQLException {
        contentDAO.add(new Content(documentId, contentId, index));
    }

    @Override
    public ConcurrentMap<Integer, Content> getAllContent() throws SQLException {
        return contentDAO.getAll();
    }

    @Override
    public Content getContent(int contentId) throws SQLException{
        return contentDAO.get(contentId);
    }


    @Override
    public void deleteContent(int id) throws SQLException {
        contentDAO.delete(id);
    }

    @Override
    public void deleteMapping(int documentId, int id) throws SQLException {
        contentMapper.deleteMapping(documentId, id);
    }

    @Override
    public ConcurrentNavigableMap<Integer, Integer> loadAllContent(int documentId) throws SQLException {
        return contentMapper.loadAllContent(documentId);
    }
    @Override
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

    @Override
    public void generatePDF(Doc doc, String dest) throws IOException, SQLException {
        ConcurrentNavigableMap<Integer, Integer> contentMap = loadAllContent(doc.getId());
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        document.setFont(font);
        document.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.setTextAlignment(TextAlignment.CENTER);
        document.add(new Paragraph(doc.getName()).setFont(bold).setFontSize(32));
        String description = doc.getDescription();
        document.add(new Paragraph(description == null ? "" : description).setFontSize(16));
        document.add(new Paragraph("Document created on: " + doc.getCreationDate().toString()));
        document.add(new Paragraph("PDF generated on: " + LocalDateTime.now()));

        for (Integer contentId : contentMap.values()) {
            Content content = getContent(contentId);
            if (content.getImage() != null) {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(content.getImage(), null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                com.itextpdf.layout.element.Image img = new com.itextpdf.layout.element.Image(ImageDataFactory.create(baos.toByteArray()));
                img.setHeight(150);
                img.setWidth(250);
                img.setMarginLeft(137);
                img.setMarginTop(35);
                document.add(img);
            } else {
                Color blackColor = Color.makeColor(DeviceRgb.BLACK.getColorSpace());
                document.add(new Paragraph(content.getText()).setMarginTop(35).setBorder(new SolidBorder(blackColor, 1)));

            }
        }

        //Logic for stamping the logo watermark on every page in the pdf

        String logoPath = Objects.requireNonNull(Main.class.getResource("icons/WUAV-Logo.png")).getPath();
        float logoWidth = 123;
        float logoHeight = 33;
        float margin = 20;
        float logoX = pdf.getPage(1).getPageSize().getRight() - logoWidth - margin;
        float logoY = pdf.getPage(1).getPageSize().getTop() - logoHeight - margin;

        int numberOfPages = pdf.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            PdfPage page = pdf.getPage(i);
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            pdfCanvas.addImageFittedIntoRectangle(ImageDataFactory.create(logoPath), new Rectangle(logoX, logoY, logoWidth, logoHeight), false);
        }

        document.close();
    }

    @Override
    public ConcurrentMap<Integer, Doc> getProjectDocuments(int projectId) throws SQLException {
        IProjectMapper projectMapper = new ProjectDAO();
        return projectMapper.getDocumentsByProjectId(projectId);
    }
}
