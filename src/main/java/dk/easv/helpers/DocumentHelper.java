package dk.easv.helpers;
import dk.easv.be.Doc;

import java.util.List;
import java.util.StringJoiner;


public class DocumentHelper {
    private static boolean oldDocWarningShown = false;
    public static String convertToString(List<Doc> oldDocuments){
        StringJoiner docNames = new StringJoiner("\n");
        docNames.add("The following documents are 48 months old, do you wish to delete them:");
        for (Doc doc : oldDocuments) {
            docNames.add(doc.getName());
        }
        return docNames.toString();
    }

    public static boolean isOldDocWarningShown() {
        return oldDocWarningShown;
    }

    public static void setOldDocWarningShown(boolean oldDocWarningShown) {
        DocumentHelper.oldDocWarningShown = oldDocWarningShown;
    }
}
