package com.ideas2it.application.model;

import java.util.HashSet;
import java.util.Set;

import com.ideas2it.application.model.Client;
import com.ideas2it.application.model.Employee;
import com.ideas2it.application.common.Constants;

/**
 * <p>
 * Project class is a POJO class which has getters and setters for
 * all necessary project details
 * </p>
 *
 * @author Latheeshwar Raj
 */

public class Project {
    private Integer id;
    private String name;
    private Integer clientId;
    private String status;
    private Set<Employee> employees = new HashSet<Employee>();

    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public Integer getClientId() {
        return this.clientId;
    }
    public Set<Employee> getEmployees() {
        return this.employees;
    }
    public String getStatus() {
        return this.status;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;    
    }
    public void setClientId(Integer clientId) { 
        this.clientId = clientId;
    }
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;    
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
