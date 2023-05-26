package dk.easv.bll;

import dk.easv.be.*;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

/**
 * This interface declares several methods related to database operations for User, Doc, and City objects.
 */
public interface ICRUDLogic {

    /**
     * This method takes a parameter of type User.
     * It is intended to add a user.
     *
     * @returns an integer
     */
    int addUser(User user) throws SQLException;

    /**
     * This method takes a parameter of type Doc.
     * It is used to add a document.
     *
     * @returns an integer
     */

    int addDocument(Doc document) throws SQLException;

    /**
     * This method takes a parameter of type Doc.
     * It is intended to update a document.
     *
     * @returns an integer
     */
    int updateDocument(Doc document) throws SQLException;

    /**
     * This method takes an integer parameter representing an ID.
     * Its purpose is to retrieve a document from the system based on the provided ID.
     *
     * @returns a Doc object.
     */
    Doc getDocument(int id) throws SQLException;

    /**
     * Its purpose is to retrieve all documents from the system.
     *
     * @returns a ConcurrentMap with integer keys and Doc values.
     */
    ConcurrentMap<Integer, Doc> getAllDocuments() throws SQLException;

    /**
     * This method takes an integer parameter representing an ID.
     * It is used to delete a document.
     *
     * @returns an integer
     */
    int deleteDocument(int id) throws SQLException;

    /**
     * This method takes an integer parameter representing a ZIP code.
     * Its purpose is to retrieve a city from the system based on the provided ZIP code.
     *
     * @returns a City object.
     */
    City getCity(int zipcode) throws SQLException;

    int addCustomer(Customer customer) throws SQLException, NullPointerException;

    int addCity(City city) throws SQLException, NullPointerException;

    ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException;

    ConcurrentMap<Integer, User> getAllUsers() throws SQLException;

    int addProject(Project project) throws SQLException;

    ConcurrentMap<Integer, Project> getAllProjects() throws SQLException;

    int updateUser(User user) throws SQLException;

    int deleteUser(int id) throws SQLException;

    int updateCustomer(Customer customer) throws SQLException;

    int deleteCustomer(Customer customer) throws SQLException;

    void updateProject(Project project) throws SQLException;

    void deleteProject(Project project) throws SQLException;
}
