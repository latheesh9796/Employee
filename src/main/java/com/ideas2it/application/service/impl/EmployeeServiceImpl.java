package com.ideas2it.application.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

import com.ideas2it.application.dao.EmployeeDAO;
import com.ideas2it.application.dao.impl.EmployeeDAOImpl;
import com.ideas2it.application.exception.ApplicationException;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.model.Employee;
import com.ideas2it.application.service.EmployeeService;
import com.ideas2it.application.service.ProjectService;
import com.ideas2it.application.service.impl.ProjectServiceImpl;

/**
 * <p>
 * EmployeeService class is the class which performs operations such as
 * removing projects related to the employee when the employee is deleted and
 * other business logics
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class EmployeeServiceImpl implements EmployeeService {
    private static EmployeeDAO employeeDAO;

    public  void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    public EmployeeDAO getEmployeeDAO() {
        return this.employeeDAO;
    }
    /**
     * {@inheritDoc}
     */
    public boolean addEmployeeDetails(Employee employee)
                                            throws ApplicationException {
        employee.setStatus(Constants.ACTIVE);
        return getEmployeeDAO().insertEmployee(employee);
    }

    /**
     * {@inheritDoc}
     */
    public boolean modifyEmployee(Employee employee)
                                            throws ApplicationException {
        return getEmployeeDAO().modifyEmployee(employee);
   }

    /**
     * {@inheritDoc}
     */
    public List<Employee> retrieveEmployeesByStatus(String status)
                                            throws ApplicationException {
        return getEmployeeDAO().retrieveEmployeesByStatus(status);
    }

    /**
     * {@inheritDoc}
     */
    public Employee getEmployeeById(int id) throws ApplicationException {
        return getEmployeeDAO().retrieveEmployeeById(id);
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteEmployeeById(int employeeId)
                                        throws ApplicationException {
        Employee employee = getEmployeeById(employeeId);
        if (null != employee) {
            return getEmployeeDAO().deleteEmployee(employee);
        } else {
            return Boolean.FALSE;
        }
    }
}   

