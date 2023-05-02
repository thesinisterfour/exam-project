package dk.easv.dal.dao;

import dk.easv.be.City;
import dk.easv.be.Customer;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CityDAO implements ICRUDDao<City> {

    private final ConnectionManager cm = new ConnectionManager();
    @Override
    public int add(City city) throws SQLException {
        try(Connection con = cm.getConnection()){
            String sql = "INSERT INTO cities (zipcode, city_name)"+
                    "OUTPUT inserted.zipcode\n" +
                    "VALUES (?, ?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, city.getZipcode());
            ps.setString(2, city.getCityName());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
    }

    @Override
    public int update(City object) throws SQLException {
        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("" + "UPDATE dbo.[cities] SET zipcode=?, city_name=? " +
                    "WHERE zipcode=?;");

            ps.setInt(1, object.getZipcode());
            ps.setString(2, object.getCityName());
            ps.setInt(3, object.getZipcode());

            ps.executeQuery();
        }
        return 0;
    }

    @Override
    public City get(int zipcode) throws SQLException {
        try(Connection con = cm.getConnection()){
            String sql = "SELECT * FROM cities WHERE zipcode = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, zipcode);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new City(rs.getInt("zipcode"), rs.getString("city_name"));
        }
    }

    @Override
    public ConcurrentMap<Integer, City> getAll() throws SQLException {
        ConcurrentMap<Integer, City> cities = new ConcurrentHashMap<>();
        try (Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM dbo.[cities];"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int dbZip = rs.getInt("customer_id");
                String dbName = rs.getString("name");
                City city = new City(dbZip, dbName);
                cities.put(dbZip, city);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

    @Override
    public int delete(int id) throws SQLException {
        try(Connection connection = cm.getConnection()){
            PreparedStatement ps = connection.prepareStatement("" + "DELETE FROM dbo.[cities] WHERE zipcode=?;");
            ps.setInt(1, id);

            ps.executeQuery();
        }
        return 0;
    }
}
