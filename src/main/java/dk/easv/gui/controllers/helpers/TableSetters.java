package dk.easv.gui.controllers.helpers;

import dk.easv.be.Customer;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;

import java.sql.SQLException;
import java.util.Comparator;

public class TableSetters {
    public static void setUpCustomerTable(MFXTableView<Customer> table) throws SQLException {
        ICustomerModel customerModel = CustomerModel.getInstance();
        MFXTableColumn<Customer> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(Customer::getCustomerID));
        MFXTableColumn<Customer> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(Customer::getCustomerName));
        MFXTableColumn<Customer> emailColumn = new MFXTableColumn<>("Email", true, Comparator.comparing(Customer::getCustomerEmail));
        MFXTableColumn<Customer> addressColumn = new MFXTableColumn<>("Address", true, Comparator.comparing(Customer::getCustomerAddress));
        MFXTableColumn<Customer> zipCodeColumn = new MFXTableColumn<>("Zip Code", true, Comparator.comparing(Customer::getZipCode));

        idColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerID));
        nameColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerName));
        emailColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerEmail));
        addressColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getCustomerAddress));
        zipCodeColumn.setRowCellFactory(customer -> new MFXTableRowCell<>(Customer::getZipCode));

        table.getFilters().addAll(new StringFilter<>("Name", Customer::getCustomerName),
                new StringFilter<>("Email", Customer::getCustomerEmail),
                new StringFilter<>("Address", Customer::getCustomerAddress),
                new IntegerFilter<>("Zip Code", Customer::getZipCode));
        table.getTableColumns().setAll(nameColumn, emailColumn, addressColumn, zipCodeColumn);
        table.autosizeColumnsOnInitialization();
        table.setItems(customerModel.getObsAllCustomers());
    }
}
