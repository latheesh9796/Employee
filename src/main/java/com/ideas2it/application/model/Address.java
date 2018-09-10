package com.ideas2it.application.model;

/**
 * <p>
 * Address class is a POJO class which has getters and setters for
 * all necessary address details
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class Address {
    private Integer id;
    private String address;
    private String state;
    private String country;
    private Integer employeeId;
    private Integer clientId;
    private String type;

    public Address() {
    }
    public Address(String address, String state,
                    String country) {
        this.address = address;
        this.state = state;
        this.country = country;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getId() {
        return this.id;
    }
    public String getAddress() {
        return this.address;
    }
    public String getState() {
        return this.state;
    }
    public String getCountry() {
        return this.country;
    }
    public String getType() {
        return this.type;
    }
    public Integer getClientId() {
        return this.clientId;
    }
    public Integer getEmployeeId() {
        return this.employeeId;
    }
}
