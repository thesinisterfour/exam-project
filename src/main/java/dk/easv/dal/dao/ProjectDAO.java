package dk.easv.dal.dao;

import dk.easv.be.City;
import dk.easv.be.Customer;
import dk.easv.be.Project;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProjectDAO implements ICRUDDao<Project> {

    private final ConnectionManager cm = new ConnectionManager();


    @Override
    public int add(Project object) throws SQLException {
        return 0;
    }

    @Override
    public int update(Project object) throws SQLException {
        return 0;
    }

    @Override
    public Project get(int id) throws SQLException {
        return null;
    }

    @Override
    public ConcurrentMap<Integer, Project> getAll() throws SQLException {
        ConcurrentMap<Integer, Project> projects = new ConcurrentHashMap<>();
        try (Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT dbo.[project].[project_id], dbo.[project].[project_name], dbo.customer.customer_id, dbo.customer.name, dbo.customer.email, dbo.customer.address, dbo.[project].[address], dbo.[project].[zipcode] FROM dbo.project"
                    + "INNER JOIN dbo.customer ON dbo.project.customer_id = dbo.customer.customer_id;"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int dbProject_id = rs.getInt("project_id");
                int dbCustomer_id = rs.getInt("customer_id");
                String dbProject_name = rs.getString("project_name");
                String dbAddress = rs.getString("address");
                int dbZipcode = rs.getInt("zipcode");

                Customer customer = new Customer(dbCustomer_id, );
                Project project = new Project(dbProject_id, dbProject_name, dbAddress, dbZipcode, customer);
                projects.put(dbProject_id, customer);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }


    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
