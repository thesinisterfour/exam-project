package dk.easv.dal;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;
import dk.easv.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.sql.PooledConnection;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.*;

public class ConnectionManager {
    private static final String usualConfigPath = "config.cfg";
    private static ConnectionManager INSTANCE;
    private final SQLServerConnectionPoolDataSource ds;

    private final ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
    private final int numberOfConnections = 50;
    private final BlockingDeque<PooledConnection> connections = new LinkedBlockingDeque<>(numberOfConnections);

    private ConnectionManager() {
        this(usualConfigPath);
    }

    private ConnectionManager(String resourcePath) {
        Properties props = new Properties();
        try (InputStream resourceStream = Main.class.getResourceAsStream(resourcePath)) {
            props.load(resourceStream);
        } catch (NullPointerException nullPointerException){
            showConfigNotFoundAlert(resourcePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ds = new SQLServerConnectionPoolDataSource();
        ds.setServerName(props.getProperty("SERVER"));
        ds.setDatabaseName(props.getProperty("DATABASE"));
        ds.setPortNumber(Integer.parseInt(props.getProperty("PORT")));
        ds.setUser(props.getProperty("USER"));
        ds.setPassword(props.getProperty("PASSWORD"));
        ds.setTrustServerCertificate(true);


        es.scheduleAtFixedRate(() -> {
            try {
                connections.putLast(createConnection());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }

    public static ConnectionManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionManager();
        }
        return INSTANCE;
    }


    public Connection getConnection() throws SQLException {
        try {
            return connections.takeFirst().getConnection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private PooledConnection createConnection() {
        try {
            return ds.getPooledConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopExecutorService() {
        es.shutdown();
        try {
            if (!es.awaitTermination(10, TimeUnit.SECONDS)){
                es.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void showConfigNotFoundAlert(String path) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Config file not found");
        alert.setContentText("""
                Config file not found
                Please create a config file in the following path:
                src/main/resources/dk/easv/config.cfg
                """ + path);
        Optional<ButtonType> resultButton = alert.showAndWait();
        if (resultButton.isPresent()) {
            if (resultButton.get() == ButtonType.OK) {
                System.exit(0);
            }
        } else {
            System.exit(1);
        }
    }

}
