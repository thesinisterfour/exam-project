package dk.easv.helpers;

import dk.easv.be.Document;
import dk.easv.dal.dao.DocumentDAO;
import javafx.scene.control.Alert;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class AlertHelper {
    public static Alert alert;
    public static void showDefaultAlert(String content, Alert.AlertType type) throws SQLException {
        LocalDateTime currentDate = LocalDateTime.now();
        DocumentDAO documentDAO = new DocumentDAO();
        ConcurrentMap<Integer, Document> documents = documentDAO.getAll();
        List<String> documentNames = documents.values()
                .stream()
                .filter(doc -> ChronoUnit.MONTHS.between(
                        doc.getCreationDate().atStartOfDay(), currentDate) >= 48)
                .map(Document::getName)
                .collect(Collectors.toList());

        if (!documentNames.isEmpty()) {
            alert = new Alert(type);
            alert.setAlertType(type);
            alert.setHeaderText(null);
            alert.setContentText(content + documentNames);
            alert.setResizable(false);
            alert.show();
        }
    }
}
