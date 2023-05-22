package dk.easv.dal;

import dk.easv.be.*;
import dk.easv.dal.dao.*;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CRUDDAOFactoryTest {
    @Test
    void getDaoUser() {
        ICRUDDao<User> expected = new UserDAO();
        ICRUDDao<User> actual = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        assert actual != null;
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getDaoCustomer() {
        ICRUDDao<Customer> expected = new CustomerDAO();
        ICRUDDao<Customer> actual = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        assert actual != null;
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getDaoDocument() {
        ICRUDDao<Doc> expected = new DocumentDAO();
        ICRUDDao<Doc> actual = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        assert actual != null;
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getDaoCity() {
        ICRUDDao<City> expected = new CityDAO();
        ICRUDDao<City> actual = CRUDDAOFactory.getDao(DAOType.CITY_DAO);
        assert actual != null;
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getDaoContent() {
        ICRUDDao<Content> expected = new ContentDAO();
        ICRUDDao<Content> actual = CRUDDAOFactory.getDao(DAOType.CONTENT_DAO);
        assert actual != null;
        assertEquals(expected.getClass(), actual.getClass());
    }

    @DisplayName("Test if getDao returns null")
    @Test
    void getDaoNull() {
        assertThrows(NullPointerException.class, () -> CRUDDAOFactory.getDao(null));
    }
}