package dk.easv.be;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class DocTest {
    private Doc doc;

    @BeforeEach
    public void setUp() {
        doc = new Doc("test.doc", LocalDate.of(2022, 5, 11), "Test document");
    }

    @Test
    public void testGetters() {
        assertEquals("test.doc", doc.getName());
        assertEquals(LocalDate.of(2022, 5, 11), doc.getCreationDate());
        assertEquals("Test document", doc.getDescription());
        assertNull(doc.getLastView());
    }

    @Test
    public void testSetters() {
        doc.setId(1);
        assertEquals(1, doc.getId());

        doc.setName("new.doc");
        assertEquals("new.doc", doc.getName());

        LocalDate newDate = LocalDate.of(2023, 5, 11);
        doc.setCreationDate(newDate);
        assertEquals(newDate, doc.getCreationDate());

        LocalDate newLastView = LocalDate.of(2023, 5, 10);
        doc.setLastView(newLastView);
        assertEquals(newLastView, doc.getLastView());

        doc.setDescription("New description");
        assertEquals("New description", doc.getDescription());
    }

    @Test
    public void testConstructors() {
        Doc doc1 = new Doc("test.doc", LocalDate.of(2022, 5, 11));
        assertEquals("test.doc", doc1.getName());
        assertEquals(LocalDate.of(2022, 5, 11), doc1.getCreationDate());
        assertNull(doc1.getDescription());
        assertNull(doc1.getLastView());

        Doc doc2 = new Doc("test.doc");
        assertEquals("test.doc", doc2.getName());
        assertNull(doc2.getCreationDate());
        assertNull(doc2.getDescription());
        assertNull(doc2.getLastView());

        Doc doc3 = new Doc(1, "test.doc", LocalDate.of(2022, 5, 11), "Test document");
        assertEquals(1, doc3.getId());
        assertEquals("test.doc", doc3.getName());
        assertEquals(LocalDate.of(2022, 5, 11), doc3.getCreationDate());
        assertEquals("Test document", doc3.getDescription());
        assertNull(doc3.getLastView());

        Doc doc4 = new Doc(2, "test.doc", LocalDate.of(2022, 5, 11), LocalDate.of(2022, 5, 12), "Test document");
        assertEquals(2, doc4.getId());
        assertEquals("test.doc", doc4.getName());
        assertEquals(LocalDate.of(2022, 5, 11), doc4.getCreationDate());
        assertEquals(LocalDate.of(2022, 5, 12), doc4.getLastView());
        assertEquals("Test document", doc4.getDescription());

        Doc doc5 = new Doc("test.doc", "Test document");
        assertEquals("test.doc", doc5.getName());
        assertNull(doc5.getCreationDate());
    }

    @Test
    void testToString() {
        assertEquals("Doc{id=0, name='test.doc', creationDate=2022-05-11, lastView=null, description='Test document'}", doc.toString());
    }
}