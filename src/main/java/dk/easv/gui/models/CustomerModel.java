package dk.easv.gui.models;

import dk.easv.be.Customer;
import dk.easv.bll.ICRUDLogic;
import dk.easv.bll.CRUDLogic;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CustomerModel {

    private final ICRUDLogic crudLogic = new CRUDLogic();

    public ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException {
        return crudLogic.getAllCustomers();
    }

    public int add(Customer customer) throws SQLException {
        return crudLogic.addCustomer(customer);
    }
}
