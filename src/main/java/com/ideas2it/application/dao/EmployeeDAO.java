package com.ideas2it.application.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.Employee;

/**
 * <p>
 * EmployeeDAO interface contains all method declarations
 * for employee database manipulation.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public interface EmployeeDAO {

    /**
     * <p>
     * insertEmployee method is used to create and
     * add the employee map from the employee object.
     * </p>
     *
     * @param employee               Object of Employee class
     *
     * @return boolean               Indicates whether the employee is added or
     *                               not              
     */
    public boolean insertEmployee(Employee employee) 
                                    throws ApplicationException;

    /**
     * <p>
     * retrieveEmployees method is used to
     * retrieve the employee set and returns it.
     * </p>
     *
     * @param status                 status indicates whether to retrieve
     *                               active/inactive users
     *
     * @return List                  List that contains all the employee objects
     */
    public List<Employee> retrieveEmployeesByStatus(String status)
                                                throws ApplicationException;

    /**
     * <p>
     * modifyEmployee method is used to replace a
     * specific employee with his newly modified object.
     * </p>
     *
     * @param employee               Employee object.
     *
     * @return boolean
     */
    public boolean modifyEmployee(Employee employee)
                                throws ApplicationException;


    /**
     * <p>
     * deleteEmployee method is used to delete the
     * the employee object.
     * </p>
     *
     * @param employee               Employee object.
     *
     * @return boolean               Indicates whether the employee is deleted
     *                               or not
     */
    public boolean deleteEmployee(Employee employee)
                                throws ApplicationException;

    /**
     * <p>
     * retrieveEmployeeById method is used to retrieve
     * the employee given his employee id.
     * </p>
     *
     * @param id                     Employee id.
     *
     * @return Employee              Employee object for concern id is returned
     */
    public Employee retrieveEmployeeById(int id)
                                    throws ApplicationException;
}
