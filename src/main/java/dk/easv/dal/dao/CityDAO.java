package dk.easv.dal.dao;

import dk.easv.be.City;
import dk.easv.be.Customer;
import dk.easv.dal.ConnectionManager;
import dk.easv.dal.interafaces.ICRUDDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(City object) throws SQLException {
        return 0;
    }

    @Override
    public City get(int id) throws SQLException {
        return null;
    }

    @Override
    public ConcurrentMap<Integer, City> getAll() throws SQLException {
        return null;
    }

    @Override
    public int delete(int id) throws SQLException {
        return 0;
    }
}
