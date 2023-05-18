package dk.easv.dal.connectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionManager {
    Connection getConnection() throws SQLException;

    void stopExecutorService();
}
