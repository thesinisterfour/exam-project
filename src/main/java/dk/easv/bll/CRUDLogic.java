package dk.easv.bll;

import dk.easv.be.Customer;
import dk.easv.be.Document;
import dk.easv.be.User;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CRUDLogic implements BLLFacade {

    @Override
    public int addUser(User user) throws SQLException{
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        if (userDAO == null) {
            return -1;
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



    @Override
    public User checkForUser(String username, String password) throws SQLException {
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        ConcurrentMap<Integer, User> userMap = userDAO.getAll();
        for (User user : userMap.values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException{
        ICRUDDao<Customer> customerDao = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        if (customerDao == null) {
            throw new NullPointerException("CustomerDAO is null");
        } else {
            return customerDao.getAll();
        }
    }

    public int addDocument(Document document) throws SQLException {
        ICRUDDao<Document> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.add(document);
    }

    public int updateDocument(Document document) throws SQLException {
        ICRUDDao<Document> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.update(document);
    }
    public Document getDocument(int id) throws SQLException {
        ICRUDDao<Document> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.get(id);
    }

    public ConcurrentMap<Integer, Document> getAllDocuments() throws SQLException {
        ICRUDDao<Document> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.getAll();
    }
    public int deleteDocument(int id) throws SQLException {
        ICRUDDao<Document> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.delete(id);
    }



}
