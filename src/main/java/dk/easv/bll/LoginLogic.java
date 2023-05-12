package dk.easv.bll;

import dk.easv.be.User;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class LoginLogic implements ILoginLogic {
    @Override
    public User checkForUser(String username, String password) throws SQLException {
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        ConcurrentMap<Integer, User> userMap = userDAO.getAll();
        for (User user : userMap.values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}
