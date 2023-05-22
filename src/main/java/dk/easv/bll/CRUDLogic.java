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
        if (userDAO == null) {
            return -1;
        } else {
            return userDAO.add(user);
        }
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
        if (customerDAO == null) {
            throw new NullPointerException("CustomerDAO is null");
        } else {
            return customerDAO.add(customer);
        }
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
        if (cityDao == null) {
            throw new NullPointerException("CityDAO is null");
        } else {
            return cityDao.add(city);
        }
    }

    /**
     * Specified by: BLLFacade
     * the checkForUser method retrieves the User DAO, gets all users from the data source,
     * and iterates through them to find a user with the given username and password.
     *
     * @return User object if a matching user is found or null otherwise.
     * @throws SQLException if there is errors doing the operations.
     */
    @Override
    public User checkForUser(String username, String password) throws SQLException {
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        ConcurrentMap<Integer, User> userMap = userDAO.getAll();
        for (User user : userMap.values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
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
        if (customerDao == null) {
            throw new NullPointerException("CustomerDAO is null");
        } else {
            return customerDao.getAll();
        }
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
        if (cityDao == null) {
            throw new NullPointerException("CityDAO is null");
        } else {
            return cityDao.get(zipcode);
        }
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

    @Override
    public ConcurrentMap<Integer, Role> getAllRoles() throws SQLException {
        ICRUDDao<Role> roleDao = CRUDDAOFactory.getDao(DAOType.ROLE_DAO);
        if (roleDao == null) {
            throw new NullPointerException("roleDAO is null");
        } else {
            return roleDao.getAll();
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


    /**
     * The method will retrieve all the customer projects from the system. It retrieves the
     * project DAO from the CURDDAOFactory and calls the getAll method.
     *
     * @return the resulting ConcurrentMap<Integer, Projects>
     * The keys are integers representing the project IDs and the values are project objects
     * @throws SQLException if there is an error during the database operation.
     */
    public ConcurrentMap<Integer, Project> getCustomerProject() throws SQLException {
        ICRUDDao<Project> projectDAO = CRUDDAOFactory.getDao(DAOType.PROJECT_DAO);
        return projectDAO.getAll();
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

}
