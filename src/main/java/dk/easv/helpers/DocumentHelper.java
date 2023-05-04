package dk.easv.helpers;
import dk.easv.be.Document;
import dk.easv.bll.DocumentLogic;

import java.sql.SQLException;
import java.util.List;


public class DocumentHelper {
    public static StringBuilder convertToString() throws SQLException {
        List<Document> oldDocuments = DocumentLogic.showDocumentName();
        List<String> documentNames = oldDocuments.stream()
                .map(Document::getName)
                .toList();

        StringBuilder docNames = new StringBuilder("The following documents are 48 months old:\n");
        for (String documentName : documentNames) {
            docNames.append(documentName).append("\n");
        }
        return docNames;
    }
}
