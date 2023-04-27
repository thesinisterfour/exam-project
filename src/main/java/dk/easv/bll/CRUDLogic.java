package dk.easv.bll;

import dk.easv.be.User;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

public class CRUDLogic {
    private ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
    private User user;

    public int addUser(User user) throws SQLException, NullPointerException {
        if (userDAO == null) {
            throw new NullPointerException("UserDAO is null");
        } else {
            return userDAO.add(user);
        }
    }

    public User checkForUser(String username, String password) throws SQLException {
        ConcurrentMap<Integer, User> userMap = userDAO.getAll();
        List<Map.Entry<Integer, User>> userList = new ArrayList<>(userMap.entrySet());
        for (Map.Entry<Integer, User> selectedUser:userList) {
            if (selectedUser.getValue().getUsername().equals(username) && selectedUser.getValue().getPassword().equals(password)){
                return selectedUser.getValue();
            }
        }
        return null;
    }


}
