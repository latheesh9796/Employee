package com.ideas2it.application.dao;

import java.util.List;

import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.model.Client;
import com.ideas2it.application.model.Project;

/**
 * <p>
 * ClientDAO interface contains all method declarations
 * for client database manipulation.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public interface ClientDAO {

    /**
     * <p>
     * insertClient method is used to add
     * the client to the database.
     * </p>
     *
     * @param client                 Client object
     *
     * @return boolean               Indicates whether client is successfully
     *                               added or not
     */
    public boolean insertClient(Client client) throws ApplicationException;

    /**
     * <p>
     * retrieveClients method is used to
     * retrieve all clients from the database.
     * </p>
     * @param status                 status indicates whether to retrieve
     *                               active/inactive clients
     *
     * @return List<Client>           List of clients are returned
     */
    public List<Client> retrieveClientsByStatus(String status) 
                                                    throws ApplicationException;

    /**
     * <p>
     * fetchClient method is used to
     * retrieve a specific client from the database.
     * </p>
     *
     * @param id                     Client id
     * 
     * @return Client                Client of the concern id is returned
     */
    public Client fetchClientById(int id) throws ApplicationException;

    /**
     * <p>
     * modifyClient method is used to update
     * the client from the database.
     * </p>
     *
     * @param client                 Client object
     * 
     * @return boolean               Indicates whether client is modified or
     *                               not
     */
    public boolean modifyClient(Client client) throws ApplicationException;

    /**
     * <p>
     * deleteClient method is used to delete
     * the client from the database.
     * </p>
     *
     * @param client                 Client object
     * 
     * @return boolean               Indicates whether client is deleted or
     *                               not
     */
    public boolean deleteClient(Client client) throws ApplicationException;
}
