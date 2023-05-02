package dk.easv.dal;

import dk.easv.dal.dao.CityDAO;
import dk.easv.dal.dao.CustomerDAO;
import dk.easv.dal.dao.DocumentDAO;
import dk.easv.dal.dao.UserDAO;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

public class CRUDDAOFactory {
    public static ICRUDDao getDao(DAOType dao) {
        return switch (dao) {
            case CUSTOMER_DAO -> new CustomerDAO();
            case USER_DAO -> new UserDAO();
            case DOCUMENT_DAO -> new DocumentDAO();
            case CITY_DAO -> new CityDAO();
        };
    }
}
