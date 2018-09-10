package com.ideas2it.application.service.impl;

import java.util.List;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.dao.UserDAO;
import com.ideas2it.application.dao.impl.UserDAOImpl;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.User;
import com.ideas2it.application.service.UserService;
import com.ideas2it.application.util.EncryptionUtil;

/**
 * <p>
 * UserService interface contains all method declarations
 * needed to perform business logics on user related information.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class UserServiceImpl implements UserService {
    private static UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public UserDAO getUserDAO() {
        return this.userDAO;
    }

    /**
     * {@inheritDoc}
     */
    public boolean createUser(User user) throws ApplicationException {
        String password = user.getPassword();
        user.setPassword(EncryptionUtil.encrypt(password));
        return userDAO.createUser(user);
    }

    /**
     * {@inheritDoc}
     */
    public User getUserByEmail (String email) throws ApplicationException {
        return userDAO.getUserByEmail(email);
    }
    /**
     * {@inheritDoc}
     */
    public List<User> getUsers() throws ApplicationException {
        return userDAO.getUsers();
    }

    
}
