package dk.easv.dal.dao;

import dk.easv.be.City;
import dk.easv.be.Customer;
import dk.easv.dal.connectionManager.ConnectionManagerFactory;
import dk.easv.dal.connectionManager.IConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomerDAO implements ICRUDDao<Customer> {
    private final IConnectionManager cm = ConnectionManagerFactory.createConnectionManager();

    @Override
    public int add(Customer customer) throws SQLException {
        try(Connection con = cm.getConnection()){
                String sql = "INSERT INTO customer (name, email, address, zipcode) " +
                        "OUTPUT inserted.customer_id\n" +
                        "VALUES (?, ?, ?, ?);";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, customer.getCustomerName());
                ps.setString(2, customer.getCustomerEmail());
                ps.setString(3, customer.getCustomerAddress());
                ps.setInt(4, customer.getZipCode());
                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Customer object) throws SQLException {
        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("UPDATE dbo.[customer] SET name=?, email=?, address=?, zipcode=? WHERE customer_id=?;");

            ps.setString(1, object.getCustomerName());
            ps.setString(2, object.getCustomerEmail());
            ps.setString(3, object.getCustomerAddress());
            ps.setInt(4, object.getZipCode());
            ps.setInt(5, object.getCustomerID());

            ps.executeQuery();
        }
        return 0;
    }

    @Override
    public Customer get(int id) throws SQLException {
        try(Connection connection = cm.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbo.[customer] INNER JOIN dbo.cities ON dbo.cities.zipcode = dbo.customer.zipcode WHERE customer_id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                City city = new City(rs.getInt("zipcode"), rs.getString("city_name"));
                return new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("address"), city);
            }
        }
        return null;
    }

    @Override
    public ConcurrentMap<Integer, Customer> getAll() throws SQLException {
        ConcurrentMap<Integer, Customer> customers = new ConcurrentHashMap<>();
        try (Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("select dbo.[customer].[customer_id], dbo.[customer].[name], dbo.[customer].[email], dbo.[customer].[address], dbo.cities.city_name, dbo.cities.zipcode\n" +
                    "from dbo.customer\n" +
                    "inner join dbo.cities on dbo.cities.zipcode = dbo.customer.zipcode;"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int dbId = rs.getInt("customer_id");
                String dbName = rs.getString("name");
                String dbEmail = rs.getString("email");
                String dbAddress = rs.getString("address");
                int dbZipcode = rs.getInt("zipcode");
                String dbCityName = rs.getString("city_name");
                City city = new City(dbZipcode, dbCityName);
                Customer customer = new Customer(dbId,dbName,dbEmail,dbAddress,city);
                customers.put(dbId, customer);
            }
        }
        return customers;
    }

    @Override
    public int delete(int id) throws SQLException {
        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("DELETE FROM dbo.[customer] WHERE customer_id=?;");
            ps.setInt(1, id);

            ps.executeQuery();
        }
        return 0;
    }
}
