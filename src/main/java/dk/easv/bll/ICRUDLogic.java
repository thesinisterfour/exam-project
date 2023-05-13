package dk.easv.bll;

import dk.easv.be.City;
import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.User;

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
}
