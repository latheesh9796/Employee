package com.ideas2it.application.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.dao.EmployeeDAO;
import com.ideas2it.application.dao.Hibernate;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.model.Employee;

import org.hibernate.cfg.Configuration;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.HibernateException; 
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * <p>
 * EmployeeDAOImpl class is where all the Database operations are done such
 * as storing modifying and deleting etc.
 * </p>
 *
 * @author Latheeshwar Raj
 */

public class EmployeeDAOImpl extends Hibernate implements EmployeeDAO {
    private static final String ADD_FAILED = 
        "Hibernate Exception occured while adding Employee : ";
    private static final String RETRIEVE_EMPLOYEES_FAILED = 
        "Hibernate Exception occured while retrieving employees";
    private static final String RETRIEVE_EMPLOYEE_FAILED = 
        "Hibernate Exception occured while retrieving Employee : ";
    private static final String MODIFY_FAILED = 
        "Hibernate Exception occured while modifiying Employee : ";

    /**
     * {@inheritDoc}
     */
    public boolean insertEmployee(Employee employee) 
                                        throws ApplicationException {
        Boolean isEmployeeAdded = Boolean.FALSE;
        Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(employee); 
            transaction.commit();
            isEmployeeAdded = Boolean.TRUE;
        } catch (HibernateException e) {
            ApplicationLogger.error(ADD_FAILED+employee.getName(),e);
            throw new ApplicationException(ADD_FAILED + employee.getName());
        } finally {
            session.close();
            return isEmployeeAdded;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Employee> retrieveEmployeesByStatus(String status)
                                        throws ApplicationException {
        Session session = getSession();
        List<Employee> employees = null;
        try {
            Criteria criteria = session.createCriteria(Employee.class);
            if (status == Constants.ALL) {
                employees = criteria.list();
            } else {
                employees = criteria.add
                        (Restrictions.eq(Constants.STATUS,status)).list();
            }
            ApplicationLogger.error("Employees - "+employees,new Exception());
        } catch (HibernateException e) {
            System.out.println("Employee exception");
            e.printStackTrace();
            ApplicationLogger.error(RETRIEVE_EMPLOYEES_FAILED,e);
            throw new ApplicationException(RETRIEVE_EMPLOYEES_FAILED);
        } finally {
            session.close();
            return employees;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Employee retrieveEmployeeById(int id) 
                                throws ApplicationException {
        Session session = getSession();
        Employee employee = null;
        try {
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq(Constants.ID, id));
            employee = (Employee) criteria.uniqueResult();
        } catch (HibernateException e) {
            ApplicationLogger.error(RETRIEVE_EMPLOYEE_FAILED + id,e);
            throw new ApplicationException(RETRIEVE_EMPLOYEE_FAILED+id);
        } finally {
            session.close();
            return employee;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean modifyEmployee(Employee employee)
                        throws ApplicationException {
        Session session = getSession();
        boolean isEmployeeModified = Boolean.FALSE;
        Transaction transaction = null;
        try {
		    transaction = session.beginTransaction();
		    session.update(employee);
		    transaction.commit();
            isEmployeeModified = Boolean.TRUE;
        } catch (HibernateException e) {
            ApplicationLogger.error(MODIFY_FAILED + employee.getId(),e);
            throw new ApplicationException(MODIFY_FAILED+employee.getId());
        } finally {
            session.close();
            return isEmployeeModified;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteEmployee(Employee employee)
                                        throws ApplicationException {
        employee.setStatus(Constants.INACTIVE);
        employee.getProjects().clear();
        return modifyEmployee(employee);
    }
}
