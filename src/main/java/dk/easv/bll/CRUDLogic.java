package dk.easv.bll;

import dk.easv.be.*;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CRUDLogic implements ICRUDLogic {

    @Override
    public int addUser(User user) throws SQLException{
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        if (userDAO == null) {
            return -1;
        } else {
            return userDAO.add(user);
        }
    }

    @Override
    public int addCustomer(Customer customer) throws SQLException, NullPointerException {
        ICRUDDao<Customer> customerDAO = CRUDDAOFactory.getDao(DAOType.CUSTOMER_DAO);
        if (customerDAO == null) {
            throw new NullPointerException("CustomerDAO is null");
        } else {
            return customerDAO.add(customer);
        }
    }

    @Override
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

    @Override
    public ConcurrentMap<Integer, User> getAllUsers() throws SQLException{
        ICRUDDao<User> userDao = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        if(userDao == null){
            throw  new NullPointerException("userDAO is null");
        }
        else{
            return userDao.getAll();
        }
    }
    @Override
    public ConcurrentMap<Integer, Role> getAllRoles() throws SQLException{
        ICRUDDao<Role> roleDao = CRUDDAOFactory.getDao(DAOType.ROLE_DAO);
        if (roleDao == null){
            throw  new NullPointerException("roleDAO is null");
        }
        else {
            return roleDao.getAll();
        }
    }
    @Override
    public int addDocument(Doc document) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.add(document);
    }

    @Override
    public int updateDocument(Doc document) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.update(document);
    }

    @Override
    public Doc getDocument(int id) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.get(id);
    }

    @Override
    public ConcurrentMap<Integer, Doc> getAllDocuments() throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.getAll();
    }

    @Override
    public int deleteDocument(int id) throws SQLException {
        ICRUDDao<Doc> documentDao = CRUDDAOFactory.getDao(DAOType.DOCUMENT_DAO);
        return documentDao.delete(id);
    }

    @Override
    public int addProject(Project project) throws SQLException {
        ICRUDDao<Project> projectDao = CRUDDAOFactory.getDao(DAOType.PROJECT_DAO);
        return  projectDao.add(project);
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
