package dk.easv.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.Main;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class ConnectionManager {
    private static final String usualConfigPath = "/config.cfg";
    private final SQLServerDataSource ds;

    public ConnectionManager() {
        this(usualConfigPath);
    }

    public ConnectionManager(String resourcePath) {
        Properties props = new Properties();
        try (InputStream resourceStream = Main.class.getResourceAsStream(resourcePath)) {
            props.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ds = new SQLServerDataSource();
        ds.setServerName(props.getProperty("SERVER"));
        ds.setDatabaseName(props.getProperty("DATABASE"));
        ds.setPortNumber(Integer.parseInt(props.getProperty("PORT")));
        ds.setUser(props.getProperty("USER"));
        ds.setPassword(props.getProperty("PASSWORD"));
        ds.setTrustServerCertificate(true);
    }


    public Connection getConnection() throws SQLServerException {
        return ds.getConnection();
    }

}
