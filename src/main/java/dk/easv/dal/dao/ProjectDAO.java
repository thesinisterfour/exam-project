package dk.easv.dal.dao;

import dk.easv.be.Project;
import dk.easv.dal.connectionManager.ConnectionManagerFactory;
import dk.easv.dal.connectionManager.IConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.dal.interafaces.IProjectMapper;

import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProjectDAO implements ICRUDDao<Project>, IProjectMapper {

    private final IConnectionManager cm = ConnectionManagerFactory.createConnectionManager();
    @Override
    public int add(Project object) throws SQLException {
        try(Connection con = cm.getConnection()){
            if (object == null) {
                throw new SQLException("Object cannot be null");
            } else {

                String sql = "INSERT INTO project (project_name, project_start_date, project_end_date, customer_id, address, zipcode) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, object.getProjectName());
                ps.setDate(2, Date.valueOf(object.getStartDate()));
                ps.setDate(3, Date.valueOf(object.getEndDate()));
                ps.setInt(4, object.getCustomerID());
                ps.setString(5, object.getProjectAddress());
                ps.setInt(6, object.getProjectZipcode());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Could not add project");
                }
            }
        }
    }

    @Override
    public int update(Project object) throws SQLException {
        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("UPDATE dbo.[project] SET project_name=?, project_start_date=?, " +
                    "project_end_date=?, customer_id=?, address=?, zipcode=? WHERE project_id=?;");

            ps.setString(1, object.getProjectName());
            ps.setDate(2, Date.valueOf(object.getStartDate()));
            ps.setDate(3, Date.valueOf(object.getEndDate()));
            ps.setInt(4, object.getCustomerID());
            ps.setString(5, object.getProjectAddress());
            ps.setInt(6, object.getProjectZipcode());
            ps.setInt(7, object.getProjectID());

            ps.executeQuery();
        }
        return 0;
    }

    @Override
    public Project get(int id) throws SQLException {
        try(Connection connection = cm.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbo.[project] WHERE project_id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Project(rs.getInt("project_id"), rs.getString("project_name"), rs.getDate("project_start_date").toLocalDate(),
                        rs.getDate("project_end_date").toLocalDate(), rs.getInt("customer_id"), rs.getString("address"), rs.getInt("zipcode"));
            }
        }
        return null;
    }

    @Override
    public ConcurrentMap<Integer, Project> getAll() throws SQLException {
        ConcurrentMap<Integer, Project> projects = new ConcurrentHashMap<>();
        try (Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbo.[project]");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int dbId = rs.getInt("project_id");
                String dbName = rs.getString("project_name");
                Date dbStartDate = rs.getDate("project_start_date");
                Date dbEndDate = rs.getDate("project_end_date");
                int dbCustomerId = rs.getInt("customer_id");
                String dbAddress = rs.getString("address");
                int dbZipcode = rs.getInt("zipcode");
                Project project = new Project(dbId,dbName,dbStartDate.toLocalDate(), dbEndDate.toLocalDate(), dbCustomerId, dbAddress, dbZipcode);
                projects.put(dbId, project);
            }
        }
        return projects;
    }

    @Override
    public int delete(int id) throws SQLException {
        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("DELETE FROM dbo.[project] WHERE project_id=?;");
            ps.setInt(1, id);

            ps.executeQuery();
        }
        return 0;
    }

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByCustomerId(int id) throws SQLException {
        ConcurrentMap<Integer, Project> projects = new ConcurrentHashMap<>();
        try (Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbo.[project] INNER JOIN project_customer pc on project.project_id = pc.project_id WHERE pc.customer_id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int dbId = rs.getInt("project_id");
                String dbName = rs.getString("project_name");
                Date dbStartDate = rs.getDate("project_start_date");
                Date dbEndDate = rs.getDate("project_end_date");
                int dbCustomerId = rs.getInt("customer_id");
                String dbAddress = rs.getString("address");
                int dbZipcode = rs.getInt("zipcode");
                Project project = new Project(dbId,dbName,dbStartDate.toLocalDate(), dbEndDate.toLocalDate(), dbCustomerId, dbAddress, dbZipcode);
                projects.put(dbId, project);
            }
        }
        return projects;
    }
}
