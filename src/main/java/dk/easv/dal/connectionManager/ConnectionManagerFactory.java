package dk.easv.dal.connectionManager;

import java.util.LinkedList;
import java.util.List;

public class ConnectionManagerFactory {
    private static final List<IConnectionManager> connectionManagers = new LinkedList<>();

    public static IConnectionManager createConnectionManager() {
        ConnectionManager cm = new ConnectionManager();
        connectionManagers.add(cm);
        return cm;
    }

    public static void stopAllConnectionManagers() {
        for (IConnectionManager cm : connectionManagers) {
            cm.stopExecutorService();
        }
    }
}
