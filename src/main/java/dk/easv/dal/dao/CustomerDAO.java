package dk.easv.dal.dao;

import dk.easv.be.City;
import dk.easv.be.Customer;
import dk.easv.be.User;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import dk.easv.dal.ConnectionManager;

public class CustomerDAO implements ICRUDDao<Customer> {
    private final ConnectionManager cm = new ConnectionManager();
    @Override
    public int add(Customer object) throws SQLException {
        return 0;
    }

    @Override
    public int update(Customer object) throws SQLException {
        return 0;
    }

    @Override
    public Customer get(int id) throws SQLException {
        return null;
    }

    @Override
    public ConcurrentMap<Integer, Customer> getAll() throws SQLException {
        ConcurrentMap<Integer, Customer> customers = new ConcurrentHashMap<>();
        try (Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("" +
                    "select dbo.[customer].[customer_id], dbo.[customer].[name], dbo.[customer].[email], dbo.[customer].[address], dbo.cities.city_name, dbo.cities.zipcode\n" +
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
