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
            case CONTENT_DAO -> new ContentDAO();
        };
    }
}
