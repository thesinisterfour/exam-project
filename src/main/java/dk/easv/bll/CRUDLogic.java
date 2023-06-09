package dk.easv.bll;

import dk.easv.be.*;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CRUDLogic implements ICRUDLogic {

    /**
     * the addUser method attempts to retrieve a user DAO from the CRUDDAOFactory
     * and adds the provided User object by invoking the add method on the DAO.
     *
     * @return -1 if the DAO retrieval fails or the result returned by the DAO's add method.
     * @throws SQLException if there is an error doing the database operation.
     */
    @Override
    public int addUser(User user) throws SQLException {
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        return userDAO.add(user);
    }


    /**
     * the addCustomer method attempts to retrieve a customer DAO from the CRUDDAOFactory
     * and adds the provided Customer object by invoking the add method on the DAO.
     *
     * @return the result returned by the DAO's add method
     * @throws SQLException         if there is an error doing the database operations.
     * @throws NullPointerException if the DAO retrieval fails.
     */

    @Override
    public int addCustomer(Customer customer) throws SQLException, NullPointerException {
        ICRUDDao<Customer> customerDAO = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        return customerDAO.add(customer);
    }

    /**
     * The addCity method attempts to add a City object to the data source using a DAO obtained from the CRUDDAOFactory.
     *
     * @return If the DAO is not null, the add method of the DAO is called, and its return value is returned by the addCity method.
     * @throws SQLException         if there is an error doing the database operations.
     * @throws NullPointerException if the DAO is null.
     */

    @Override
    public int addCity(City city) throws SQLException, NullPointerException {
        ICRUDDao<City> cityDao = CRUDDAOFactory.getDao(DAOType.CITY_DAO);
        return cityDao.add(city);
    }

    /**
     * The getAllCustomers method retrieves the Customer DAO from the CRUDDAOFactory
     * and calls the getAll method on it to retrieve all customers from the data source.
     *
     * @return the resulting ConcurrentMap<Integer, Customer>.
     * @throws SQLException         if there is an error doing the database operation.
     * @throws NullPointerException if the DAO is null.
     */
    @Override
    public ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException {
        ICRUDDao<Customer> customerDao = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        return customerDao.getAll();
    }

    /**
     * Specified by: getCity in interface BLLFacade
     * The getCity method retrieves the City DAO from the CRUDDAOFactory
     * and calls the get method on it to retrieve a specific city based on the provided zipcode.
     *
     * @return the resulting City object.
     * @throws SQLException         if there is an error in the database operation.
     * @throws NullPointerException if the DAO is null.
     */
    @Override
    public City getCity(int zipcode) throws SQLException {
        ICRUDDao<City> cityDao = CRUDDAOFactory.getDao(DAOType.CITY_DAO);
        return cityDao.get(zipcode);
    }

    /**
     * The getAllUsers method retrieves the User DAO from the CRUDDAOFactory
     * and calls the getAll method on it to retrieve all users from the data source.
     *
     * @return the resulting ConcurrentMap<Integer, User>.
     * @throws SQLException         if there is an error in the database operations.
     * @throws NullPointerException if the DAO is null.
     */
    @Override
    public ConcurrentMap<Integer, User> getAllUsers() throws SQLException {
        ICRUDDao<User> userDao = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        if (userDao == null) {
            throw new NullPointerException("userDAO is null");
        } else {
            return userDao.getAll();
        }
    }

    /**
     * the addDocument method retrieves the Doc DAO from the CRUDDAOFactory
     * and calls the add method on it to add a document to the data source.
     *
     * @return the integer value returned by the add method.
     * @throws SQLException if there is an error in the database operation
     */


    @Override
    public int addDocument(Doc document) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.add(document);
    }


    /**
     * The updateDocument method retrieves the Doc DAO from the CRUDDAOFactory
     * and calls the update method on it to update a document in the data source.
     *
     * @return the integer value returned by the update method.
     * @throws SQLException if there is an error in the database operation.
     */
    @Override
    public int updateDocument(Doc document) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.update(document);
    }

    /**
     * Specified by: getDocument in interface BLLFacade
     * The getDocument method retrieves  the Doc DAO from the CRUDDAOFactory
     * and calls the get method on it to get a document from the data source.
     *
     * @return the integer value returned by the get method.
     * @throws SQLException if there is an error doing the database operation.
     */

    @Override
    public Doc getDocument(int id) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.get(id);
    }


    /**
     * The purpose of this method is to retrieve all documents from the system.
     * It retrieves the Doc DAO from the CRUDDAOFactory and calls the getAll method
     * on it to retrieve all documents from the data source.
     *
     * @return the resulting ConcurrentMap<Integer, Doc>
     * @throws SQLException if there is an error doing the database operation.
     */
    @Override
    public ConcurrentMap<Integer, Doc> getAllDocuments() throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.getAll();
    }

    /**
     * Specified by: deleteDocument in interface BLLFacade
     * The deleteDocument method will retrieve all the Doc DAO from the CRUDDAOFactory
     * and then calls the delete method on a document from the data source to remove it.
     *
     * @return the integer value returned by the delete method.
     * @throws SQLException if there are any problems during the database operation.
     */

    @Override
    public int deleteDocument(int id) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.delete(id);
    }


    @Override
    public int addProject(Project project) throws SQLException {
        ICRUDDao<Project> projectDao = CRUDDAOFactory.getDao(DAOType.PROJECT_DAO);
        return projectDao.add(project);
    }

    @Override
    public ConcurrentMap<Integer, Project> getAllProjects() throws SQLException {
        ICRUDDao<Project> projectDAo = CRUDDAOFactory.getDao(DAOType.PROJECT_DAO);
        return projectDAo.getAll();
    }

    @Override
    public int updateUser(User user) throws SQLException {
        ICRUDDao<User> userDao = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        return userDao.update(user);
    }

    @Override
    public int deleteUser(int id) throws SQLException {
        ICRUDDao<User> userDao = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        return userDao.delete(id);
    }

    @Override
    public int updateCustomer(Customer customer) throws SQLException {
        ICRUDDao<Customer> customerDao = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        return customerDao.update(customer);
    }

    @Override
    public int deleteCustomer(Customer customer) throws SQLException {
        ICRUDDao<Customer> customerDao = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        return customerDao.delete(customer.getCustomerID());
    }

    @Override
    public void updateProject(Project project) throws SQLException {
        ICRUDDao<Project> projectDao = CRUDDAOFactory.getDao(DAOType.PROJECT_DAO);
        projectDao.update(project);
    }

    @Override
    public void deleteProject(Project project) throws SQLException {
        ICRUDDao<Project> projectDao = CRUDDAOFactory.getDao(DAOType.PROJECT_DAO);
        projectDao.delete(project.getProjectID());
    }

}
