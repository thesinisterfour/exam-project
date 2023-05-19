package dk.easv.gui.controllers.helpers;

import dk.easv.be.Customer;
import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.be.User;
import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import dk.easv.gui.models.interfaces.IUserModel;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.NoSuchElementException;

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
        try {
            table.getSelectionModel().setAllowsMultipleSelection(false);
        } catch (NoSuchElementException e) {

        }
    }

    public static void setUpProjectTable(MFXTableView<Project> table) throws SQLException {
        IProjectModel projectModel = ProjectModel.getInstance();


        MFXTableColumn<Project> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(Project::getProjectID));
        MFXTableColumn<Project> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(Project::getProjectName));
        MFXTableColumn<Project> dateStartColumn = new MFXTableColumn<>("Start Date", true, Comparator.comparing(Project::getStartDate));
        MFXTableColumn<Project> dateEndColumn = new MFXTableColumn<>("End Date", true, Comparator.comparing(Project::getEndDate));
        MFXTableColumn<Project> addressColumn = new MFXTableColumn<>("Address", true, Comparator.comparing(Project::getProjectAddress));
        MFXTableColumn<Project> zipCodeColumn = new MFXTableColumn<>("Zip Code", true, Comparator.comparing(Project::getProjectZipcode));

        idColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getProjectID));
        nameColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getProjectName));
        dateStartColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getStartDate));
        dateEndColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getEndDate));
        addressColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getProjectAddress));
        zipCodeColumn.setRowCellFactory(project -> new MFXTableRowCell<>(Project::getProjectZipcode));

        table.getTableColumns().setAll(nameColumn, dateStartColumn, dateEndColumn, addressColumn, zipCodeColumn);
        table.autosizeColumnsOnInitialization();


        table.getFilters().addAll(
                new StringFilter<>("Name", Project::getProjectName),
                new StringFilter<>("Address", Project::getProjectAddress),
                new IntegerFilter<>("Zip Code", Project::getProjectZipcode)
        );
        table.setItems(projectModel.getProjectObservableList());
        try {
            table.getSelectionModel().setAllowsMultipleSelection(false);
        } catch (NoSuchElementException e) {

        }
    }

    public static void setUpDocumentTable(MFXTableView<Doc> documentsTable) throws SQLException {
        IDocumentModel documentModel = DocumentModel.getInstance();
        MFXTableColumn<Doc> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(Doc::getId));
        MFXTableColumn<Doc> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(Doc::getName));
        MFXTableColumn<Doc> dateCreatedColumn = new MFXTableColumn<>("Date Created", true, Comparator.comparing(Doc::getCreationDate));
        MFXTableColumn<Doc> dateLastOpenedColumn = new MFXTableColumn<>("Date Last Opened", true, Comparator.comparing(Doc::getLastView));
        MFXTableColumn<Doc> descriptionColumn = new MFXTableColumn<>("Description", true, Comparator.comparing(Doc::getDescription));

        idColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getId));
        nameColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getName));
        dateCreatedColumn.setRowCellFactory(document -> new MFXTableRowCell<>(Doc::getCreationDate));
        dateLastOpenedColumn.setRowCellFactory(document -> {
            LocalDate date = document.getLastView();
            if (date == null) {
                return new MFXTableRowCell<>(doc -> "Never");
            }
            return new MFXTableRowCell<>(Doc::getLastView);
        });
        descriptionColumn.setRowCellFactory(document -> {
            String description = document.getDescription();
            if (description == null) {
                return new MFXTableRowCell<>(doc -> "No description");
            }
            return new MFXTableRowCell<>(Doc::getDescription);
        });

        documentsTable.getTableColumns().setAll(nameColumn, dateCreatedColumn, dateLastOpenedColumn, descriptionColumn);
        documentsTable.autosizeColumnsOnInitialization();


        documentsTable.getFilters().addAll(
                new StringFilter<>("Name", Doc::getName),
                new StringFilter<>("Description", Doc::getDescription)
        );

        documentsTable.setItems(documentModel.getObsDocuments());
        try {
            documentsTable.getSelectionModel().setAllowsMultipleSelection(false);
        } catch (NoSuchElementException e) {

        }
    }

    public static void setupUsersTable(MFXTableView<User> table) throws SQLException {
        IUserModel userModel = UserModel.getInstance();
        MFXTableColumn<User> idColumn = new MFXTableColumn<>("ID", true, Comparator.comparing(User::getUserID));
        MFXTableColumn<User> firstNameColumn = new MFXTableColumn<>("First Name", true, Comparator.comparing(User::getFirstName));
        MFXTableColumn<User> lastNameColumn = new MFXTableColumn<>("Last Name", true, Comparator.comparing(User::getLastName));
        MFXTableColumn<User> roleColumn = new MFXTableColumn<>("Role", true, Comparator.comparing(User::getRole));
        MFXTableColumn<User> userNameColumn = new MFXTableColumn<>("Username", true, Comparator.comparing(User::getUsername));

        idColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getUserID));
        firstNameColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getFirstName));
        lastNameColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getLastName));
        roleColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getRole));
        userNameColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getUsername));

        table.getTableColumns().setAll(firstNameColumn, lastNameColumn, roleColumn, userNameColumn);
        table.autosizeColumnsOnInitialization();

        table.getFilters().addAll(
                new StringFilter<>("First Name", User::getFirstName),
                new StringFilter<>("Last Name", User::getLastName),
                new StringFilter<>("Role", user -> user.getRole().toString()),
                new StringFilter<>("Username", User::getUsername)
        );

        table.setItems(userModel.getObsAllUsers());
    }


}
