package com.ideas2it.application.service;

import java.util.List;

import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.Employee;
import com.ideas2it.application.model.Project;

/**
 * <p>
 * EmployeeService interface contains all method declarations
 * needed to perform business logics on employee related informations
 * </p>
 *
 * @author Latheeshwar Raj
 */
public interface EmployeeService {

    /**
     * <p>
     * addEmployeeDetails method is used to add
     * the employee to the database.
     * </p>
     *
     * @param employee               Employee object.
     * 
     * @return boolean               Indicates whether employee is successfully
     *                               added or not.
     */
    public boolean addEmployeeDetails(Employee employee)
                                        throws ApplicationException;

    /**
     * <p>
     * retrieveEmployees method is used to retrieve
     * the employee list from the DAO class.
     * </p>
     *
     * @param status                 status indicates whether to retrieve
     *                               active/inactive users
     *
     * @return L<Employee>           List of Employees are returned
     */
    public List<Employee> retrieveEmployeesByStatus(String status)
                                                throws ApplicationException;

    /**
     * <p>
     * getEmployeeById method is used to retrieve the
     * the employee object 
     * </p>
     *
     * @return Employee              Employee object for given id is returned
     */
    public Employee getEmployeeById(int id) throws ApplicationException;

    /**
     * <p>
     * deleteEmployeeById method is used to delete the
     * the employee object specific to that employee ID.
     * </p>
     *
     * @param id                     Employee Id
     *
     * @return boolean               Indicates whether employee is deleted or
     *                               not
     */
    public boolean deleteEmployeeById(int id) throws ApplicationException;

    /**
     * <p>
     * modifyEmployee method is used for modifying
     * a specific employee object.
     * </p>
     *
     * @param employee               Employee object.
     *
     * @return boolean               Indicates whether employee details are
     *                               modified or not
     */
    public boolean modifyEmployee(Employee employee)
                                            throws ApplicationException;
}
