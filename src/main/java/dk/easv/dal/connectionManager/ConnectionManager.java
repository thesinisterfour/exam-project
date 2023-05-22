package dk.easv.dal.connectionManager;

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

public class ConnectionManager implements IConnectionManager {
    private static final String usualConfigPath = "config.cfg";
    private final SQLServerConnectionPoolDataSource ds;

    private final ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
    private final int numberOfConnections = 20;
    private final BlockingDeque<PooledConnection> connections = new LinkedBlockingDeque<>(numberOfConnections);

    public ConnectionManager() {
        this(usualConfigPath);
    }

    /**
     * This constructor loads configuration properties from a resource file,
     * initializes a SQLServerConnectionPoolDataSource with the retrieved properties,
     * and schedules a task to create new database connections at a fixed rate.
     * Additionally, it handles exceptions related to resource loading and displays an alert if the configuration file is not found.
     */
    private ConnectionManager(String resourcePath) {
        Properties props = new Properties();
        try (InputStream resourceStream = Main.class.getResourceAsStream(resourcePath)) {
            props.load(resourceStream);
        } catch (NullPointerException nullPointerException) {
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

    /**
     * @return this code attempts to get a connection from a pool by taking the first available connection from the queue and returning it.
     * it throws a .
     * @throws RuntimeException wrapping the InterruptedException, if there is an interruption while waiting for a connection.
     * @throws SQLException     if there's an error during the database operation.
     */
    @Override
    public Connection getConnection() throws SQLException {
        try {
            return connections.takeFirst().getConnection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @return this code attempts to create a pooled connection by invoking the getPooledConnection() method on a data source (ds).
     * @throws SQLException (If an exception occurs during this process, it is caught and rethrown as a RuntimeException with the original SQLException as the cause.)
     */
    private PooledConnection createConnection() {
        try {
            return ds.getPooledConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this methode stops the executor service by calling shutdownNow().
     * This will attempt to stop the running tasks and return the list of pending tasks.
     */
    public void stopExecutorService() {
        es.shutdownNow();

    }

    /**
     * Overall, this method displays an error message in the form of an alert dialog,
     * informs the user about a missing configuration file, and provides instructions on how to create it.
     * Depending on the user's interaction, the program may terminate with different exit statuses.
     */
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
