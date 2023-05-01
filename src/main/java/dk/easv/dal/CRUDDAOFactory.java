package dk.easv.dal;

import dk.easv.dal.dao.CityDAO;
import dk.easv.dal.dao.CustomerDAO;
import dk.easv.dal.dao.UserDAO;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

public class CRUDDAOFactory {
    public static ICRUDDao getDao(DAOType dao) {
        switch (dao) {
            case CUSTOMER_DAO:
                return new CustomerDAO();
            case CITY_DAO:
                return new CityDAO();
            case TICKET_DAO:
//                return new TicketDAO();
            case USER_DAO:
                return new UserDAO();
            default:
                return null;
        }
    }
}
