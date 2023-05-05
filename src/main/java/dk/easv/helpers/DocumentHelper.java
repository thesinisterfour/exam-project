package dk.easv.helpers;
import dk.easv.be.Doc;

import java.util.List;
import java.util.StringJoiner;


public class DocumentHelper {
    public static String convertToString(List<Doc> oldDocuments){
        StringJoiner docNames = new StringJoiner("\n");
        docNames.add("The following documents are 48 months old:");
        for (Doc doc : oldDocuments) {
            docNames.add(doc.getName());
        }
        return docNames.toString();
    }
}
