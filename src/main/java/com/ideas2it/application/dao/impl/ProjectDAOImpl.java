package com.ideas2it.application.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.cfg.Configuration;
import org.hibernate.Criteria;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException; 
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.dao.Hibernate;
import com.ideas2it.application.dao.ProjectDAO;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.model.Project;
import com.ideas2it.application.model.Employee;
/**
 * <p>
 * ProjectDAOImpl class is where all the Database operations are done such
 * as storing modifying and deleting etc.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class ProjectDAOImpl extends Hibernate implements ProjectDAO {
    private static final String ADD_FAILED = 
        "Hibernate Exception occured while adding Project : ";
    private static final String RETRIEVE_PROJECTS_FAILED = 
        "Hibernate Exception occured while retrieving projects";
    private static final String RETRIEVE_PROJECT_FAILED = 
        "Hibernate Exception occured while retrieving Project : ";
    private static final String MODIFY_FAILED = 
        "Hibernate Exception occured while modifiying Project : ";
 
    /**
     * {@inheritDoc}
     */
    public boolean insertProject(Project project) throws ApplicationException {
        boolean isProjectAdded = Boolean.FALSE;
        Session session = getSession();
        Transaction transaction = null;
        try {
		    transaction = session.beginTransaction();
            session.save(project);
		    session.getTransaction().commit();
            isProjectAdded = Boolean.TRUE;
        } catch (HibernateException e) {
            ApplicationLogger.error(ADD_FAILED+project.getName(),e);
            throw new ApplicationException(ADD_FAILED + project.getName());
        } finally {
            session.close();
            return isProjectAdded;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean modifyProject(Project project) throws ApplicationException {
        Session session = getSession();
        Transaction transaction = null;
        boolean isProjectModified = Boolean.FALSE;
        try {
		    transaction = session.beginTransaction();
		    session.merge(project);
		    session.getTransaction().commit();
            isProjectModified = Boolean.TRUE;
        } catch (HibernateException e) {
            e.printStackTrace();
            ApplicationLogger.error(MODIFY_FAILED+project.getId(),e);
            throw new ApplicationException(MODIFY_FAILED + project.getId());
        } finally {
            session.close();
            return isProjectModified;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Project> retrieveProjectsByStatus(String status)
                                                 throws ApplicationException {
        Session session = getSession();
        List<Project> projects = null;
        try {
            Criteria criteria = session.createCriteria(Project.class);
            if (status == Constants.ALL) {
                projects = criteria.list();
            } else {
                projects =
                  criteria.add(Restrictions.eq(Constants.STATUS,status)).list();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            ApplicationLogger.error(RETRIEVE_PROJECTS_FAILED,e);
            throw new ApplicationException(RETRIEVE_PROJECTS_FAILED);
        } finally {
            session.close();
            return projects;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Project retrieveProjectById(int id)
                                        throws ApplicationException {
        Session session = getSession();
        Project project = null;
        try {
            Criteria criteria = session.createCriteria(Project.class);
            criteria.add(Restrictions.eq(Constants.ID, id));
            project = (Project) criteria.uniqueResult();
        } catch (HibernateException e) {
            ApplicationLogger.error(RETRIEVE_PROJECT_FAILED + id,e);
            throw new ApplicationException(RETRIEVE_PROJECT_FAILED + id);
        } finally {
            session.close();
            return project;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteProject(Project project) throws ApplicationException {
        return modifyProject(project);
    }
}
