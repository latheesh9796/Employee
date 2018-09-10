package com.ideas2it.application.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Transient; 

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.model.Address;
import com.ideas2it.application.model.Project;

/**
 * <p>
 * Client class is a POJO class which has getters and setters for
 * all necessary client details
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class Client {
    private Integer id;
    private String name;
    private String description;
    private Set<Project> projects = new HashSet<Project>();
    private String status;
    @Transient
    private List<Address> listOfAddresses = new ArrayList<Address>(2);
    private Set<Address> addresses = new HashSet<Address>();

    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public Set<Project> getProjects() {
        return this.projects;
    }
    public String getStatus() {
        return this.status;
    }
    public Set<Address> getAddresses() {
      return this.addresses;
    }
    public List<Address> getListOfAddresses() {
        return this.listOfAddresses;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
    public void setListOfAddresses(List<Address> listOfAddresses) {
        this.listOfAddresses = listOfAddresses;
    }
}
