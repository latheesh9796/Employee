package com.ideas2it.application.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.dao.Hibernate;
import com.ideas2it.application.dao.UserDAO;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.model.User;

import org.hibernate.cfg.Configuration;
import org.hibernate.Criteria;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException; 
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 * <p>
 * ProjectDAOImpl class is where all the Database operations are done such
 * as storing modifying and deleting etc.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class UserDAOImpl extends Hibernate implements UserDAO {
    private static final String ADD_FAILED = 
        "Hibernate Exception occured while adding User : ";
    private static final String RETRIEVE_USERS_FAILED = 
        "Hibernate Exception occured while retrieving users";
    private static final String RETRIEVE_USER_FAILED = 
        "Hibernate Exception occured while retrieving user : ";
    private static final String MODIFY_FAILED = 
        "Hibernate Exception occured while modifiying Project : ";
 
    /**
     * {@inheritDoc}
     */
    public boolean createUser(User user) throws ApplicationException {
        boolean isUserAdded = Boolean.FALSE;
        Session session = getSession();
        Transaction transaction = null;
        try {
		    transaction = session.beginTransaction();
            session.save(user);
		    session.getTransaction().commit();
            isUserAdded = Boolean.TRUE;
        } catch (HibernateException e) {
            ApplicationLogger.error(ADD_FAILED + user.getEmail(),e);
            throw new ApplicationException(ADD_FAILED+user.getEmail());
        } finally {
            session.close();
            return isUserAdded;
        }
    }

    /**
     * {@inheritDoc}
     */
    public User getUserByEmail(String email) throws ApplicationException {
        Session session = getSession();
        User user = null;
        try {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq(Constants.EMAIL, email));
            user = (User) criteria.uniqueResult();
        } catch (HibernateException e) {
            ApplicationLogger.error(RETRIEVE_USER_FAILED + user.getEmail(),e);
            throw new ApplicationException(RETRIEVE_USER_FAILED + user.getEmail());
        } finally {
            session.close();
            return user;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers() throws ApplicationException {
        Session session = getSession();
        List<User> users = null;
        try {
            Criteria criteria = session.createCriteria(User.class);
            users = criteria.list();
        } catch (HibernateException e) {
            ApplicationLogger.error(RETRIEVE_USERS_FAILED,e);
            throw new ApplicationException(RETRIEVE_USERS_FAILED);
        } finally {
            session.close();
            return users;
        }
    }
}
