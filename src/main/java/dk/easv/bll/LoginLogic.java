package dk.easv.bll;

import dk.easv.be.User;
import dk.easv.bll.tasks.GetAllUsersTask;
import dk.easv.gui.models.interfaces.ILoginModel;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginLogic implements ILoginLogic {
    ConcurrentMap<Integer, User> userMap;

    public LoginLogic(ILoginModel loginModel) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        GetAllUsersTask task = new GetAllUsersTask();
        task.valueProperty().addListener(((observable, oldValue, newValue) -> {
            userMap = newValue;
            notifyAllowLogin(loginModel);
        }));
        es.submit(task);
        es.shutdown();
    }

    private void notifyAllowLogin(ILoginModel model) {
        model.setLoginReady(true);
    }

    @Override
    public User checkForUser(String username, String password) throws SQLException {
        for (User user : userMap.values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
