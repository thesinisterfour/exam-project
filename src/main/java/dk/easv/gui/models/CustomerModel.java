package dk.easv.gui.models;

import dk.easv.be.Customer;
import dk.easv.bll.ICRUDLogic;
import dk.easv.bll.CRUDLogic;
import dk.easv.gui.models.interfaces.ICustomerModel;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CustomerModel implements ICustomerModel {

    private final ICRUDLogic crudLogic = new CRUDLogic();

    @Override
    public ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException {
        return crudLogic.getAllCustomers();
    }

    @Override
    public int add(Customer customer) throws SQLException {
        return crudLogic.addCustomer(customer);
    }
}
