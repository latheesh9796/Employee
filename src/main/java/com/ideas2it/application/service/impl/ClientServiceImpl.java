package com.ideas2it.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.dao.ClientDAO;
import com.ideas2it.application.dao.impl.ClientDAOImpl;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.Client;
import com.ideas2it.application.model.Project;
import com.ideas2it.application.service.ClientService;
import com.ideas2it.application.service.ProjectService;
import com.ideas2it.application.service.impl.ProjectServiceImpl;

/**
 * <p>
 * ClientServiceImpl class performs the operations such as removing and adding
 * projects to a particular client,getting unassigned projects and other
 * business logics.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class ClientServiceImpl implements ClientService {
    private static ClientDAO clientDAO;
    private static ProjectService projectService;

    public  void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }
    public ClientDAO getClientDAO() {
        return this.clientDAO;
    }

    public  void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    public ProjectService getProjectService() {
        return this.projectService;
    }

    /**
     * {@inheritDoc}
     */
    public boolean addClient(Client client) throws ApplicationException {
        client.setStatus(Constants.ACTIVE);
        return clientDAO.insertClient(client);
    }

   /**
    * {@inheritDoc}
    */
    public Project getProjectById(int projectId) throws ApplicationException {
        return projectService.retrieveProjectById(projectId);
    }

   /**
    * {@inheritDoc}
    */
    public List<Project> getProjects() throws ApplicationException {
        return projectService.getProjectsByStatus(Constants.ACTIVE);
    }

   /**
    * {@inheritDoc}
    */
    public List<Client> retrieveClientsByStatus(String status) 
                                                throws ApplicationException {
        return clientDAO.retrieveClientsByStatus(status);
    }

   /**
    * {@inheritDoc}
    */
    public Client getClientById(int id) throws ApplicationException {
        return clientDAO.fetchClientById(id);
    }

   /**
    * {@inheritDoc}
    */
    public boolean modifyClient(Client client) throws ApplicationException {
        return clientDAO.modifyClient(client);
    }

   /**
    * {@inheritDoc}
    */
    public boolean deleteClientById(int id) throws ApplicationException {
        Client client = new Client();
        client = getClientById(id);
        if (null != client) {
            client.setStatus(Constants.INACTIVE);
            client.getProjects().clear();
            return clientDAO.deleteClient(client);
        } else {
            return false;
        }
    }

   /**
    * {@inheritDoc}
    */
    public boolean isProjectPresentInClient(Client client, int projectId) {
        for (Project project : client.getProjects()) {
            if (project.getId() == projectId) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * {@inheritDoc}
     */
    public boolean addProjectsToClient(String[] ids,int clientId)
                                             throws ApplicationException {
        Client client = getClientById(clientId);
        for(String id : ids) {
            client.getProjects().add(getProjectById(Integer.parseInt(id)));
        }
        if(modifyClient(client)){
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeProjectFromClient(int projectId,int clientId)
                                             throws ApplicationException {
        Project project = null;
        Client client = getClientById(clientId);
        if(null == client) {
            return Boolean.FALSE;
        } else {
            for (Project clientProject : client.getProjects()) {
                if (projectId == clientProject.getId()) {
                    project = clientProject;
                    break;
                }
            }
            client.getProjects().remove(project);
        }
        if(modifyClient(client)){
            return Boolean.TRUE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Project> getUnassignedProjects(Client client){
        try {
            List<Project> projects = new ArrayList<Project>();
            for(Project project : getProjects()) {
                if(null == project.getClientId() || project.getClientId() == 0){
                    projects.add(project);
                }
            } 
            return projects;
        } catch (ApplicationException e) {
            return null;
        }
    }
}
