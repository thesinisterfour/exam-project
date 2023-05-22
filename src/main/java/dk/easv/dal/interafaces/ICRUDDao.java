package dk.easv.dal.interafaces;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

/**
 * This is a public interface named ICRUDDao with generic type parameter T.
 * This interface specifies a set of methods that can be implemented by classes to perform CRUD operations on objects of type T.
 */
public interface ICRUDDao<T> {

    /**
     * This method is used to add a new object of type T to the data source.
     *
     * @return It takes an object as a parameter and returns an integer value.
     * @throws SQLException if there's an error during the database operation.
     */
    int add(T object) throws SQLException;

    /**
     * This method is used to update an existing object of type T in the data source.
     *
     * @return It takes an object as a parameter and returns an integer value.
     * @throws SQLException if there's an error during the database operation.
     */
    int update(T object) throws SQLException;

    /**
     * This method is used to retrieve an object of type T from the data source based on its ID.
     *
     * @return It takes an integer ID as a parameter and returns the corresponding object.
     * @throws SQLException if there's an error during the database operation.
     */
    T get(int id) throws SQLException;

    /**
     * This method is used to retrieve all objects of type T from the data source.
     *
     * @return It returns a ConcurrentMap where the keys are the IDs of the objects and the values are the objects themselves.
     * @throws SQLException if there's an error during the database operation.
     */
    ConcurrentMap<Integer, T> getAll() throws SQLException;

    /**
     * This method is used to delete an object of type T from the data source based on its ID.
     *
     * @return It takes an integer ID as a parameter and returns an integer value.
     * @throws SQLException if there's an error during the database operation.
     */
    int delete(int id) throws SQLException;
}

/**
 * By implementing this interface, you can create classes that provide the implementation for these CRUD operations for a specific type T.
 */