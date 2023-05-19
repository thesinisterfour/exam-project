package dk.easv.bll.tasks;

import dk.easv.be.User;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;
import javafx.concurrent.Task;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class GetAllUsersTask extends Task<ConcurrentMap<Integer, User>> {
    @Override
    protected ConcurrentMap<Integer, User> call() throws Exception {
        return getAllUsers();
    }

    private ConcurrentMap<Integer, User> getAllUsers() throws SQLException {
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        return userDAO.getAll();
    }
}
