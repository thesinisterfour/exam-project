package dk.easv.dal;

import dk.easv.dal.dao.*;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

public class CRUDDAOFactory {
    public static ICRUDDao getDao(DAOType dao) {
        return switch (dao) {
            case CUSTOMER_DAO -> new CustomerDAO();
            case USER_DAO -> new UserDAO();
            case DOCUMENT_DAO -> new DocumentDAO();
            case CITY_DAO -> new CityDAO();
            case PROJECT_DAO -> new ProjectDAO();
<<<<<<< HEAD
            case ROLE_DAO -> new RoleDAO();
=======
            case CONTENT_DAO -> new ContentDAO();
>>>>>>> 123100806ef40248b40d1aa3d441fb096207a14f
        };
    }
}
