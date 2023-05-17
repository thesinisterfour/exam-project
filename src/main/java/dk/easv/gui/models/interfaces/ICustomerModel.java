package dk.easv.gui.models.interfaces;

import dk.easv.be.Customer;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface ICustomerModel {
    ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException;

    int add(Customer customer) throws SQLException;
}
