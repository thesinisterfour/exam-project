package dk.easv.gui.models;

import dk.easv.be.Customer;
import dk.easv.bll.CRUDLogic;
import dk.easv.bll.ICRUDLogic;
import dk.easv.gui.models.interfaces.ICustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class CustomerModel implements ICustomerModel {

    private static ICustomerModel INSTANCE;
    private final ICRUDLogic crudLogic = new CRUDLogic();
    /**
     * @returns a ConcurrentMap of Customer objects. The method retrieves the customers using an object of crudLogic class.
     * @throws SQLException
     */
    private final ObservableList<Customer> obsCustomers;

    private int selectedCustomerId = 0;

    private CustomerModel() throws SQLException {
        obsCustomers = FXCollections.observableArrayList();
        loadAllCustomers();
    }

    public static ICustomerModel getInstance() throws SQLException {
        if (INSTANCE == null) {
            INSTANCE = new CustomerModel();
        }
        return INSTANCE;
    }


    @Override
    public int add(Customer customer) throws SQLException {
        int id = crudLogic.addCustomer(customer);
        loadAllCustomers();
        return id;
    }

    @Override
    public int updateCustomer(Customer customer) throws SQLException {
        int rows = crudLogic.updateCustomer(customer);
        loadAllCustomers();
        return rows;
    }

    @Override
    public int deleteCustomer(Customer customer) throws SQLException {
        int rows = crudLogic.deleteCustomer(customer);
        loadAllCustomers();
        return rows;
    }

    @Override
    public ObservableList<Customer> getObsCustomers() {
        return obsCustomers;
    }

    @Override
    public void loadAllCustomers() throws SQLException {
        obsCustomers.setAll(crudLogic.getAllCustomers().values());
    }

    @Override
    public int getSelectedCustomerId() {
        return selectedCustomerId;
    }

    @Override
    public void setSelectedCustomerId(int selectedCustomerId) {
        this.selectedCustomerId = selectedCustomerId;
    }
}
