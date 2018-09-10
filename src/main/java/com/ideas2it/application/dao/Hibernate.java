package com.ideas2it.application.dao;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;

import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * <p>
 * Hibernate class is the parent class for all other DAO's which has
 * all the hibernate configuration code and other hibernate related 
 * functionalities in it.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class Hibernate {
    private static final String SESSION_FACTORY_FAILED =
                                        "Session factory build failed!";
    private static final String TRANSACTION_ROLLBACK =
                                        "Transaction has been rolled back.";
    private static SessionFactory sessionFactory = null;
    private static String ERROR_INFO = "Error connecting";
    private static String URL = 
                            "hibernate.cfg.xml";

    private void createSessionFactory() throws ApplicationException {
        try {
            sessionFactory = 
                      new Configuration().configure(URL).buildSessionFactory();
        } catch (HibernateException e) {
            ApplicationLogger.error(ERROR_INFO, e);
            throw new ApplicationException(ERROR_INFO);  
        }
    }

    /**
     * <p>
     *   Used to return session which is used to connect dataBase.
     * </p>
     *
     * @return   session   Returns session to perfrom CRUD operation. 
     */   
    protected Session getSession() throws ApplicationException {
        if(null == sessionFactory){
            createSessionFactory();
        }
        return sessionFactory.openSession();
    }
}
