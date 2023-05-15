package dk.easv.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOTypeTest {

    @Test
    void values() {
        DAOType[] expected = {DAOType.CUSTOMER_DAO, DAOType.CITY_DAO, DAOType.USER_DAO, DAOType.DOCUMENT_DAO, DAOType.CONTENT_DAO};
        DAOType[] actual = DAOType.values();

        assertArrayEquals(expected, actual);
    }

    @Test
    void valueOf() {
        DAOType expected = DAOType.CUSTOMER_DAO;
        DAOType actual = DAOType.valueOf("CUSTOMER_DAO");
        assertEquals(expected, actual);

        expected = DAOType.CITY_DAO;
        actual = DAOType.valueOf("CITY_DAO");
        assertEquals(expected, actual);

        expected = DAOType.USER_DAO;
        actual = DAOType.valueOf("USER_DAO");
        assertEquals(expected, actual);

        expected = DAOType.DOCUMENT_DAO;
        actual = DAOType.valueOf("DOCUMENT_DAO");
        assertEquals(expected, actual);

        expected = DAOType.CONTENT_DAO;
        actual = DAOType.valueOf("CONTENT_DAO");
        assertEquals(expected, actual);

    }
}