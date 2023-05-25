package dk.easv.gui.models.interfaces;

import dk.easv.be.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ICustomerModel {

    int add(Customer customer) throws SQLException;

    int updateCustomer(Customer customer) throws SQLException;

    int deleteCustomer(Customer customer) throws SQLException;

    ObservableList<Customer> getObsCustomers() throws SQLException;

    void loadAllCustomers() throws SQLException;

    int getSelectedCustomerId();

    void setSelectedCustomerId(int selectedCustomerId);

}
