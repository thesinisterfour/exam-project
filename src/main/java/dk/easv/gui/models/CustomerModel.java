package dk.easv.gui.models;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.bll.ICRUDLogic;
import dk.easv.bll.CRUDLogic;
import dk.easv.gui.models.interfaces.ICustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CustomerModel implements ICustomerModel {

    private final ICRUDLogic crudLogic = new CRUDLogic();

    private final ObservableList<Customer> obsAllCustomers;

    public CustomerModel() throws SQLException {
        obsAllCustomers= FXCollections.observableArrayList();
        setObsAllCustomers();
    }

    @Override
    public ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException {
        return crudLogic.getAllCustomers();
    }

    @Override
    public void setObsAllCustomers() throws SQLException {
        ConcurrentMap<Integer, Customer> allCustomers = getAllCustomers();
        this.obsAllCustomers.setAll(allCustomers.values());
    }


    @Override
    public int add(Customer customer) throws SQLException {
        return crudLogic.addCustomer(customer);
    }

    @Override
    public ObservableList<Customer> getObsAllCustomers() throws SQLException {
        setObsAllCustomers();
        return obsAllCustomers;
    }
}
