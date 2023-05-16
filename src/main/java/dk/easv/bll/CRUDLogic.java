package dk.easv.bll;

import dk.easv.be.*;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CRUDLogic implements BLLFacade {

    /**
     * the addUser method attempts to retrieve a user DAO from the CRUDDAOFactory
     * and adds the provided User object by invoking the add method on the DAO.
     * @return -1 if the DAO retrieval fails or the result returned by the DAO's add method.
     * @throws SQLException if there is an error doing the database operation.
     */
    @Override
    public int addUser(User user) throws SQLException{
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        if (userDAO == null) {
            return -1;
        } else {
            return userDAO.add(user);
        }
    }

    /**
     *  the addCustomer method attempts to retrieve a customer DAO from the CRUDDAOFactory
     *  and adds the provided Customer object by invoking the add method on the DAO.
     * @return the result returned by the DAO's add method
     * @throws SQLException if there is an error doing the database operations.
     * @throws NullPointerException if the DAO retrieval fails.
     */
    public int addCustomer(Customer customer) throws SQLException, NullPointerException {
        ICRUDDao<Customer> customerDAO = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        if (customerDAO == null) {
            throw new NullPointerException("CustomerDAO is null");
        } else {
            return customerDAO.add(customer);
        }
    }

    /**
     * @param city
     * @return
     * @throws SQLException
     * @throws NullPointerException
     */
    public int addCity(City city) throws SQLException, NullPointerException{
        ICRUDDao<City> cityDao = CRUDDAOFactory.getDao(DAOType.CITY_DAO);
        if(cityDao == null){
            throw new NullPointerException("CityDAO is null");
        }
        else {
            return cityDao.add(city);
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

    @Override
    public City getCity(int zipcode) throws SQLException {
        ICRUDDao<City> cityDao = CRUDDAOFactory.getDao(DAOType.CITY_DAO);
        if (cityDao == null) {
            throw new NullPointerException("CityDAO is null");
        } else {
            return cityDao.get(zipcode);
        }
    }
    public ConcurrentMap<Integer, User> getAllUsers() throws SQLException{
        ICRUDDao<User> userDao = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        if(userDao == null){
            throw  new NullPointerException("userDAO is null");
        }
        else{
            return userDao.getAll();
        }
    }
    public int addDocument(Doc document) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.add(document);
    }

    public int updateDocument(Doc document) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.update(document);
    }
    public Doc getDocument(int id) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.get(id);
    }

    public ConcurrentMap<Integer, Doc> getAllDocuments() throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.getAll();
    }
    public int deleteDocument(int id) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.delete(id);
    }
    public ConcurrentMap<Integer, Project> getCustomerProject() throws SQLException {
        ICRUDDao<Project> projectDAO = CRUDDAOFactory.getDao(DAOType.PROJECT_DAO);
        return projectDAO.getAll();
    }



}
