package dk.easv.bll;

import dk.easv.be.Customer;
import dk.easv.be.User;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CRUDLogic {
    public int addUser(User user) throws SQLException, NullPointerException {
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        if (userDAO == null) {
            throw new NullPointerException("UserDAO is null");
        } else {
            return userDAO.add(user);
        }
    }
    public int addCustomer(Customer customer) throws SQLException, NullPointerException {
        ICRUDDao<Customer> customerDAO = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        if (customerDAO == null) {
            throw new NullPointerException("CustomerDAO is null");
        } else {
            return customerDAO.add(customer);
        }



    }

    public ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException{
        ICRUDDao<Customer> customerDao = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        if (customerDao == null) {
            throw new NullPointerException("CustomerDAO is null");
        } else {
            return customerDao.getAll();
        }
    }

}
