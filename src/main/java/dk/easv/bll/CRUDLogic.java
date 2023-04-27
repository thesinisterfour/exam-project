package dk.easv.bll;

import dk.easv.be.User;
import dk.easv.dal.CRUDDAOFactory;
import dk.easv.dal.interafaces.ICRUDDao;
import dk.easv.helpers.DAOType;

import java.sql.SQLException;

public class CRUDLogic {
    public int addUser(User user) throws SQLException, NullPointerException {
        ICRUDDao<User> userDAO = CRUDDAOFactory.getDao(DAOType.USER_DAO);
        if (userDAO == null) {
            throw new NullPointerException("UserDAO is null");
        } else {
            return userDAO.add(user);
        }



    }


}
