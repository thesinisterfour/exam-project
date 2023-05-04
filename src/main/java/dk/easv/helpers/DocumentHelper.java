package dk.easv.helpers;
import dk.easv.be.Document;

import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;


public class DocumentHelper {
    public static String convertToString(List<Document> oldDocuments) throws SQLException {
        StringJoiner docNames = new StringJoiner("\n");
        docNames.add("The following documents are 48 months old:");
        for (Document doc : oldDocuments) {
            docNames.add(doc.getName());
        }
        return docNames.toString();
    }
}
