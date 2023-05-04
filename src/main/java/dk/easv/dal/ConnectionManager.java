package dk.easv.dal;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;
import dk.easv.Main;

import javax.sql.PooledConnection;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.*;

public class ConnectionManager {
    private static final String usualConfigPath = "config.cfg";
    private static ConnectionManager INSTANCE;
    private final SQLServerConnectionPoolDataSource ds;
    private ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();

    private BlockingDeque<PooledConnection> connections;

    private ConnectionManager() {
        this(usualConfigPath);
    }

    private ConnectionManager(String resourcePath) {
        Properties props = new Properties();
        try (InputStream resourceStream = Main.class.getResourceAsStream(resourcePath)) {
            props.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ds = new SQLServerConnectionPoolDataSource();
        ds.setServerName(props.getProperty("SERVER"));
        ds.setDatabaseName(props.getProperty("DATABASE"));
        ds.setPortNumber(Integer.parseInt(props.getProperty("PORT")));
        ds.setUser(props.getProperty("USER"));
        ds.setPassword(props.getProperty("PASSWORD"));
        ds.setTrustServerCertificate(true);

        connections = new LinkedBlockingDeque<>(20);

        es.schedule(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    connections.putLast(createConnection());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }, 0, TimeUnit.MILLISECONDS);
    }

    public static ConnectionManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionManager();
        }
        return INSTANCE;
    }


    public Connection getConnection() throws SQLException {
//        try {
//            return connections.takeFirst().getConnection();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return ds.getConnection();
    }

    private PooledConnection createConnection() {
        try {
            return ds.getPooledConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
