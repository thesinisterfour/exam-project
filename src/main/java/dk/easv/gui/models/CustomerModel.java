package dk.easv.gui.models;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.DocumentLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomerModel {

    private CRUDLogic crudLogic = new CRUDLogic();

    private ObservableList<Customer> obsAllCustomers;

    /*
    public CustomerModel() throws SQLException {
        obsAllCustomers= FXCollections.observableArrayList();
    }

    public ObservableList<Customer> getObsAllCustomers() throws SQLException {
        setObsAllCustomers();
        return obsAllCustomers;
    }

    public void setObsAllCustomers() throws SQLException {
        ConcurrentMap<Integer, Customer> allDocuments = getAllCustomers();
        this.obsAllCustomers.setAll(allDocuments.values());
    }

     */


    public ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException {
        return crudLogic.getAllCustomers();
    }

    public int add(Customer customer) throws SQLException {
        return crudLogic.addCustomer(customer);
    }
}
