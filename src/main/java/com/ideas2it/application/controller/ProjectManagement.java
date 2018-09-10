package com.ideas2it.application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Scanner;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.service.impl.ProjectServiceImpl;
import com.ideas2it.application.service.ProjectService;
import com.ideas2it.application.model.Employee;
import com.ideas2it.application.model.Project;

import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.ModelAttribute;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

/**
 * <p>
 * ProjectManagement class acts as a user interface which interacts with the
 * user and perform operations with the Project details.
 * </p>
 *
 * @author Latheeshwar Raj
 */
@Controller
public class ProjectManagement extends HttpServlet {

    private static ProjectService projectService;

    public  void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    public ProjectService getProjectService() {
        return this.projectService;
    }

    /**
     * <p>
     * displayProjects is used to display all the projects present in database
     * irrespective of their status.
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping(value = "/projectPage",method = RequestMethod.GET)  
    public ModelAndView displayProjects(){
        try {
            ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
            List<Project> projects = 
                projectService.getProjectsByStatus(Constants.ALL);
            modelAndView.addObject(Constants.PROJECTS, projects);
            return modelAndView;
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * createProject is used to return a empty project object inorder
     * to populate itself inside the form and bind the values into it.
     *
     * @param id                      Project id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping(value="/createProject",method = RequestMethod.POST)  
    public ModelAndView createProject(Model model){
        try {
            ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
            List<Project> projects = 
                projectService.getProjectsByStatus(Constants.ALL);
            modelAndView.addObject(Constants.PROJECTS, projects);
            modelAndView.addObject(Constants.ADD_BUTTON,Constants.YES);
            Project project = new Project();
            model.addAttribute(Constants.ATTR_PROJECT, project);
            modelAndView.addObject(Constants.COMMAND,model);
            return modelAndView;
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * addProject method is to add the project to the database.
     * </p>
     *
     * @param project                Project object
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
   @RequestMapping("/addProject")   
    public ModelAndView addProject
                    (@ModelAttribute(Constants.ATTR_PROJECT) Project project){
        try {
            ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
            if(projectService.createProject(project)) {
                modelAndView.addObject(Constants.MESSAGE,Constants.PROJECT_SUCCESS_ADD);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,Constants.PROJECT_FAIL_ADD);
            }
            return dispatchResponse(new ModelAndView(Constants.PROJECT_JSP_URL));
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * DeleteProject accepts the project id and soft deletes the
     * project for the concern id.
     *
     * @param id                     Project id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/DeleteProject")  
    public ModelAndView DeleteProject(@RequestParam(Constants.ID) int id) {
        ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
        try {
            if (projectService.deleteProjectById(id)){
                modelAndView.addObject(Constants.MESSAGE,
                                Constants.PROJECT_ID + id + Constants.REMOVED);
                return dispatchResponse(modelAndView);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,
                           Constants.PROJECT_ID + id + Constants.DELETE_FAILED);
                return dispatchResponse(modelAndView);
            }
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * ModifyProject accepts the project id and returns the project
     * details for further modification.
     * </p>
     *
     * @param id                     Project id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/ModifyProject")  
    public ModelAndView ModifyProject(@RequestParam(Constants.ID) int id,Model model) {
        ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
        try {
            Project project = projectService.retrieveProjectById(id);
            if (null != project){
                modelAndView.addObject(Constants.MODIFY_REQUEST,Constants.YES);
                model.addAttribute(Constants.ATTR_PROJECT, project);
                modelAndView.addObject(Constants.COMMAND,model);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,Constants.PROJECT_NOT_FOUND);
                modelAndView.addObject(Constants.MODIFY_REQUEST,Constants.NO);
            }
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * ModifyProjectDetails accepts the project object and updates it in
     * the database.
     * </p>
     *
     * @param project                Project object
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/ModifyProjectDetails")  
    public ModelAndView ModifyProjectDetails(@ModelAttribute(Constants.ATTR_PROJECT) Project project){
        ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
        try {
            project.setEmployees(projectService.retrieveProjectById
                                            (project.getId()).getEmployees());
            if(projectService.modifyProject(project)) {
                modelAndView.addObject
                    (Constants.MESSAGE,Constants.PROJECT_MODIFICATION_SUCCCESS);
            } else {
                modelAndView.addObject
                 (Constants.FAIL_MESSAGE,Constants.PROJECT_MODIFICATION_FAILED);
            }
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * RestoreProject accepts the project object and restores it back
     * in the database.
     * </p>
     *
     * @param id                     Project id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/RestoreProject")  
    public ModelAndView RestoreProject(@RequestParam(Constants.ID) int id) {
        ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
        try {
            Project project = projectService.retrieveProjectById(id);
            project.setStatus(Constants.ACTIVE);
            if (projectService.modifyProject(project)){
                modelAndView.addObject(Constants.MESSAGE,
                                            Constants.RESTORE_SUCCESS + id);
                return dispatchResponse(modelAndView);
                } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,
                                                    Constants.RESTORE_FAIL);
                return dispatchResponse(modelAndView); 
                }
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * getUnassignedEmployees accepts the project id and returns the list 
     * of unassigned employees for the concern project id.
     * </p>
     *
     * @param id                     Project id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/AddEmployees")  
    public ModelAndView getUnassignedEmployees(@RequestParam(Constants.ID) int id) {
        ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
        try {
            Project project = projectService.retrieveProjectById(id);
            if(null != project) {
                modelAndView.addObject(Constants.EMPLOYEES,
                            projectService.getUnassignedEmployees(project));
                modelAndView.addObject(Constants.ADD_EMP,Constants.YES);
                modelAndView.addObject(Constants.ATTRIBUTE_PROJECT_ID,id);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,Constants.NO_PROJECT);
            }
            return dispatchResponse(modelAndView); 
        } catch(ApplicationException e) {  
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * removeEmployeeFromProject method is used to remove an employee from a
     * certain project.
     * </p>
     *
     * @param request                request from the user interface 
     * @param response               response to the user interface
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/RemoveEmployeeFromProject")  
    public ModelAndView RemoveEmployeeFromProject(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,IOException {
        ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
        try {
            int employeeId = Integer.valueOf
                        (request.getParameter(Constants.ATTRIBUTE_EMPLOYEE_ID));
            int projectId = Integer.valueOf(request.getParameter(Constants.ID));
            if(projectService.removeEmployeeFromProject(employeeId,projectId)) {
                modelAndView.addObject(Constants.MESSAGE,
                        Constants.EMPLOYEE_ID + employeeId + Constants.REMOVED);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,
                                                Constants.FAIL_OPERATION);
            }
            return dispatchResponse(modelAndView); 
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * ViewProject accepts the project id and returns the project
     * object to display it in the user interface.
     * </p>
     *
     * @param id                     Project id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/ViewProject")  
    public ModelAndView ViewProject(@RequestParam(Constants.ID) int id) {
        ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
        try {
            Project project = projectService.retrieveProjectById(id);
            if (null != project){
                modelAndView.addObject(Constants.ATTR_PROJECT,project);
                modelAndView.addObject(Constants.ATTR_VIEW,Constants.YES);
                modelAndView.addObject(Constants.EMPLOYEES, project.getEmployees());
                return dispatchResponse(modelAndView);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE, Constants.NO_PROJECT);
                return dispatchResponse(modelAndView);
            }
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * addEmployeesToProject method is used to add set of employees to project
     * </p>
     *
     * @param id                     Project id
     * @param request                request from the user interface 
     * @param response               response to the user interface
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/AddEmployeesToProject")  
    public ModelAndView AddEmployeesToProject(@RequestParam(Constants.ID) int id,
                HttpServletRequest request,HttpServletResponse response)
                                         throws ServletException,IOException{
        ModelAndView modelAndView = new ModelAndView(Constants.PROJECT_JSP_URL);
        try {
            String employeeIds[] = request.getParameterValues(Constants.SELECT);
            int projectId = Integer.valueOf(request.getParameter(Constants.ID));
            if(null == employeeIds) {
                modelAndView.addObject(Constants.FAIL_MESSAGE,
                                                Constants.NO_EMPLOYEE_SELECTED);
            } else {
                if(projectService.addEmployeesToProject(employeeIds,projectId)){
                    modelAndView.addObject(Constants.MESSAGE,
                                        Constants.EMPLOYEES_ASSIGNED+projectId);
                } else {
                    modelAndView.addObject(Constants.FAIL_MESSAGE,
                                            Constants.FAIL_OPERATION);
                }
            }
            return dispatchResponse(modelAndView); 
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * dispatchResponse is used to dispatch response back to the client side
     * which holds the entire project details in the view.
     *
     * @param ModelAndView            ModelAndView object
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                                application user.
     */ 
    public ModelAndView dispatchResponse(ModelAndView modelAndView) {
        try {
            List<Project> projects = 
                projectService.getProjectsByStatus(Constants.ALL);
            modelAndView.addObject(Constants.PROJECTS, projects);
            return modelAndView;
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }
}


