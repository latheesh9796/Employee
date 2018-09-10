package com.ideas2it.application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.model.Address;
import com.ideas2it.application.model.Client;
import com.ideas2it.application.model.Project;
import com.ideas2it.application.service.ClientService;
import com.ideas2it.application.service.impl.ClientServiceImpl;

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
 * ClientManagement class acts as a user interface which interacts with
 * the user and perform operations with the Client details.
 * </p>
 *
 * @author Latheeshwar Raj
 */
@Controller
public class ClientManagement extends HttpServlet {

    private static ClientService clientService;

    public void init() throws ServletException {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
    public ClientService getClientService() {
        return this.clientService;
    }

    /**
     * <p>
     * displayClients is used to display all the clients present in database
     * irrespective of their status.
     *
     * @param ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping(value = "/clientPage",method = RequestMethod.GET)  
    public ModelAndView displayClients(Model model) {
        try {
            ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
            Client client = new Client();
            List<Address> listOfAddresses = new ArrayList<Address>();
            Address permanentAddress = new Address();
            permanentAddress.setType(Constants.PERMANENT);
            Address presentAddress = new Address();
            presentAddress.setType(Constants.PRESENT);
            listOfAddresses.add(permanentAddress);
            listOfAddresses.add(presentAddress); 
            client.setListOfAddresses(listOfAddresses);
            List<Client> clients = 
                clientService.retrieveClientsByStatus(Constants.ALL);
            modelAndView.addObject(Constants.CLIENTS, clients);
            model.addAttribute(Constants.CLIENT,client);
            modelAndView.addObject(Constants.COMMAND,model);
            return modelAndView;
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * RestoreClient accepts the client id and restores the client back in the
     * database.
     * </p>
     *
     * @param id                      Client id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                                application user.
     */ 
    @RequestMapping("/RestoreClient")  
    public ModelAndView RestoreClient(@RequestParam(Constants.ID) int id) throws ServletException,IOException{
        ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
        try {
            Client client = clientService.getClientById(id);
            client.setStatus(Constants.ACTIVE);
                if(clientService.modifyClient(client)){
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
     * createClient is used to return a empty client object inorder
     * to populate itself inside the form and bind the values into it.
     *
     * @param model                   Model object
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                                application user.


     */ 
    @RequestMapping("/createClient")
    public ModelAndView createClient(Model model) throws ServletException,IOException{
        try {
            ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
            modelAndView.addObject(Constants.ADD_BUTTON,Constants.YES);
            Client client = new Client();
            List<Address> listOfAddresses = new ArrayList<Address>();
            Address permanentAddress = new Address();
            permanentAddress.setType(Constants.PERMANENT);
            Address presentAddress = new Address();
            presentAddress.setType(Constants.PRESENT);
            listOfAddresses.add(permanentAddress);
            listOfAddresses.add(presentAddress); 
            client.setListOfAddresses(listOfAddresses);
            List<Client> clients = 
                clientService.retrieveClientsByStatus(Constants.ALL);
            modelAndView.addObject(Constants.CLIENTS, clients);
            model.addAttribute(Constants.CLIENT,client);
            modelAndView.addObject(Constants.COMMAND,model);
            return modelAndView;
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * <p>
     * addClient is used to add the client object to the database.
     *
     * @param client                   Client object
     *
     * @return ModelAndView            Used for displaying the view for the 
     *                                 application user.
     */ 
    @RequestMapping(value="/addClient" , method= RequestMethod.POST)   
    public ModelAndView addClient(@ModelAttribute(Constants.CLIENT) Client client) {
        try {
            ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
            client.getListOfAddresses().get(0).setType(Constants.PRESENT);
            client.getListOfAddresses().get(1).setType(Constants.PERMANENT);
            client.setAddresses(new HashSet<Address>(client.getListOfAddresses()));
            if(clientService.addClient(client)) {
                modelAndView.addObject(Constants.MESSAGE, Constants.CLIENT_CREATED);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE, Constants.CLIENT_CREATION_FAILED);
            }
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * dispatchResponse is used to dispatch response back to the client side
     * which holds the entire client details in the view.
     *
     * @param ModelAndView            ModelAndView object
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                                application user.
     */ 
    public ModelAndView dispatchResponse(ModelAndView modelAndView) throws
                        ApplicationException {
        try {
            List<Client> clients = 
                clientService.retrieveClientsByStatus(Constants.ALL);
            modelAndView.addObject(Constants.CLIENTS, clients);
            return modelAndView;
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * ModifyClient accepts the client id and gets the concern client object and
     * sends it to the user for further modification.
     * </p>
     *
     * @param id                      Client id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                                application user.
     */ 
    @RequestMapping("/ModifyClient")  
    public ModelAndView ModifyClient(@RequestParam(Constants.ID) int id) throws ServletException,IOException{
        ModelAndView modelAndView = new ModelAndView(Constants.MODIFY_CLIENT_URL);
        try {
            Client client = clientService.getClientById(id);
            if (null != client){
                modelAndView.addObject(Constants.MODIFY_REQUEST,Constants.YES);
                client.setListOfAddresses(new ArrayList<Address>(client.getAddresses()));
                modelAndView.addObject(Constants.CLIENT, client);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,Constants.CLIENT_NOT_FOUND);
                modelAndView.addObject(Constants.MODIFY_REQUEST,Constants.NO);
            }
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * updateClient method is to modify the client to the database.
     * </p>
     *
     * @param client                  Client object
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                               application user.
     */ 
    @RequestMapping(value="/updateClient" , method= RequestMethod.POST)   
    public ModelAndView updateClient(@ModelAttribute(Constants.CLIENT) Client client) {
        ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
        client.setAddresses(new HashSet<Address>(client.getListOfAddresses()));
        client.setStatus(Constants.ACTIVE);
        try{
            client.setProjects(clientService.getClientById(client.getId()).getProjects());
            if(clientService.modifyClient(client)) {
                modelAndView.addObject(Constants.MESSAGE,
                                Constants.CLIENT_ID+client.getId()+
                                                Constants.MODIFICATION_SUCCESS);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,Constants.MODIFY_FAILED);
            }
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * DeleteClient accepts the client id and performs soft delete over the
     * concern client.
     * </p>
     *
     * @param id                      Client id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                                application user.
     */ 
    @RequestMapping("/DeleteClient")  
    public ModelAndView DeleteClient(@RequestParam(Constants.ID) int id) throws ServletException,IOException{
        ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
        try {
            if (clientService.deleteClientById(id)){
                modelAndView.addObject(Constants.MESSAGE,
                                Constants.CLIENT_ID + id + Constants.REMOVED);
                return dispatchResponse(modelAndView);
            } else {
                modelAndView.addObject(Constants.FAIL_MESSAGE,
                                            Constants.CLIENT_DELETION_FAILED);
                return dispatchResponse(modelAndView);
            }
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * removeProjectFromClient method is used to remove a project from a
     * certain client.
     * </p>
     *
     * @param request                request from the user interface 
     * @param response               response to the user interface
     */ 
   @RequestMapping("/RemoveProjectFromClient")
    public ModelAndView removeProjectFromClient(HttpServletRequest request,
             HttpServletResponse response)throws ServletException, IOException{
        ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
        try {
            int clientId = Integer.valueOf
                        (request.getParameter(Constants.ATTRIBUTE_CLIENT_ID));
            int projectId = Integer.valueOf
                        (request.getParameter(Constants.ATTRIBUTE_PROJECT_ID));
            if(clientService.removeProjectFromClient(projectId,clientId)) {
                modelAndView.addObject(Constants.MESSAGE,
                        Constants.PROJECT_ID + projectId + Constants.REMOVED);
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
     * ViewClient accepts the client id and sends the concern client object
     * to the user interface for viewing the details
     * </p>
     *
     * @param id                      Client id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                                application user.
     */
   @RequestMapping("/ViewClient")  
    public ModelAndView ViewClient(@RequestParam(Constants.ID) int id) throws ServletException,IOException{
        ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
        try {
            Client client = clientService.getClientById(id);
            modelAndView.addObject(Constants.CLIENT, client);
            modelAndView.addObject(Constants.ATTR_VIEW, Constants.YES);
            modelAndView.addObject(Constants.PROJECTS, client.getProjects());
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * getUnassignedProjects accepts the client id and returns the list 
     * of unassigned projects available for assigning.
     * </p>
     *
     * @param id                      Client id
     *
     * @return ModelAndView           Used for displaying the view for the 
     *                                application user.
     */ 
    @RequestMapping("/addProjects") 
    public ModelAndView getUnassignedProjects(@RequestParam(Constants.ID) int id) {
        ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
        try {
            Client client = clientService.getClientById(id);
            modelAndView.addObject(Constants.PROJECTS, 
                                  clientService.getUnassignedProjects(client));
            modelAndView.addObject(Constants.PROJECT_ADD,Constants.YES);
            modelAndView.addObject(Constants.ATTRIBUTE_CLIENT_ID, id);
            return dispatchResponse(modelAndView);
        } catch(ApplicationException e) {
            return new ModelAndView(Constants.ERROR_JSP_URL,Constants.FAIL_MESSAGE,e);
        }
    }

    /**
     * <p>
     * addProjectsToClient method is used to add a project to certain client.
     * </p>
     *
     * @param request                request from the user interface 
     * @param response               response to the user interface
     */ 
    @RequestMapping("/addProjectsToClient") 
    public ModelAndView addProjectsToClient(HttpServletRequest request,
             HttpServletResponse response)throws ServletException, IOException{
        ModelAndView modelAndView = new ModelAndView(Constants.CLIENT_JSP_URL);
        try {
            String projectIds[] = request.getParameterValues(Constants.SELECT);
            int clientId = Integer.valueOf(request.getParameter(Constants.ID));
            ClientService clientService = new ClientServiceImpl();
            if(null == projectIds) {
                modelAndView.addObject(Constants.FAIL_MESSAGE,
                                        Constants.NO_PROJECT_SELECTED);
            } else {
                if(clientService.addProjectsToClient(projectIds,clientId)) {
                    modelAndView.addObject(Constants.MESSAGE,
                                          Constants.PROJECTS_ASSIGNED+clientId);
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
}

