package com.ideas2it.application.service;

import java.util.List;
import java.util.Set;

import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.Employee;
import com.ideas2it.application.model.Project;

/**
 * <p>
 * ProjectService interface contains all method declarations
 * needed to perform business logics on project related informations
 * </p>
 *
 * @author Latheeshwar Raj
 */
public interface ProjectService {

    /**
     * <p>
     * createProject method takes input of name,
     * head of project and set of members and creates a
     * project object with it.
     * </p>
     *
     * @param project                Project Object
     *
     * @return boolean               Indicates whether project is created or not
     */
    public boolean createProject(Project project) throws ApplicationException;

    /**
     * <p> 
     * retrieveProjectById method is used to retrieve
     * the project belonging to that id.
     *
     * @param id                     Project ID
     *
     * @return Project               Project object for given id is returned
     */
    public Project retrieveProjectById (int id) throws ApplicationException;

    /**
     * <p> 
     * getEmployeeById method is used to get
     * the employee detail by its id and returns it.
     * </p>
     *
     * @param id                     Employee ID
     *
     * @return Employee              Fetched employee is returned
     */
    public Employee getEmployeeById(int id) throws ApplicationException;

    /**
     * <p> 
     * deleteProjectById method is used to delete
     * the project completely.
     * </p>
     *
     * @param id                     Project ID
     *
     * @return boolean               Indicates whether project is deleted or not
     */
    public boolean deleteProjectById(int id) throws ApplicationException;

    /**
     * <p> 
     * getProjectsByStatus method is used to return
     * all the projects created.
     * </p>
     * @param status                 status indicates whether to retrieve
     *                               active/inactive projects
     *
     * @return List<Project>          List of projects are returned
     */
    public List<Project> getProjectsByStatus(String status)
                                                 throws ApplicationException;

    /**
     * <p> 
     * getEmployees method is used to return
     * all the active employees.
     * </p>
     *
     * @return List<Employee>          List of employees are returned
     */
    public List<Employee> getEmployees() throws ApplicationException;


    /**
     * <p> 
     * modifyProject method is used to
     * modify a project details.
     * </p>
     *
     * @return boolean              Indicates whether project is 
     *                              successfully modified or not
     */
    public boolean modifyProject(Project project) throws ApplicationException;

    /**
     * <p>
     * isEmployeePresentInProject method is used to find if
     * an employee with a specific employee id is present in
     * a project object or not.
     *
     * @param project                Project object
     * @param employeeId             Employee Id     
     *
     * @return boolean              
     * </p>
     */
    public boolean isEmployeePresentInProject(Project project, int employeeId)
                                             throws ApplicationException;

    /**
     * <p>
     * removeEmployeeFromProject method is used to remove a 
     * an employee from a project given their respectives id.
     *
     * @param projectId              Project Id
     * @param employeeId             Employee Id     
     *
     * @return boolean              
     * </p>
     */
    public boolean removeEmployeeFromProject(int employeeId,int projectId)
                                             throws ApplicationException;

    /**
     * <p>
     * addEmployeesToProject method is used to add a list of 
     * an employees given their id's in a string array to a project for a
     * given projectId
     *
     * @param projectId              Project Id
     * @param ids                    Array of Employee Id's in string format
     *
     * @return boolean              
     * </p>
     */
    public boolean addEmployeesToProject(String[] ids,int projectId)
                                             throws ApplicationException;
    /**
     * <p>
     * getUnassignedEmployees method is to get unassigned employees for project
     * </p>
     *
     * @param project                Project object
     *
     * @param List<Employee>         List of unassigned employees      
     */ 
    public List<Employee> getUnassignedEmployees(Project project)
                                                throws ApplicationException;
}
