package dk.easv.gui.models;

import dk.easv.be.Customer;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.ICRUDLogic;
import dk.easv.gui.models.interfaces.ICustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CustomerModel implements ICustomerModel {

    private static ICustomerModel INSTANCE;
    private final ICRUDLogic crudLogic = new CRUDLogic();
    /**
     * @returns a ConcurrentMap of Customer objects. The method retrieves the customers using an object of crudLogic class.
     * @throws SQLException
     */
    private final ObservableList<Customer> obsAllCustomers;
    private ConcurrentMap<Integer, Customer> allCustomers;

    private int selectedCustomerId = 0;

    private CustomerModel() throws SQLException {
        obsAllCustomers = FXCollections.observableArrayList();
        loadAllCustomers();
        setObsAllCustomers();
    }

    public static ICustomerModel getInstance() throws SQLException {
        if (INSTANCE == null) {
            INSTANCE = new CustomerModel();
        }
        return INSTANCE;
    }

    @Override
    public ConcurrentMap<Integer, Customer> getAllCustomers() throws SQLException {
        return allCustomers;
    }

    @Override
    public void setObsAllCustomers() throws SQLException {
        this.obsAllCustomers.setAll(allCustomers.values());
    }


    @Override
    public int add(Customer customer) throws SQLException {
        int id = crudLogic.addCustomer(customer);
        loadAllCustomers();
        setObsAllCustomers();
        return id;
    }

    @Override
    public ObservableList<Customer> getObsAllCustomers() {
        return obsAllCustomers;
    }

    @Override
    public void loadAllCustomers() throws SQLException {
        allCustomers = crudLogic.getAllCustomers();
    }

    @Override
    public int getSelectedCustomerId() {
        return selectedCustomerId;
    }

    @Override
    public void setSelectedCustomerId(int selectedCustomerId) {
        this.selectedCustomerId = selectedCustomerId;
    }

    @Override
    public int deleteCustomer(Customer customer) throws SQLException {
        int rows = crudLogic.deleteCustomer(customer);
        loadAllCustomers();
        setObsAllCustomers();
        return rows;
    }

    @Override
    public int updateCustomer(Customer customer) throws SQLException {
        int rows = crudLogic.updateCustomer(customer);
        loadAllCustomers();
        setObsAllCustomers();
        return rows;
    }
}
