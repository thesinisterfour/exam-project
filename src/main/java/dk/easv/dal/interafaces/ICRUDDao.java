package dk.easv.dal.interafaces;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public interface ICRUDDao<T>{
    int add(T object) throws SQLException;
    int update(T object) throws SQLException;
    T get(int id) throws SQLException;
    ConcurrentMap<Integer, T> getAll() throws SQLException;
    int delete(int id) throws SQLException;
}
