package dk.easv.dal.dao;

import dk.easv.be.Project;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProjectDAO implements ICRUDDao<Project> {


    private final ConnectionManager cm = ConnectionManager.getINSTANCE();
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
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT dbo.[project].[project_id], dbo.[project].[project_name], dbo.[project].[project_start_date], dbo.[project].[project_end_date], dbo.customer.customer_id, dbo.[project].[address], dbo.[project].[zipcode]" +
                    " FROM dbo.project"
                    +" INNER JOIN dbo.customer ON dbo.project.customer_id = dbo.customer.customer_id;"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int dbProject_id = rs.getInt("project_id");
                String dbProject_name = rs.getString("project_name");
                LocalDate dbProject_start_date = rs.getDate("project_start_date")!= null ? rs.getDate("project_start_date").toLocalDate() : null;
                LocalDate dbProject_end_date = rs.getDate("project_end_date")!= null ? rs.getDate("project_end_date").toLocalDate() : null;
                int dbCustomer_id = rs.getInt("customer_id");
                String dbAddress = rs.getString("address");
                int dbZipcode = rs.getInt("zipcode");

                Project project = new Project(dbProject_id, dbProject_name, dbProject_start_date, dbProject_end_date, dbCustomer_id, dbAddress, dbZipcode);
                projects.put(dbProject_id, project);

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
