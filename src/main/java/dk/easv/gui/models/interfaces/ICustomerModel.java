package dk.easv.gui.models.interfaces;

import dk.easv.be.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface ICustomerModel {
    ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException;

    void setObsAllCustomers() throws SQLException;

    int add(Customer customer) throws SQLException;


    ObservableList<Customer> getObsAllCustomers() throws SQLException;

    void loadAllCustomers() throws SQLException;

    int getSelectedCustomerId();

    void setSelectedCustomerId(int selectedCustomerId);

    int deleteCustomer(Customer customer) throws SQLException;

    int updateCustomer(Customer customer) throws SQLException;
}
