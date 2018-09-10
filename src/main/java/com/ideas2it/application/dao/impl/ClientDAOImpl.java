package com.ideas2it.application.dao.impl;

import java.util.List;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.dao.ClientDAO;
import com.ideas2it.application.dao.Hibernate;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.model.Client;
import com.ideas2it.application.model.Project;

import org.hibernate.cfg.Configuration;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * <p>
 * ClientDAOImpl class is where all the Database operations are done such
 * as storing modifying and deleting etc.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class ClientDAOImpl extends Hibernate implements ClientDAO {
    private static final String ADD_FAILED =
        "Hibernate Exception occured while adding Client : ";
    private static final String RETRIEVE_CLIENTS_FAILED =
        "Hibernate Exception occured while retrieving clients";
    private static final String RETRIEVE_CLIENT_FAILED =
        "Hibernate Exception occured while retrieving Client : ";
    private static final String MODIFY_FAILED =
        "Hibernate Exception occured while modifiying Client : ";

    /**
     * {@inheritDoc}
     */
    public boolean insertClient(Client client) throws ApplicationException {
        Boolean isClientAdded = Boolean.FALSE;
        Session session = getSession();
        Transaction transaction = null;
        try {
		    transaction = session.beginTransaction();
            session.save(client);
		    transaction.commit();
            isClientAdded = Boolean.TRUE;
        } catch (HibernateException e) {
            ApplicationLogger.error(ADD_FAILED+client.getName(),e);
            throw new ApplicationException(ADD_FAILED + client.getName());
        } finally {
            session.close();
            return isClientAdded;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Client> retrieveClientsByStatus(String status)
                                                 throws ApplicationException {
        Session session = getSession();
        List<Client> clients = null;
        try {
            Criteria criteria = session.createCriteria(Client.class);
            if (status == Constants.ALL) {
                clients = criteria.list();
            } else {
                clients =
                  criteria.add(Restrictions.eq(Constants.STATUS,status)).list();
            }
        } catch (HibernateException e) {
            System.out.println("Client exception");
            e.printStackTrace();
            ApplicationLogger.error(RETRIEVE_CLIENTS_FAILED,e);
            throw new ApplicationException(RETRIEVE_CLIENTS_FAILED);
        } finally {
            session.close();
            return clients;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Client fetchClientById(int id) throws ApplicationException {
        Session session = getSession();
        Client client = null;
        try {
            Criteria criteria = session.createCriteria(Client.class);
            criteria.add(Restrictions.eq(Constants.ID, id));
            client = (Client) criteria.uniqueResult();
            return client;
        } catch (HibernateException e) {
            ApplicationLogger.error(RETRIEVE_CLIENT_FAILED + id,e);
            throw new ApplicationException(RETRIEVE_CLIENT_FAILED + id);
        } finally {
            session.close();
            return client;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean modifyClient(Client client) throws ApplicationException{
        Session session = getSession();
        Transaction transaction = null;
        Boolean isClientModified = Boolean.FALSE;
        try {
		    transaction = session.beginTransaction();
		    session.update(client);
		    transaction.commit();
            isClientModified = Boolean.TRUE;
        } catch (HibernateException e) {
            ApplicationLogger.error(MODIFY_FAILED+client.getId(),e);
            throw new ApplicationException(MODIFY_FAILED + client.getId());
        } finally {
            session.close();
            return isClientModified;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteClient(Client client) throws ApplicationException{
        return modifyClient(client);
    }
}
