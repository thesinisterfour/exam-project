package dk.easv.bll;

import dk.easv.be.*;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface ICRUDLogic {
    int addUser(User user) throws SQLException;

    int addDocument(Doc document) throws SQLException;
    int updateDocument(Doc document) throws SQLException;
    Doc getDocument(int id) throws SQLException;
    ConcurrentMap<Integer, Doc> getAllDocuments() throws SQLException;
    int deleteDocument(int id) throws SQLException;

    City getCity(int zipcode) throws SQLException;

    int addCustomer(Customer customer) throws SQLException, NullPointerException;

    int addCity(City city) throws SQLException, NullPointerException;

    ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException;

    ConcurrentMap<Integer, User> getAllUsers() throws SQLException;

    int addProject(Project project) throws SQLException;

    ConcurrentMap<Integer, Project> getAllProjects() throws SQLException;
}
