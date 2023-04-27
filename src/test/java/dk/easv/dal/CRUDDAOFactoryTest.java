package dk.easv.dal;

import dk.easv.be.User;
import dk.easv.dal.dao.UserDAO;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CRUDDAOFactoryTest {

    @DisplayName("Test if getDao returns a UserDAO")
    @Test
    void getDaoUser() {
        ICRUDDao<User> expected = new UserDAO();
        ICRUDDao<User> actual = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        assert actual != null;
        assertEquals(expected.getClass(), actual.getClass());
    }

    @DisplayName("Test if getDao returns null")
    @Test
    void getDaoNull(){
        assertThrows(NullPointerException.class, () -> CRUDDAOFactory.getDao(null));
    }
}