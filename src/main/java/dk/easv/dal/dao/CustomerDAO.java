package dk.easv.dal.dao;

import dk.easv.be.Customer;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class CustomerDAO implements ICRUDDao<Customer> {
    private final ConnectionManager cm = new ConnectionManager();

    @Override
    public int add(Customer customer) throws SQLException {
        try(Connection con = cm.getConnection()){
            if (customer == null) {
                throw new SQLException("Object cannot be null");
            } else {

                String sql = "INSERT INTO customer (name, email, address, zipcode) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, customer.getCustomerName());
                ps.setString(2, customer.getCustomerEmail());
                ps.setString(3, customer.getCustomerAddress());
                ps.setInt(4, customer.getZipCode());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Could not add customer");
                }
            }
        }
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
        return null;
    }

    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
