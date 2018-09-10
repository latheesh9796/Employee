package com.ideas2it.application.service;

import java.util.List;

import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.User;

/**
 * <p>
 * UserService interface contains all method declarations
 * needed to perform business logics on user related information.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public interface UserService {

    /**
     * <p>
     * createUser method takes input of a user information,encrypts the data 
     * and stores it in database.
     * </p>
     *
     * @param user                   User Object
     *
     * @return boolean               Indicates whether user is created or not
     */
    public boolean createUser(User user) throws ApplicationException;

    /**
     * <p> 
     * retrieveUserByEmail method is used to retrieve
     * the user information belonging to that id.
     *
     * @param email                     User email
     *
     * @return User                     User object for given email is returned
     */
    public User getUserByEmail(String email) throws ApplicationException;

    /**
     * <p> 
     * getUsers method is used to get
     * the employee detail by its id and returns it.
     * </p>
     *
     * @return List<User>              List of users are returned
     */
    public List<User> getUsers() throws ApplicationException;

}
