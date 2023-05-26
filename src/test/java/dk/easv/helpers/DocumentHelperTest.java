package dk.easv.helpers;

import dk.easv.be.Doc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class DocumentHelperTest {

    @Test
    void convertToString() {
        String expected = """
                The following documents are 48 months old, do you wish to delete them:
                Test1
                Test2
                Test3""";
        Doc doc1 = new Doc("Test1");
        Doc doc2 = new Doc("Test2");
        Doc doc3 = new Doc("Test3");
        String actual = DocumentHelper.convertToString(Arrays.asList(doc1, doc2, doc3));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void isOldDocWarningShown() {
        DocumentHelper.setOldDocWarningShown(true);
        Assertions.assertTrue(DocumentHelper.isOldDocWarningShown());
    }
}