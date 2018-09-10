package com.ideas2it.application.dao;

import java.util.List;

import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.User;

/**
 * <p>
 * UserDAO interface contains all method declarations
 * for user database manipulation.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public interface UserDAO {
 
    /**
     * <p>
     * createUser method is used to 
     * add the user object to the database
     * </p>
     *
     * @param user                   Object of User class
     *
     * @return boolean               Indicates whether the user is added or
     *                               not
     */
    public boolean createUser(User user) throws ApplicationException;

    /**
     * <p> 
     * getUserByEmail method is used to retrieve
     * a user for a given user email if present.
     * </p>
     *
     * @param email                  User Email
     *
     * @return User                  User object for concern email is returned.              
     */
    public User getUserByEmail(String email) throws ApplicationException;

    /**
     * <p> 
     * getUsers method is used to retrieve
     * all the users created.
     *
     * @return List<User>           List of Users.
     */
    public List<User> getUsers() throws ApplicationException;
}
