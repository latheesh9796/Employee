package com.ideas2it.application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.time.LocalDate;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.model.Address;
import com.ideas2it.application.model.Employee;
import com.ideas2it.application.model.Project;
import com.ideas2it.application.service.EmployeeService;
import com.ideas2it.application.service.impl.EmployeeServiceImpl;
import com.ideas2it.application.util.DateUtil;

import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.ModelAttribute;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

/**
 * <p>
 * EmployeeManagement class acts as a user interface which interacts with
 * the user and perform operations with the employee details.
 * </p>
 *
 * @author Latheeshwar Raj
 */
@Controller
public class EmployeeManagement extends HttpServlet {
    private static EmployeeService employeeService;

    public  void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }

    @ModelAttribute("employee")
    public Employee createModel() {
        return new Employee();
    }

    /**
     * <p>
     * addEmployee method is to add the employee to the database.
     * </p>
     *
     * @param employee               Employee object
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping(value="/addEmployee" , method= RequestMethod.POST)   
    public ModelAndView addEmployee(@ModelAttribute("employee") Employee employee) 
                                    throws ServletException,IOException,ApplicationException {
        ModelAndView modelAndView = new ModelAndView(Constants.EMPLOYEE_JSP_URL);
        employee.getListOfAddresses().get(0).setType(Constants.PRESENT);
        employee.getListOfAddresses().get(1).setType(Constants.PERMANENT);
        employee.setAddresses(new HashSet<Address>(employee.getListOfAddresses()));
        if(employeeService.addEmployeeDetails(employee)) {
            modelAndView.addObject(Constants.MESSAGE,Constants.EMPLOYEE_SUCCESS_ADD);
        } else {
            modelAndView.addObject(Constants.FAIL_MESSAGE,Constants.EMPLOYEE_FAIL_ADD);
        }
        return dispatchResponse(modelAndView);
    }

    /**
     * <p>
     * ViewEmployeeDetails accepts the employee id and returns the employee
     * object to display it in the user interface.
     * </p>
     *
     * @param id                     Employee id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/ViewEmployeeDetails")  
    public ModelAndView ViewEmployeeDetails(@RequestParam(Constants.ID) int id){
        ModelAndView modelAndView = new ModelAndView(Constants.EMPLOYEE_JSP_URL);
        try {
            Employee employee = getEmployeeService().getEmployeeById(id);
            modelAndView.addObject(Constants.VIEW,Constants.YES);
            if (null != employee){
                modelAndView.addObject(Constants.DOJ,DateUtil.convertDateToString
                                            (employee.getDateOfJoining()));
                modelAndView.addObject(Constants.DOB,DateUtil.convertDateToString
                                            (employee.getDob()));
                modelAndView.addObject(Constants.EMPLOYEE,employee);
                employee.setListOfAddresses(new ArrayList<Address>(employee.getAddresses()));
                for(Address address : employee.getAddresses()) {
                    if(address.getType().equals(Constants.PERMANENT)) {
                        modelAndView.addObject(Constants.ATTR_PERMANENT,address);
                    } else if(address.getType().equals(Constants.PRESENT)){
                        modelAndView.addObject(Constants.ATTR_PRESENT,address);
                    }
                }
            } else {
                modelAndView.addObject
                        (Constants.FAIL_MESSAGE,Constants.EMPLOYEE_NOT_FOUND);
                modelAndView.addObject(Constants.MODIFY_REQUEST,Constants.NO);
            }
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * EditEmployeeDetails accepts the employee id and returns the employee
     * details along with date restrictions for user to modify details.
     * </p>
     *
     * @param id                     Employee id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/EditEmployeeDetails")  
    public ModelAndView EditEmployeeDetails(@RequestParam(Constants.ID) int id){
        ModelAndView modelAndView = new ModelAndView(Constants.EMPLOYEE_JSP_URL);
        try {
            Employee employee = getEmployeeService().getEmployeeById(id);
            modelAndView.addObject(Constants.MODIFY_REQUEST,Constants.YES);
            if (null != employee){
                modelAndView.addObject(Constants.MAX_DOB,DateUtil.getDateMinusYears(90));
                modelAndView.addObject(Constants.MIN_DOB,DateUtil.getDateMinusYears(18));
                modelAndView.addObject(Constants.DOJ,DateUtil.convertDateToString
                                            (employee.getDateOfJoining()));
                modelAndView.addObject(Constants.DOB,DateUtil.convertDateToString
                                            (employee.getDob()));
                employee.setListOfAddresses(new ArrayList<Address>(employee.getAddresses()));
                modelAndView.addObject(Constants.EMPLOYEE,employee);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,Constants.EMPLOYEE_NOT_FOUND);
                modelAndView.addObject(Constants.MODIFY_REQUEST,Constants.NO);
            }
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * DeleteEmployee accepts the employee id and soft deletes the
     * employee for the concern id.
     *
     * @param id                     Employee id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/DeleteEmployee")  
    public ModelAndView DeleteEmployee(@RequestParam(Constants.ID) int id) {
        ModelAndView modelAndView = new ModelAndView(Constants.EMPLOYEE_JSP_URL);
        try {
            Employee employee = new Employee();
            EmployeeService employeeService = new EmployeeServiceImpl();
                if (employeeService.deleteEmployeeById(id)){
                    modelAndView.addObject(Constants.MESSAGE,
                            Constants.EMPLOYEE_ID + id + Constants.REMOVED);
                    return dispatchResponse(modelAndView); 
                } else {
                    modelAndView.addObject(Constants.MESSAGE,
                       Constants.EMPLOYEE_ID + id + Constants.DELETE_FAILED);
                    return dispatchResponse(modelAndView); 
                }
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * dispatchResponse is used to dispatch response back to the client side
     * which holds the entire employee details in the view.
     *
     * @param ModelAndView           ModelAndView object
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    public ModelAndView dispatchResponse(ModelAndView modelAndView) {
        try {
            List<Employee> employees = 
                employeeService.retrieveEmployeesByStatus(Constants.ALL);
            modelAndView.addObject(Constants.EMPLOYEES, employees);
            return modelAndView;
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * getDateRestriction is used to return a empty employee object inorder
     * to populate itself inside the form and bind the values into it.
     *
     * @param Model                     Model object
     *
     * @return ModelAndView              Used for displaying the view for the 
     *                                  application user.
     */ 
    @RequestMapping("/getDateRestriction")  
    public ModelAndView getDateRestriction(Model model) {
        try {
            List<Employee> employees = 
                employeeService.retrieveEmployeesByStatus(Constants.ALL);   
            ModelAndView modelAndView = new ModelAndView(Constants.EMPLOYEE_JSP_URL);
            modelAndView.addObject(Constants.EMPLOYEES, employees);
            modelAndView.addObject(Constants.MAX_DOB,DateUtil.getDateMinusYears(90));
            modelAndView.addObject(Constants.MIN_DOB,DateUtil.getDateMinusYears(18));
            modelAndView.addObject
                   (Constants.MAX_DOJ,DateUtil.convertDateToString(new Date()));
            modelAndView.addObject(Constants.ADD_BUTTON,Constants.YES);
            Employee employee = new Employee();
            List<Address> listOfAddresses = new ArrayList<Address>();
            Address permanentAddress = new Address();
            permanentAddress.setType(Constants.PERMANENT);
            Address presentAddress = new Address();
            presentAddress.setType(Constants.PRESENT);
            listOfAddresses.add(permanentAddress);
            listOfAddresses.add(presentAddress); 
            employee.setListOfAddresses(listOfAddresses);
            model.addAttribute(Constants.EMPLOYEE, employee);
            modelAndView.addObject(Constants.COMMAND,model);
            return modelAndView;
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * RestoreEmployee is used to restore back the employee for the given 
     * employee id
     *
     * @param id                     Employee id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping("/RestoreEmployee")  
    public ModelAndView RestoreEmployee(@RequestParam(Constants.ID) int id) {
        ModelAndView modelAndView = new ModelAndView(Constants.EMPLOYEE_JSP_URL);
        try {
            EmployeeService employeeService = new EmployeeServiceImpl();
            Employee employee = employeeService.getEmployeeById(id);
            employee.setStatus(Constants.ACTIVE);
                if(employeeService.modifyEmployee(employee)){
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
     * modifyEmployee method is to modify the employee to the database.
     * </p>
     *
     * @param employee               Employee object
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping(value="/modifyEmployee" , method= RequestMethod.POST)   
    public ModelAndView modifyEmployee(@ModelAttribute("employee") Employee employee) {
        ModelAndView modelAndView = new ModelAndView(Constants.EMPLOYEE_JSP_URL);
        employee.setAddresses(new HashSet<Address>(employee.getListOfAddresses()));
        employee.setStatus(Constants.ACTIVE);
        employee.getListOfAddresses().get(0).setType(Constants.PRESENT);
        employee.getListOfAddresses().get(1).setType(Constants.PERMANENT);
        try{
            if(employeeService.modifyEmployee(employee)) {
                modelAndView.addObject(Constants.MESSAGE,"Modified");
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,"Failed");
            }
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }
    /**
     * <p>
     * displayEmployees is used to display all the employees present in database
     * irrespective of their status.
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping(value = "/employeePage",method = RequestMethod.GET)  
    public ModelAndView displayEmployees() throws ServletException,IOException{
        try {
            ModelAndView modelAndView = new ModelAndView(Constants.EMPLOYEE_JSP_URL);
            List<Employee> employees = 
                employeeService.retrieveEmployeesByStatus(Constants.ALL);
            modelAndView.addObject(Constants.EMPLOYEES, employees);
            return modelAndView;
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }
}
