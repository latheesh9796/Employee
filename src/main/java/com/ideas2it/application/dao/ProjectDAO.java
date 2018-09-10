package com.ideas2it.application.dao;

import java.util.List;
import java.util.Set;

import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.Employee;
import com.ideas2it.application.model.Project;

/**
 * <p>
 * ProjectDAO interface contains all method declarations
 * for project database manipulation.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public interface ProjectDAO {

    /**
     * <p> 
     * insertProject method is used to create
     * a project and add it to the list of projects.
     *
     * @param project                Project object
     *
     * @return boolean               Indicates whether project is added or not
     */
    public boolean insertProject(Project project) throws ApplicationException;

    /**
     * <p> 
     * retrieveProjectsByStatus method is used to retrieve
     * all the projects created.
     *
     * @param status                 status indicates whether to retrieve
     *                               active/inactive projects.
     *
     * @return List<Project>          List of Projects.
     */
    public List<Project> retrieveProjectsByStatus(String status)
                                                 throws ApplicationException;

    /**
     * <p> 
     * deleteProject method is used to delete
     * the project belonging to that id.
     *
     * @param project                Project object
     *
     * @return boolean               Indicates whether project is deleted or not
     */
    public boolean deleteProject(Project project) throws ApplicationException;

    /**
     * <p> 
     * retrieveProjectById method is used to retrieve
     * a project for a given project id if present.
     * </p>
     *
     * @param id                     Project id
     *
     * @return Project               Project object for concern id is returned.              
     */
    public Project retrieveProjectById(int id)
                                throws ApplicationException;

    /**
     * <p>
     * modifyProject method is used to replace a
     * specific project with its newly modified object.
     * </p>
     *
     * @param project                Project object
     *
     * @return boolean               Indicates whether project is modified
     *                               or not
     */
    public boolean modifyProject(Project project) throws ApplicationException;
}
