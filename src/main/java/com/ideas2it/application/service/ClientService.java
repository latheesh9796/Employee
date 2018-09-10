package com.ideas2it.application.service;

import java.util.List;

import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.Client;
import com.ideas2it.application.model.Project;
/**
 * <p>
 * ClientService interface contains all method declarations
 * needed to perform business logics on client related informations
 * </p>
 *
 * @author Latheeshwar Raj
 */
public interface ClientService {

    /**
     * <p>
     * addClient method is used to add
     * the client to the database.
     * </p>
     *
     * @param client                 Client object
     * 
     * @return boolean               Indicates whether client is added in
     *                               database or not
     */
    public boolean addClient(Client client) throws ApplicationException;

    /**
     * <p>
     * getProjectById method is used to add
     * the retrieve the project for a given id.
     * </p>
     *
     * @param id                     Project Id
     * 
     * @return Project               Project object for concern id is returned
     */
    public Project getProjectById(int id) throws ApplicationException;

    /**
     * <p>
     * retrieveClientsByStatus method is used to retrieve
     * all clients from the database.
     * </p>
     *
     * @param status                 status indicates whether to retrieve
     *                               active/inactive clients
     *
     * @return List<Client>          List of clients
     */
    public List<Client> retrieveClientsByStatus(String status) 
                                                throws ApplicationException;

    /**
     * <p>
     * getProjects method is used to retrieve
     * all active projects from the database.
     * </p>
     *
     * @return List<Project>          List of projects
     */
    public List<Project> getProjects() throws ApplicationException;

    /**
     * <p>
     * getClientById method is used to add
     * the retrieve the client for a given id.
     * </p>
     *
     * @param id                     Client Id
     * 
     * @return Client                Client object for concern id is returned.
     */
    public Client getClientById(int id) throws ApplicationException;

    /**
     * <p>
     * modifyClient method is used to update
     * the modified client object in the database.
     * </p>
     *
     * @param client                 Client object
     * 
     * @return boolean               Indicates whether the client details are
     *                               modified or not
     */
    public boolean modifyClient(Client client) throws ApplicationException;

    /**
     * <p>
     * deleteClientById method is used to soft delete
     * the client from the database.
     * </p>
     *
     * @param client                 Client object
     * 
     * @return boolean               Indicates whether the client is deleted
     *                               or not
     */
    public boolean deleteClientById(int id) throws ApplicationException;

    /**
     * <p>
     * isProjectPresentInClient method is used to find
     * if a project is already present in a client or not.
     * </p>
     *
     * @param client                 Client object
     * @param projectId              Project Id
     *
     * @return boolean
     */
    public boolean isProjectPresentInClient(Client client, int projectId);

    /**
     * <p>
     * addProjectsToClient method is used to add a list of 
     * an projects given their id's in a string array to a client for a
     * given clientId
     *
     * @param clientId               Client Id
     * @param ids                    Array of Project Id's in string format
     *
     * @return boolean              
     * </p>
     */
    public boolean addProjectsToClient(String[] ids,int clientId)
                                             throws ApplicationException;

    /**
     * <p>
     * removeProjectFromClient method is used to remove a 
     * an project from a client given their respectives id.
     *
     * @param projectId              Project Id
     * @param clientId               Client Id     
     *
     * @return boolean              
     * </p>
     */
    public boolean removeProjectFromClient(int projectId,int clientId)
                                             throws ApplicationException;

    /**
     * <p>
     * getUnassignedProjects method is to get unassigned projects for client
     * </p>
     *
     * @param client                Client object
     *
     * @param List<Project>         List of unassigned projects      
     */
    public List<Project> getUnassignedProjects(Client client);
}
