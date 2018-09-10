package com.ideas2it.application.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Transient; 

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.model.Address;
import com.ideas2it.application.model.Project;
import com.ideas2it.application.util.DateUtil;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * Employee class is a POJO class which has getters and setters for
 * all necessary employee details
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class Employee {
    private Integer id;
    private Date dateOfJoining;
    private Date dob;
    private Integer rating;
    private String name;
    private String email;
    private String designation;
    private String status;
    private Integer age;
    private Integer experience;
    @Transient
    private List<Address> listOfAddresses = new ArrayList<Address>(2);
    private Set<Address> addresses = new HashSet<Address>();
    private Set<Project> projects = new HashSet<Project>();
    
    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public String getDesignation() {
        return this.designation;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDob() {
        return this.dob;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDateOfJoining() {
        return this.dateOfJoining;
    }
    public String getStatus() {
        return this.status;
    }
    public Set<Project> getProjects() {
        return this.projects;
    }
    public List<Address> getListOfAddresses() {
        return this.listOfAddresses;
    }
    public Set<Address> getAddresses() {
        return this.addresses;
    }
    public Integer getRating() {
        return this.rating;
    }
    public Integer getExperience() {
        experience = DateUtil.getYearDifference
                            (DateUtil.convertDateToString(dateOfJoining));
        return experience;
    }
    public Integer getAge() {
        age = DateUtil.getYearDifference(DateUtil.convertDateToString(dob));
        return age;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
    public void setListOfAddresses(List<Address> listOfAddresses) {
        this.listOfAddresses = listOfAddresses;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
