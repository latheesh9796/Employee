package com.ideas2it.application.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.dao.impl.ProjectDAOImpl;
import com.ideas2it.application.dao.ProjectDAO;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.model.Employee;
import com.ideas2it.application.model.Project;
import com.ideas2it.application.service.EmployeeService;
import com.ideas2it.application.service.ProjectService;
import com.ideas2it.application.service.impl.EmployeeServiceImpl;

/**
 * <p>
 * ProjectServiceImpl class performs the operations such as removing and adding
 * employees to a particular project,getting unassigned employees and
 * other business logics.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class ProjectServiceImpl implements ProjectService{
    private static ProjectDAO projectDAO;
    private static EmployeeService employeeService;

    public  void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }
    public ProjectDAO getProjectDAO() {
        return this.projectDAO;
    }

    public  void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }
    /**
     * {@inheritDoc}
     */
    public boolean createProject(Project project) throws ApplicationException {
        project.setStatus(Constants.ACTIVE);
        return projectDAO.insertProject(project);
    }

    /**
     * {@inheritDoc}
     */
    public Project retrieveProjectById(int id) throws ApplicationException {
        return projectDAO.retrieveProjectById(id); 
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteProjectById(int id) throws ApplicationException{
        Project project = retrieveProjectById(id);
        if (null != project) {
            project.setStatus(Constants.INACTIVE);
            project.getEmployees().clear();
            project.setClientId(null);
            return projectDAO.deleteProject(project);
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Employee getEmployeeById(int id) throws ApplicationException {
        return employeeService.getEmployeeById(id);
    }

    /**
     * {@inheritDoc}
     */
    public List<Project> getProjectsByStatus(String status)
                                                 throws ApplicationException {
        return projectDAO.retrieveProjectsByStatus(status);
    }

    /**
     * {@inheritDoc}
     */
    public List<Employee> getEmployees() throws ApplicationException {
        return employeeService.retrieveEmployeesByStatus(Constants.ACTIVE);
    }

    /**
     * {@inheritDoc}
     */
    public boolean modifyProject(Project project) throws ApplicationException {
        return projectDAO.modifyProject(project);    
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmployeePresentInProject(Project project, int employeeId)
                                             throws ApplicationException {
        for (Employee employee : project.getEmployees()) {
            if (employee.getId() == employeeId) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeEmployeeFromProject(int employeeId,int projectId)
                                             throws ApplicationException {
        Project project = retrieveProjectById(projectId);
        Employee employee = getEmployeeById(employeeId);
        if(null == project || null == employee) {
            return Boolean.FALSE;
        } else {
            for (Employee teamMember : project.getEmployees()) {
                if (employee.getId().equals(teamMember.getId())) {
                    employee = teamMember;
                     break;
                 }
            }
            project.getEmployees().remove(employee);
            if(modifyProject(project)){
               return Boolean.TRUE;
            } else {
               return Boolean.TRUE;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean addEmployeesToProject(String[] ids,int projectId)
                                             throws ApplicationException {
        Project project = retrieveProjectById(projectId);
        for(String id : ids) {
            project.getEmployees().add(getEmployeeById(Integer.parseInt(id)));
        }
        if(modifyProject(project)){
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Employee> getUnassignedEmployees(Project project)
                                                throws ApplicationException {
        Employee removeEmployee = null;
        List<Employee> employees = getEmployees();
        for(Employee teamMember : project.getEmployees()) {
            for(Employee employee : employees) {
                if(teamMember.getId().equals(employee.getId())) {
                    removeEmployee = employee;
                    break;
                }
            }
            employees.remove(removeEmployee);
        }
        return employees;
    }
}
