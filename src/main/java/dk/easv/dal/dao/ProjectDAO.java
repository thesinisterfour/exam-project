package dk.easv.dal.dao;

import dk.easv.be.Doc;
import dk.easv.be.Project;
import dk.easv.dal.connectionManager.ConnectionManagerFactory;
import dk.easv.dal.connectionManager.IConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.dal.interafaces.IProjectMapper;

import java.sql.*;
import java.time.LocalDate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProjectDAO implements ICRUDDao<Project>, IProjectMapper {
    private final IConnectionManager cm = ConnectionManagerFactory.createConnectionManager();

    @Override
    public int add(Project object) throws SQLException {
        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("INSERT INTO dbo.[project] (project_name, project_start_date, " +
                    "project_end_date, customer_id, address, zipcode) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, object.getProjectName());
            ps.setDate(2, Date.valueOf(object.getStartDate()));
            ps.setDate(3, Date.valueOf(object.getEndDate()));
            ps.setInt(4, object.getCustomerID());
            ps.setString(5, object.getProjectAddress());
            ps.setInt(6, object.getProjectZipcode());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                return rs.getInt(1);
            }
        }
        return 0;
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

            return ps.executeUpdate();
        }
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
        try (Connection connection = cm.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbo.[project]");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int dbProject_id = rs.getInt("project_id");
                String dbProject_name = rs.getString("project_name");
                LocalDate dbProject_start_date = rs.getDate("project_start_date") != null ? rs.getDate("project_start_date").toLocalDate() : null;
                LocalDate dbProject_end_date = rs.getDate("project_end_date") != null ? rs.getDate("project_end_date").toLocalDate() : null;
                int dbCustomer_id = rs.getInt("customer_id");
                String dbAddress = rs.getString("address");
                int dbZipcode = rs.getInt("zipcode");

                Project project = new Project(dbProject_id, dbProject_name, dbProject_start_date, dbProject_end_date, dbCustomer_id, dbAddress, dbZipcode);
                projects.put(dbProject_id, project);

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
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbo.[project] WHERE customer_id=?;");
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

    @Override
    public ConcurrentMap<Integer, Doc> getDocumentsByProjectId(int id) throws SQLException {
        ConcurrentMap<Integer, Doc> documents = new ConcurrentHashMap<>();
        try (Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbo.documents INNER JOIN project_documents pd on documents.document_id = pd.document_id WHERE pd.project_id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int dbId = rs.getInt("document_id");
                String dbName = rs.getString("document_name");
                Date dateCreated = rs.getDate("date_created");
                Date dateEdited = rs.getDate("date_last_opened");
                String description = rs.getString("description");
                Doc document = new Doc(dbId,dbName,dateCreated.toLocalDate(), dateEdited == null ? null : dateEdited.toLocalDate(), description);
                documents.put(dbId, document);

            }
        }
        return documents;
    }

    @Override
    public ConcurrentMap<Integer, Project> getProjectsByWorkerId(int id) throws SQLException {
        ConcurrentMap<Integer, Project> projects = new ConcurrentHashMap<>();
        try (Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbo.[project] INNER JOIN projects_users pu on project.project_id = pu.project_id WHERE pu.user_id=?;");
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

    @Override
    public int addUserToProject(int projectId, int userId) throws SQLException {
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO dbo.projects_users (project_id, user_id) VALUES (?,?);");
            ps.setInt(1, projectId);
            ps.setInt(2, userId);
            return ps.executeUpdate();
        }
    }

    @Override
    public int deassignProject(int projectId, int userId) throws SQLException {
        try (Connection con = cm.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM dbo.projects_users WHERE project_id=? AND user_id=?;");
            ps.setInt(1, projectId);
            ps.setInt(2, userId);
            return ps.executeUpdate();
        }
    }
}
